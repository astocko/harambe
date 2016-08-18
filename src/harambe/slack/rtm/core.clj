(ns harambe.slack.rtm.core
  (:gen-class)
  (:require [cheshire.core :as json]))

(require '[clojure.core.async
           :as async
           :refer [>! <! chan close! go go-loop pub sub unsub]])
(require '[clojure.test])
(require '[harambe.slack.web.chat :as chat])
(require '[harambe.slack.web.rtm :as rtm])
(require '[gniazdo.core :as ws])
(require '[robert.bruce :refer [try-try-again] :as rb])

;; parts taken/adapted from https://github.com/casidiablo/slack-rtm

(defn- create-rtm []
  (let [info (rtm/start)]
    (if (= (:ok info) true)
      info
      (throw (Exception. (:error info))))))


(defn- loop-inf
  ([f] (loop-inf f (chan)))
  ([f ch]
   (go-loop []
     (when-let [val (<! ch)]
       (do (try (f val)
                (catch Exception e
                  (.printStackTrace e)
                  (println "Failed to call fn on " val (.getMessage e))))
           (recur))))
   ch))

(defn- parse-msgs-ch [ch]
  (async/map
   #(json/parse-string (:message %) true)
   [ch]))

(defn- spinner [client]
  (let [dispatcher-ch (chan)]
    (loop-inf
     #(if (= :close %)
        (do
          (close! dispatcher-ch)
          (ws/close client))
        (ws/send-msg client (json/generate-string %)))
     dispatcher-ch)))

(defn- reconnect [cb-ch msg]
  (if (not= (:reason msg) "Shutdown")
    (try-try-again
     {:decay 1.1 :sleep 5000 :tries 500}
     (fn []
       (let [url (:url (create-rtm))]
         (ws-connect url cb-ch))))
    (go (>! cb-ch msg))))

(defn- ws-connect [url cb-ch]
  (ws/connect
   url
   :on-connect #(go (>! cb-ch {:type :on-connect :session %}))
   :on-receive #(go (>! cb-ch {:type :on-receive :message %}))
   :on-binary #(go (>! cb-ch {:type :on-binary :payload %1 :offset %2 :len %3}))
   :on-close #(reconnect cb-ch {:type :on-close :status-code %1 :reason %2})
   :on-error #(go (>! cb-ch {:type :on-error :throwable %}))))

(defn- apply-if
  [x p f]
  (if (p x) (f x) x))

(declare sub-to-event)
(defn- sub-initial
  [ws-pub ev-pub init-subs]
  (let [ws-topics [:on-connect :on-receive :on-binary :on-close :on-error]
        ws-subs (select-keys init-subs ws-topics)
        non-ws-topics (remove (set ws-topics) (keys init-subs))
        non-ws-subs (select-keys init-subs non-ws-topics)]
    (doall (for [[topic subscriber] ws-subs]
             (let [subscriber (apply-if subscriber clojure.test/function? loop-inf)]
               (sub ws-pub topic subscriber))))
    (doall (for [[topic subscriber] non-ws-subs]
                 (sub-to-event ev-pub topic subscriber)))))

(defn send-event
  [dispatcher event]
  (if (keyword? event)
    (go (>! dispatcher event))
    (go (>! dispatcher
            (update-in event [:id] #(or % (rand-int Integer/MAX_VALUE)))))))

(defn sub-to-event
  ([publication type]
   (sub-to-event publication type (chan)))
  ([publication type ch-or-fn]
   (let [
         type (apply-if type keyword? name)
         taker (apply-if ch-or-fn clojure.test/function? loop-inf)]
     (sub publication type taker)
     taker)))

(defn unsub-from-event
  [publication type ch]
  (unsub publication type ch))

(defn connect [& {:as init-subs}]
  (let [
        start (create-rtm)
        cb-ch (chan)
        ws-pub (pub cb-ch :type)
        in-msg-ch (sub ws-pub :on-receive (chan))
        ev-pub (pub (parse-msgs-ch in-msg-ch)
                    #(or (:type %) (if-not (:ok %) "error")))
        _ (sub-initial ws-pub ev-pub init-subs)
        dispatcher (-> start :url (ws-connect cb-ch) spinner)]
    {:start start
     :ws-pub ws-pub
     :ev-pub ev-pub
     :dispatcher dispatcher}))
