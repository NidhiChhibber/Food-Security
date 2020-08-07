package com.example.research_app.ui;

import com.google.firebase.database.DataSnapshot;

public interface callbackForListeners {

    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}