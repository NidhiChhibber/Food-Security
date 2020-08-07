package com.example.research_app.DonorActivities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import com.example.research_app.R;
import com.example.research_app.ui.CustomAdapterSearch;

import java.util.ArrayList;

public class DonorSearch extends BaseDrawerActivity {

    RecyclerView recyclerView;
    EditText editTextSearch;
    ArrayList<String> items;
    CustomAdapterSearch adapter;
    private MyDB mDbHelper;
    public MyDB myDB = new MyDB(this);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        getLayoutInflater().inflate(R.layout.activity_donor_search, contentFrameLayout);
        items = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CustomAdapterSearch(items);

        recyclerView.setAdapter(adapter);
        mDbHelper = new MyDB(this);
        mDbHelper.open();
        mDbHelper.deleteAllNames();
        loadDB();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                //after the change calling the method and passing the search input

                filter(editable.toString());
            }
        });
    }

    private void filter(String text)
    {
        //new array list that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : items) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }
    public void loadDB()
    {
        ArrayList<String> itemStrings= new ArrayList<String>();
        itemStrings.add( "Granola");
        itemStrings.add( "Orange Juice");
        itemStrings.add( "Orange");
        itemStrings.add( "Oatmeal Toasters, Cranberry Orange");
        itemStrings.add( "Milk, 2% Reduced Fat");
        itemStrings.add( "Pancakes, Chocolate Chip");
        itemStrings.add( "SpaghettiOs, Meatballs");
        itemStrings.add( "Romaine Hearts");
        itemStrings.add( "Bean and Beef Enchiladas");
        itemStrings.add( "Kalamata Olive");
        itemStrings.add( "Sour Cream, Light");

        for(String itemName : itemStrings)
        {
            mDbHelper.createList(itemName);
        }
    }

}