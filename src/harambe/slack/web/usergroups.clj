(ns
 harambe.slack.web.usergroups
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 create
 "Create a user group"
 [name & {:keys [channels description handle include_count]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:name name,
       :channels channels,
       :description description,
       :handle handle,
       :include_count include_count})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/usergroups.create"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 disable
 "Disable an existing user group"
 [usergroup & {:keys [include_count]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:usergroup usergroup, :include_count include_count})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/usergroups.disable"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 enable
 "Enable a user group"
 [usergroup & {:keys [include_count]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:usergroup usergroup, :include_count include_count})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/usergroups.enable"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 list
 "List all user groups for a team"
 [& {:keys [include_count include_disabled include_users]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:include_count include_count,
       :include_disabled include_disabled,
       :include_users include_users})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/usergroups.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 update
 "Update an existing user group"
 [usergroup & {:keys [channels description handle include_count name]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:usergroup usergroup,
       :channels channels,
       :description description,
       :handle handle,
       :include_count include_count,
       :name name})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/usergroups.update"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 users-list
 "List all users in a user group"
 [usergroup & {:keys [include_disabled]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:usergroup usergroup, :include_disabled include_disabled})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/usergroups.users.list"
      {:form-params query}))]
   (json/parse-string res true))))

(defn
 users-update
 "Update the list of users for a user group"
 [usergroup users & {:keys [include_count]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:usergroup usergroup,
       :users users,
       :include_count include_count})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/usergroups.users.update"
      {:form-params query}))]
   (json/parse-string res true))))
