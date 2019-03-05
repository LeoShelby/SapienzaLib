package com.example.sapienzaLib;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingListAdapter extends ArrayAdapter<Booking> {

    private Context mContext;
    int mResource;
    private String type_booking;

    //public BookingListAdapter(MainActivity mainActivity, int booking_view_layout, ArrayList<Booking> peopleList) {
    public BookingListAdapter(Context context, int resource, ArrayList<Booking> objects, String type_booking) {
        super(context,resource,objects);
        mContext = context;
        mResource = resource;
        this.type_booking = type_booking;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String title = getItem(position).getTitle();
        String author = getItem(position).getAuthor();
        Date date = getItem(position).getDate();

        int copies = getItem(position).getCopies();



        //Booking booking = new Booking(title,author,date);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTitle = convertView.findViewById(R.id.textView2);
        TextView tvAuthor = convertView.findViewById(R.id.textView3);
        TextView tvDate = convertView.findViewById(R.id.textView1);

        tvTitle.setText(title);
        tvAuthor.setText(author);

        String s = "";
        if(date!=null) s = new SimpleDateFormat("MM-dd-YYYY").format(date);

        if(this.type_booking.equals("Fresh")){
            tvDate.setText("Expiring on: "+s);
            tvDate.setTextColor(Color.parseColor( "#009900"));
        }

        if(this.type_booking.equals("Exp")){
            tvDate.setText("Expiring on: "+s);
            tvDate.setTextColor(Color.RED);
        }

        if(this.type_booking.equals("Pop")){
            if(copies > 0){
                tvDate.setText("Available now");
                tvDate.setTextColor(Color.parseColor( "#009900"));
            }
            else{
                tvDate.setText("Available soon");
                tvDate.setTextColor(Color.RED);
            }
        }
        return convertView;

    }



    public void update() {
        this.notifyDataSetChanged();
    }
}
