package com.example.bloodcollectionandroidapp.HomePageFragment;

public class BloodRequest {



    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    //model
        private String user_id;
        private String name;

    public String getUser_mode() {
        return user_mode;
    }

    public void setUser_mode(String user_mode) {
        this.user_mode = user_mode;
    }

    private String user_mode;

    public BloodRequest(String user_id, String bloodType, String date, String name, String location, String user_mode) {
        this.user_id = user_id;
        this.bloodType = bloodType;
        this.date = date;
        this.name = name;
        this.location = location;
        this.user_mode= user_mode;
    }

    private String date;
        private String location;
        private String bloodType;


    public BloodRequest(String name, String date, String location, String bloodType) {
            this.name = name;
            this.date = date;
            this.location = location;
            this.bloodType = bloodType;
        }

    public BloodRequest() {
    }

    public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getLocation() {
            return location;
        }

        public String getBloodType() {
            return bloodType;
        }

}
