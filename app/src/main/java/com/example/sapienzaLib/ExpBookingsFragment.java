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



public class ExpBookingsFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.exp_bookings_fragment, container, false);

        View lw = inflater.inflate(R.layout.pop_bookings_fragment, container, false);


        //Create the Person objects
        Booking book1 = new Booking("Harry Potter","J.K.Rowling", "descrizione matta", "immagine matta", new Date("03/03/2019"),0,"","");
        Booking book2 = new Booking("SuperLongMegaGigaTestBreaKTitle","author2", "descrizione matta", "immagine matta", new Date("05/06/2019"),0,"","");



        //Add the Person objects to an ArrayList
        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(book1);
        bookingList.add(book2);

        BookingListAdapter adapter = new BookingListAdapter(getActivity().getBaseContext(), R.layout.booking_view_layout, bookingList, "Exp");
        setListAdapter(adapter);


        // Inflate the layout for this fragment
        ListView mListView = lw.findViewById(android.R.id.list);
        mListView.setDividerHeight(0);
        mListView.setDivider(null);

        return lw;
    }
}
