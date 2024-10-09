package com.example.bloodcollectionandroidapp.HomePageFragment;

public class BloodRequest {
    private String id;



    private String toDonorName;
    private String fromTakerName;
    private String location;
    private String bloodType;
    private String post_date;

    public String getReqBloodStatus() {
        return ReqBloodStatus;
    }

    public void setReqBloodStatus(String reqBloodStatus) {
        ReqBloodStatus = reqBloodStatus;
    }

    private String ReqBloodStatus;

    public BloodRequest(String fromTakerName, String today, String location, String bloodType) {
    this.fromTakerName=fromTakerName;
        this.post_date=today;
        this.location=location;
        this.bloodType=bloodType;
    }



    // Default constructor for Firebase
    public BloodRequest(){}

    // Constructor
    public BloodRequest(String toDonorName, String location, String bloodType) {
        this.toDonorName = toDonorName;

        this.location = location;
        this.bloodType = bloodType;
    }
    public BloodRequest(String toDonorName, String fromTakerName, String date, String location, String bloodType) {
        this.toDonorName = toDonorName;
        this.fromTakerName = fromTakerName;
        this.post_date = date;
        this.location = location;
        this.bloodType = bloodType;
    }
    public BloodRequest(String toDonorName, String fromTakerName, String date, String location, String bloodType, String reqBloodStatus) {
        this.toDonorName = toDonorName;
        this.fromTakerName = fromTakerName;
        this.post_date = date;
        this.location = location;
        this.bloodType = bloodType;
        this.ReqBloodStatus=reqBloodStatus;
    }
    // Getters and setters

    public String getToDonorName() {
        return toDonorName;
    }

    public void setToDonorName(String toDonorName) {
        this.toDonorName = toDonorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromTakerName() {
        return fromTakerName;
    }

    public void setFromTakerName(String fromTakerName) {
        this.fromTakerName = fromTakerName;
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

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }




    //  Getters and setters
}
