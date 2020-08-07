package com.example.research_app.ConsumerActivities;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.research_app.R;

public class ItemAutoTextAdapter extends CursorAdapter
        implements android.widget.AdapterView.OnItemClickListener {

    private DatabaseAccess mDbHelper;

    public ItemAutoTextAdapter(Context context, DatabaseAccess dbHelper)
    {
        super(context,null,0);
        mDbHelper = dbHelper;
    }


    @Override

    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if (getFilterQueryProvider() != null) {
            return getFilterQueryProvider().runQuery(constraint);
        }

        Cursor cursor = mDbHelper.searchCustomer((constraint != null ? constraint.toString() : "@@@@"));

        return cursor;
    }


    @Override
    public String convertToString(Cursor cursor) {
        final int columnIndex = cursor.getColumnIndexOrThrow("name");
        final String str = cursor.getString(columnIndex);
        return str;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvName = (TextView) view.findViewById(R.id.textViewName);
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        tvName.setText(name);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.donor_item_list,parent, false);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

    }

}

