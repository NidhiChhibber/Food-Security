package com.example.research_app.ConsumerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.research_app.R;
import com.example.research_app.ui.CreateFreeFormString;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConsumerProfileActivity extends AppCompatActivity {

    private ImageView avatar;
    private ImageButton editName, editAddress, editAbout;
    private TextView userEmail, userType;
    EditText userName, userAddress, userAbout, fieldName;
    private FirebaseAuth mAuth;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_profile);
        init();
        attachListeners();
    }
    protected void onStart()
    {
        super.onStart();
        getUserData();
    }

    protected void init()
    {
        saveBtn =findViewById(R.id.consumerProfile_saveBtn);
        saveBtn.setVisibility(View.INVISIBLE);
        userName = findViewById(R.id.consumerProfile_userNameET);
        userName.setEnabled(false);
        userAddress = findViewById(R.id.consumerProfile_addressET);
        userAddress.setEnabled(false);
        userAbout = findViewById(R.id.consumerProfile_aboutET);
        userAbout.setEnabled(false);
        userEmail = findViewById(R.id.consumerProfile_emailTV);
        userType = findViewById(R.id.consumerProfile_accountTypeTV);
        editName = findViewById(R.id.consumerProfile_updateNameIBtn);
        editName.setVisibility(View.INVISIBLE);
        editAddress = findViewById(R.id.consumerProfile_updateAddressIBtn);
        editAddress.setVisibility(View.INVISIBLE);
        editAbout = findViewById(R.id.consumerProfile_updateAboutIBtn);
        editAbout.setVisibility(View.INVISIBLE);
    }

    protected void attachListeners()
    {
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                editBtnClicked("userName");
            }
        });
        /*
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                editBtnClicked(userAddress);
            }
        });*/
        editAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                editBtnClicked("userAbout");
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(checkTextFields())
                {
                    updateNameDB();
                }
            }
        });
    }
    protected void updateNameDB()
    {

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String userId = user.getUid();
        final DatabaseReference userDetailsRef = database.getReference("consumers/"+userId);
        userDetailsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                final String newName = userName.getText().toString();
                final String newAbout = userAbout.getText().toString();
                userDetailsRef.child("name").setValue(newName);
                userDetailsRef.child("description").setValue(newAbout);
                userName.setEnabled(false);
                userName.setBackgroundColor(Color.WHITE);
                userName.setText(newName);
                userAbout.setEnabled(false);
                userAbout.setBackgroundColor(Color.WHITE);
                userAbout.setText(newAbout);
                CreateFreeFormString.createAndSetFreeformString(userId);
                saveBtn.setVisibility(View.INVISIBLE);
                //Intent intent = new Intent(DonorProfile.this, DonorProfile.class);
                //startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    protected boolean checkTextFields()
    {
        if(userName.getText().toString().isEmpty() || userAbout.getText().toString().isEmpty() || userName.getError()!= null
        || userAbout.getError() != null)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    protected  void editBtnClicked(String fieldNameString)
    {
        if(fieldNameString.equals("userName"))
            fieldName = userName;
        else if(fieldNameString.equals("userAbout"))
            fieldName = userAbout;
        fieldName.setBackgroundColor(Color.GRAY);
        fieldName.setEnabled(true);
        fieldName.setText("");
        fieldName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(fieldName.getText().toString().isEmpty()) {
                    fieldName.setError("Can not be empty");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        saveBtn.setVisibility(View.VISIBLE);
    }

    protected void getUserData()
    {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String userID = user.getUid();
        Log.d("userID", ""+userID);
        DatabaseReference donorDetailsRef = database.getReference("consumers/"+userID);
        donorDetailsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                String name = (String) dataSnapshot.child("name").getValue();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String email = (String) dataSnapshot.child("email") .getValue();
                String state = (String) dataSnapshot.child("state").getValue();
                state = state.substring(0, 1).toUpperCase() + state.substring(1);
                String country = (String) dataSnapshot.child("country").getValue();
                country = country.substring(0, 1).toUpperCase() + country.substring(1);
                String city = (String) dataSnapshot.child("city").getValue();
                city = city.substring(0, 1).toUpperCase() + city.substring(1);
                String type = (String) dataSnapshot.child("accType").getValue();
                type = type.substring(0, 1).toUpperCase() + type.substring(1);
                String about = (String) dataSnapshot.child("description").getValue();
                userName.setText(name);
                userAddress.setText(city+" ,"+state+" ,"+country);
                userEmail.setText(email);
                userType.setText(type);
                userAbout.setText(about);
                editName.setVisibility(View.VISIBLE);
                editAddress.setVisibility(View.VISIBLE);
                editAbout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
