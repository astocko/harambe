(ns
 harambe.slack.web.chat
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 delete
 "Deletes a message."
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
      "https://slack.com/api/chat.delete"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 post-message
 "Sends a message to a channel."
 [channel
  text
  &
  {:keys
   [as_user
    attachments
    icon_emoji
    icon_url
    link_names
    parse
    unfurl_links
    unfurl_media
    username]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:unfurl_links unfurl_links,
       :attachments attachments,
       :username username,
       :as_user as_user,
       :channel channel,
       :icon_emoji icon_emoji,
       :link_names link_names,
       :parse parse,
       :unfurl_media unfurl_media,
       :icon_url icon_url,
       :text text})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/chat.postMessage"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 update
 "Updates a message."
 [channel text ts & {:keys [as_user attachments link_names parse]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:channel channel,
       :text text,
       :ts ts,
       :as_user as_user,
       :attachments attachments,
       :link_names link_names,
       :parse parse})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/chat.update"
      {:form-params query}))]
   (json/parse-string res true))))
