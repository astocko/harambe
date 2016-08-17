(ns
 harambe.slack.web.search
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 all
 "Searches for messages and files matching a query."
 [query & {:keys [highlight sort sort_dir]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:query query,
       :highlight highlight,
       :sort sort,
       :sort_dir sort_dir})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/search.all"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 files
 "Searches for files matching a query."
 [query & {:keys [highlight sort sort_dir]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:query query,
       :highlight highlight,
       :sort sort,
       :sort_dir sort_dir})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/search.files"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 messages
 "Searches for messages matching a query."
 [query & {:keys [highlight sort sort_dir]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:query query,
       :highlight highlight,
       :sort sort,
       :sort_dir sort_dir})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/search.messages"
      {:form-params query}))]
   (json/parse-string res true))))
