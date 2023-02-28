package com.example.mark1;

public class Address {
    String city;
    String district;
    String houseno;
    String pincode;
    String society;
    String state;
    String street;

    public Address() {

    }

    public Address(String city, String district, String houseno, String pincode, String society, String state, String street) {
        this.city = city;
        this.district = district;
        this.houseno = houseno;
        this.pincode = pincode;
        this.society = society;
        this.state = state;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
