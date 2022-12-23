(defproject com.github.tstout/spring-repl "1.0.0"

  :scm {:name "git" :url "https://github.com/nrepl/piggieback"}
  :description "java agent for injecting a clojure repl"
  :url "https://github.com/tstout/spring-repl"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :pom-addition [:developers [:developer {:id "tstout"}
                              [:name "Todd Stout"]
                              [:url "https://github.com/tstout"]]]
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
             "Main-Class"    "spring.repl.ReplAgent"}

  :plugins [[lein-javadoc "0.3.0"]]

  :javadoc-opts {:package-names ["spring.repl"]
                 :output-dir "target/javadoc/out"
                 :additional-args ["-windowtitle" "Spring REPL"
                                   "-quiet"
                                   "-Xdoclint:none"
                                   "-link" "https://docs.oracle.com/javase/8/docs/api/"
                                   "-link" "https://www.javadoc.io/static/org.clojure/clojure/1.10.1"]}

  :classifiers {:sources {:prep-tasks ^:replace []}
                :javadoc {:prep-tasks ^:replace ["javadoc"]
                          :omit-source true
                          :filespecs ^:replace [{:type :path, :path "target/javadoc/out"}]}}

  :repositories
  {"snapshots" {:url "https://oss.sonatype.org/content/repositories/snapshots"}}

  :deploy-repositories
  {"releases" {:url "https://oss.sonatype.org/service/local/staging/deploy/maven2"
               :username [:gpg :env/sonatype_username]
               :password [:gpg :env/sonatype_password]}
   "snapshots" {:url "https://oss.sonatype.org/content/repositories/snapshots"
                :username [:gpg :env/sonatype_username]
                :password [:gpg :env/sonatype_password]}})
