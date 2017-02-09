(ns app.services
  (:require [feathers.app :as feathers]
            [feathers.memory :as memory]
            [feathers.hooks :as hooks]
            [feathers.authentication :as auth]))

(def auth (:authenticate auth/hooks))
(def hashpass (:hashPassword auth/hooks))

(def msg-pre-hooks {:all    []
                    :find   [(auth. #js["jwt" "local"])]
                    :get    [(auth. #js["jwt" "local"])]
                    :create []
                    :update []
                    :patch  []
                    :remove []})

(def msg-post-hooks {:all    []
                     :find   []
                     :get    []
                     :create []
                     :update []
                     :patch  []
                     :remove []})

(def usr-pre-hooks {:all    []
                    :find   [(auth. #js["jwt" "local"])]
                    :get    [(auth. #js["jwt" "local"])]
                    :create [(hashpass.)]
                    :update []
                    :patch  []
                    :remove []})

(def usr-post-hooks {:all    []
                     :find   []
                     :get    []
                     :create []
                     :update []
                     :patch  []
                     :remove []})

(def msg-hooks {:before msg-pre-hooks :after msg-post-hooks})

(def usr-hooks {:before usr-pre-hooks :after usr-post-hooks})

(defn messages [app]
  (feathers/api app "messages" (memory/memory) msg-hooks))

(defn gravatar [hook]
  (let [new-hook (js->clj hook)]
    (prn new-hook)
    hook))

(defn users [app]
  (feathers/api app "users" (memory/memory) usr-hooks))
