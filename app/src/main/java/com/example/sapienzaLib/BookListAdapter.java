package com.example.sapienzaLib;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    public List<Book> getmBooks() {
        return mBooks;
    }

    // Store a member variable for the contacts
    private List<Book> mBooks;
    private Activity activity;

    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Pass in the contact array into the constructor
    public BookListAdapter(List<Book> books, Activity activity) {
        mBooks = books;
        this.activity = activity;
    }

    @NonNull
    @Override
    public BookListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bookView = inflater.inflate(R.layout.book_item_layout, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(bookView);

    }

    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.ViewHolder myViewHolder, int i) {
        Book book = mBooks.get(i);

        myViewHolder.titleTextView.setText(book.getTitle());
        myViewHolder.authorTextView.setText(book.getAuthor());
        myViewHolder.descriptionTextView.setText(book.getDescription());
        Picasso.with(activity).load(book.getThumbnail()).fit().into(myViewHolder.thumbnailImageView);

    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTextView,authorTextView,descriptionTextView;
        public ImageView thumbnailImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.book_list_title);
            authorTextView = (TextView) itemView.findViewById(R.id.book_list_author);
            descriptionTextView = (TextView) itemView.findViewById(R.id.book_list_description);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.book_list_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        // Handles the row being being clicked


    }
}
