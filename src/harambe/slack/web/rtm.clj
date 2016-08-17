(ns
 harambe.slack.web.rtm
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 start
 "Starts a Real Time Messaging session."
 [& {:keys [mpim_aware no_unreads simple_latest]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:mpim_aware mpim_aware,
       :no_unreads no_unreads,
       :simple_latest simple_latest})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/rtm.start"
      {:form-params query}))]
   (json/parse-string res true))))
