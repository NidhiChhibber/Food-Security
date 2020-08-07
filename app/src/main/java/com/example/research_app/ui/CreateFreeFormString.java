package com.example.research_app.ui;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateFreeFormString
{
    static String userName, userCity,userCountry,userState,userDescription,itemString, freeFormString;
    final static FirebaseDatabase db = FirebaseDatabase.getInstance();

    public static void createAndSetFreeformAndItemString(final String uId)
    {
        final DatabaseReference consumerDetailRef = db.getReference("consumers/"+uId);
        consumerDetailRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                itemString ="";
                freeFormString ="";
                userName = (String) dataSnapshot.child("name").getValue();
                userCountry = (String) dataSnapshot.child("country").getValue();
                userState = (String) dataSnapshot.child("state").getValue();
                userCity = (String) dataSnapshot.child("city").getValue();
                userDescription = (String) dataSnapshot.child("description").getValue();
                freeFormString = userName +" "+userCountry+" "+ userState+ " "+ userCity+ " "+userDescription;
                DatabaseReference requestRef = db.getReference("consumerRequests/" + uId);
                requestRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists()) {

                            for (DataSnapshot ds : dataSnapshot.getChildren())
                            {
                                RequiredItemsList post = (RequiredItemsList) ds.getValue(RequiredItemsList.class);
                                freeFormString = freeFormString+" "+post.getName();
                                itemString = itemString+" "+post.getName();

                            }

                        }

                        db.getReference("consumers/"+uId).child("itemString").setValue(itemString.toLowerCase());
                        db.getReference("consumers/"+uId).child("string").setValue(freeFormString.toLowerCase());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void createAndSetFreeformString(final String uId)
    {
        final DatabaseReference consumerDetailRef = db.getReference("consumers/"+uId);
        consumerDetailRef.addListenerForSingleValueEvent(new ValueEventListener()

        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                freeFormString ="";
                userName = (String) dataSnapshot.child("name").getValue();
                userCountry = (String) dataSnapshot.child("country").getValue();
                userState = (String) dataSnapshot.child("state").getValue();
                userCity = (String) dataSnapshot.child("city").getValue();
                userDescription = (String) dataSnapshot.child("description").getValue();
                itemString = (String) dataSnapshot.child("itemString").getValue();
                freeFormString = userName +" "+userCountry+" "+ userState+ " "+ userCity+ " "+userDescription+ " "+itemString;
                db.getReference("consumers/"+uId).child("string").setValue(freeFormString.toLowerCase());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

