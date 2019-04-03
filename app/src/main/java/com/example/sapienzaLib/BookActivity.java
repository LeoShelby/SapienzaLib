package com.example.sapienzaLib;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
            Log.d("valco",dater + "---" + date);
        }
        else{
            booked = false;
        }
        if(i.hasExtra("wished")){
            Log.d("valco", "set a Remove");
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

        try {
            Request r = BackendUtilities.getBookInfo(isbn);
            (BackendUtilities.client).newCall((Request) r).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (response.isSuccessful()){
                            String s = responseBody.string();

                            Log.d("valco", "laccidi");
                            JSONObject jObject = new JSONObject(s);
                            String amount = jObject.getString("amount");
                            String buyLink = jObject.getString("link");

                            Log.d("valco", amount);

                            if(!amount.equals("-1")) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CardView cv = findViewById(R.id.card_view);
                                        cv.setVisibility(View.VISIBLE);
                                        cv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Uri uri = Uri.parse(buyLink); // missing 'http://' will cause crashed
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                            }
                                        });
                                        ((TextView) findViewById(R.id.textView2)).setText(amount);

                                    }
                                });
                            }
                            else{
                                Log.d("valco","ciao");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
