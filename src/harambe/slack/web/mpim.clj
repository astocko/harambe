(ns
 harambe.slack.web.mpim
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 close
 "Closes a multiparty direct message channel."
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
      "https://slack.com/api/mpim.close"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 history
 "Fetches history of messages and events from a multiparty direct message."
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
      "https://slack.com/api/mpim.history"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 list
 "Lists multiparty direct message channels for the calling user."
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
      "https://slack.com/api/mpim.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 mark
 "Sets the read cursor in a multiparty direct message channel."
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
      "https://slack.com/api/mpim.mark"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 open
 "This method opens a multiparty direct message."
 [users & {:keys []}]
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
      "https://slack.com/api/mpim.open"
      {:form-params query}))]
   (json/parse-string res true))))
