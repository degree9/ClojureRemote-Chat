(ns app.users
  (:require [goog.object :as obj]
            [feathers.app :as feathers]
            [feathers.memory :as memory]
            [feathers.authentication :as auth]
            [feathers.hooks :as hooks]))

;(def restricted-hooks
;  [(.verifyToken auth/hooks)
;   (.populateUser auth/hooks)
;   (.restrictToAuthenticated auth/hooks)])

;(def owner-restricted-hooks
;  (conj restricted-hooks
;    [(.restrictToOwner auth/hooks #js {:ownerField "_id"})]))

(defn gravatar [hook]
  (let [new-hook (js->clj hook)]
    (prn new-hook)
    hook))

(def pre-hooks {:all    []
                ;:find   restricted-hooks
                ;:get    restricted-hooks
                ;:create [(.hashPassword auth/hooks) gravatar]
                ;:update owner-restricted-hooks
                ;:patch  owner-restricted-hooks
                ;:remove owner-restricted-hooks
                })

(def post-hooks {:all    []
                 :find   []
                 :get    []
                 :create []
                 :update []
                 :patch  []
                 :remove []})

(def hooks {:before pre-hooks :after post-hooks})

(defn users [app]
  (feathers/api app "users" (memory/memory) hooks))
