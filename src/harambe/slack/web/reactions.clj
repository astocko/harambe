(ns
 harambe.slack.web.reactions
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 add
 "Adds a reaction to an item."
 [name & {:keys [channel file file_comment timestamp]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:name name,
       :channel channel,
       :file file,
       :file_comment file_comment,
       :timestamp timestamp})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/reactions.add"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 get
 "Gets reactions for an item."
 [& {:keys [channel file file_comment full timestamp]}]
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
       :full full,
       :timestamp timestamp})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/reactions.get"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 list
 "Lists reactions made by a user."
 [& {:keys [full user]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:full full, :user user})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/reactions.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 remove
 "Removes a reaction from an item."
 [name & {:keys [channel file file_comment timestamp]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:name name,
       :channel channel,
       :file file,
       :file_comment file_comment,
       :timestamp timestamp})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/reactions.remove"
      {:form-params query}))]
   (json/parse-string res true))))
