package com.example.bloodcollectionandroidapp.ProfileFragment;

public class UserDetails {
    String id;
    String username;
    String mobile;
    String location;

    public UserDetails(String id, String username, String mobile, String location, String bloodGroup, String userMode) {
        this.id = id;
        this.username = username;
        this.mobile = mobile;
        this.location = location;
        this.bloodGroup = bloodGroup;
        this.userMode = userMode;
    }

    public UserDetails() {
    }

    String bloodGroup;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getUserMode() {
        return userMode;
    }

    public void setUserMode(String userMode) {
        this.userMode = userMode;
    }

    String userMode;




}
