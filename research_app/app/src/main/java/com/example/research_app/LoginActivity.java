package com.example.research_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity
{
    private EditText emailEF,passwordEF;
    private Button loginB;
    private ProgressBar pBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEF = findViewById(R.id.email);
        passwordEF = findViewById(R.id.password);
        loginB = findViewById(R.id.login);
        pBar = findViewById(R.id.progressBar);
        pBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        loginB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loginUser();
            }
        });

    }

    protected void loginUser()
    {
        String strEmail, strPassword;
        strEmail = emailEF.getText().toString();
        strPassword = passwordEF.getText().toString();
        if(!validateEntry(strEmail, strPassword)) return;
        mAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {

                    pBar.setVisibility(View.GONE);
                    FirebaseUser user = mAuth.getCurrentUser();
                    String uid = user.getUid();
                    String email = user.getEmail();
                    HashMap<Object,String> hashMap = new HashMap<>();
                    hashMap.put("email", email);
                    hashMap.put("uid", uid);
                    hashMap.put("name","");
                    hashMap.put("address","");
                    hashMap.put("image","");
                    hashMap.put("acc_type" , "");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users");
                    myRef.child(uid).setValue(hashMap);
                    Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, DonorDash.class);
                    startActivity(intent);

                }
                else
                {
                    FirebaseAuthException error = (FirebaseAuthException)task.getException();
                    Toast.makeText(getApplicationContext(), "Login Failed: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    protected boolean validateEntry(String strEmail, String strPassword)
    {

        if(TextUtils.isEmpty(strEmail))
        {
            Toast.makeText(getApplicationContext(), "Email-Id field can not be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(strPassword))
        {
            Toast.makeText(getApplicationContext(), "Password field can not be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}
