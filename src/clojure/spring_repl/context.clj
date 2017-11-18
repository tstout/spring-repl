(ns spring-repl.context
  (:require [clojure.tools.logging :as log]
            [clojure.reflect :as r])
  (:import (org.springframework.context ApplicationContext)))

(def context (atom {}))

(defn stash-context [c]
  (log/info "spring-repl: context initialized")
  (swap! context assoc :spring-ctx c))

(defn ^ApplicationContext get-ctx []
  (@context :spring-ctx))

(defn beans []
  (->
    get-ctx
    (.getBeanDefinitionNames)
    seq))

(defn get-bean [bean-name]
  (->
    (get-ctx)
    (.getBean bean-name)))

(defn bean-info [bean-name]
  (->
    bean-name
    get-bean
    r/reflect))

(defn bean-methods [bean-name]
  (->>
    bean-name
    bean-info
    (filter #(:name))))