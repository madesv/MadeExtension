package com.vjh0107.madesv.bytebuddy.intercept;

public class BaseUser implements User {

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String name;
    private String password;

}