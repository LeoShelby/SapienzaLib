package com.example.sapienzaLib;


import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    private CompactCalendarView calendarView;
    private ListView listViewBooking;
    private TextView textMonth;
    private SimpleDateFormat sdf,sdff;
    private ArrayList<Booking> bookings = new ArrayList<>();
    private HashMap<String, ArrayList<Booking>> bookList = new HashMap<>();
    private CalendarListAdapter adapter;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        listViewBooking = (ListView) rootView.findViewById(R.id.calendar_list);
        listViewBooking.setDividerHeight(0);
        listViewBooking.setDivider(null);

        calendarView = (CompactCalendarView) rootView.findViewById(R.id.calendarView);

        textMonth = (TextView) rootView.findViewById(R.id.calendar_month);

        sdf = new SimpleDateFormat(); // creo l'oggetto
        sdf.applyPattern("MMMM yyyy");

        sdff = new SimpleDateFormat(); // creo l'oggetto
        sdff.applyPattern("dd MMMM yyyy");

        adapter = new CalendarListAdapter(getActivity(),bookings);
        listViewBooking.setAdapter(adapter);

        textMonth.setText(sdf.format(Calendar.getInstance().getTime()));
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //calendarView.setCurrentDate(dateClicked);
                List<Event> events = calendarView.getEvents(dateClicked);
                Calendar c = Calendar.getInstance();
                c.setTime(dateClicked);
                String until = c.get(Calendar.YEAR) + "-" +c.get(Calendar.MONTH)  +"-"+ c.get(Calendar.DATE);
                ArrayList<Booking> bi = bookList.get(until);
                bookings.clear();
                adapter.notifyDataSetChanged();
                if(bi == null){
                    return;
                }
                for(Booking b : bi){
                    b.setDate(dateClicked);
                    bookings.add(b);
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                String dataStr = sdf.format(firstDayOfNewMonth);
                textMonth.setText(dataStr);
            }
        });
        listViewBooking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Booking b = bookings.get(position);
                Intent intent = new Intent(getContext(), BookActivity.class);
                intent.putExtra("title",b.getTitle());
                intent.putExtra("author",b.getAuthor());
                intent.putExtra("description",b.getDescription());
                intent.putExtra("thumbnail",b.getThumbnail());
                intent.putExtra("isbn",b.getIsbn());
                intent.putExtra("date",sdff.format(b.getDate()));
                startActivity(intent);
            }
        });
        setupCalendar();

        return rootView;
    }

    public void setupCalendar(){
        try {
            final String[] result = {""};
            Request request = BackendUtilities.getAllBookings();
            //Activity context = getActivity();
            (BackendUtilities.client).newCall((Request) request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) result[0] = null;

                        if( getActivity()!=null)
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(getActivity()!=null) getActivity().findViewById(R.id.loading_text).setVisibility(View.GONE);
                            }
                        });
                        result[0] = responseBody.string();
                        Log.d("valco", result[0]);
                        JSONObject jObject = new JSONObject(result[0]);
                        JSONArray jArray = jObject.getJSONArray("items");
                        for (int i=0; i < jArray.length(); i++)
                        {
                            try {


                                JSONObject oneObject = jArray.getJSONObject(i);

                                Log.d("valco", oneObject.toString());
                                // Pulling items from the array
                                String isbn = oneObject.getString("isbn");
                                String until = oneObject.getString("until");
                                String title = oneObject.getString("title");
                                String desc = oneObject.getString("description");
                                String thumb = oneObject.getString("thumbnail");
                                String auth = oneObject.getString("author");
                                String gid = oneObject.getString("gid");


                                int month = Integer.parseInt(until.split("-")[1]);
                                int day = Integer.parseInt(until.split("-")[2]);
                                int year = Integer.parseInt(until.split("-")[0]);

                                Calendar dd = Calendar.getInstance();
                                dd.set(year,month,day,0,0);

                                Booking booking = new Booking(title,auth,desc,thumb,null,0,isbn,until);
                                if(bookList.get(until) == null){
                                    bookList.put(until, new ArrayList<>());
                                }
                                bookList.get(until).add(booking);
                                Event ev = new Event(Color.BLUE, dd.getTimeInMillis());
                                calendarView.addEvent(ev);

                            } catch (JSONException e) {
                                // Oops
                            }
                        }                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
