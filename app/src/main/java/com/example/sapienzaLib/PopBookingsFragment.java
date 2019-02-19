package com.example.sapienzaLib;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class PopBookingsFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View lw = inflater.inflate(R.layout.pop_bookings_fragment, container, false);

        //Create the Person objects
        Booking book1 = new Booking("Harry Potter","J.K.Rowling", new Date("03/03/2019"));
        Booking book2 = new Booking("SuperLongMegaGigaTestBreaKTitle","author2", new Date("05/06/2019"));
        Booking book3 = new Booking("Title3","Author3", new Date("22/07/2019"));
        Booking book4 = new Booking("Title4","Author1", new Date("12/03/2019"));
        Booking book5 = new Booking("Title5","Author4", new Date("20/04/2019"));
        Booking book6 = new Booking("Title6","Author3", new Date("05/10/2019"));
        Booking book7 = new Booking("Title7","Author1", new Date("14/12/2019"));



        //Add the Person objects to an ArrayList
        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(book1);
        bookingList.add(book2);
        bookingList.add(book3);
        bookingList.add(book4);
        bookingList.add(book5);
        bookingList.add(book6);
        bookingList.add(book7);


        BookingListAdapter adapter = new BookingListAdapter(getActivity().getBaseContext(), R.layout.booking_view_layout, bookingList, "Pop");
        setListAdapter(adapter);


        // Inflate the layout for this fragment
        //TODO qua proprio me sa che ho fatto na cazzata
        ListView mListView = lw.findViewById(android.R.id.list);
        mListView.setDividerHeight(0);
        mListView.setDivider(null);


        return lw;
    }


}
