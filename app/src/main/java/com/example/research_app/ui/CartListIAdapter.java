package com.example.research_app.ui;

import android.app.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.research_app.DonorActivities.DonorCart;
import com.example.research_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CartListIAdapter extends RecyclerView.Adapter<CartListIAdapter.ProductViewHolder>
{
    final private Context mCtx;
    public List<itemCartClass> cartList;
    FirebaseAuth mAuth;
    String userId;

    public CartListIAdapter(Context mCtx, List<itemCartClass> productList, String id) {
        this.mCtx = mCtx;
        this.cartList = productList;
        this.userId = id;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cart_item, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position)
    {
        final itemCartClass product = cartList.get(position);
        holder.textViewTitle.setText(product.getObj().getName());
        holder.requiredQuantity.setText(product.getObj().getQuantity() +" "+ product.getObj().getUnits());
        holder.unitsTV.setText(product.getObj().getUnits());
        holder.increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int newQuantity = Integer.parseInt(holder.originalQuantity.getText().toString());
                int requiredQuantity = product.getObj().getQuantity();
                newQuantity = newQuantity+1;
                if (newQuantity<= requiredQuantity)
                {
                    holder.originalQuantity.setText(""+newQuantity);
                    product.setQuantity(newQuantity);
                    updateDBCartquantity(newQuantity, product.getObj().getName());
                }
                else
                    Toast.makeText(mCtx, "Required quantity only"+product.getQuantity()+product.getObj().getUnits() , Toast.LENGTH_SHORT).show();
            }
        });
        holder.decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = Integer.parseInt(holder.originalQuantity.getText().toString());
                newQuantity = newQuantity-1;
                if(newQuantity>0)
                {
                    holder.originalQuantity.setText(""+newQuantity);
                    product.setQuantity(newQuantity);
                    updateDBCartquantity(newQuantity,product.getObj().getName());
                }
                else
                    Toast.makeText(mCtx, "Required quantity "+product.getQuantity()+product.getObj().getUnits() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewTitle,originalQuantity,requiredQuantity, unitsTV;
        ImageButton increaseQuantity,decreaseQuantity;
        public ProductViewHolder(View itemView)
        {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tvCartItemName);
            increaseQuantity = itemView.findViewById(R.id.cart_plus_btn);
            decreaseQuantity = itemView.findViewById(R.id.cart_minus_btn);
            originalQuantity = itemView.findViewById(R.id.weight_text);
            requiredQuantity= itemView.findViewById(R.id.donorCart_requiredQuantityTV);
            unitsTV = itemView.findViewById(R.id.weight_unit);
        }
    }
    public void updateDBCartquantity(final int quantity, String name )
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser = mAuth.getCurrentUser();
        String donorUserId = currUser.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef =
                database.getReference("carts/"+donorUserId+"/"+userId+"/"+name);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                DBItemsClass itemObject = dataSnapshot.getValue(DBItemsClass.class);
                itemObject.setItemQuantity(quantity);
                myRef.setValue(itemObject);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}