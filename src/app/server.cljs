(ns app.server
  (:require [feathers.app :as feathers]
            [app.messages :as messages]
            [app.users :as users]))

(enable-console-print!)

(def app (feathers/feathers!))

(def public (str js/__dirname "/../../"))

(feathers/configuration app public)

(-> app
    feathers/compress
    feathers/cors
    (feathers/favicon (str public "favicon.png"))
    (feathers/static public)
    feathers/body-parser
    feathers/hooks
    feathers/rest
    feathers/socketio
    users/users
    feathers/authentication
    messages/messages)

(defn -main []
  (feathers/listen app "8080"))

(defn init []
  (set! *main-cli-fn* -main))
