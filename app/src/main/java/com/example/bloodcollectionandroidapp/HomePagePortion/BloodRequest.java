package com.example.bloodcollectionandroidapp.HomePagePortion;

public class BloodRequest {
//model
        private String name;
        private String date;
        private String location;
        private String bloodType;

        public BloodRequest(String name, String date, String location, String bloodType) {
            this.name = name;
            this.date = date;
            this.location = location;
            this.bloodType = bloodType;
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
