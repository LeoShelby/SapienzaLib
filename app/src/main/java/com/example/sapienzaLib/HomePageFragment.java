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

public class HomePageFragment extends Fragment {

    public String quote = "Chi non pensa a ora non pranza a tempu\n -Filippo";
    public void setQuote(String quote){this.quote = quote;}

    ListView lw;
    CalendarListAdapter adapter;
    boolean more = false;
    ArrayList<Booking> bookings =  new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        lw = rootView.findViewById(R.id.list_homepage);
        Date c = Calendar.getInstance().getTime();
        adapter = new CalendarListAdapter(getActivity(),bookings);
        lw.setAdapter(adapter);


        TextView text = rootView.findViewById(R.id.quote);
        text.setText(quote);


        CardView cw = rootView.findViewById(R.id.card_view_more);
        cw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!more) {
                    bookings.add(new Booking("Leonardo Di Canio. Il mio passato comunista", "Leonardo", "cia", "", c, 0, "", ""));
                    bookings.add(new Booking("Differenziata non fa differenza", "Valerio", "cia", "", c, 0, "", ""));
                    bookings.add(new Booking("Il mio mac non è più lo stesso", "Alessio", "cia", "", c, 0, "", ""));
                    adapter.notifyDataSetChanged();
                }
                else{
                    bookings.clear();
                    adapter.notifyDataSetChanged();
                }
                more = !more;
            }
        });
        CircleImageView cim = rootView.findViewById(R.id.image_home);
        Picasso.with(getActivity()).load(getActivity().getIntent().getStringExtra("user_pic")).fit().into(cim);
        //rootView.findViewById(R.id.card).setBackgroundResource(R.drawable.side_nav_bar);

        int num = 0;

        try {
            //String response = BackendUtilities.getAllBookings();
            String response = "";
            JSONObject jObject = new JSONObject(response);
            JSONArray jArray = jObject.getJSONArray("items");
            num = jArray.length();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView numRead = rootView.findViewById(R.id.num_home);

        numRead.setText("Libri letti questo mese: " + num);

        String lastBook = "";

        try {
            String response = BackendUtilities.getLastBook();
            JSONObject jObject = new JSONObject(response);
            lastBook = jObject.getString("title");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView last = rootView.findViewById(R.id.last_home);
        last.setText(lastBook);


        return rootView;
    }

}
