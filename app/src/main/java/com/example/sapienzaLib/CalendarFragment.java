package com.example.sapienzaLib;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    private CalendarView calendarView;
    private ListView listViewBooking;
    private ArrayList<Booking> bookings = new ArrayList<>();
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

        calendarView = (CalendarView) rootView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                //dateDisplay.setText("Date: " + i2 + " / " + i1 + " / " + i);

                bookings.clear();
                bookings.add(new Booking("Harry Potter","J.K. Rowling",new Date(2019,3,20)));
                bookings.add(new Booking("AC Origins","Paolo Joiki",new Date(2019,3,20)));
                bookings.add(new Booking("Non so fa un cazzo","Alessio Fiorenza",new Date(2019,3,20)));

                adapter = new CalendarListAdapter(getActivity(),bookings);
                listViewBooking.setAdapter(adapter);

            }
        });
        return rootView;
    }

}