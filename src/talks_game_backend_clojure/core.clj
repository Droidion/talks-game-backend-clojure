(ns talks-game-backend-clojure.core
  (:gen-class)
  (:require
    [io.pedestal.http :as http]
    [com.walmartlabs.lacinia.pedestal :as lacinia]
    [talks-game-backend-clojure.schema :as s])
  )

(def schema (s/load-schema))

(def service (lacinia/service-map schema {:graphiql true}))

(defonce runnable-service (http/create-server service))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Starting server...")
  (http/start runnable-service))

(-main)