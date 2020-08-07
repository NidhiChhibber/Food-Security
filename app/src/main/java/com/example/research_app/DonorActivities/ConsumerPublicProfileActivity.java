package com.example.research_app.DonorActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.research_app.CosumerClasses.RequestItemsClass;
import com.example.research_app.R;
import com.example.research_app.UserDetailsClass;
import com.example.research_app.ui.ConsumerSuggestionList;
import com.example.research_app.ui.DBItemsClass;
import com.example.research_app.ui.MyLinearLayoutManager;
import com.example.research_app.ui.RecyclerItemClickListener;
import com.example.research_app.ui.RequiredItemListAdapter;
import com.example.research_app.ui.RequiredItemsList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerPublicProfileActivity extends BaseDrawerActivity  {
    TextView cName,cAddress,cDescription;
    Button  addCartBtn;
    private RecyclerView recyclerView;
    private RequiredItemListAdapter mAdapter;
    public ArrayList<RequiredItemsList> ItemSelectedList = new ArrayList<>();

    LinearLayoutManager manager;
    String consumerName,consumerCountry, consumerState, consumerCity , consumerDesc, consumerUserId;
    public  ArrayList<String> itemNameList = new ArrayList<>();
    public  ArrayList<String> itemDateList= new ArrayList<>();
    public ArrayList<String> itemUnitList= new ArrayList<>();
    public  ArrayList<Integer> itemQuantityList = new ArrayList<>();
    ArrayList<RequiredItemsList> userList = new ArrayList<>();
    ArrayList<RequiredItemsList> multiselectList = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    boolean isMultiSelect = false;
    DatabaseReference consumerRef, itemRef;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.fragment_container);
        getLayoutInflater().inflate(R.layout.activity_consumer_public_profile, contentFrameLayout);

        cName = findViewById(R.id.consumer_profile_name);
        cAddress = findViewById(R.id.consumer_address);
        cDescription = findViewById(R.id.consumer_bio);
        addCartBtn = findViewById(R.id.addToCartButton);
        addCartBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                putInCart();
            }
        });
        Intent intent = getIntent();
        consumerUserId = intent.getStringExtra("userID");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position)
            {
                if (isMultiSelect)
                {
                    multi_select(position);
                }
                else
                    Toast.makeText(getApplicationContext(), "Details Page", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position)
            {
                if (!isMultiSelect)
                {
                    multiselectList = new ArrayList<RequiredItemsList>();
                    isMultiSelect = true;
                }
                multi_select(position);

            }
        }));

        getConsumerData();
        getItemData();

    }

    public void getConsumerData()
    {
        consumerRef = database.getReference(("consumers/"+consumerUserId));
        consumerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consumerName = (String) dataSnapshot.child("name").getValue();

                consumerCountry = (String) dataSnapshot.child("country").getValue();
                consumerState = (String) dataSnapshot.child("state").getValue();
                consumerCity = (String) dataSnapshot.child("city").getValue();
                consumerDesc = (String) dataSnapshot.child("description").getValue();
                cName.setText(consumerName);
                //cAddress.setText(consumerCity);
                cDescription.setText(consumerDesc);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void getItemData()
    {
        itemNameList.clear();
        itemDateList.clear();
        itemQuantityList.clear();
        itemUnitList.clear();
        itemRef = database.getReference("consumerRequests/" + consumerUserId);
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsChild : dataSnapshot.getChildren())
                {
                    RequiredItemsList itemList = dsChild.getValue(RequiredItemsList.class);
                    int itemQuantity = itemList.getQuantity();
                    if(itemQuantity>0) {
                        String itemName = itemList.getName();
                        itemNameList.add(itemName);
                        itemQuantityList.add(itemQuantity);
                        String itemDate = itemList.getDate();
                        itemDateList.add(itemDate);
                        String itemUnit = itemList.getUnits();
                        itemUnitList.add(itemUnit);
                    }

                }
                data_load();
                setLayout();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void setLayout()
    {
        multiselectList.clear();
        mAdapter = new RequiredItemListAdapter(this, userList, multiselectList);
        manager = new LinearLayoutManager(ConsumerPublicProfileActivity.this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
    }

    public void multi_select(int position)
    {
            if (multiselectList.contains(userList.get(position))) {
                multiselectList.remove(userList.get(position));
                ItemSelectedList.remove(userList.get(position));
            }
            else {
                multiselectList.add(userList.get(position));
                Toast.makeText(getApplicationContext(), "SE:" + userList.get(position).getName(), Toast.LENGTH_SHORT).show();
                RequiredItemsList itemName = userList.get(position);
                ItemSelectedList.add(itemName);
            }
            refreshAdapter();

    }


    public void refreshAdapter()
    {
        mAdapter.selected_usersList = multiselectList;
        mAdapter.usersList = userList;
        mAdapter.notifyDataSetChanged();
    }

    public void data_load()
    {
        userList.clear();
        Toast.makeText(getApplicationContext(), "size is"+userList.size()+" "+multiselectList.size(), Toast.LENGTH_LONG).show();
        for (int i = 0; i < itemNameList.size(); i++) {
            RequiredItemsList mSample = new RequiredItemsList();
            mSample.setName(itemNameList.get(i));
            mSample.setDate(itemDateList.get(i));
            mSample.setQuantity(itemQuantityList.get(i));
            mSample.setUnits(itemUnitList.get(i));
            userList.add(mSample);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void putInCart()
    {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser = mAuth.getCurrentUser();
        String donorUserId = currUser.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef =
                database.getReference("carts/"+donorUserId+"/"+consumerUserId);
        for( RequiredItemsList item : ItemSelectedList)
        {
            DBItemsClass itemObject = new DBItemsClass();
            itemObject.setItemName(item.getName());
            itemObject.setItemQuantity(1);
            itemObject.setItemAddDate(java.time.LocalDate.now().toString());
            itemObject.setItemAddTime(java.time.LocalTime.now().toString());
            myRef.child(item.getName()).setValue(itemObject);
        }

        goToCart();

    }

    public void goToCart()
    {
        Intent intent = new Intent(ConsumerPublicProfileActivity.this, DonorCart.class);
        intent.putParcelableArrayListExtra("itemList", ItemSelectedList);
        intent.putExtra("ConsumerName",consumerName);
        intent.putExtra("ConsumerAddress",consumerCity);
        intent.putExtra("ConsumerAbout",consumerDesc);
        intent.putExtra("userId",consumerUserId );
        startActivity(intent);
    }
}




