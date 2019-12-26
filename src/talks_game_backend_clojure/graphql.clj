(ns talks-game-backend-clojure.graphql
  "Control point for working with GraphQL using Lacinia"
  (:require
    [com.walmartlabs.lacinia.util :as util]
    [com.walmartlabs.lacinia.schema :as schema]
    [talks-game-backend-clojure.graphql.auth :as graphql-auth]
    [talks-game-backend-clojure.utils :as utils]))

(defn resolver-map
  "Map GraphQL models resolvers to Clojure functions"
  []
  {:query/try-auth graphql-auth/resolve-auth})

(defn load-schema
  "Load GraphQL schema from edn file"
  []
  (-> "graphql-schema"
      utils/load-edn
      (util/attach-resolvers (resolver-map))
      schema/compile))