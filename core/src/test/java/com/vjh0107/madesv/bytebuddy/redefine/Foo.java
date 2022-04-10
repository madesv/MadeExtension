package com.vjh0107.madesv.bytebuddy.redefine;

public class Foo {
    boolean a = true;
    public String getHello() {
        return "not redefined hello!";
    }
    public String getBye() {
        return "not redefined Bye!";
    }
    public void nothing(String s) {}
}
