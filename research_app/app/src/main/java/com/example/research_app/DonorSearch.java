package com.example.research_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

public class DonorSearch extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        getLayoutInflater().inflate(R.layout.activity_donor_search, contentFrameLayout);
    }
}
