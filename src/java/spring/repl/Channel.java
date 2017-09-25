package spring.repl;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

/**
 * Conduit for publishing information from java code to
 * subscribers written in clojure.
 */
public class Channel {
    private final IFn pubEvt;
    private final IFn fromJava;
    private static final Channel instance = new Channel();

    private Channel() {
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("spring-repl.pubsub"));
        require.invoke(Clojure.read("clojure.java.data"));
        fromJava = Clojure.var("clojure.java.data", "from-java");
        pubEvt = Clojure.var("spring-repl.pubsub", "pub-evt");
    }

    public static void pub(Topic t, Object javaBean) {
        instance.pubEvt.invoke(
                t.keyword,
                instance.fromJava.invoke(javaBean));
    }
}
