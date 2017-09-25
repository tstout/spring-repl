(ns spring-repl.loggers
  (:require [clojure.core.async :refer [<! go go-loop]]
            [spring-repl.pubsub :refer [sub-evt]]
            [clojure.tools.logging :as log]))

(defn info-listener []
  "Listen for informational events"
  (let [ch (sub-evt :info-log :info-ch)]
    (go-loop
      []
      (let [info (<! ch)]
        (log/info (-> info :evt :message)))
      (recur))))

(defn error-listener []
  "Listen for error events"
  (let [ch (sub-evt :error-log :info-ch)]
    (go-loop
      []
      (let [info (<! ch)]
        (log/error (-> info :evt :message)))
      (recur))))