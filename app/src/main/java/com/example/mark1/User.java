package com.example.mark1;

public class User {

    String mobileNo;
    String name;
    String email;
    String password;

    public String getMobileNo() {
        return mobileNo;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User() {

    }

    public User(String mobileNo, String name, String email, String password) {
        this.mobileNo = mobileNo;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
