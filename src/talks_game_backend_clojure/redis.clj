(ns talks-game-backend-clojure.redis
  "Things to work with Redis"
  (:require
    [config.core :refer [env]]
    [taoensso.carmine :as car :refer (wcar)]))

(def redis-url
  (if-let [url (env :redis)]
    url
    ((throw (Exception. "Could not load REDIS environment variable."))
     (System/exit 0))))

(def server1-conn {:pool {} :spec {:uri redis-url}})
(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))