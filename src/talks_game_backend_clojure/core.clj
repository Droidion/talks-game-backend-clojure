(ns talks-game-backend-clojure.core
  "App entry point"
  (:gen-class)
  (:require
    [talks-game-backend-clojure.server :as server])
  )

(defn -main
  "Start the app"
  [& args]
  (server/start))

