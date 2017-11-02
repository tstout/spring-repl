(ns spring-repl.bootstrap
  (:require [clojure.core.async :refer [<! go go-loop]]
            [spring-repl.pubsub :refer [sub-evt]]
            [spring-repl.loggers :refer [info-listener error-listener main-listener]]))



(defn boot []
  (error-listener)
  (info-listener)
  (main-listener))