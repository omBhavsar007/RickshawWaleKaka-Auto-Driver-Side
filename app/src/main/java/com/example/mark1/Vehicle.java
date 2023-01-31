package com.example.mark1;

public class Vehicle {

    String vehicleType;
    String noPlate;
    String capacity;

    public String getVehiclePhoto() {
        return VehiclePhoto;
    }

    public void setVehiclePhoto(String vehiclePhoto) {
        VehiclePhoto = vehiclePhoto;
    }

    String VehiclePhoto;

    public String getVehicleType() {
        return vehicleType;
    }

    public String getNoPlate() {
        return noPlate;
    }

    public String getCapacity() {
        return capacity;
    }

    public Vehicle() {
    }

    public Vehicle(String vehicleType, String noPlate, String capacity,String VehiclePhoto) {
        this.vehicleType = vehicleType;
        this.noPlate = noPlate;
        this.capacity = capacity;
        this.VehiclePhoto = VehiclePhoto;
    }
}
