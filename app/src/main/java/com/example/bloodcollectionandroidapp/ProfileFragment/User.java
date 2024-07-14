package com.example.bloodcollectionandroidapp.ProfileFragment;

public class User {

    public User(String id, String email, String password, String username, String image_url) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    String id;
    String email;

    public User(String email, String password, String username, String image_url) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.image_url = image_url;
    }

    String password;
    String username;
    String image_url;

    public User() {
    }


}
