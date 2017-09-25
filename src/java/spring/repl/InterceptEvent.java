package spring.repl;

public class InterceptEvent {
    private final Object obj;

    public InterceptEvent(Object obj) {
        this.obj = obj;
    }

    public Object getObject() {
        return obj;
    }
}
