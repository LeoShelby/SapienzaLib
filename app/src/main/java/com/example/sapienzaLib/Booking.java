package com.example.sapienzaLib;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {
    private String title;
    private String author;
    private String description;
    private String thumbnail;
    private Date date;
    private int copies;
    private String isbn;
    private String until;

    public Booking(String title, String author, String description, String thumbnail, Date date, int copies, String isbn, String until) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.thumbnail = thumbnail;
        this.date = date;
        this.copies = copies;
        this.isbn = isbn;
        this.until = until;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public String getIsbn() { return isbn; }

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

    public void setCopies(int copies) {this.copies = copies;}

    public void setIsbn(String isbn) {this.isbn = isbn;}
}







