package com.example.mark1;

public class Profile {
    String age;

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    String gender;

    public Profile() {
    }

    public Profile(String age, String gender) {
        this.age = age;
        this.gender = gender;
    }
}
