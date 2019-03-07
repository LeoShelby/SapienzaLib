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

    public static String quote = "Chi non pensa a ora non pranza a tempu\n -Filippo";
    public static void setQuote(String q){quote = q;}
    public static TextView textQuote;


    String lastBook;
    int numLib;
    ListView lw;
    CalendarListAdapter adapter;
    boolean more = false;
    ArrayList<Booking> bookings =  new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        lw = rootView.findViewById(R.id.list_homepage);
        Date c = Calendar.getInstance().getTime();

        //NAME
        TextView name = (TextView) rootView.findViewById(R.id.name_home);
        String tmp = getActivity().getIntent().getStringExtra("user_name").split(" ")[0];
        String output = tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
        name.setText("Ciao " + output);




        //QUOTE
        TextView text = rootView.findViewById(R.id.quote);
        textQuote = text;
        try{
            final String[] result = {""};
            Request request = BackendUtilities.getQuoteBack();
            //Request request = null;
            (BackendUtilities.client).newCall((Request) request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) result[0] = null;
                        result[0] = responseBody.string();
                        //Log.e("mm","AO: "+result[0]);
                        setQuote(result[0]);
                        if(getActivity()!=null)getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(quote);
                            }
                        });
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        //WISHED BOOKS

        TextView wishNum = (TextView) rootView.findViewById(R.id.wish_num);

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
                        //System.out.println("len"+jArray.length());

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
                        if(getActivity()!=null)adapter = new CalendarListAdapter(getActivity(),bookings);
                        if(getActivity()!=null)getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lw.setAdapter(adapter);
                                wishNum.setText("Wished books available: "+jArray.length());
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


        //NUMBER OF BOOKS
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
                        if(getActivity()!=null)getActivity().runOnUiThread(new Runnable() {
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



        //LAST BOOK
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
                        if(getActivity()!=null)getActivity().runOnUiThread(new Runnable() {
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
