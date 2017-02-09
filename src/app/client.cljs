(ns app.client
  (:require [cljsjs.socket-io]
            [goog.object :as obj]
            [feathers.client :as feathers]
            [feathers.client.services :as svc]))

;; Feathers Client ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def app (feathers/feathers))

(-> app
    (feathers/socketio (js/io))
    (feathers/hooks)
    (feathers/authentication #js{:storage (obj/get js/window "localStorage")}))

(def users (feathers/service app "users"))

(def messages (feathers/service app "messages"))

;; Helper Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- verifyToken [res]
  (let [passport (obj/get app "passport")
        token (:accessToken (js->clj res :keywordize-keys true))]
        (.verifyJWT passport token)))

(defn- decodePayload [payload]
  (let [uid (:userId (js->clj payload :keywordize-keys true))
        user (-> app (feathers/service "users") (svc/get uid))]
    user))

(defn- setUser [user]
  (let [udat (js->clj user :keywordize-keys true)]
    (obj/set app "user" user)
    user))

;; Client Auth API ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn signup! [email password]
  (svc/create users #js{:email email :password password}))

(defn login! [email password]
  (-> app
    (feathers/authenticate (clj->js {:strategy "local" :email email :password password}))
    (.then verifyToken)
    (.then decodePayload)
    (.then setUser)))

(defn logout! []
  (feathers/logout app))

(defn auth! []
  (-> app
    (feathers/authenticate)
    (.then verifyToken)
    (.then decodePayload)
    (.then setUser)
    (.catch #(.error js/console (obj/get % "message")))
    ))
