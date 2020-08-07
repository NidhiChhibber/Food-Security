package com.example.research_app.DonorActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.research_app.R;
import com.example.research_app.ui.NewRequiredItemListAdapter;
import com.example.research_app.ui.RecyclerItemClickListener;
import com.example.research_app.ui.RequiredItemListAdapter;
import com.example.research_app.ui.RequiredItemsList;

import java.util.ArrayList;

public class NewConsumerPublicProfileActivity extends AppCompatActivity
{
    TextView cName,cAddress,cDescription;
    Button addCartBtn;
    private RecyclerView recyclerView;
    private NewRequiredItemListAdapter mAdapter;
    LinearLayoutManager manager;
    String consumerName;
    ArrayList<RequiredItemsList> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_consumer_public_profile);
        cName = findViewById(R.id.Newconsumer_profile_name);
        cAddress = findViewById(R.id.Newconsumer_address);
        cDescription = findViewById(R.id.Newconsumer_bio);
        addCartBtn = findViewById(R.id.NewaddToCartButton);
        Intent intent = getIntent();
        consumerName= intent.getStringExtra("ConsumerName");
        String address = intent.getStringExtra("ConsumerAddress");
        String desc = intent.getStringExtra("ConsumerAbout");
        cName.setText(consumerName);
        cAddress.setText(address);
        cDescription.setText(desc);
        recyclerView = (RecyclerView) findViewById(R.id.Newrecycler_view);
        data_load();
        mAdapter = new NewRequiredItemListAdapter(this, userList);
        manager = new LinearLayoutManager(NewConsumerPublicProfileActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

    }

    public void data_load() {
        String nameList[] =  {"Chicken"};
        String dateList[] = {"125/2019"};
        int quantityList[] = {2};
        String unitsList[] = {"Lb"};
        for (int i = 0; i < nameList.length; i++) {
            RequiredItemsList mSample = new RequiredItemsList();
            mSample.setName(nameList[i]);
            mSample.setDate(dateList[i]);
            mSample.setQuantity(quantityList[i]);
            mSample.setUnits(unitsList[i]);
            userList.add(mSample);
        }
    }

    protected void putInCart()
    {
        //Intent intent = new Intent(ConsumerPublicProfileActivity.this, DonorCart.class);
       // intent.putParcelableArrayListExtra("itemList", ItemSelectedList);
       // intent.putExtra("ConsumerName",consumerName);
       // startActivity(intent);
    }
}
