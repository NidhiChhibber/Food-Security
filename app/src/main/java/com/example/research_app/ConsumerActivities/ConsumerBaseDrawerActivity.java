package com.example.research_app.ConsumerActivities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.research_app.DonorActivities.DonorCart;
import com.example.research_app.DonorActivities.DonorDash;
import com.example.research_app.DonorActivities.DonorProfile;
import com.example.research_app.DonorActivities.DonorSearch;
import com.example.research_app.LoginActivity;
import com.example.research_app.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ConsumerBaseDrawerActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_base_drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.consumerNavView);
        toolbar = findViewById(R.id.consumerNavToolbar);
        setSupportActionBar(toolbar);
        drawerLayout =  findViewById(R.id.consumerDrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this ,drawerLayout, toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.consumerNavProfile:
                        Intent in1 = new Intent(getApplicationContext(), ConsumerProfileActivity.class);
                        startActivity(in1);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.consumerNavHome:
                        Intent in2 = new Intent(getApplicationContext(), ConsumerDash.class);
                        startActivity(in2);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.consumerNavSearch:
                        //Intent in3 = new Intent(getApplicationContext(), ConsumerSearch.class);
                        //startActivity(in3);
                        //drawerLayout.closeDrawers();
                        //break;
                    case R.id.consumerNavItems:
                        /*Intent in4 = new Intent(getApplicationContext(), ConsumerItems.class);
                        startActivity(in4);
                        drawerLayout.closeDrawers();
                        break;*/
                    case R.id.signOutConsumer:
                        signOut();
                        Intent in5 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(in5);
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public void signOut()
    {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut();
        Log.d("GSIGNIN", "Signing Out");
    }
}
