package com.example.sapienzaLib;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Booking> bookings;
    private static LayoutInflater inflater = null;

    //public BookingListAdapter(MainActivity mainActivity, int booking_view_layout, ArrayList<Booking> peopleList) {
    public CalendarListAdapter(Context context, ArrayList<Booking> objects) {

        this.mContext = context;
        this.bookings = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bookings.size();
    }

    @Override
    public Object getItem(int position) {
        return bookings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.calendar_item_layout,null):itemView;
        TextView textViewTitle = (TextView) itemView.findViewById(R.id.textView2);
        TextView textViewAuthor = (TextView) itemView.findViewById(R.id.textView3);
        TextView textViewDate = (TextView) itemView.findViewById(R.id.textView1);
        textViewAuthor.setText(bookings.get(position).getTitle());
        textViewTitle.setText(bookings.get(position).getAuthor());
        textViewDate.setText("20/02/2019");
        return itemView;
    }
}
