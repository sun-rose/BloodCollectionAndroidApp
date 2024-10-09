package com.example.bloodcollectionandroidapp.FindDonorFragment;

public class BloodPost {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    String id;

    public BloodPost(String username, String postDate, String location, String bloodType) {
        this.id = id;
        this.username = username;
        this.postDate = postDate;
        this.location = location;
        this.bloodType = bloodType;
    }

    String username;
    String postDate;
    String location;
    String bloodType;

    public BloodPost() {
    }
}
