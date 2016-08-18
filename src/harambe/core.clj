(ns harambe.core
  (:gen-class))

(require '[harambe.slack.state :as state])
(require '[harambe.slack.rtm.core :as rtm])
(require '[harambe.slack.web.api :as api])
(require '[harambe.slack.web.chat :as chat])
(require '[gniazdo.core :as ws])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn on-close [conn config status])

(state/init)

(def rtm-info (atom nil))

(defn disconnect []
  (rtm/send-event (:dispatcher @rtm-info) :close))

(reset! rtm-info (rtm/connect
                :on-close (fn [{:keys [status reason]}] (prn status reason))))

