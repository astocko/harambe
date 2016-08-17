(ns
 harambe.slack.web.pins
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 add
 "Pins an item to a channel."
 [channel & {:keys [file file_comment timestamp]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:channel channel,
       :file file,
       :file_comment file_comment,
       :timestamp timestamp})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/pins.add"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 list
 "Lists items pinned to a channel."
 [channel & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:channel channel})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/pins.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 remove
 "Un-pins an item from a channel."
 [channel & {:keys [file file_comment timestamp]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:channel channel,
       :file file,
       :file_comment file_comment,
       :timestamp timestamp})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/pins.remove"
      {:form-params query}))]
   (json/parse-string res true))))
