package com.example.sapienzaLib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class YourBookingsFragment extends ListFragment {

    ArrayList<Booking> bookingList = new ArrayList<>();
    BookingListAdapter adapter;

    public YourBookingsFragment(){
        Log.e("QOO","NEW FRESH");
        try {
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


                        /*
                        if(getActivity()!=null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((TextView)getActivity().findViewById(R.id.loading_text_fresh)).setVisibility(View.GONE);
                                    adapter.notifyDataSetChanged();
                                }
                            });
*/

                        result[0] = responseBody.string();
                        JSONObject jObject = new JSONObject(result[0]);
                        JSONArray jArray = jObject.getJSONArray("items");

                        for (int i=0; i < jArray.length(); i++)
                        {
                            try {
                                JSONObject oneObject = jArray.getJSONObject(i);
                                // Pulling items from the array
                                String isbn = oneObject.getString("isbn");
                                String until = oneObject.getString("until");
                                String title = oneObject.getString("title");
                                String author = oneObject.getString("author");
                                String description = oneObject.getString("description");
                                String thumbnail = oneObject.getString("thumbnail");

                                int month = Integer.parseInt(until.split("-")[1]);
                                int day = Integer.parseInt(until.split("-")[2]);
                                int year = Integer.parseInt(until.split("-")[0]);

                                Date aux = new Date(year-1900, month-1, day);

                                String date = month+1 + "/" + day + "/" + year;
                                int diffDay = diffDate(date);

                                if(diffDay >= 8){
                                    bookingList.add(new Booking(title, author, description, thumbnail ,aux , 0, isbn, ""));

                                }

                            } catch (JSONException e) {
                                // Oops
                            }
                        } if(getActivity()!=null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    //Log.e("po","porciod: "+bookingList.get(0));
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("QOO","CreateViewFRESH");
        View lw = inflater.inflate(R.layout.your_bookings_fragment, container, false);


        adapter = new BookingListAdapter(getActivity().getBaseContext(), R.layout.booking_view_layout, bookingList, "Fresh");
        setListAdapter(adapter);


        // Inflate the layout for this fragment
        ListView mListView = lw.findViewById(android.R.id.list);
        mListView.setDividerHeight(0);
        mListView.setDivider(null);


        return lw;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("QOO","CreatedFRESH");



        ListView l = (ListView) getListView();
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BookActivity.class);
                intent.putExtra("title",bookingList.get(position).getTitle());
                intent.putExtra("author",bookingList.get(position).getAuthor());
                intent.putExtra("description",bookingList.get(position).getDescription());
                intent.putExtra("thumbnail",bookingList.get(position).getThumbnail());
                intent.putExtra("isbn",bookingList.get(position).getIsbn());
                startActivity(intent);
            }
        });



    }


    private static int diffDate(String date) {
        long diff = 0;
        int diffDays = 0;
        try {
            String time = "00:00 AM";

            String format = "MM/dd/yyyy hh:mm a";

            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date dateObj2 = sdf.parse(date + " " + time);
            Date dateObj1 = Calendar.getInstance().getTime();

            DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

            // getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
            diff = dateObj2.getTime() - dateObj1.getTime();

            diffDays = (int) (diff / (24 * 60 * 60 * 1000));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diffDays;
    }

}
