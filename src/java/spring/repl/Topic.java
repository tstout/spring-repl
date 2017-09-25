package spring.repl;

import clojure.lang.Keyword;

import static clojure.lang.Keyword.*;

public enum Topic {
    MAIN_BUS("main-bus"),
    INFO("info-log"),
    ERROR("error-log");

    Topic(String s) {
        keyword = intern(s);
    }

    public final Keyword keyword;
}
