package com.example.research_app.DonorActivities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.research_app.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends BaseDrawerActivity {
    private EditText serchTextBox;
    private Button searchB;
    private TextView searchTextTV;
    PlacesClient placesClient;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.country_item_search);

        searchTextTV = (TextView) findViewById(R.id.searchActivityTypeTV);
        serchTextBox = findViewById(R.id.keywordSearchActivityTV);
        searchB = findViewById(R.id.searchActivitySearchBtn);
        Intent i = getIntent();
        final int position = i.getIntExtra("position", 0);
        if(position == 0) searchTextTV.setHint("Enter keyword to search for");
        if(position == 2) searchTextTV.setHint("Enter the item name to search for");
        searchB.setOnClickListener(
                new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String searchText = serchTextBox.getText().toString().toLowerCase();
                if(serchTextBox.getText().toString().isEmpty())
                {
                    serchTextBox.setHint("Please enter a keyword");
                    serchTextBox.setError("Keyword is required");
                }
                else if(position == 0)
                {
                    Intent intent = new Intent(SearchActivity.this, FreeformSearchActivity.class);
                    intent.putExtra("keyword", searchText);
                    startActivity(intent);
                }
                else if(position == 2)
                {
                    Intent intent = new Intent(SearchActivity.this, SearchByItemAcitivity.class);
                    intent.putExtra("itemName", searchText);
                    startActivity(intent);
                }

            }

        });
    }
    public void initializePlaces()
    {

    }
    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }

}
