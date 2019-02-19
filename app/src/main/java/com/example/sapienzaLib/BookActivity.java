package com.example.sapienzaLib;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
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

        TextView tTitle = findViewById(R.id.book_title);
        TextView tAuthor = findViewById(R.id.book_author);
        TextView tDescription = findViewById(R.id.description_book);
        ImageView tThumbnail = findViewById(R.id.book_thumb);

        tTitle.setText(title);
        tAuthor.setText(author);
        tDescription.setText(description);
        Picasso.with(this).load(thumbnail).fit().into(tThumbnail);

    }

}
