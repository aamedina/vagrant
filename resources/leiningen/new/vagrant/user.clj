(ns user
  (:require
   [cemerick.austin]
   [cemerick.austin.repls :as repls]
   [clojure.java.io :as io]
   [clojure.tools.namespace.repl :refer [refresh-all]]
   [weasel.repl.websocket :as repl]))

(def system nil)

(defn init
  []
  (alter-var-root #'system
                  (fn [system]
                    (let [repl-env (repl/repl-env :ip "0.0.0.0" :port 9001)]
                      (assoc system
                        :repl-env (reset! repls/browser-repl-env repl-env))))))

(defn start
  []
  (repls/cljs-repl (:repl-env system) :optimizations :none))

(defn stop
  []
  :stopped)

(defn go
  []
  (init)
  (start)
  :ready)

(defn reset
  []
  (stop)
  (refresh-all :after 'user/go))
