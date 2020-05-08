# spring-repl

A java.lang.instrument agent providing a clojure repl that is spring aware.
The intent is to allow any spring based service or app to be started with this agent
and provide useful clojurey things to java-based software. Even if you don't care about
spring, this agent can still be useful to create an nrepl server in an existing application without
modifying any code or dependencies of the application.

## Usage

Create the agent jar via:

```
lein uberjar
```

Add this option to your java application's startup args:

```
-javaagent:/your/path/to/target/spring-repl-1.0.0-standalone.jar
```

At startup, this agent will create an nrepl server on port 8000. You can then use any nrepl client to connect.
In addition to starting an nrepl server, the agent detects the creation of a Spring ApplicationContext, and maintains
a clojure var referring to it. This allows clojure code to access any beans in the application context. However, even if your application/service is not using spring, the repl has full access to anything on the classpath of the JVM you are running.

For example

```
lein repl :connect localhost:8000
```

Execute the following to list spring-related functions available:

```
(dir spring-repl.context)
```

For convenience, you might want to require the namespace and give it a shorter alias:

```
(require '[spring-repl.context :as ctx])
```

In the future I will likely make this require automatic.

Keep in mind that you can also import any java classes available on the classpath and invoke instances via clojure's java interop. This tool has utility beyond spring environments.

## Spring Boot In an IDE

Add this to your build.gradle for running in your IDE:
bootRun {
jvmArgs = ["-javaagent:/your/path/to/target/spring-repl-1.0.0-standalone.jar"]
}

If you are running a spring boot app outside an IDE, you should not need any special config
other than the -javaagent JVM option.

## Historical Caveats

The first couple of verisions of this agent included dependencies which could cause
all manner of chaos at runtime. These dependencies have been removed. I included them
for my own experiments in using the agent as a vehicle to inject new dependencies into a running
JVM for running tests in an unorthodox manner.

## TODO

The spring-specific functions provided are minimal at the moment. I will likely add more over time.
I have exclusively been using this agent as a vehicle for injecting clojure code into crufty legacy
java code for testing purposes. Specifically, I have used this to experiment with writing integration tests for
java code which will never be refactored for testability due to resource constraints. Having a repl
which can access all java packages and spring beans in a full running application has been interesting and
useful.

## License

Copyright © 2017 Todd Stout
Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
