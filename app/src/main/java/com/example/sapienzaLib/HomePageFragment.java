package com.example.sapienzaLib;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

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
