(ns app.actions
  (:require [meta.client :as client]))

(defn mk-message [text]
  (client/create client/messages #js{:text text}))

(defn get-message [& [config]]
  (client/find client/messages (or config {})))

(defn listen-messages [cell]
  (client/created client/messages #(swap! cell conj (js->clj % :keywordize-keys true))))

(defn get-user [& [config]]
  (client/find client/users (or config {})))

(defn listen-users [cell]
  (client/created client/users #(swap! cell conj (js->clj % :keywordize-keys true))))
