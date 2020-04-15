(defproject com.github.tstout/spring-repl "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/core.async "0.7.559"]
                 [org.clojure/java.data "0.1.1"]
                 [net.bytebuddy/byte-buddy "1.10.7"]
                 [nrepl "0.6.0"]]
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
