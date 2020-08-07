package com.example.research_app.ConsumerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.research_app.R;
import com.example.research_app.ui.CreateFreeFormString;
import com.example.research_app.ui.RequiredItemsList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ConsumerRequestItemActivity extends AppCompatActivity {
    EditText itemQuantityET, itemUnitsET;
    AutoCompleteTextView itemNameET;
    TextView itemDateTV;
    Button putRequestBtn;
    String itemName, itemDate, itemUnits, userName, userCity, userCountry, userState, userDescription, itemString, freeFormString;
    int itemQuantity;
    private FirebaseAuth mAuth;
    final FirebaseDatabase db = FirebaseDatabase.getInstance();
    final Calendar myCalendar = Calendar.getInstance();
    ImageButton calenderImgBtn;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_request_item);
        itemDateTV = findViewById(R.id.consumerReqItemDateTV);
        itemNameET = findViewById(R.id.consumerReqItemName);
        itemQuantityET = findViewById(R.id.consumerReqItemQuantity);
        putRequestBtn = findViewById(R.id.requestBtn);
        itemUnitsET = findViewById(R.id.consumerReqItemUnits);
        calenderImgBtn = findViewById(R.id.calenderImgBtn);


        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        ItemAutoTextAdapter adapter = new ItemAutoTextAdapter(
                ConsumerRequestItemActivity.this, databaseAccess);
        itemNameET.setAdapter(adapter);
        itemNameET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                itemNameET.setText(itemNameET.getText());
                itemQuantityET.requestFocus();
            }
        });

        putRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserRequestDatabase();
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                view.setMinDate(System.currentTimeMillis() - 1000);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        itemDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalender(date);

            }
        });
        calenderImgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openCalender(date);
            }
        });


    }

    public void openCalender(DatePickerDialog.OnDateSetListener date)
    {
        DatePickerDialog mDate = new DatePickerDialog(ConsumerRequestItemActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDate.show();
    }

    public void updateUserRequestDatabase() {
        mAuth = FirebaseAuth.getInstance();
        itemName = itemNameET.getText().toString();
        itemDate = itemDateTV.getText().toString();
        itemQuantity = Integer.parseInt(itemQuantityET.getText().toString());
        itemUnits = itemUnitsET.getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference itemDetailsRef = db.getReference("consumerRequests");
        final String uId = user.getUid();
        RequiredItemsList newRequest = new RequiredItemsList();
        newRequest.setName(itemName);
        newRequest.setDate(itemDate);
        newRequest.setQuantity(itemQuantity);
        newRequest.setUnits(itemUnits);
        itemDetailsRef.child(uId).child(itemName).setValue(newRequest);

        final DatabaseReference myRef2 = db.getReference("consumers/" + uId);
        myRef2.child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("name")) {

                } else {
                    myRef2.child("items").child(itemName).setValue(itemName);
                    setFreeFormString();
                    clearViews();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void clearViews() {
        itemNameET.getText().clear();
        itemDateTV.setText("");
        itemQuantityET.getText().clear();
        itemUnitsET.getText().clear();
    }

    public void setFreeFormString() {
        FirebaseUser user = mAuth.getCurrentUser();
        final String uId = user.getUid();
        CreateFreeFormString.createAndSetFreeformAndItemString(uId);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        itemDateTV.setText(sdf.format(myCalendar.getTime()));
    }

    /*private void showResults(String query) {

        Cursor cursor = databaseAccess.searchCustomer((query != null ? query.toString() : "@@@@"));

        adapter = new MyRecyclerAdapter(ConsumerRequestItemActivity.this, cursor);
        recyclerView.setAdapter(adapter);
    }*/
}