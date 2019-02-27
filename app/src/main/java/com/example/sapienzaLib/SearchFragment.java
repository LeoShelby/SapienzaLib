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

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        FloatingSearchView floatingSearchView = rootView.findViewById(R.id.floating_search_view);
        lw = (RecyclerView) rootView.findViewById(R.id.list_search);

        final ArrayList<Book> mBooks = new ArrayList<Book>();

        mBooks.add(new Book("Regardex Moi", "Frah Quintale", "Album di FQ", "http://www.rapburger.com/wp-content/uploads/2017/10/22310425_1682374125147785_5102209946843909253_n.jpg"));

        mAdapter = new BookListAdapter(mBooks, getActivity());
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

        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                try {
                    String response = BackendUtilities.getBookByQuery(currentQuery);
                    JSONObject jObject = new JSONObject(response);
                    JSONArray jArray = jObject.getJSONArray("items");
                    for (int i=0; i < jArray.length(); i++)
                    {
                        try {
                            JSONObject oneObject = jArray.getJSONObject(i);
                            // Pulling items from the array
                            String oneObjectsItem = oneObject.getString("title");
                            mBooks.add(new Book(oneObjectsItem, "Frah Quintale", "Album di FQ", "http://www.rapburger.com/wp-content/uploads/2017/10/22310425_1682374125147785_5102209946843909253_n.jpg"));

                        } catch (JSONException e) {
                            // Oops
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }



}
