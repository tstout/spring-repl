package spring.repl;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.asm.Advice.Origin;
import net.bytebuddy.asm.Advice.This;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.springframework.context.ApplicationContext;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class ReplAgent {

    public static void main(String[] args) {
        bootStrap();
        System.out.println("bootstrap finished");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void bootStrap() {
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("spring-repl.bootstrap"));
        IFn boot = Clojure.var("spring-repl.bootstrap", "boot");
        boot.invoke();
        Channel.pub(Topic.INFO, new LogMessage("Bootstrap complete"));
    }

    public static void premain(String arguments, Instrumentation instrumentation) {
        try {
            bootStrap();

            new AgentBuilder.Default()
                    .with(new DebugListener())
                    .ignore(nameStartsWith("net.bytebuddy.")
                            .or(nameStartsWith("sun.reflect"))
                            .or(nameStartsWith("org.apache.log4j"))
                            .or(nameStartsWith("sun.misc")))
                    .type(ElementMatchers.isSubTypeOf(ApplicationContext.class))
                    .transform((builder, typeDescription, classLoader, module) -> builder
                            .visit(Advice
                                    .to(ConstructorInterceptor.class)
                                    .on(isConstructor())))
                    .installOn(instrumentation);
        } catch (Throwable t) {
            System.out.println(String.format("Error %s", t.getMessage()));
        }
    }

    // Needed for starting an agent after main has already executed.
    public static void agentmain(String arguments, Instrumentation instrumentation) {
        premain(arguments, instrumentation);
    }

    public static class ConstructorInterceptor {

        @Advice.OnMethodExit
        public static void intercept(@Origin Constructor m, @This Object inst) throws Exception {
            System.out.println("--------------------Intercepted Spring Context!---------");
            Channel.pub(Topic.MAIN_BUS, new InterceptEvent(inst));
        }
    }

    static class DebugListener implements AgentBuilder.Listener {

        @Override
        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
        }

        @Override
        public void onTransformation(TypeDescription typeDescription,
                                     ClassLoader classLoader,
                                     JavaModule module,
                                     boolean loaded,
                                     DynamicType dynamicType) {
        }

        @Override
        public void onIgnored(TypeDescription typeDescription,
                              ClassLoader classLoader,
                              JavaModule module,
                              boolean loaded) {
        }

        @Override
        public void onError(String typeName,
                            ClassLoader classLoader,
                            JavaModule module,
                            boolean loaded,
                            Throwable throwable) {
            Channel.pub(Topic.ERROR, new LogMessage(throwable.getMessage()));
        }

        @Override
        public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
        }
    }
}
