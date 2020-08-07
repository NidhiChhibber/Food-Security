package com.example.research_app.DonorActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.research_app.R;
import com.vikktorn.picker.City;
import com.vikktorn.picker.CityPicker;
import com.vikktorn.picker.Country;
import com.vikktorn.picker.CountryPicker;
import com.vikktorn.picker.OnCityPickerListener;
import com.vikktorn.picker.OnCountryPickerListener;
import com.vikktorn.picker.OnStatePickerListener;
import com.vikktorn.picker.State;
import com.vikktorn.picker.StatePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SelectCity extends AppCompatActivity {

    EditText countryNameET, stateNameET, cityNameET;
    Button searchBtn;
    String selectedCity, selectedState, selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        countryNameET = findViewById(R.id.countryNameCitySearchTV);
        stateNameET = findViewById(R.id.stateNameCitySearchTV);
        cityNameET = findViewById(R.id.cityNameCitySearchTV);
        searchBtn = findViewById(R.id.selectCity_search_BTN);
        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean error = false;
                if(countryNameET.getText().toString().isEmpty())
                {
                    countryNameET.setError("This field can not be empty");
                    error=true;
                }
                if(stateNameET.getText().toString().isEmpty())
                {
                    stateNameET.setError("This field can not be empty");
                    error=true;
                }
                if(cityNameET.getText().toString().isEmpty())
                {
                    cityNameET.setError("This field can not be empty");
                    error=true;
                }

                if(error == false)
                {
                    Intent i = new Intent(v.getContext(), DonorDash.class);
                    i.putExtra("cityName", cityNameET.getText().toString());
                    i.putExtra("stateName", stateNameET.getText().toString());
                    i.putExtra("countryName", countryNameET.getText().toString());
                    v.getContext().startActivity(i);
                }
            }
        });


    }

}
