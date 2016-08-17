(ns
 harambe.slack.web.dnd
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 end-dnd
 "Ends the current user's Do Not Disturb session immediately."
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
      "https://slack.com/api/dnd.endDnd"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 end-snooze
 "Ends the current user's snooze mode immediately."
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
      "https://slack.com/api/dnd.endSnooze"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 info
 "Retrieves a user's current Do Not Disturb status."
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
      "https://slack.com/api/dnd.info"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 set-snooze
 "Turns on Do Not Disturb mode for the current user, or changes its duration."
 [num_minutes & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:num_minutes num_minutes})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/dnd.setSnooze"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 team-info
 "Retrieves the Do Not Disturb status for users on a team."
 [& {:keys [users]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:users users})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/dnd.teamInfo"
      {:form-params query}))]
   (json/parse-string res true))))
