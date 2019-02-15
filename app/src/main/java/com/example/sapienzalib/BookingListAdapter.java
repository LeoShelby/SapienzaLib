package com.example.sapienzalib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class BookingListAdapter extends ArrayAdapter<Booking> {

    private Context mContext;
    int mResource;

    //public BookingListAdapter(MainActivity mainActivity, int booking_view_layout, ArrayList<Booking> peopleList) {
    public BookingListAdapter(Context context, int resource, ArrayList<Booking> objects) {
        super(context,resource,objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String title = getItem(position).getTitle();
        String author = getItem(position).getAuthor();
        //Date date = getItem(position).getDate();

        Booking booking = new Booking(title,author);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.textView2);
        //TextView tvDate = (TextView) convertView.findViewById(R.id.textView3);

        tvTitle.setText(title);
        tvAuthor.setText(author);
        //tvDate.setText((CharSequence) date);

        return convertView;

    }
}
