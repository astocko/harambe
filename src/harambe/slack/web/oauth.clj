(ns
 harambe.slack.web.oauth
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 access
 "Exchanges a temporary OAuth code for an API token."
 [client_id client_secret code & {:keys [redirect_uri]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove
      (comp nil? second)
      {:client_id client_id,
       :client_secret client_secret,
       :code code,
       :redirect_uri redirect_uri})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/oauth.access"
      {:form-params query}))]
   (json/parse-string res true))))
