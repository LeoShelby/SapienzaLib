package com.example.sapienzaLib;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        lw = (RecyclerView) rootView.findViewById(R.id.list_search);

        ArrayList<Book> mBooks = new ArrayList<Book>();

        mBooks.add(new Book("Moby tumadre", "Anjelocat", "Sceinadeimerda", "/Users/salvatorecolitta/AndroidStudioProjects/SapienzaLib/app/src/main/res/drawable/logo_sap.png"));
        mBooks.add(new Book("Moby tumadre", "Anjelocat", "Sceinadeimerda", "/Users/salvatorecolitta/AndroidStudioProjects/SapienzaLib/app/src/main/res/drawable/logo_sap.png"));
        mBooks.add(new Book("Moby tumadre", "Anjelocat", "Sceinadeimerda", "/Users/salvatorecolitta/AndroidStudioProjects/SapienzaLib/app/src/main/res/drawable/logo_sap.png"));
        mBooks.add(new Book("Moby tumadre", "Anjelocat", "Sceinadeimerda", "/Users/salvatorecolitta/AndroidStudioProjects/SapienzaLib/app/src/main/res/drawable/logo_sap.png"));

        mAdapter = new BookListAdapter(mBooks);
        lw.setAdapter(mAdapter);
        lw.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return rootView;
    }

}
