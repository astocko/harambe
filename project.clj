(defproject harambe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.385"]
                 [cheshire "5.6.3"]
                 [clj-http "2.2.0"]
                 [environ "1.1.0"]
                 [stylefruits/gniazdo "1.0.0"]
                 [mount "0.1.10"]
                 ]
  :main ^:skip-aot harambe.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-exec "0.3.6"]]
  :aliases {"gen-api" ["exec" "scripts/generator.clj"]}
  )
