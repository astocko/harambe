(ns
 harambe.slack.web.users
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 get-presence
 "Gets user presence information."
 [user & {:keys []}]
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
      "https://slack.com/api/users.getPresence"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 info
 "Gets information about a user."
 [user & {:keys []}]
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
      "https://slack.com/api/users.info"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 list
 "Lists all users in a Slack team."
 [& {:keys [presence]}]
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
      "https://slack.com/api/users.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 set-active
 "Marks a user as active."
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
      "https://slack.com/api/users.setActive"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 set-presence
 "Manually sets user presence."
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
      "https://slack.com/api/users.setPresence"
      {:form-params query}))]
   (json/parse-string res true))))
