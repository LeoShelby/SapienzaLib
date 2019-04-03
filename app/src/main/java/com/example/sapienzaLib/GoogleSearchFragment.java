package com.example.sapienzaLib;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GoogleSearchFragment extends SearchFragment {

    BookListAdapter mAdapter;
    RecyclerView lw;
    ArrayList<Book> mBooks;

    public GoogleSearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater,container,savedInstanceState);
        FloatingSearchView floatingSearchView = rootView.findViewById(R.id.floating_search_view);
        mAdapter = (BookListAdapter) getmAdapter();
        mBooks = (ArrayList<Book>) mAdapter.getmBooks();
        lw = getLw();

        mAdapter.setOnItemClickListener(new BookListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Book selectedBook = mBooks.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("") //
                        .setMessage("Do you really want to add: "+selectedBook.getTitle()) //
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // TODO
                                try {
                                    BackendUtilities.postBookByISBN(selectedBook.getIsbn());
                                    Snackbar.make(rootView, "Book added with success !", Snackbar.LENGTH_SHORT).show();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                            }
                        }) //
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // TODO
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });
        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                try {
                    mBooks.clear();
                    Request request = BackendUtilities.getBooksOnGoogle(currentQuery);
                    BackendUtilities.client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try (ResponseBody responseBody = response.body()) {
                                JSONObject jObject = new JSONObject(responseBody.string());
                                JSONArray jArray = jObject.getJSONArray("items");
                                for (int i = 0; i < jArray.length(); i++) {
                                    try {
                                        JSONObject oneObject = jArray.getJSONObject(i);
                                        // Pulling items from the array
                                        String title = oneObject.getString("title");
                                        String desc = oneObject.getString("description");
                                        String thumb = oneObject.getString("thumbnail");
                                        String auth = oneObject.getString("author");
                                        String isbn = oneObject.getString("isbn");

                                        if (desc.equals(""))
                                            desc = "Questo libro non possiede una descrizione";

                                        mBooks.add(new Book(title, auth, desc, thumb, isbn, "", ""));

                                    } catch (JSONException e) {
                                        // Oops
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("valco", mBooks.size()+"");
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Clicca sul libro da aggiungere", Toast.LENGTH_SHORT);
                                    mAdapter.notifyDataSetChanged();
                                }
                            });


                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        return rootView;
    }

}
