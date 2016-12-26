(set-env!
  :dependencies   '[[org.clojure/clojure      "1.8.0"]
                   [org.clojure/clojurescript "1.9.293"]
                   [adzerk/boot-cljs          "1.7.228-2" :scope "test"]
                   [adzerk/bootlaces          "0.1.13" :scope "test"]
                   [cljsjs/socket-io          "1.6.0-0"]
                   [degree9/featherscript     "0.2.0-SNAPSHOT"]
                   [degree9/boot-semver       "1.3.6" :scope "test"]
                   [degree9/boot-nodejs       "0.1.0-SNAPSHOT" :scope "test"]
                   [hoplon/hoplon             "6.0.0-alpha17"]
                   [hoplon/brew               "0.2.0-SNAPSHOT"]]
  :asset-paths #{"assets"}
  :source-paths #{"src"}
  :resource-paths #{"resources"})

(require
 '[adzerk.bootlaces       :refer :all]
 '[adzerk.boot-cljs       :refer :all]
 '[degree9.boot-semver    :refer :all]
 '[degree9.boot-nodejs    :refer :all]
 '[feathers.boot-feathers :refer :all]
 '[hoplon.boot-hoplon     :refer :all])

(task-options!
  cljs     {:source-map       true
            :optimizations    :none
            :compiler-options {:pseudo-names   true
                               :pretty-print   true
                               :language-in    :ecmascript5
                               :language-out   :ecmascript6
                               :parallel-build true}}
  hoplon   {:pretty-print     true}
  target   {:dir              #{"target"}})

(deftask develop
  "Build ClojureRemote-Chat for local development."
  []
  (comp
    (feathers)
    (watch)
    (hoplon)
    (nodejs :init-fn 'app.server/init)
    (cljs)
    (target)
    (serve)
    (speak)))

(deftask heroku
  "Build ClojureRemote-Chat for Heroku."
  []
  (comp
    (feathers)
    (hoplon)
    (nodejs :init-fn 'app.server/init)
    (cljs)
    (target)))
