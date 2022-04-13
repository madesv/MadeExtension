package com.vjh0107.madesv.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Objects;

public class ReflectionTest {

    @Test
    public void reflectionTest() throws NoSuchFieldException, IllegalAccessException {
        ReflectionTestComponent reflectionTestComponent = new ReflectionTestComponent();
        Field field = reflectionTestComponent.getClass().getDeclaredField("privateString");
        field.setAccessible(true);
        String string = (String) field.get(null);
        assert(Objects.equals(string, "asdfasdf"));
    }
}
