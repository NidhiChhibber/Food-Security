package com.example.research_app.DonorActivities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.research_app.ui.DBItemsClass;
import com.example.research_app.ui.DBItemsClass;

import java.util.ArrayList;
import java.util.List;

public  class MyDB

    {
        public  static final String ITEM_NAME= "ITEM_NAME";
        private  static final String DATABASE_NAME = "DICTIONARY";
        private  static final String FTS_VIRTUAL_TABLE = "FTS";
        private  static final int DATABASE_VERSION = 1;
        private DatabaseHelper mDbHelper;
        private static SQLiteDatabase mDb;
        private static final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +" USING fts3 (" + ITEM_NAME + ")";
        private final Context context;

        private static class DatabaseHelper extends SQLiteOpenHelper
        {


            DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }


            @Override
            public void onCreate(SQLiteDatabase db)
            {
                mDb = db;
                db.execSQL(FTS_TABLE_CREATE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                Log.w("DB", "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
                db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
                onCreate(db);
            }
        }

        public MyDB(Context ctx)
        {
            this.context = ctx;
        }

        public MyDB open() throws SQLException {
            mDbHelper = new DatabaseHelper(context);
            mDb = mDbHelper.getWritableDatabase();
            return this;
        }

        public void close() {
            if (mDbHelper != null) {
                mDbHelper.close();
            }
        }
        public long createList(String name) {

            ContentValues initialValues = new ContentValues();
            initialValues.put(ITEM_NAME, name);
            return mDb.insert(FTS_VIRTUAL_TABLE, null, initialValues);

        }


        public Cursor getWordMatches(String query, String[] columns) {
            String selection = ITEM_NAME + " MATCH ?";
            String[] selectionArgs = new String[] {query+"*"};
            return query(selection, selectionArgs, columns);
        }

        private Cursor query(String selection, String[] selectionArgs, String[] columns) {
            SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
            builder.setTables(FTS_VIRTUAL_TABLE);

            Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),
                    columns, selection, selectionArgs, null, null, null);

            if (cursor == null) {
                return null;
            } else if (!cursor.moveToFirst()) {
                cursor.close();
                return null;
            }
            return cursor;
        }
        public boolean deleteAllNames() {

            int doneDelete = mDb.delete(FTS_VIRTUAL_TABLE, null , null);
            return doneDelete > 0;

        }
    }



