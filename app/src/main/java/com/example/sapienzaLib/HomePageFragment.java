package com.example.sapienzaLib;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import java.io.IOException;

public class HomePageFragment extends Fragment {

    public String quote = "Chi non pensa a ora non pranza a tempu\n -Filippo";
    public void setQuote(String quote){this.quote = quote;}

    String lastBook;
    int numLib;
    ListView lw;
    CalendarListAdapter adapter;
    boolean more = false;
    ArrayList<Booking> bookings =  new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        lw = rootView.findViewById(R.id.list_homepage);
        Date c = Calendar.getInstance().getTime();


        TextView text = rootView.findViewById(R.id.quote);
        text.setText(quote);


        CardView cw = rootView.findViewById(R.id.card_view_more);
        cw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!more) {
                    lw.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
                else{
                    lw.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
                more = !more;
            }
        });

        try {
            final String[] result = {""};
            Request request = BackendUtilities.getAllWishes();
            (BackendUtilities.client).newCall((Request) request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) result[0] = null;

                        result[0] = responseBody.string();
                        JSONObject jObject = new JSONObject(result[0]);
                        JSONArray jArray = jObject.getJSONArray("items");

                        for (int i=0; i < jArray.length(); i++)
                        {
                            try {
                                JSONObject oneObject = jArray.getJSONObject(i);
                                // Pulling items from the array
                                String isbn = oneObject.getString("isbn");
                                String title = oneObject.getString("title");
                                String author = oneObject.getString("author");
                                String description = oneObject.getString("description");
                                String thumbnail = oneObject.getString("thumbnail");
                                Date c = Calendar.getInstance().getTime();
                                bookings.add(new Booking(title, author, description, thumbnail ,c , 0, isbn, ""));


                            } catch (JSONException e) {
                                // Oops
                            }

                        }
                        adapter = new CalendarListAdapter(getActivity(),bookings);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView)getActivity().findViewById(R.id.textView7)).setText("Wished books available "+jArray.length());
                                lw.setAdapter(adapter);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            final String[] result = {""};
            Request request = BackendUtilities.getAllBookings();
            (BackendUtilities.client).newCall((Request) request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) result[0] = null;
                        result[0] = responseBody.string();
                        JSONObject jObject = new JSONObject(result[0]);
                        JSONArray jArray = jObject.getJSONArray("items");
                        numLib = jArray.length();
                        TextView numRead = rootView.findViewById(R.id.num_home);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                numRead.setText("Libri letti: " + numLib);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            final String[] result = {""};
            Request request = BackendUtilities.getLastBook2();
            (BackendUtilities.client).newCall((Request) request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) result[0] = null;
                        result[0] = responseBody.string();
                        JSONObject jObject = new JSONObject(result[0]);
                        lastBook = jObject.getString("title");
                        TextView last = rootView.findViewById(R.id.last_home);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                last.setText(lastBook);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Inflate the layout for this fragment

        CircleImageView cim = rootView.findViewById(R.id.image_home);
        Picasso.with(getActivity()).load(getActivity().getIntent().getStringExtra("user_pic")).fit().into(cim);
        //rootView.findViewById(R.id.card).setBackgroundResource(R.drawable.side_nav_bar);





        return rootView;
    }

}
