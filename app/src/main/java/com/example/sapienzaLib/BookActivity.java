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
    String date = "";
    FloatingActionButton fbook,fwish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionMenu fab = findViewById(R.id.fab);
        fbook = findViewById(R.id.fabAddSubcategory);
        fbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackendUtilities.bookABook(isbn);
                Snackbar.make(v, "You succesfully booked it", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        fwish = findViewById(R.id.fabAddProduct);
        fwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackendUtilities.wishaBook(isbn);
                Snackbar.make(v, "Hope you can read it soon", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
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
            fwish.setVisibility(View.GONE);
            fbook.setLabelText("Return");
            date = "Da restituire : " + i.getStringExtra("date");
        }
        if(i.hasExtra("wished")){
            fwish.setLabelText("Unwish");
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
