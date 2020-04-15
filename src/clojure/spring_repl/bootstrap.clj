(ns spring-repl.bootstrap
  (:require [spring-repl.nrepl :refer [start-repl]]
            [spring-repl.consumers :refer [info-listener
                                           error-listener
                                           main-listener]]))

(defn boot []
  (error-listener)
  (info-listener)
  (main-listener)
  (start-repl))