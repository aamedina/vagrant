(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description ""
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"sonatype-oss-public"
                 "https://oss.sonatype.org/content/groups/public/"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]
                 [org.clojure/core.async "0.1.303.0-886421-alpha"]
                 [om "0.6.4"]
                 [sablono "0.2.18"]
                 [cljs-http "0.1.14"]]
  :jvm-opts ^:replace ["-Xmx512m" "-server"]
  :source-paths ["src/clj"]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]
                                  [weasel "0.3.0"]]
                   :plugins [[com.cemerick/austin "0.1.5-SNAPSHOT"]]
                   :source-paths ["dev"]}
             :uberjar {:aot :all}}
  :repl-options {:host "0.0.0.0" :port 4041}
  :plugins [[lein-cljsbuild "1.0.3"]]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src/cljs" "dev"]
                :compiler {:output-to "resources/public/js/main.js"
                           :output-dir "resources/public/js/out"
                           :source-map true
                           :optimizations :none}}
               {:id "prod"
                :source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/main.js"
                           :pretty-print false
                           :preamble ["react/react.min.js"]
                           :externs ["react/externs/react.js"]
                           :optimizations :advanced}}]})
