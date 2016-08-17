(ns
 harambe.slack.web.team
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 access-logs
 "Gets the access logs for the current team."
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
      "https://slack.com/api/team.accessLogs"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 info
 "Gets information about the current team."
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
      "https://slack.com/api/team.info"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 integration-logs
 "Gets the integration logs for the current team."
 [& {:keys [app_id change_type service_id user]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:app_id app_id,
       :change_type change_type,
       :service_id service_id,
       :user user})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/team.integrationLogs"
      {:form-params query}))]
   (json/parse-string res true))))
