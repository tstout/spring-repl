# spring-repl

A java.lang.instrument agent providing a clojure repl that is spring aware.
The is raw at the moment, and most certainly incomplete. The intent is
to allow any spring based service or app to be started with this agent
and provide useful clojurey things to spring-based software.

## Usage
Add this option to your java startup args:

-javaagent:/path/to/this/jar


## License

Copyright © 2017 Todd Stout

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
