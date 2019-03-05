package com.example.sapienzaLib;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class ExpBookingsFragment extends ListFragment {

    ArrayList<Booking> bookingList = new ArrayList<>();

    public ExpBookingsFragment(){


        String response="";


        try {
            response = BackendUtilities.getAllBookings();
            JSONObject jObject = new JSONObject(response);
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

                    Date aux = new Date(year-1900, month, day);

                    String date = month + "/" + day + "/" + year;
                    int diffDay = diffDate(date);


                    if(diffDay < 8) bookingList.add(new Booking(title, author, description, thumbnail ,aux , 0, isbn, ""));

                } catch (JSONException e) {
                    // Oops
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View lw = inflater.inflate(R.layout.exp_bookings_fragment, container, false);


        BookingListAdapter adapter = new BookingListAdapter(getActivity().getBaseContext(), R.layout.booking_view_layout, bookingList, "Exp");
        setListAdapter(adapter);

        // Inflate the layout for this fragment
        ListView mListView = lw.findViewById(android.R.id.list);
        mListView.setDividerHeight(0);
        mListView.setDivider(null);

        return lw;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

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


    /*
    private void getExpBook(String isbn, Date until){
        try {
            String book = BackendUtilities.getBookByISBN(isbn);
            JSONObject jObject = new JSONObject(book);
            JSONArray jArray = jObject.getJSONArray("items");
            for (int i=0; i < jArray.length(); i++)
            {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    String title = oneObject.getString("title");
                    String desc = oneObject.getString("description");
                    String thumb = oneObject.getString("thumbnail");
                    String auth = oneObject.getString("authors");

                    bookingList.add(new Booking(title, auth, desc, thumb,until , 0, isbn, ""));
                } catch (JSONException e) {
                    // Oops
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    */
}
