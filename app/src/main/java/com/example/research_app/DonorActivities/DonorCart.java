package com.example.research_app.DonorActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.research_app.R;
import com.example.research_app.ui.CartListIAdapter;
import com.example.research_app.ui.ConsumerSuggestionList;
import com.example.research_app.ui.CreateFreeFormString;
import com.example.research_app.ui.CustomAdapter;
import com.example.research_app.ui.RequiredItemsList;
import com.example.research_app.ui.itemCartClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DonorCart extends BaseDrawerActivity {
    List<itemCartClass> productList;
    RecyclerView recyclerView;
    TextView tvConsumerName,tvDonatingTo;
    Button donateBtn,closePopupBtn;
    CartListIAdapter adapter;
    PopupWindow popupWindow;
    ConstraintLayout cL;
    String cName,cAdd,cAbout,cuserId;
    ArrayList<RequiredItemsList> userList = new ArrayList<>();
    ArrayList<RequiredItemsList> itemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        getLayoutInflater().inflate(R.layout.activity_donor_cart, contentFrameLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        itemList = getIntent().getParcelableArrayListExtra("itemList");
        cName = getIntent().getStringExtra("ConsumerName");
        cAdd = getIntent().getStringExtra("ConsumerAddress");
        cAbout = getIntent().getStringExtra("ConsumerAbout");
        cuserId = getIntent().getStringExtra("userId");
        tvConsumerName = (TextView) findViewById(R.id.tvConsumerName);
        donateBtn = findViewById(R.id.donateButton);
        cL = (ConstraintLayout) findViewById(R.id.cL);
        tvDonatingTo = findViewById(R.id.tvDonatingTo);
        setView();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //donateBtn.setVisibility(View.GONE);
                //tvConsumerName.setVisibility(View.GONE);
                //tvDonatingTo.setVisibility(View.GONE);
                final List<itemCartClass> finalCartList = adapter.cartList;
                for(int i =0; i<itemList.size();i++)
                {
                    RequiredItemsList item = itemList.get(i);
                    final String itemName = item.getName();
                    final int donationQuantity = finalCartList.get(i).getQuantity();
                    final DatabaseReference myRef = database.getReference("consumerRequests/"+cuserId+"/"+itemName);
                    myRef.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData)
                        {
                            if(mutableData.getValue()==null)
                            {
                                Log.d("Status", "Its null"+cuserId+" "+itemName);
                                runOnUiThread(new Runnable(){
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "ITS NULL", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else
                            {
                                RequiredItemsList currentItem = mutableData.getValue(RequiredItemsList.class);
                                int oldQuantity = currentItem.getQuantity();
                                if(oldQuantity < donationQuantity)
                                {
                                    Log.d("Status", "Transaction Aborted "+itemName);
                                    return Transaction.abort();
                                }
                                else
                                {
                                    int newQuantity = oldQuantity - donationQuantity;
                                    Log.d("Status", "Donating"+itemName);
                                    currentItem.setQuantity(newQuantity);
                                    mutableData.setValue(currentItem);
                                    if(newQuantity <1)
                                    {
                                        database.getReference("consumerRequests/"+cuserId).child(itemName).removeValue();
                                    }
                                    CreateFreeFormString.createAndSetFreeformAndItemString(cuserId);
                                    Log.d("Status", "Donated"+itemName);
                                }
                            }
                            

                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot)
                        {
                            Log.d("Status", "postTransaction:onComplete:" + databaseError);
                            if(b == true)
                                    displayBox();

                        }
                    });




                }

            }
        });

    }

    public void displayBox()
    {
        LayoutInflater layoutInflater = (LayoutInflater) DonorCart.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.pop_up_donation_complete,null);
        closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ((TextView)popupWindow.getContentView().findViewById(R.id.donatedToName)).setText(cName);
        popupWindow.showAtLocation(cL, Gravity.CENTER, 0, 0);

        closePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                popupWindow.dismiss();
                Intent intent = new Intent(DonorCart.this, DonorHome.class);
                intent.putExtra("userID",cuserId);
                startActivity(intent);
            }
        });
    }


    public  void setView()
    {
        tvConsumerName.setText(cName);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        for(int i=0; i<itemList.size();i++)
        {
            productList.add(new itemCartClass(itemList.get(i),1));
        }

        adapter = new CartListIAdapter(this, productList,cuserId );
        recyclerView.setAdapter(adapter);
    }


    /*public void updateRemainingQuantity()
    {
        List<itemCartClass> finalCartList = adapter.cartList;
        for(int i=0; i<finalCartList.size();i++)
        {
            int newQuantity = finalCartList.get(i).getObj().getQuantity()-finalCartList.get(i).getQuantity();
            finalCartList.get(i).getObj().setQuantity(newQuantity);
        }
    }*/





}

