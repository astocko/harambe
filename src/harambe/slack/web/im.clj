(ns
 harambe.slack.web.im
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 close
 "Close a direct message channel."
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
      "https://slack.com/api/im.close"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 history
 "Fetches history of messages and events from direct message channel."
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
      "https://slack.com/api/im.history"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 list
 "Lists direct message channels for the calling user."
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
      "https://slack.com/api/im.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 mark
 "Sets the read cursor in a direct message channel."
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
      "https://slack.com/api/im.mark"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 open
 "Opens a direct message channel."
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
      "https://slack.com/api/im.open"
      {:form-params query}))]
   (json/parse-string res true))))
