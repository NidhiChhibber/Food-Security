package com.example.research_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button regD, regC, loginB;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginB =  findViewById(R.id.login);
        regD = findViewById(R.id.register_donor);
        regC = findViewById(R.id.register_consumer);
        regD.setOnClickListener(this);
        regC.setOnClickListener(this);
        loginB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.register_donor)
        {
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("user_type", "Donor");
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.register_consumer)
        {
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("user_type", "Consumer");
            intent.putExtras(bundle);
            startActivity(intent);
        }

        else if(v.getId()==R.id.login)
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

}
