package com.example.sapienzaLib;

import android.net.Uri;

import java.net.URL;

public class User {
    private String name;
    private String email;
    private Uri picUrl;

    public User(String name, String email, Uri picUrl) {
        this.name = name;
        this.email = email;
        this.picUrl = picUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPicUrl(Uri picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Uri getPicUrl() {
        return picUrl;
    }
}
