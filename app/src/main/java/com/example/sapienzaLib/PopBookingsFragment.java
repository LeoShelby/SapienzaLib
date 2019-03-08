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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PopBookingsFragment extends ListFragment {

    ArrayList<Booking> bookingList = new ArrayList<>();
    BookingListAdapter adapter;
    SimpleDateFormat sdf = new SimpleDateFormat(); // creo l'oggetto

    public PopBookingsFragment(){

        sdf.applyPattern("MMMM yyyy");
        //Log.e("QOO","New fragment pop");
        try {
            final String[] result = {""};
            Request request = BackendUtilities.getPopularBooks();
            (BackendUtilities.client).newCall((Request) request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) result[0] = null;


                        if(getActivity()!=null)
                            getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(getActivity()!=null) ((TextView)getActivity().findViewById(R.id.loading_text_pop)).setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            }
                        });


                        result[0] = responseBody.string();
                        JSONObject jObject = new JSONObject(result[0]);
                        JSONArray jArray = jObject.getJSONArray("items");
                        for (int i=0; i < jArray.length(); i++){
                            try {
                                JSONObject oneObject = jArray.getJSONObject(i);
                                // Pulling items from the array
                                String title = oneObject.getString("title");
                                String authors = oneObject.getString("authors");
                                String copies =  oneObject.getString("copies");
                                String description =  oneObject.getString("description");
                                String thumbnail =  oneObject.getString("thumbnail");
                                String isbn =  oneObject.getString("isbn");
                                //String wished = oneObject.getString("wished");
                                String wished = "false";


                                if(copies.equals(""))copies = "0";

                                bookingList.add(new Booking(title, authors, description,thumbnail, null, Integer.parseInt(copies),isbn,"", wished));

                            } catch (JSONException e) {
                                // Oops
                            }
                        }if(getActivity()!=null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                    } catch (JSONException e) {e.printStackTrace();}
                }
            });
        } catch (InterruptedException e) { e.printStackTrace();}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Log.e("QOO","createViewPOP");

        View lw = inflater.inflate(R.layout.pop_bookings_fragment, container, false);

        adapter = new BookingListAdapter(getActivity().getBaseContext(), R.layout.booking_view_layout, bookingList, "Pop");
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
        //Log.e("QOO","viewCreatedPOP");





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
                if(bookingList.get(position).getWished().equals("true")){
                    intent.putExtra("wished", "true");
                }
                startActivity(intent);
            }
        });
    }
}
