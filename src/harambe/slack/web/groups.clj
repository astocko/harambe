(ns
 harambe.slack.web.groups
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 archive
 "Archives a private channel."
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
      "https://slack.com/api/groups.archive"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 close
 "Closes a private channel."
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
      "https://slack.com/api/groups.close"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 create
 "Creates a private channel."
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
      "https://slack.com/api/groups.create"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 create-child
 "Clones and archives a private channel."
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
      "https://slack.com/api/groups.createChild"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 history
 "Fetches history of messages and events from a private channel."
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
      "https://slack.com/api/groups.history"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 info
 "Gets information about a private channel."
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
      "https://slack.com/api/groups.info"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 invite
 "Invites a user to a private channel."
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
      "https://slack.com/api/groups.invite"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 kick
 "Removes a user from a private channel."
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
      "https://slack.com/api/groups.kick"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 leave
 "Leaves a private channel."
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
      "https://slack.com/api/groups.leave"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 list
 "Lists private channels that the calling user has access to."
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
      "https://slack.com/api/groups.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 mark
 "Sets the read cursor in a private channel."
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
      "https://slack.com/api/groups.mark"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 open
 "Opens a private channel."
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
      "https://slack.com/api/groups.open"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 rename
 "Renames a private channel."
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
      "https://slack.com/api/groups.rename"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 set-purpose
 "Sets the purpose for a private channel."
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
      "https://slack.com/api/groups.setPurpose"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 set-topic
 "Sets the topic for a private channel."
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
      "https://slack.com/api/groups.setTopic"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 unarchive
 "Unarchives a private channel."
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
      "https://slack.com/api/groups.unarchive"
      {:form-params query}))]
   (json/parse-string res true))))
