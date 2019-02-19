package com.example.sapienzaLib;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class PopBookingsFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View lw = inflater.inflate(R.layout.pop_bookings_fragment, container, false);

        //Create the Person objects
        Booking book1 = new Booking("title1","author1");
        Booking book2 = new Booking("title2","author1");
        Booking book3 = new Booking("title3","author1");
        Booking book4 = new Booking("title4","author1");
        Booking book5 = new Booking("title5","author1");
        Booking book6 = new Booking("title5","author1");
        Booking book7 = new Booking("title5","author1");
        Booking book8 = new Booking("title5","author1");
        Booking book9 = new Booking("title5","author1");
        Booking book10 = new Booking("title5","author1");
        Booking book11= new Booking("title5","author1");


        //Add the Person objects to an ArrayList
        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(book1);
        bookingList.add(book2);
        bookingList.add(book3);
        bookingList.add(book4);
        bookingList.add(book5);
        bookingList.add(book6);
        bookingList.add(book7);
        bookingList.add(book8);
        bookingList.add(book9);
        bookingList.add(book10);
        bookingList.add(book11);

        BookingListAdapter adapter = new BookingListAdapter(getActivity().getBaseContext(), R.layout.booking_view_layout, bookingList);
        setListAdapter(adapter);


        // Inflate the layout for this fragment
        //TODO qua proprio me sa che ho fatto na cazzata
        ListView mListView = lw.findViewById(android.R.id.list);
        mListView.setDividerHeight(0);
        mListView.setDivider(null);


        return lw;
    }


}
