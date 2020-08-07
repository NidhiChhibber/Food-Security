package com.example.research_app.DonorActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

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

public class FreeformSearchActivity extends BaseDrawerActivity
{
    String keyword;
    FirebaseAuth mAuth;
    List<ConsumerSuggestionList> consumerList;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    public  ArrayList<String> uIdList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        consumerList = new ArrayList<>();
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
        Log.d("SEE", "started");
        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("consumers");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    String freeformString = ds.child("string").getValue(String.class);
                    Log.d("SEE", "" + freeformString);

                    if(!freeformString.equals("")) {
                        if (freeformString.contains(keyword)) {
                            final String desc = (String) ds.child("description").getValue();
                            final String name = (String) ds.child("name").getValue();
                            final String userId = (String) ds.child("uid").getValue();
                            final String userState = (String) ds.child("state").getValue();
                            final String userCountry = (String) ds.child("country").getValue();
                            final String userCity = (String) ds.child("city").getValue();
                            Log.d("SEE", "" + desc + name);
                            DatabaseReference requestRef = database.getReference("consumerRequests/" + userId);
                            requestRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        uIdList.add(userId);
                                        ArrayList<String> itemListDatabase = new ArrayList<>();
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
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
                Log.d("SEE", "called2");
                setView();
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
