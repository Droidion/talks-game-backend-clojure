(defproject talks-game-backend-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.walmartlabs/lacinia "0.34.0"]
                 [io.pedestal/pedestal.service "0.5.7"]
                 [io.pedestal/pedestal.jetty "0.5.7"]
                 [ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.26"]
                 [org.slf4j/jcl-over-slf4j "1.7.26"]
                 [org.slf4j/log4j-over-slf4j "1.7.26"]
                 [com.walmartlabs/lacinia-pedestal "0.12.0"]
                 [de.mkammerer/argon2-jvm "2.6"]
                 [com.taoensso/carmine "2.19.1"]
                 [yogthos/config "1.1.7"]
                 ]
  :jvm-opts ["-Dconfig=dev-config.edn"]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  :main ^:skip-aot talks-game-backend-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
