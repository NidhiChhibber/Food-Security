package com.example.research_app.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import  android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.research_app.DonorActivities.ConsumerPublicProfileActivity;
import com.example.research_app.DonorActivities.DonorDash;
import com.example.research_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public  class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.ViewHolder>
{
    private LayoutInflater layoutInflater;
    private List<ConsumerSuggestionList> consumerList;
    private Context cntxt;
    private AdapterView.OnItemClickListener listener;
    //private String uId;
    private ArrayList<String> uList;
    public  ArrayList<Integer> imageList = new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<String> uIdList, List<ConsumerSuggestionList> cList)
    {
        this.cntxt = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.consumerList = cList;
        this.uList = uIdList;
        imageList.add(R.drawable.user_1);
        imageList.add(R.drawable.user_2);
        imageList.add(R.drawable.user_3);
        imageList.add(R.drawable.user_4);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.card_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ConsumerSuggestionList consumer = consumerList.get(i);
        viewHolder.textViewName.setText(consumer.getName());
        viewHolder.textViewShortDesc.setText(consumer.getShortdesc());
        String address = createTitle(String.valueOf(consumer.getCountry()))+", "
                +createTitle(String.valueOf(consumer.getState()))+", "+
                createTitle(String.valueOf(consumer.getCity()));
        viewHolder.textViewAddress.setText(address);
        String items = String.valueOf(consumer.getItems());
        int length = items.length();
        Random rand = new Random();
        int n = rand.nextInt(4);
        viewHolder.textViewItems.setText(items.subSequence(1,length-1));
        viewHolder.imageView.setImageDrawable(cntxt.getResources().getDrawable(imageList.get(n)));

    }

    @Override
    public int getItemCount() {
        return consumerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName, textViewShortDesc, textViewAddress, textViewItems;
        ImageView imageView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), ConsumerPublicProfileActivity.class);
                    i.putExtra("userID", uList.get(getAdapterPosition()));
                    v.getContext().startActivity(i);
                }
            });
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewItems = itemView.findViewById(R.id.textViewItems);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
    public String createTitle(String word)
    {
        return (word.substring(0, 1).toUpperCase() + word.substring(1));
    }
}
