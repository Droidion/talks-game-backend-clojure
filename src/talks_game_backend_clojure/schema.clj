(ns talks-game-backend-clojure.schema
  "Contains custom resolvers and a function to provide the full schema."
  (:require
    [clojure.java.io :as io]
    [com.walmartlabs.lacinia.util :as util]
    [com.walmartlabs.lacinia.schema :as schema]
    [com.walmartlabs.lacinia.resolve :as resolve]
    [clojure.edn :as edn])
  (:import (java.util UUID)
           (de.mkammerer.argon2 Argon2Factory Argon2Factory$Argon2Types)))

(defn uuid-random-string
  "Generate a random UUID string for using as a session token"
  []
  (.toString (UUID/randomUUID)))

(def argon2 (Argon2Factory/create))

(defn- argon2-hash
  "Hash string with Argon2id"
  [str-to-be-hashed]
  (.hash argon2 10 65536 1 str-to-be-hashed))

(defn- argon2-verify
  "verify argon2-hash(plaintext_str) == hashed_str"
  [hashed-str plaintext-str]
  (.verify argon2 hashed-str plaintext-str))

(defn load-profiles
  "Load profiles"
  []
  (-> (io/resource "profiles.edn")
      slurp
      edn/read-string))

(def profiles (atom (load-profiles)))

(defn find-profile-by-login
  [login profiles]
  (filter #(= (:login %) login) profiles))

(defn auth-response-ok
  [{:keys [team-number team-type] }]
  (resolve/resolve-as {:token (uuid-random-string)
                       :team_number team-number
                       :team_type team-type
                       :controller false}))

(defn auth-response-wrong-password
  [{:keys [team-number team-type] }]
  (resolve/resolve-as {:token nil
                       :team_number team-number
                       :team_type team-type
                       :controller nil}
                      {:message "Incorrect password"}))

(defn auth-response-not-found
  []
  (resolve/resolve-as {:token nil
                       :team_number nil
                       :team_type nil
                       :controller nil}
                      {:message "Profile not found"}))

(defn resolve-auth
  "Process auth Graphql request"
  [_ args _]
  (if-let [found-profiles (seq (find-profile-by-login (:login args) @profiles))]
    (if (argon2-verify (:password (first found-profiles)) (:password args))
      (auth-response-ok (first found-profiles))
      (auth-response-wrong-password (first found-profiles)))
    (auth-response-not-found))
  )

(defn resolver-map
  "Map GraphQL models resolvers to Clojure functions"
  []
  {:query/try-auth resolve-auth})

(defn load-schema
  "Load GraphQL schema"
  []
  (-> (io/resource "graphql-schema.edn")
      slurp
      edn/read-string
      (util/attach-resolvers (resolver-map))
      schema/compile))