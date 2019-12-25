(ns talks-game-backend-clojure.schema
  "Contains custom resolvers and a function to provide the full schema."
  (:require
    [clojure.java.io :as io]
    [com.walmartlabs.lacinia.util :as util]
    [com.walmartlabs.lacinia.schema :as schema]
    [clojure.edn :as edn])
  (:import (java.util UUID)))

(defn resolve-auth
  [context args value]
  {:token (.toString (UUID/randomUUID))})

(defn resolver-map
  []
  {:query/try-auth resolve-auth})

(defn load-schema
  []
  (-> (io/resource "talks-game-backend-clojure-schema.edn")
      slurp
      edn/read-string
      (util/attach-resolvers (resolver-map))
      schema/compile))