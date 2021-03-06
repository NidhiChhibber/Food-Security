package com.example.research_app.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.research_app.R;

import java.util.ArrayList;


public class NewRequiredItemListAdapter extends RecyclerView.Adapter<NewRequiredItemListAdapter.MyViewHolder> {


    public ArrayList<RequiredItemsList> usersList;
    Context mContext;

    public NewRequiredItemListAdapter(Context context, ArrayList<RequiredItemsList> userList)
    {
        this.usersList = userList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RequiredItemsList model = usersList.get(position);
        holder.textViewItemName.setText(model.getName());
        holder.textViewItemDate.setText(model.getDate());
        holder.textViewItemQuantity.setText(String.valueOf(model.getQuantity()));
        holder.textViewItemUnits.setText(model.getUnits());
    }


    @Override
    public int getItemCount() {
        return usersList == null ? 0 : usersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ConstraintLayout rowCL;
        private TextView textViewItemName,textViewItemDate,textViewItemQuantity,textViewItemUnits;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rowCL = itemView.findViewById(R.id.CLPPItemDetails);
            textViewItemName = itemView.findViewById(R.id.itemName);
            textViewItemDate = itemView.findViewById(R.id.itemDate);
            textViewItemQuantity = itemView.findViewById(R.id.itemQuantity);
            textViewItemUnits = itemView.findViewById(R.id.itemUnits);

        }
    }
}

