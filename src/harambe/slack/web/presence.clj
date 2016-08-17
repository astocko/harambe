(ns
 harambe.slack.web.presence
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 set
 "Manually set user presence"
 [presence & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:presence presence})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/presence.set"
      {:form-params query}))]
   (json/parse-string res true))))
