(ns talks-game-backend-clojure.server
  "All about Pedestal web server"
  (:require
    [io.pedestal.http :as http]
    [com.walmartlabs.lacinia.pedestal :as lacinia]
    [talks-game-backend-clojure.graphql :as graphql]))

(def schema (graphql/load-schema))

(def service (lacinia/service-map schema {:graphiql true}))

(defonce runnable-service (http/create-server service))

(defn start
  "Start Pedestal web server"
  []
  (println "Starting server...")
  (http/start runnable-service))