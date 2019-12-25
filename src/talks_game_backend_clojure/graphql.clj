(ns talks-game-backend-clojure.graphql
  "Control point for working with GraphQL using Lacinia"
  (:require
    [clojure.java.io :as io]
    [com.walmartlabs.lacinia.util :as util]
    [com.walmartlabs.lacinia.schema :as schema]
    [clojure.edn :as edn]
    [talks-game-backend-clojure.graphql.auth :as graphql-auth]))

(defn resolver-map
  "Map GraphQL models resolvers to Clojure functions"
  []
  {:query/try-auth graphql-auth/resolve-auth})

(defn load-schema
  "Load GraphQL schema from edn file"
  []
  (-> (io/resource "graphql-schema.edn")
      slurp
      edn/read-string
      (util/attach-resolvers (resolver-map))
      schema/compile))