(ns spring-repl.consumers
  (:require [clojure.core.async :refer [<! go go-loop]]
            [spring-repl.pubsub :refer [sub-evt]]
            [spring-repl.context :refer [stash-context]]
            [clojure.tools.logging :as log]
            [clojure.pprint :refer [pprint]]))

(defn info-listener
  "Listen for informational events"
  []
  (let [ch (sub-evt :info-log :info-ch)]
    (go-loop
     []
      (let [info (<! ch)]
        (log/info (-> info :evt :message)))
      (recur))))

(defn error-listener
  "Listen for error events"
  []
  (let [ch (sub-evt :error-log :err-ch)]
    (go-loop
     []
      (let [info (<! ch)]
        (log/error info)
        (recur)))))

(defn main-listener
  "Listen for main events, such as spring context creation"
  []
  (let [ch (sub-evt :main-bus :main-ch)]
    (go-loop
     []
      (let [info (<! ch)]
        (log/info "main bus event %s" (with-out-str (pprint info)))
        (stash-context (get-in info [:evt :app-context]))
        (recur)))))