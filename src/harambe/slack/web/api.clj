(ns
 harambe.slack.web.api
 (:gen-class)
 (:require
  [cheshire.core :as json]
  [clj-http.client :as client]
  [harambe.slack.state :refer [slack-api-token]]))

(defn
 test
 "Checks API calling code."
 [& {:keys [error foo]}]
 (let
  [query
   (into
    {}
    (concat
     [[:token slack-api-token]]
     (remove (comp nil? second) {:error error, :foo foo})))]
  (let
   [res
    (:body
     (client/post
      "https://slack.com/api/api.test"
      {:form-params query}))]
   (json/parse-string res true))))
