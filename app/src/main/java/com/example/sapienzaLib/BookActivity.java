package com.example.sapienzaLib;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class BookActivity extends BaseBaseActivity {

    String isbn;
    boolean booked,wished;
    String date = "";
    String dater = "";
    FloatingActionButton fbook,fwish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionMenu fab = findViewById(R.id.fab);
        fbook = findViewById(R.id.fabAddSubcategory);
        fwish = findViewById(R.id.fabAddProduct);
        fbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(booked){
                    fwish.setVisibility(View.VISIBLE);
                    fbook.setLabelText("Book it");
                    fbook.setImageResource(R.drawable.fab_add);
                    TextView tExpire = findViewById(R.id.book_expire);
                    tExpire.setText("");
                    BackendUtilities.unbookABook(isbn);
                    booked = false;
                }
                else{
                    fwish.setVisibility(View.GONE);
                    fbook.setLabelText("Return");
                    fbook.setImageResource(R.drawable.ic_minus_white);
                    BackendUtilities.bookABook(isbn);
                    booked = true;
                }


            }
        });
        fwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wished){
                    fwish.setLabelText("Add to wishlist");
                    BackendUtilities.unwishaBook(isbn);
                }
                else{
                    fwish.setLabelText("Remove from wishlist");
                    BackendUtilities.wishaBook(isbn);
                }
                wished = !wished;
            }
        });

        setupUI();

    }

    public void setupUI(){
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String author = i.getStringExtra("author");
        String description = i.getStringExtra("description");
        String thumbnail = i.getStringExtra("thumbnail");
        isbn = i.getStringExtra("isbn");
        if(i.hasExtra("date")){
            dater = i.getStringExtra("date");
            date = "To return: " + dater;
            fwish.setVisibility(View.GONE);
            fbook.setLabelText("Return");
            fbook.setImageResource(R.drawable.ic_minus_white);
            booked = true;
        }
        else{
            booked = false;
        }
        if(i.hasExtra("wished")){
            fwish.setLabelText("Remove from wishlist");
            wished=true;
        }
        else{
            wished=false;
        }

        TextView tTitle = findViewById(R.id.book_title);
        TextView tAuthor = findViewById(R.id.book_author);
        TextView tDescription = findViewById(R.id.description_book);
        ImageView tThumbnail = findViewById(R.id.book_thumb);
        TextView tExpire = findViewById(R.id.book_expire);
        tExpire.setText(date);

        tTitle.setText(title);
        tAuthor.setText(author);
        tDescription.setText(description);
        Picasso.with(this).load(thumbnail).fit().into(tThumbnail);

    }

}
