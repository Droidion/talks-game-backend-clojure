(ns talks-game-backend-clojure.utils
  "Various utility functions"
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(defn load-edn
  "Try to load and parse edn file"
  [filename]
  (if-let [edn (io/resource (str filename ".edn"))]
    (-> edn
        slurp
        edn/read-string)
    ((throw (Exception. (str "Could not load file resources/" filename ".edn")))
     (System/exit 0))))