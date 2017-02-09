(ns app.actions
  (:require [feathers.client.services :as svc]
            [app.client :as client]))

(defn mk-message [text]
  (svc/create client/messages #js{:text text}))

(defn get-message [& [config]]
  (svc/find client/messages (or config {})))

(defn listen-messages [cell]
  (svc/created client/messages #(swap! cell conj (js->clj % :keywordize-keys true))))

(defn get-user [& [config]]
  (svc/find client/users (or config {})))

(defn listen-users [cell]
  (svc/created client/users #(swap! cell conj (js->clj % :keywordize-keys true))))
