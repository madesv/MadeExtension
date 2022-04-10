package com.vjh0107.madesv.bytebuddy.redefine;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;


public class GeneralInterceptor {
    @RuntimeType
    public static Object intercept(@AllArguments Object[] allArguments, @This Object proxy) {
        if (allArguments[0] instanceof String) {
            String string = (String) allArguments[0];
            System.out.println(string + " with bytebuddy interceptor");
            System.out.println(((Foo) proxy).getBye() + " with bytebuddy interceptor");
        }

        return null;
    }
}