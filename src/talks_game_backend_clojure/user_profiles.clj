(ns talks-game-backend-clojure.user-profiles
  "All about user profiles"
  (:require
    [clojure.java.io :as io]
    [clojure.edn :as edn]
    [talks-game-backend-clojure.redis :as redis]
    [taoensso.carmine :as car :refer (wcar)]))

(defn load-profiles
  "Load profiles from edn file"
  []
  (-> (io/resource "profiles.edn")
      slurp
      edn/read-string))

(def profiles (atom (load-profiles)))

(defn find-profile-by-login
  "Find all profiles with a given login string"
  [login profiles]
  (filter #(= (:login %) login) profiles))

(defn add-session-to-redis
  [session]
  (redis/wcar* (car/rpush "sessions" session)))