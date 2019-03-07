package com.example.sapienzaLib;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WishedActivity extends AppCompatActivity {

    final ArrayList<Book> mBooks = new ArrayList<Book>();
    RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView lw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wished);

        lw = (RecyclerView) findViewById(R.id.list_search);

        Activity aux = this;

        mAdapter = new BookListAdapter(mBooks,this);
        lw.setAdapter(mAdapter);
        lw.setLayoutManager(new LinearLayoutManager(this));

        try {
            final String[] result = {""};
            Request request = BackendUtilities.getWished();
            (BackendUtilities.client).newCall((Request) request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) result[0] = null;

                        //findViewById(R.id.loading_text_pop).setVisibility(View.GONE);
                        //mAdapter.notifyDataSetChanged();

                        result[0] = responseBody.string();
                        JSONObject jObject = new JSONObject(result[0]);
                        JSONArray jArray = jObject.getJSONArray("items");
                        for (int i=0; i < jArray.length(); i++){
                            try {
                                JSONObject oneObject = jArray.getJSONObject(i);
                                Log.e("k","OK1 "+result[0]);
                                // Pulling items from the array
                                String title = oneObject.getString("title");

                                String authors = oneObject.getString("author");

                                //String copies =  oneObject.getString("copies");
                                String description =  oneObject.getString("description");
                                String thumbnail =  oneObject.getString("thumbnail");
                                String isbn =  oneObject.getString("isbn");

                                //if(copies.equals(""))copies = "0";

                                Log.e("k","aaa: "+ result[0]);
                                mBooks.add(new Book(title, authors, description, thumbnail,isbn, "", "true"));

                            } catch (JSONException e) {
                                // Oops
                            }
                        }
                            aux.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //((TextView)aux.findViewById(R.id.loading_text_pop)).setVisibility(View.GONE);
                                    mAdapter.notifyDataSetChanged();
                                }
                            });

                    } catch (JSONException e) {e.printStackTrace();}
                }
            });
        } catch (InterruptedException e) { e.printStackTrace();}



        //ListView l = (ListView) getListView();
        ((BookListAdapter)mAdapter).setOnItemClickListener(new BookListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getBaseContext(), BookActivity.class);
                intent.putExtra("title",mBooks.get(position).getTitle());
                intent.putExtra("author",mBooks.get(position).getAuthor());
                intent.putExtra("description",mBooks.get(position).getDescription());
                intent.putExtra("thumbnail",mBooks.get(position).getThumbnail());
                intent.putExtra("isbn",mBooks.get(position).getIsbn());
                startActivity(intent);
            }
        });
    }
}
