(defproject ascii-clock "0.0.1-SNAPSHOT"
  :description "Cool new project to do things and stuff"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main "ascii-clock.core"
  :profiles {:dev {:dependencies [[midje "1.8.3"]]
                    :plugins [[lein-midje "3.2"]]}})
