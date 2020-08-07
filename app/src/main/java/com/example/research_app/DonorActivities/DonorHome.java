package com.example.research_app.DonorActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.research_app.ConsumerActivities.ConsumerBaseDrawerActivity;
import com.example.research_app.CosumerClasses.ConsumerDashAdapter;
import com.example.research_app.CosumerClasses.ConsumerDashItemList;
import com.example.research_app.R;
import com.example.research_app.ui.CurrentDataClass;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class DonorHome extends BaseDrawerActivity
{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton keywordSearchBtn, locationSearchBtn, itemSearchBtn;
    private FirebaseAuth mAuth;
    final FirebaseDatabase db = FirebaseDatabase.getInstance();
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        getLayoutInflater().inflate(R.layout.activity_donor_home, contentFrameLayout);
        mAuth = FirebaseAuth.getInstance();;
        FirebaseUser user = mAuth.getCurrentUser();
        final String uId = user.getUid();
        final DatabaseReference myRef2 = db.getReference("donors/" + uId);
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                    name = dataSnapshot.child("name").getValue().toString();
                    setName(name);
                    Toast.makeText(getApplicationContext(), "xx"+name, Toast.LENGTH_LONG);
                    Log.d("NAME","name is"+name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        keywordSearchBtn = findViewById(R.id.keywordSearchBtn);
        locationSearchBtn = findViewById(R.id.locationSearchBtn);
        itemSearchBtn = findViewById(R.id.itemSearchBtn);
        keywordSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(v.getContext(),SearchActivity.class);
                i.putExtra("position", 0);
                v.getContext().startActivity(i);
            }
        });
        locationSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SelectCity.class);
                v.getContext().startActivity(i);

            }
        });
        itemSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SearchActivity.class);
                i.putExtra("position", 2);
                v.getContext().startActivity(i);

            }
        });


    }
}
