package com.example.research_app.DonorActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.research_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonationQueriesActivity extends AppCompatActivity
{
    private MyDB mDbHelper;
    private SimpleCursorAdapter dataAdapter;
    EditText donorId,donationDays;
    Button searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_queries);
        mDbHelper = MyDB.getInstance(this);
        donorId = findViewById(R.id.donationQuery_donorId_ET);
        donationDays = findViewById(R.id.donationQuery_days_ET);
        searchBtn = findViewById(R.id.donationQuery_search_BTN);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String donorIdStr = donorId.getText().toString();
                String days = donationDays.getText().toString();
                // 1 All consumers that donor has donated to
                queryOne();
                // 2 All donor's that donate to the consumer
                //queryTwo();
                // 3 All consumers the donor has donated to in last x days (modified 1)
                //queryThree(donorIdStr,days);
                // 4 All donor's that have donated to the consumer in last x days (modified 2)
                //queryFour(donorIdStr, days);
                // 5 donor's last consumer
                //queryFive(donorIdStr);
                // 6 consumer's last donor
                //querySix(donorIdStr);
                // 7 donor's top consumers
                //querySeven(donorIdStr);
                // 8 consumer's top donors
                //queryEight(donorIdStr);
            }
        });


    }

    public void queryOne()
    {
        Cursor c = mDbHelper.DonorsAllConsumers("n7MVFfzsewVY27KnwpoaP1v10SL2");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String[] columns = new String[] {"D_C_CONSUMER"};
        int[] to = new int[]{R.id.donationQueryListName};
        attachAdapter(c,columns,to);

    }


    public void queryTwo()
    {
        Cursor c = mDbHelper.ConsumersAllDonors("pEBbygcHUVRQRZCf1lW8pKeDR2n2");
        String[] columns = new String[] {"D_C_DONOR"};
        int[] to = new int[]{R.id.donationQueryListName};
        attachAdapter(c,columns,to);
    }

    public void queryThree(String donorId, String days)
    {
        Cursor c = mDbHelper.DonorsAllConsumersInDays(donorId, days);
        String[] columns = new String[] {"D_C_CONSUMER"};
        int[] to = new int[]{R.id.donationQueryListName};
        attachAdapter(c,columns,to);
    }

    public void queryFour(String consumerId, String days)
    {
        Cursor c = mDbHelper.ConsumersAllDonorsInDays(consumerId, days);
        String[] columns = new String[] {"D_C_DONOR"};
        int[] to = new int[]{R.id.donationQueryListName};
        attachAdapter(c,columns,to);
    }

    public void queryFive(String donorId)
    {
        Cursor c = mDbHelper.donorsLastConsumer(donorId);
        String[] columns = new String[] {"D_C_CONSUMER"};
        int[] to = new int[]{R.id.donationQueryListName};
        attachAdapter(c,columns,to);
    }

    public void querySix(String consumerId)
    {
        Cursor c = mDbHelper.consumersLastDonor(consumerId);
        String[] columns = new String[] {"D_C_DONOR"};
        int[] to = new int[]{R.id.donationQueryListName};
        attachAdapter(c,columns,to);
    }

    public void querySeven(String donorId)
    {
        Cursor c = mDbHelper.topConsumer(donorId);
        String[] columns = new String[] {"KEY_NUM_DONATIONS_CONSUMER_ID"};
        int[] to = new int[]{R.id.donationQueryListName};
        attachAdapter(c,columns,to);
    }

    public void queryEight(String consumerId)
    {
        Cursor c = mDbHelper.topDonor(consumerId);
        String[] columns = new String[] {"KEY_NUM_DONATIONS_DONOR_ID"};
        int[] to = new int[]{R.id.donationQueryListName};
        attachAdapter(c,columns,to);
    }

    public void attachAdapter(Cursor c, String[] columns,int[] to)
    {
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.list_item,
                c,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.donationQueryListView);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
    }
}
