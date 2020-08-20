package com.example.research_app.DonorActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.research_app.R;

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
                //queryOne();
                // 2 All D's that donate to a C
                //queryTwo();
                // 3 All consumers D has donated to in last x days (modified 1)
                //queryThree(donorIdStr,days);
                // 4 All D's that have donated to C in last x days (modified 2)
                //queryFour(donorIdStr, days);
                // 5 D's last consumer
                //queryFive(donorIdStr);
                // 6 C's last donor
                //querySix(donorIdStr);
                // 7 D's top consumers
                querySeven(donorIdStr);
                // 8 C's top donors
            }
        });


    }

    public void queryOne()
    {
        Cursor c = mDbHelper.DonorsAllConsumers("n7MVFfzsewVY27KnwpoaP1v10SL2");
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
        String[] columns = new String[] {"NUM_OF_DONATIONS"};
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
