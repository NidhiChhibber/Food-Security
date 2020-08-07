package com.example.research_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class BaseDrawerActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout =  findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this ,drawerLayout, toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId())
                {

                    case R.id.nav_profile:
                        Intent in1 = new Intent(getApplicationContext(), DonorProfile.class);
                        startActivity(in1);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_home:
                        Intent in2 = new Intent(getApplicationContext(), DonorDash.class);
                        startActivity(in2);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_search:
                        Intent in3 = new Intent(getApplicationContext(), DonorSearch.class);
                        startActivity(in3);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_cart:
                        Intent in4 = new Intent(getApplicationContext(), DonorCart.class);
                        startActivity(in4);
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


}
