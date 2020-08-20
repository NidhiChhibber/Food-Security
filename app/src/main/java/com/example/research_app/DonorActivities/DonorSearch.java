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

    private MyDB mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        getLayoutInflater().inflate(R.layout.activity_donor_search, contentFrameLayout);


        //mDbHelper = MyDB.getInstance(this);
        //mDbHelper.deleteDatabase();
        //long donorInsertKey = mDbHelper.addDonor("randomID","randomName");
        //long keyDonations = mDbHelper.addDonation("randomdonorid","randomconsumerid");
        //mDbHelper.addOrder(keyDonations,"Fish",5,"2/2/2020","2:30:00");
        //mDbHelper.closeDB();
    }
}