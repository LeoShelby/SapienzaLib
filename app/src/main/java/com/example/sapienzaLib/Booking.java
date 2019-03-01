package com.example.sapienzaLib;

import java.util.Date;

public class Booking {
    private String title;
    private String author;
    private String description;
    private String thumbnail;
    private Date date;
    private int copies;

    public Booking(String title, String author, String description, String thumbnail, Date date, int copies) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.thumbnail = thumbnail;
        this.date = date;
        this.copies = copies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate(){return  date;}

    public int getCopies(){return copies;}

    public String getDescription() {return description; }

    public String getThumbnail() {return thumbnail;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setDescription(String description) {this.description = description;}

    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail;}
}







