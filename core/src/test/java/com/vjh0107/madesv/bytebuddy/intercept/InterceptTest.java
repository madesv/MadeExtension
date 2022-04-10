package com.vjh0107.madesv.bytebuddy.intercept;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class InterceptTest {

    @Test
    public void interceptTest() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        GenericInterceptor interceptor = new GenericInterceptor();

        Class<?> clazz = new ByteBuddy()
                .subclass(BaseUser.class)
                .method(ElementMatchers.isDeclaredBy(BaseUser.class).and(ElementMatchers.isSetter()))
                .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.to(interceptor)))
                .make()
                .load(InterceptTest.class.getClassLoader())
                .getLoaded();

        BaseUser user1 = (BaseUser) clazz.getConstructors()[0].newInstance();
        BaseUser user2 = new BaseUser();
        user1.setName("user1");
        user1.setPassword("password1");
        user2.setName("user2");
        user2.setPassword("password2");

        System.out.println(interceptor.getInterceptedValue("user1", "name"));
        System.out.println(interceptor.getInterceptedValue("user1", "password"));
        System.out.println(interceptor.getInterceptedValue("user2", "name"));
        System.out.println(interceptor.getInterceptedValue("user2", "password"));

        user1.setPassword("password2");
        user1.setPassword("password3");
    }

    public static class GenericInterceptor {
        private Map<String, Object> interceptedValuesMap = new HashMap();

        public void set(String obj, @This User user, @Origin Method setter) {
            String setterName = setter.getName();
            String propertyName = setterName.substring(3, setterName.length()).toLowerCase();
            String key = user.getName() + "_" + propertyName;

            System.out.println("Setting " + propertyName + " to " + obj);
            System.out.println("Previous value " + interceptedValuesMap.get(key));

            interceptedValuesMap.put(key, obj);
        }

        public Object getInterceptedValue(String userName, String fieldName) {
            return interceptedValuesMap.get(userName + "_" + fieldName);
        }
    }
}