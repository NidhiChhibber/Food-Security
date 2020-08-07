package com.example.research_app.ConsumerActivities;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    public static final String KEY_NAME = "name";
    private static final String FTS_VIRTUAL_TABLE = "CustomerInfo";

    private static final String TAG = "CustomersDbAdapter";

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }


    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    public void open() {
        this.database = openHelper.getWritableDatabase();
    }


    public void close() {
        if (database != null) {
            this.database.close();
        }
    }


    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM quotes", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public Cursor searchCustomer(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        String query = "SELECT docid as _id," +
                KEY_NAME +
                " from " + FTS_VIRTUAL_TABLE +
                " where " +  KEY_NAME + " MATCH '" + inputText +"';";
        Log.w(TAG, query);
        Cursor mCursor = database.rawQuery(query,null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
}