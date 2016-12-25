(ns app.messages
  (:require [feathers.app :as feathers]
            [feathers.memory :as memory]
            [feathers.hooks :as hooks]))

(def pre-hooks {:all    []
                :find   []
                :get    []
                :create []
                :update []
                :patch  []
                :remove []})

(def post-hooks {:all    []
                 :find   []
                 :get    []
                 :create []
                 :update []
                 :patch  []
                 :remove []})

(def hooks {:before pre-hooks :after post-hooks})

(defn messages [app]
  (feathers/api app "messages" (memory/memory) hooks))
