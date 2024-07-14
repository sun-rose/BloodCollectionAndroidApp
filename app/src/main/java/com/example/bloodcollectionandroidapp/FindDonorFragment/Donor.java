package com.example.bloodcollectionandroidapp.FindDonorFragment;


public class Donor {
    private String name;
    private String lastDonate;
    private String location;

    public Donor(String name, String lastDonate, String location) {
        this.name = name;
        this.lastDonate = lastDonate;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLastDonate() {
        return lastDonate;
    }

    public String getLocation() {
        return location;
    }
}

