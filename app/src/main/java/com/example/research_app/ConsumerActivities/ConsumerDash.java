package com.example.research_app.ConsumerActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.research_app.CosumerClasses.ConsumerDashItemList;
import com.example.research_app.CosumerClasses.ConsumerDashAdapter;
import com.example.research_app.DonorActivities.BaseDrawerActivity;
import com.example.research_app.DonorActivities.FreeformSearchActivity;
import com.example.research_app.DonorActivities.SearchActivity;
import com.example.research_app.R;

import java.util.ArrayList;
import java.util.List;

public class ConsumerDash extends ConsumerBaseDrawerActivity
{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton putRequestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.consumerFragmentContainer);
        getLayoutInflater().inflate(R.layout.activity_consumer_dash, contentFrameLayout);
        putRequestBtn = findViewById(R.id.putRequestTV);
        putRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumerDash.this, ConsumerRequestItemActivity.class);
                startActivity(intent);
            }
        });

    }
}
