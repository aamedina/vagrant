(ns {{name}}.core
  (:require [cljs.core.async :as a :refer [<! >! put! take! chan]]
            [om.core :as om :include-macros true]
            [sablono.core :as html :refer [html] :include-macros true])
  (:require-macros [cljs.core.async.macros :as a :refer [go go-loop]]))

(defn content
  [data owner]
  (reify om/IRenderState
    (render-state [this state]
      (html [:h1 "Hello, world!"]))))

(defn ^:export -main []
  (enable-console-print!)
  (om/root content {} {:target (.getElementById js/document "content")}))
