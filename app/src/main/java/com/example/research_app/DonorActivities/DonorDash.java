package com.example.research_app.DonorActivities;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.util.Random;
import com.example.research_app.CosumerClasses.RequestItemsClass;
import com.example.research_app.R;
import com.example.research_app.ui.ConsumerSuggestionList;
import com.example.research_app.ui.CustomAdapter;
import com.example.research_app.ui.RequiredItemsList;
import com.example.research_app.ui.callbackForListeners;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.*;


public class DonorDash extends BaseDrawerActivity
{
    List<ConsumerSuggestionList> consumerList;
    RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private CustomAdapter adapter;
    String cityName, stateName, countryName;
    public  ArrayList<String> uIdList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        consumerList = new ArrayList<>();
        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        stateName = intent.getStringExtra("stateName");
        countryName = intent.getStringExtra("countryName");

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        getLayoutInflater().inflate(R.layout.activity_donor_dash, contentFrameLayout);

    }

    @Override
    public void onStart()
    {

        super.onStart();
        uIdList.clear();
        consumerList.clear();
        getConsumerData();
    }

    protected void getConsumerData()
    {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("consumers");
        Query query = myRef.orderByChild("city").equalTo(cityName);
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {

                        final String desc = (String) ds.child("description").getValue();
                        final String name = (String) ds.child("name").getValue();
                        final String userId = (String) ds.child("uid").getValue();
                        final String userState = (String) ds.child("state").getValue();
                        final String userCountry = (String) ds.child("country").getValue();
                        final String userCity = (String) ds.child("city").getValue();
                        Log.d("Address" ,"From DB is :"+userState+" "+userCountry);
                        DatabaseReference requestRef = database.getReference("consumerRequests/"+userId);
                        if(userCountry.equals(countryName) && userState.equals(stateName)) {
                            requestRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        uIdList.add(userId);
                                        Log.i("Count ", "" + dataSnapshot.getChildrenCount());
                                        ArrayList<String> itemListDatabase = new ArrayList<>();

                                        Log.i("After", "" + itemListDatabase.size());
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            RequiredItemsList post = (RequiredItemsList) ds.getValue(RequiredItemsList.class);


                                            itemListDatabase.add("" + post.getName());

                                        }
                                        ConsumerSuggestionList consumer = new ConsumerSuggestionList();

                                        consumer.setName(name);
                                        consumer.setId(userId);
                                        consumer.setCountry(countryName);
                                        consumer.setState(stateName);
                                        consumer.setCity(cityName);
                                        consumer.setShortdesc(desc);
                                        consumer.setItems(itemListDatabase);
                                        consumerList.add(consumer);
                                        setView();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    }
                    setView();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    public void setView()
    {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(this, uIdList, consumerList);
        recyclerView.setAdapter(adapter);
    }

    public String createTitle(String word)
    {
        return (word.substring(0, 1).toUpperCase() + word.substring(1));
    }

}