package com.example.research_app.CosumerClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.research_app.ConsumerActivities.ConsumerRequestItemActivity;
import com.example.research_app.DonorActivities.ConsumerPublicProfileActivity;
import com.example.research_app.R;

import java.util.List;

public class ConsumerDashAdapter extends RecyclerView.Adapter<ConsumerDashAdapter.MyViewHolder>
{

    private List<ConsumerDashItemList> consumerDashItemListList;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView cardImageView;
        public TextView titleTextView;
        public TextView subTitleTextView;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), ConsumerRequestItemActivity.class);
                    v.getContext().startActivity(i);
                }
            });
            cardImageView = itemView.findViewById(R.id.imageView2);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_description);
        }

        public void bindData(ConsumerDashItemList consumerDashItemList, Context context)
        {
            cardImageView.setImageDrawable(ContextCompat.getDrawable(context, consumerDashItemList.getImageDrawable()));
            titleTextView.setText(consumerDashItemList.getTitle());
            subTitleTextView.setText(consumerDashItemList.getDescription());
        }
    }

    public ConsumerDashAdapter(List<ConsumerDashItemList> modelList, Context context)
    {
        consumerDashItemListList = modelList;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_consumer_dash, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.bindData(consumerDashItemListList.get(position), mContext);
    }

    @Override
    public int getItemCount()
    {
        return consumerDashItemListList.size();
    }
}
