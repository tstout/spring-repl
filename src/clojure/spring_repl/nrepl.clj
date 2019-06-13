(ns spring-repl.nrepl
  (:require
    [nrepl.server :refer [start-server stop-server]]
    [clojure.tools.logging :as log]))

(def repl-server (atom nil))

(defn start-repl []
  (log/info "Starting nrepl server")
  (reset! repl-server (start-server :port 8000 :bind "0.0.0.0")))
