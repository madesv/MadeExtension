package com.vjh0107.madesv.bytebuddy.redefine;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class RedefineTest {
    ClassReloadingStrategy classReloadingStrategy;
    @BeforeEach
    public void init() {
        ByteBuddyAgent.install();
        classReloadingStrategy = ClassReloadingStrategy.fromInstalledAgent();
    }

    @Test
    public void redefineTest() {
        // okay
        new ByteBuddy()
                .redefine(Foo.class)
                .method(named("getHello"))
                .intercept(FixedValue.value("ByteBuddy Hello!"))
                .make()
                .load(Foo.class.getClassLoader(), classReloadingStrategy);
        // error
        new ByteBuddy()
                .redefine(Foo.class)
                .method(named("nothing"))
                .intercept(MethodDelegation.to(GeneralInterceptor.class))
                .make()
                .load(Foo.class.getClassLoader(), classReloadingStrategy);
        Foo foo = new Foo();
        System.out.println(foo.getHello());
        System.out.println(foo.getBye());
        foo.nothing("parameter string 1");
        foo.nothing("parameter string 2");
    }

}