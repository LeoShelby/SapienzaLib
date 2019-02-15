package com.example.sapienzaLib;

public class Booking {
    private String title;
    private String author;
    //private Date date;

    public Booking(String title, String author) {
        this.title = title;
        this.author = author;
        //this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}







