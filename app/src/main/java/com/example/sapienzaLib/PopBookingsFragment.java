package com.example.sapienzaLib;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PopBookingsFragment extends ListFragment {

    ArrayList<Booking> bookingList = new ArrayList<>();

    public PopBookingsFragment(){

        //Booking book1 = new Booking("Harry Potter","J.K.Rowling", "descrizione matta", "immagine matta", new Date("03/03/2019"));
        //Booking book2 = new Booking("SuperLongMegaGigaTestBreaKTitle","author2", "descrizione matta", "immagine matta", new Date("05/06/2019"));
        //bookingList.add(book1);
        //bookingList.add(book2);

        String response="";


        try {
            response = BackendUtilities.getPopularBooks();
            JSONObject jObject = new JSONObject(response);
            JSONArray jArray = jObject.getJSONArray("items");
            for (int i=0; i < jArray.length(); i++)
            {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    String title = oneObject.getString("title");
                    String authors = oneObject.getString("authors");
                    String copies =  oneObject.getString("copies");
                    String description =  oneObject.getString("description");
                    String thumbnail =  oneObject.getString("thumbnail");

                    if(copies.equals(""))copies = "0";

                    bookingList.add(new Booking(title, authors, description,thumbnail, null, Integer.parseInt(copies)));

                } catch (JSONException e) {
                    // Oops
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        Log.e("leo","Response "+response);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View lw = inflater.inflate(R.layout.pop_bookings_fragment, container, false);

        BookingListAdapter adapter = new BookingListAdapter(getActivity().getBaseContext(), R.layout.booking_view_layout, bookingList, "Pop");
        setListAdapter(adapter);


        // Inflate the layout for this fragment
        //TODO qua proprio me sa che ho fatto na cazzata
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
                startActivity(intent);
            }
        });
    }
}
