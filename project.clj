(defproject com.github.tstout/spring-repl "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.3.443"]
                 [org.clojure/java.data "0.1.1"]
                 [net.bytebuddy/byte-buddy "1.7.5"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [com.cemerick/pomegranate "1.0.0"]]
  :min-lein-version "2.0.0"
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :profiles {:uberjar {:aot :all}
             :dev     {:source-paths ["dev" "src/java"]
                       :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                      [org.clojure/java.classpath "0.2.3"]]}}
  :manifest {"Premain-Class" "spring.repl.ReplAgent"
             "Agent-Class"   "spring.repl.ReplAgent"
             "Main-Class"    "spring.repl.ReplAgent"})
