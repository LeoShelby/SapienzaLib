package com.example.sapienzaLib;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ChangeQuoteActivity extends AppCompatActivity {

    Activity v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_quote);
        v = this;

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextInputEditText quote = findViewById(R.id.myquote);
                String q = quote.getText().toString();
                //Log.e("ii","AO: "+q);
                //HomePageFragment.setQuote(q);
                try{
                    BackendUtilities.postQuote(q);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TextView text = HomePageFragment.textQuote;
                text.setText(q);
                Snackbar.make(v, "Quote changed", Snackbar.LENGTH_SHORT).show();


            }
        });
    }
}
