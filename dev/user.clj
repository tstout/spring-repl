(ns user)

(prn "---REPL Customizations Initialized---")

(defn load-vars []
  (require
    '[clojure.string :as str]
    '[clojure.java.data :as jd]
    '[spring-repl.bootstrap :as bs])
  (import '(spring.repl Channel Topic LogMessage InterceptEvent)))


(load-vars)
