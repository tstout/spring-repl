(ns spring-repl.context
  (:require [clojure.tools.logging :as log]
            [clojure.reflect :as r]
            [clojure.pprint :refer [pprint]]))

(def context (atom {}))

(defn stash-context [c]
  (log/info "spring-repl: context initialized")
  (swap! context assoc :spring-ctx c))

(defn get-ctx []
  (@context :spring-ctx))

(defn beans
  "A sorted list of all bean names currently defined in the spring context"
  []
  (->
    (get-ctx)
    (.getBeanDefinitionNames)
    seq
    sort))

(defn ls-beans
  "list all bean names in the context"
  []
  (pprint (beans)))

(defn bean-named
  "Find bean by name"
  [name]
  (->
    (get-ctx)
    (.getBean name)))

(defn bean-info
  "Verbose bean information obtained from clojure.reflect"
  [name]
  (->
    name
    bean-named
    r/reflect))

(defn bean-methods
  "Filter the verbose clojure.reflect to only include method information"
  [name]
  (->>
    name
    bean-info
    :members))