package com.example.research_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.research_app.ConsumerActivities.ConsumerDash;
import com.example.research_app.DonorActivities.DonorDash;
import com.example.research_app.DonorActivities.DonorHome;
import com.example.research_app.DonorActivities.SearchActivity;
import com.example.research_app.ui.CurrentDataClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class CreateProfileActivity extends AppCompatActivity
{

    private ImageView avatar;
    private EditText nameEF,aboutEF, countryEF, stateEF, cityEF;
    private Button finishB, signOutBtn;
    private FirebaseAuth mAuth;
    private RadioGroup radioUserType;
    private RadioButton userTypeButton;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        avatar = findViewById(R.id.picturebuttonCreateProfileView);
        nameEF = findViewById(R.id.userNameCreateProfileET);
        finishB= findViewById(R.id.createProfile_continue_BTN);
        radioUserType = findViewById(R.id.radioUserType);
        aboutEF = findViewById(R.id.consumerAbout);
        aboutEF.setVisibility(View.INVISIBLE);
        signOutBtn = findViewById(R.id.signOutBtn);
        countryEF = findViewById(R.id.createProfile_countryName_EF);
        stateEF = findViewById(R.id.createProfile_stateName_EF);
        cityEF = findViewById(R.id.createProfile_cityName_EF);


        radioUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                int radioBtnID = group.getCheckedRadioButtonId();

                View radioB = group.findViewById(radioBtnID);
                int position = group.indexOfChild(radioB);
                if(position == 0)
                {
                    aboutEF.setVisibility(View.INVISIBLE);
                }
                if(position== 1)
                {
                    aboutEF.setVisibility(View.VISIBLE);
                }
            }
        });
        finishB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int selectedId = radioUserType.getCheckedRadioButtonId();
                userTypeButton = (RadioButton)findViewById(selectedId);
                userType = userTypeButton.getText().toString();
                boolean error = false;
                if(nameEF.getText().toString().isEmpty())
                {
                    nameEF.setError("This field can not be empty");
                    error = true;
                }
                if(countryEF.getText().toString().isEmpty())
                {
                    countryEF.setError("This field can not be empty");
                    error = true;
                }
                if(stateEF.getText().toString().isEmpty())
                {
                    stateEF.setError("This field can not be empty");
                    error = true;
                }

                if(cityEF.getText().toString().isEmpty())
                {
                    cityEF.setError("This field can not be empty");
                    error = true;
                }

                if(error == false)
                {
                    updateProfile();
                }
            }
        });
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(CreateProfileActivity.this,gso);
                googleSignInClient.signOut();
                Log.d("GSIGNIN", "Signing Out");
                Intent in5 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(in5);
            }
        });
    }

    protected void updateProfile()
    {
        mAuth = FirebaseAuth.getInstance();
        String user_name = nameEF.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String Uid = user.getUid();
        try
        {
            DatabaseReference userRef = database.getReference("users");
            String email = user.getEmail();
            UserDetailsClass newUser = new UserDetailsClass();
            newUser.setName(user_name);
            newUser.setCountry(countryEF.getText().toString());
            newUser.setState(stateEF.getText().toString());
            newUser.setCity(cityEF.getText().toString());
            newUser.setEmail(email);
            newUser.setUid(Uid);
            CurrentDataClass currentData = new CurrentDataClass();
            currentData.setName(user_name);

            if(userType.equals("Register to donate"))
            {
                newUser.setAccType("donor");
                /*userRef.child(Uid).child("accountType").setValue("donor");
                DatabaseReference myRef = database.getReference("donors");
                myRef.child(Uid).setValue(newUser);
                Intent intent = new Intent(CreateProfileActivity.this, DonorHome.class);
                startActivity(intent);*/
            }
            else if ( userType.equals("Register to request donation"))
            {
                String aboutC = aboutEF.getText().toString();
                newUser.setDescription(aboutC);
                newUser.setAccType("consumer");

                /*userRef.child(Uid).child("accountType").setValue("consumer");
                userRef.child(Uid).child("string").setValue("");
                DatabaseReference myRef = database.getReference("consumers");
                myRef.child(Uid).setValue(newUser);
                Intent intent = new Intent(CreateProfileActivity.this, ConsumerDash.class);
                startActivity(intent);*/
            }
            Bundle b = new Bundle();
            Intent i = new Intent(CreateProfileActivity.this, CreateProfileActivity2.class);
            b.putParcelable("userDetails", newUser);
            i.putExtras(b);
            startActivity(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
