package com.example.sapienzaLib;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private RecyclerView lw;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        lw = (RecyclerView) rootView.findViewById(R.id.list_search);

        final ArrayList<Book> mBooks = new ArrayList<Book>();

        mBooks.add(new Book("Moby tumadre", "Anjelocat", "Sceinadeimerda", "http://www.rapburger.com/wp-content/uploads/2017/10/22310425_1682374125147785_5102209946843909253_n.jpg"));
        mBooks.add(new Book("Moby tumadre", "Anjelocat", "Sceinadeimerda", "/Users/salvatorecolitta/AndroidStudioProjects/SapienzaLib/app/src/main/res/drawable/logo_sap"));
        mBooks.add(new Book("Moby tumadre", "Anjelocat", "Sceinadeimerda", "/Users/salvatorecolitta/AndroidStudioProjects/SapienzaLib/app/src/main/res/drawable/logo_sap"));
        mBooks.add(new Book("Moby tumadre", "Anjelocat", "Sceinadeimerda", "/Users/salvatorecolitta/AndroidStudioProjects/SapienzaLib/app/src/main/res/drawable/logo_sap"));

        mAdapter = new BookListAdapter(mBooks);
        lw.setAdapter(mAdapter);
        lw.setLayoutManager(new LinearLayoutManager(getContext()));
        ((BookListAdapter)mAdapter).setOnItemClickListener(new BookListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("name","tumande");
                Book selectedBook = mBooks.get(position);
                Intent intent = new Intent(getContext(), BookActivity.class);
                intent.putExtra("title",selectedBook.getTitle());
                intent.putExtra("author",selectedBook.getAuthor());
                intent.putExtra("description",selectedBook.getDescription());
                intent.putExtra("thumbnail",selectedBook.getThumbnail());
                startActivity(intent);

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

}
