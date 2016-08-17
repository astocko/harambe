(ns
 harambe.slack.web.channels
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 archive
 "Archives a channel."
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
      "https://slack.com/api/channels.archive"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 create
 "Creates a channel."
 [name & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:name name})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/channels.create"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 history
 "Fetches history of messages and events from a channel."
 [channel & {:keys [count inclusive latest oldest unreads]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:channel channel,
       :count count,
       :inclusive inclusive,
       :latest latest,
       :oldest oldest,
       :unreads unreads})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/channels.history"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 info
 "Gets information about a channel."
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
      "https://slack.com/api/channels.info"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 invite
 "Invites a user to a channel."
 [channel user & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:channel channel, :user user})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/channels.invite"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 join
 "Joins a channel, creating it if needed."
 [name & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:name name})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/channels.join"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 kick
 "Removes a user from a channel."
 [channel user & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:channel channel, :user user})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/channels.kick"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 leave
 "Leaves a channel."
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
      "https://slack.com/api/channels.leave"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 list
 "Lists all channels in a Slack team."
 [& {:keys [exclude_archived]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:exclude_archived exclude_archived})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/channels.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 mark
 "Sets the read cursor in a channel."
 [channel ts & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:channel channel, :ts ts})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/channels.mark"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 rename
 "Renames a channel."
 [channel name & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:channel channel, :name name})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/channels.rename"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 set-purpose
 "Sets the purpose for a channel."
 [channel purpose & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:channel channel, :purpose purpose})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/channels.setPurpose"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 set-topic
 "Sets the topic for a channel."
 [channel topic & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:channel channel, :topic topic})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/channels.setTopic"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 unarchive
 "Unarchives a channel."
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
      "https://slack.com/api/channels.unarchive"
      {:form-params query}))]
   (json/parse-string res true))))
