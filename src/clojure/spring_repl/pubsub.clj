(ns spring-repl.pubsub
  "Convenience wrappers for core.async pub/sub."
  (:require [clojure.core.async :refer [<! put! pub sub chan <! >! timeout close! go go-loop]]))

(def mk-ch
  "A memoized fn for creating named channels"
  (memoize (fn [_] (chan))))

(def evt-publication
  (pub (mk-ch :evt-pub) :topic))

(defn pub-evt [topic evt]
  (go
    (>! (mk-ch :evt-pub) {:topic topic :evt evt})))

(defn sub-evt [topic ch-name]
  {:pre [(every? keyword? [topic ch-name])]}
  (let [out-ch (mk-ch ch-name)]
    (sub evt-publication topic out-ch)
    out-ch))
