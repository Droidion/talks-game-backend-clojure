(ns talks-game-backend-clojure.schema
  "Contains custom resolvers and a function to provide the full schema."
  (:require
    [clojure.java.io :as io]
    [com.walmartlabs.lacinia.util :as util]
    [com.walmartlabs.lacinia.schema :as schema]
    [clojure.edn :as edn])
  (:import (java.util UUID)))

(defn uuid-random-string
  "Generate a random UUID string for using as a session token"
  []
  (.toString (UUID/randomUUID)))

(defn resolve-auth
  "Process auth Graphql request"
  [context args value]
  {:token (uuid-random-string)})

(defn resolver-map
  "Map GraphQL models resolvers to Clojure functions"
  []
  {:query/try-auth resolve-auth})

(defn load-schema
  "Load GraphQL schema"
  []
  (-> (io/resource "talks-game-backend-clojure-schema.edn")
      slurp
      edn/read-string
      (util/attach-resolvers (resolver-map))
      schema/compile))