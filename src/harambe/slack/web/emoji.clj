(ns
 harambe.slack.web.emoji
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 list
 "Lists custom emoji for a team."
 [& {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/emoji.list"
      {:form-params query}))]
   (json/parse-string res true))))
