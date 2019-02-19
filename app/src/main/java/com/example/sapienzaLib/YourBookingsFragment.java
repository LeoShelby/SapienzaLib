package com.example.sapienzaLib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;


public class YourBookingsFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.your_bookings_fragment, container, false);

        View lw = inflater.inflate(R.layout.pop_bookings_fragment, container, false);

        Booking book1 = new Booking("Title_fresh_1","Author1", new Date("03/03/2019"));
        Booking book2 = new Booking("Title_fresh_2","Author1", new Date("05/06/2019"));
        Booking book3 = new Booking("Title_fresh_3","Author2", new Date("05/09/2019"));

        //Add the Person objects to an ArrayList
        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(book1);
        bookingList.add(book2);
        bookingList.add(book3);

        BookingListAdapter adapter = new BookingListAdapter(getActivity().getBaseContext(), R.layout.booking_view_layout, bookingList, "Fresh");
        setListAdapter(adapter);


        // Inflate the layout for this fragment
        ListView mListView = lw.findViewById(android.R.id.list);
        mListView.setDividerHeight(0);
        mListView.setDivider(null);

        return lw;
    }

}
