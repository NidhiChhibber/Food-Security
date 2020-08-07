package com.example.research_app.DonorActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.research_app.R;
import com.example.research_app.ui.ConsumerSuggestionList;
import com.example.research_app.ui.CustomAdapter;
import com.example.research_app.ui.RequiredItemsList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchByItemAcitivity extends BaseDrawerActivity {

    List<ConsumerSuggestionList> consumerList;
    RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private CustomAdapter adapter;
    public ArrayList<String> uIdList = new ArrayList<>();
    String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        consumerList = new ArrayList<>();
        final Intent intent = getIntent();
        itemName = intent.getStringExtra("itemName");
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        getLayoutInflater().inflate(R.layout.activity_donor_dash, contentFrameLayout);
    }
    @Override
    protected void onStart()
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
        final DatabaseReference consumerDetailRef = database.getReference("consumers");
        consumerDetailRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()) {
                    for (final DataSnapshot consumerDS : dataSnapshot.getChildren())
                    {
                        final String itemString = (String) consumerDS.child("itemString").getValue();
                        if(!itemString.equals(""))
                        {
                            if (itemString.contains(itemName.toLowerCase()))
                            {
                                final String desc = (String) consumerDS.child("description").getValue();
                                final String name = (String) consumerDS.child("name").getValue();
                                final String userId = (String) consumerDS.child("uid").getValue();
                                final String userState = (String) consumerDS.child("state").getValue();
                                final String userCountry = (String) consumerDS.child("country").getValue();
                                final String userCity = (String) consumerDS.child("city").getValue();
                                DatabaseReference requestRef = database.getReference("consumerRequests/" + userId);
                                requestRef.addListenerForSingleValueEvent(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                    {
                                        if (dataSnapshot.exists())
                                        {
                                            uIdList.add(userId);
                                            ArrayList<String> itemListDatabase = new ArrayList<>();
                                            for (DataSnapshot ds : dataSnapshot.getChildren())
                                            {
                                                RequiredItemsList post = (RequiredItemsList) ds.getValue(RequiredItemsList.class);
                                                Log.i("Get Data", "" + post.getName());

                                                itemListDatabase.add("" + post.getName());

                                            }
                                            ConsumerSuggestionList consumer = new ConsumerSuggestionList();
                                            consumer.setName(name);
                                            consumer.setId(userId);
                                            consumer.setCountry(userCountry);
                                            consumer.setState(userState);
                                            consumer.setCity(userCity);
                                            consumer.setShortdesc(desc);
                                            consumer.setItems(itemListDatabase);
                                            consumer.setImage(R.mipmap.ic_launcher);
                                            consumerList.add(consumer);
                                            Log.d("SEE", "called1");
                                            setView();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void setView()
    {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(this, uIdList, consumerList);
        recyclerView.setAdapter(adapter);
    }
}
