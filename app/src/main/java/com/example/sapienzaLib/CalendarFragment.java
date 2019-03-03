package com.example.sapienzaLib;


import android.app.usage.UsageEvents;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    private CompactCalendarView calendarView;
    private ListView listViewBooking;
    private TextView textMonth;
    private SimpleDateFormat sdf;
    private ArrayList<Booking> bookings = new ArrayList<>();
    private HashMap<String, ArrayList<BookingInfo>> bookList = new HashMap<>();
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

        adapter = new CalendarListAdapter(getActivity(),bookings);
        listViewBooking.setAdapter(adapter);

        textMonth.setText(sdf.format(Calendar.getInstance().getTime()));
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = calendarView.getEvents(dateClicked);
                Calendar c = Calendar.getInstance();
                c.setTime(dateClicked);
                String until = c.get(Calendar.YEAR) + "-" +c.get(Calendar.MONTH)  +"-"+ c.get(Calendar.DATE);
                ArrayList<BookingInfo> bi = bookList.get(until);
                bookings.clear();
                if(bi == null){
                    return;
                }
                for (BookingInfo b: bi){
                    try {
                        String book = BackendUtilities.getBookByISBN(b.getIsbn());
                        JSONObject jObject = new JSONObject(book);
                        JSONArray jArray = jObject.getJSONArray("items");
                        for (int i=0; i < jArray.length(); i++)
                        {
                            try {
                                JSONObject oneObject = jArray.getJSONObject(i);
                                // Pulling items from the array
                                String title = oneObject.getString("title");
                                String desc = oneObject.getString("description");
                                String thumb = oneObject.getString("thumbnail");
                                String auth = (String) oneObject.getJSONArray("authors").get(0);
                                String isbn = oneObject.getString("isbn");

                                bookings.add(new Booking(title, auth, "", "",dateClicked, 0));
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
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                String dataStr = sdf.format(firstDayOfNewMonth);
                textMonth.setText(dataStr);
            }
        });

        setupCalendar();

        return rootView;
    }

    public void setupCalendar(){
        try {
            String result = BackendUtilities.getAllBookings();
            JSONObject jObject = new JSONObject(result);
            JSONArray jArray = jObject.getJSONArray("items");
            for (int i=0; i < jArray.length(); i++)
            {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    String isbn = oneObject.getString("isbn");
                    String until = oneObject.getString("until");

                    int month = Integer.parseInt(until.split("-")[1]);
                    int day = Integer.parseInt(until.split("-")[2]);
                    int year = Integer.parseInt(until.split("-")[0]);

                    Calendar dd = Calendar.getInstance();
                    dd.set(year,month,day,0,0);

                    BookingInfo bookingInfo = new BookingInfo(isbn,until);
                    if(bookList.get(until) == null){
                        bookList.put(until, new ArrayList<>());
                    }
                    bookList.get(until).add(bookingInfo);
                    Event ev = new Event(Color.BLUE, dd.getTimeInMillis());
                    calendarView.addEvent(ev);

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

}
