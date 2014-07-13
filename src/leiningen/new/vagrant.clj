(ns leiningen.new.vagrant
  (:require [leiningen.new.templates :refer [renderer year project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment sanitize
                                             render-text slurp-resource]]
            [leiningen.core.main :as main]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(def render (renderer "vagrant"))

(defn vagrant
  [name]
  (let [main-ns (multi-segment (sanitize-ns name))
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)
              :year (year)}]
    (main/info "Generating a project called" name
               "based on the 'vagrant' template.")
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ["LICENSE" (render "LICENSE" data)]
             [".gitignore" (render "gitignore" data)]             
             ["doc/intro.md" (render "intro.md" data)]
             ["test/{{nested-dirs}}_test.clj" (render "test.clj" data)]
             
             ["src/clj/{{nested-dirs}}.clj" (render "core.clj" data)]
             ["src/cljs/{{nested-dirs}}.cljs" (render "core.cljs" data)]

             ["dev/user.clj" (render "user.clj" data)]
             ["dev/user.cljs" (render "user.cljs" data)]
             
             ["Vagrantfile" (render "Vagrantfile" data)]
             [".puppet/manifests/default.pp" (render "default.pp" data)]
             ".puppet/modules"

             ["resources/setup.sh" (render "setup.sh" data)]
             
             ["resources/public/index.html" (render "index.html" data)]
             ["resources/public/404.html" (render "404.html" data)]
             "resources/public/js"
             "resources/public/css")))
