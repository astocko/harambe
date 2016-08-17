(ns
 harambe.slack.web.stars
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 add
 "Adds a star to an item."
 [& {:keys [channel file file_comment timestamp]}]
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
      "https://slack.com/api/stars.add"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 list
 "Lists stars for a user."
 [& {:keys [user]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:user user})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/stars.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 remove
 "Removes a star from an item."
 [& {:keys [channel file file_comment timestamp]}]
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
      "https://slack.com/api/stars.remove"
      {:form-params query}))]
   (json/parse-string res true))))
