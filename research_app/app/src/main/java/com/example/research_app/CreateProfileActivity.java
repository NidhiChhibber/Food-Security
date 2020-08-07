package com.example.research_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CreateProfileActivity extends AppCompatActivity {

    private ImageView avatar;
    private TextView tvEF;
    private EditText nameEF,addressEF;
    private Button finishB;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        avatar = findViewById(R.id.avatar);
        nameEF = findViewById(R.id.name);
        tvEF = findViewById(R.id.tv);
        addressEF = findViewById(R.id.address);
        finishB= findViewById(R.id.finish);
        finishB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateProfile();
            }
        });

    }

    protected void updateProfile()
    {
        mAuth = FirebaseAuth.getInstance();
        String user_address = addressEF.getText().toString();
        String user_name = nameEF.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final String Uid = user.getUid();
        final DatabaseReference myRef = db.getReference("Users");
        try {
            myRef.child(Uid).child("address").setValue(user_address);
            myRef.child(Uid).child("name").setValue(user_name);
            Query query = myRef.orderByChild("uid").equalTo(Uid);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        for (DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            String acctype = ds.child("acc_type").getValue(String.class);
                            if(acctype.equals("Donor"))
                            {
                                tvEF.setText("DONE");
                                Intent intent = new Intent(CreateProfileActivity.this, DonorDash.class);
                               startActivity(intent);
                            }
                            else if ( acctype.equals("Consumer"))
                            {
                               // Intent intent = new Intent(CreateProfileActivity.this, ConsumerDashboard.class);
                                //startActivity(intent);
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
