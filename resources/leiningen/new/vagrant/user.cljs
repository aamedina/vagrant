(ns cljs.user
  (:require [weasel.repl :as repl]))

(repl/connect "ws://192.168.33.10:9001")
