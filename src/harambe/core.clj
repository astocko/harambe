(ns harambe.core
  (:gen-class))

(require '[harambe.slack.state :as state])
(require '[harambe.slack.web.api :as api])
(require '[harambe.slack.web.chat :as chat])
(require '[harambe.slack.rtm.core :as rtm])
(require '[gniazdo.core :as ws])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(state/init)
;; (def info (rtm/start))

;; (def socket (ws/connect
;;              (:url info)
;;              :on-receive #(prn 'received %)
;;              :on-close #(prn %1 %2)))




