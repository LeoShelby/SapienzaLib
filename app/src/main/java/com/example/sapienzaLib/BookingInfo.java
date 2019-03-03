package com.example.sapienzaLib;

public class BookingInfo {
    private String isbn;
    private String until;

    public BookingInfo(String isbn, String until) {
        this.isbn = isbn;
        this.until = until;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
