package com.example.research_app.DonorActivities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.util.Log;
import android.widget.CursorAdapter;

import com.example.research_app.ui.DBItemsClass;
import com.example.research_app.ui.DBItemsClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyDB extends SQLiteOpenHelper
{
    private  static final int DATABASE_VERSION = 1;
    private static MyDB sInstance;
    private static SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "DONATIONS";
    private static final String TABLE_DONORS = "DONORS_TABLE";
    private static final String TABLE_CONSUMERS = "CONSUMERS_TABLE";
    private static final String TABLE_DONATIONS = "D_C_TABLE";
    private static final String TABLE_ORDERS = "ORDERS_TABLE";
    private static final String TABLE_NUM_DONATIONS = "NUM_DONATIONS_TABLE";


    // DONORS Table - column names
    private static final String KEY_DONOR_ID = "DONOR_ID";
    private static final String KEY_DONOR_USERNAME = "DONOR_USERNAME";

    // D_C_DONATIONS Table - column names
    private static final String KEY_D_C_ID = "_id";
    private static final String KEY_D_C_DONOR_ID = "D_C_DONOR";
    public static final String KEY_D_C_CONSUMER_ID = "D_C_CONSUMER";

    // D_C_NUM_DONATIONS Table - column names
    private static final String KEY_NUM_DONATION_ID = "_id";
    private static final String KEY_NUM_DONATIONS_DONOR_ID = "NUM_DONATIONS_DONOR";
    public static final String  KEY_NUM_DONATIONS_CONSUMER_ID = "NUM_DONATIONS_CONSUMER";
    public static final String KEY_NUM_DONATIONS_VALUE = "NUM_OF_DONATIONS";

    // DONATION_DETAIL_TABLE - column names
    private static final String KEY_ORDERS_ID = "_id";
    private static final String KEY_ORDERS_TIMESTAMP = "ORDERS_TIMESTAMP";
    private static final String KEY_ORDERS_NAME = "ORDERS_NAME";
    private static final String KEY_ORDERS_DATE = "ORDERS_DATE";

    private static final String KEY_ORDERS_TIME = "ORDERS_TIME";
    private static final String KEY_ORDERS_QUANTITY = "ORDERS_QUANTITY";
    private static final String KEY_ORDERS_DONATION_ID = "ORDERS_DONATION_ID";
    private static final String KEY_ORDERS_UNITS = "ORDERS_UNITS";

    // Table Create Statements
    // DONORS table create statement
    private static final String CREATE_TABLE_DONOR = "CREATE TABLE "
            + TABLE_DONORS
            + "("
            + KEY_DONOR_ID + " TEXT, "
            + KEY_DONOR_USERNAME + " TEXT"
            + ")";
    // DONATIONS table create statement
    private static final String CREATE_TABLE_NUM_DONATIONS = "CREATE TABLE "
            + TABLE_NUM_DONATIONS
            + "( "
            + KEY_NUM_DONATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NUM_DONATIONS_DONOR_ID + " TEXT, "
            + KEY_NUM_DONATIONS_CONSUMER_ID + " TEXT, "
            + KEY_NUM_DONATIONS_VALUE + " INTEGER "
            + ")";

    private static final String CREATE_TABLE_DONATIONS = "CREATE TABLE "
            + TABLE_DONATIONS
            + "( "
            + KEY_D_C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_D_C_DONOR_ID + " TEXT, "
            + KEY_D_C_CONSUMER_ID + " TEXT "
            + ")";

    // ORDERS table create statement
    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE "
            + TABLE_ORDERS
            + " ( "
            + KEY_ORDERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ORDERS_TIMESTAMP + " TIMESTAMP NOT NULL, "
            + KEY_ORDERS_DONATION_ID + " INTEGER, "
            + KEY_ORDERS_DATE + " TEXT, "
            + KEY_ORDERS_TIME + " TEXT, "
            + KEY_ORDERS_NAME + " TEXT, "
            + KEY_ORDERS_QUANTITY + " INTEGER, "
            + KEY_ORDERS_UNITS + " TEXT, "
            + "FOREIGN KEY("+KEY_ORDERS_DONATION_ID+") REFERENCES "+TABLE_DONATIONS+"("+KEY_D_C_ID+") "
            + ")";

    public Context cont;

    public  MyDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;
    }

    public static synchronized MyDB getInstance(Context context)
    {
        if (sInstance == null)
        {
            sInstance = new MyDB(context.getApplicationContext());
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_DONOR);
        db.execSQL(CREATE_TABLE_DONATIONS);
        db.execSQL(CREATE_TABLE_ORDERS);
        db.execSQL(CREATE_TABLE_NUM_DONATIONS);
        mDb = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w("DB", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public long addDonor(String donorID, String donorName)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DONOR_ID, donorID);
        values.put(KEY_DONOR_USERNAME, donorName);

        // insert row
        long DONOR_INSERT_ID = db.insert(TABLE_DONORS, null, values);
        return DONOR_INSERT_ID;
    }


    public long addDonation(String donorID, String consumerID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_D_C_DONOR_ID, donorID);
        values.put(KEY_D_C_CONSUMER_ID, consumerID);
        long DONOR_INSERT_ID = db.insert(TABLE_DONATIONS, null, values);
        return DONOR_INSERT_ID;
    }

    public void updateNumOfDonation(String donorId, String consumerId)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        String searchQuery = " SELECT " + KEY_NUM_DONATIONS_VALUE
                + " FROM "
                + TABLE_NUM_DONATIONS
                + " WHERE "
                + KEY_NUM_DONATIONS_DONOR_ID + "= ?"
                +" AND "
                + KEY_NUM_DONATIONS_CONSUMER_ID + "= ?";
        Cursor c = db.rawQuery( searchQuery, new String [] {donorId,consumerId});
        Log.d("Cursor","C "+ c+" "+donorId+" "+consumerId);

        if(c!=null && c.getCount()>0)
        {
            String updateExec = "UPDATE " + TABLE_NUM_DONATIONS
                    + " SET " + KEY_NUM_DONATIONS_VALUE + "=" + KEY_NUM_DONATIONS_VALUE + "+1"
                    + " WHERE " + KEY_NUM_DONATIONS_DONOR_ID + "=?"
                    + " AND "+ KEY_NUM_DONATIONS_CONSUMER_ID + "=?";
            db.execSQL(updateExec, new String[] { donorId,consumerId } );
            Log.d("Cursor","c is not null "+ c+" "+donorId+" "+consumerId);
            db.execSQL("pragma wal_checkpoint;", null);
        }

        else
        {
            SQLiteDatabase db1 = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NUM_DONATIONS_DONOR_ID,donorId);
            values.put(KEY_NUM_DONATIONS_CONSUMER_ID,consumerId);
            values.put(KEY_NUM_DONATIONS_VALUE,1);
            long DONOR_INSERT_ID = db1.insert(TABLE_NUM_DONATIONS, null, values);
            Log.d("Cursor","c is null "+ c+" "+donorId+" "+consumerId);
            db.execSQL("pragma wal_checkpoint;", null);
        }
    }

    public long addOrder(long donationId, String productName, Integer quantity, String date, String time, String units)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String timeStamp = df.format(new Date());
        values.put(KEY_ORDERS_DONATION_ID,donationId);
        values.put(KEY_ORDERS_TIMESTAMP,timeStamp);
        values.put(KEY_ORDERS_DATE,date);
        values.put(KEY_ORDERS_TIME,time);
        values.put(KEY_ORDERS_NAME,productName);
        values.put(KEY_ORDERS_QUANTITY,quantity);


        // insert row
        long DONOR_INSERT_ID = db.insert(TABLE_ORDERS, null, values);
        return DONOR_INSERT_ID;
    }

    public void closeDB()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public void deleteDatabase()
    {
        cont.deleteDatabase(DATABASE_NAME);
    }

    // 1 All consumers D has donated to
    public Cursor DonorsAllConsumers(String donorId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT rowid _id,D_C_CONSUMER FROM D_C_TABLE WHERE TRIM(D_C_DONOR) = '"+donorId.trim()+"'", null);
        return c;
    }
    // 2 All D's that donate to a C
    public Cursor ConsumersAllDonors(String consumerID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT rowid _id,D_C_DONOR FROM D_C_TABLE WHERE TRIM(D_C_CONSUMER) = '"+consumerID.trim()+"'", null);
        return c;
    }
    // 3 All consumers D has donated to in last x days (modified 1)


    public Cursor DonorsAllConsumersInDays(String donorId, String days)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT DISTINCT D_C_TABLE._id,D_C_CONSUMER FROM D_C_TABLE INNER JOIN ORDERS_TABLE ON D_C_TABLE._id =  ORDERS_TABLE.ORDERS_DONATION_ID" +
                " WHERE CAST ((julianday('now') - julianday(ORDERS_DATE)) AS INTEGER) <= "+ days +
                " AND TRIM(D_C_DONOR) = '"+donorId.trim()+"'", null);
        return c;
        //
    }

    // 4 All D's that have donated to C in last x days (modified 2)
    public Cursor ConsumersAllDonorsInDays(String consumerID, String days)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT DISTINCT D_C_TABLE._id,D_C_DONOR FROM D_C_TABLE INNER JOIN ORDERS_TABLE ON D_C_TABLE._id =  ORDERS_TABLE.ORDERS_DONATION_ID" +
                " WHERE CAST ((julianday('now') - julianday(ORDERS_DATE)) AS INTEGER) <= "+ days +
                " AND TRIM(D_C_CONSUMER) = '"+consumerID.trim()+"'", null);
        return c;
    }

    /*public Cursor donorsLastConsumer(String donorId)
    {
        SQLiteDatabase db = this.getWritableDatabase();Cursor c = db.rawQuery("SELECT D_C_TABLE._id,D_C_CONSUMER FROM D_C_TABLE INNER JOIN ORDERS_TABLE ON D_C_TABLE._id =  ORDERS_TABLE.ORDERS_DONATION_ID" +
            " WHERE julianday(ORDERS_DATE) = ( SELECT  max(julianday(ORDERS_TABLE.ORDERS_DATE)) FROM ORDERS_TABLE ) LIMIT 1" +
            " AND TRIM(D_C_DONOR) = '"+donorId.trim()+"'", null);
        return c;
    }*/
    public Cursor donorsLastConsumer(String donorId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT D_C_TABLE._id,D_C_CONSUMER,MAX(ORDERS_TIMESTAMP) FROM D_C_TABLE"+
                " INNER JOIN ORDERS_TABLE ON D_C_TABLE._id =  ORDERS_TABLE.ORDERS_DONATION_ID"+
                " GROUP BY D_C_TABLE.D_C_DONOR"+
                " HAVING	D_C_CONSUMER"+
                " IN (?)";
        Cursor c = db.rawQuery( query, new String [] {donorId});

        return c;
    }
    public Cursor consumersLastDonor(String consumerId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT D_C_TABLE._id,D_C_DONOR,MAX(ORDERS_TIMESTAMP) FROM D_C_TABLE"+
                " INNER JOIN ORDERS_TABLE ON D_C_TABLE._id =  ORDERS_TABLE.ORDERS_DONATION_ID"+
                " GROUP BY D_C_TABLE.D_C_CONSUMER"+
                " HAVING	D_C_CONSUMER"+
                " IN (?)";
        Cursor c = db.rawQuery( query, new String [] {consumerId});

        return c;
    }

    public Cursor topConsumer(String donorId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_NUM_DONATION_ID+ ','+ KEY_NUM_DONATIONS_CONSUMER_ID + " FROM "+TABLE_NUM_DONATIONS
                +" WHERE "
                + KEY_NUM_DONATIONS_DONOR_ID +" = ?"
                + " ORDER BY "
                + KEY_NUM_DONATIONS_VALUE+" DESC LIMIT 1";
        Cursor c = db.rawQuery( query, new String [] {donorId});
        return c;
    }

    public Cursor topDonor(String consumerId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_NUM_DONATION_ID+ ','+ KEY_NUM_DONATIONS_DONOR_ID + " FROM "+TABLE_NUM_DONATIONS
                +" WHERE "
                + KEY_NUM_DONATIONS_DONOR_ID +" = ?"
                + " ORDER BY "
                + KEY_NUM_DONATIONS_VALUE+" DESC LIMIT 1";
        Cursor c = db.rawQuery( query, new String [] {consumerId});
        return c;
    }

}







