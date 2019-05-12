package com.example.loginapp;

public class UserInfo {

    String name;
    String phone;
    String id;
    String sim;

    public UserInfo() {}

    public UserInfo(String name,String id,String phone) {
        this.name = name;
        this.phone= phone;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
    public String getId() {
        return id;
    }
    public String getSim(){return sim;}
}


