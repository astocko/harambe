(ns
 harambe.slack.web.files
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 comments-add
 "Add a comment to an existing file."
 [comment file & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:comment comment, :file file})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/files.comments.add"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 comments-delete
 "Deletes an existing comment on a file."
 [file id & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:file file, :id id})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/files.comments.delete"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 comments-edit
 "Edit an existing file comment."
 [comment file id & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:comment comment, :file file, :id id})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/files.comments.edit"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 delete
 "Deletes a file."
 [file & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:file file})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/files.delete"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 info
 "Gets information about a team file."
 [file & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:file file})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/files.info"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 list
 "Lists & filters team files."
 [& {:keys [channel ts_from ts_to types user]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:channel channel,
       :ts_from ts_from,
       :ts_to ts_to,
       :types types,
       :user user})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/files.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 revoke-public-url
 "Revokes public/external sharing access for a file"
 [file & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:file file})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/files.revokePublicURL"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 shared-public-url
 "Enables a file for public/external sharing."
 [file & {:keys []}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:file file})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/files.sharedPublicURL"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 upload
 "Uploads or creates a file."
 [file
  filename
  &
  {:keys [channels content filetype initial_comment title]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:file file,
       :filename filename,
       :channels channels,
       :content content,
       :filetype filetype,
       :initial_comment initial_comment,
       :title title})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/files.upload"
      {:form-params query}))]
   (json/parse-string res true))))
