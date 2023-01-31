package com.example.mark1;

public class Schools {
    String schoolName;
    String schoolAddress;
    String noOfTrips;
    String tripTime;


    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public String getNoOfTrips() {
        return noOfTrips;
    }

    public String getTripTime() {
        return tripTime;
    }

    public Schools() {

    }

    public Schools(String schoolName, String schoolAddress, String noOfTrips, String tripTime) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.noOfTrips = noOfTrips;
        this.tripTime = tripTime;
    }
}
