(ns harambe.slack.rtm.core
  (:require [cheshire.core :as json]))

(require '[clojure.core.async :as async])
(require '[harambe.slack.web.chat :as chat])
(require '[harambe.slack.web.rtm :as rtm])
(require '[gniazdo.core :as ws])


(def socket (atom nil))

(defn on-event [event]
  (let [ev (json/parse-string event true)]
    (prn ev)))

(defn connect []
  (let [info (rtm/start)]
    (if (= (:ok info) true)
      (reset! socket (ws/connect (:url info)
                                 :on-receive #(on-event %)
                                 :on-close #(prn %1 %2)))
      (throw (Exception. (:error info))))))

(defn disconnect []
  (ws/close (deref socket)))

(defn- parse-msg-ch [ch]
  )

(defn connect2 []
  (let [callback-ch (async/chan)
        ws-pub (async/pub callback-ch :type)
        inc-msg-ch (async/sub ws-pub :on-receive (async/chan))
        ev-pub (async/pub (parse-msg-ch inc-msg-ch)
                          #(or (:type %) (if-not (:ok %) "error")))
        ]))
