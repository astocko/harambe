(ns harambe.slack.state)

(require '[environ.core :refer [env]])
(require '[mount.core :as mount])
(require '[mount.core :refer [defstate]])

(def token (env :slack-api-token))

(defstate slack-api-token :start token)

(defn init []
  (mount/start))
