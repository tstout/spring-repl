package spring.repl;

import clojure.lang.Keyword;

import static clojure.lang.Keyword.*;

public enum ContextKey {
    APP_CONTEXT("app-context");

    ContextKey(String s) {
        keyword = intern(s);
    }

    public final Keyword keyword;
}
