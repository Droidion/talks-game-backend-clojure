(ns talks-game-backend-clojure.redis
  "Things to work with Redis"
  (:require
    [config.core :refer [env]]
    [taoensso.carmine :as car :refer (wcar)]))

(def redis-url
  (env :redis))

(def server1-conn {:pool {} :spec {:uri redis-url}})
(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))