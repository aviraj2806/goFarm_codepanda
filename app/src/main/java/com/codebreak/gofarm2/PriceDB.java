package com.codebreak.gofarm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PriceDB {

    public static final String KEY_CROP = "crop_name";
    public static final String KEY_MARKET = "market_name";
    public static final String KEY_ARR ="arrivals";
    public static final String KEY_DATE = "date";
    public static final String KEY_MIN = "min";
    public static final String KEY_MAX = "max";

    private final String DATABASE_NAME="PriceDB";
    private final String DATABASE_TABLE="PriceTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public PriceDB (Context context)
    {
        ourContext=context;
    }

    private class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode="CREATE TABLE "+DATABASE_TABLE+" ("+KEY_CROP+" TEXT NOT NULL, "+KEY_MARKET+" TEXT NOT NULL, "
                    +KEY_DATE+" TEXT NOT NULL, "+KEY_MIN+" TEXT NOT NULL, "+KEY_MAX+" TEXT NOT NULL, "+KEY_ARR+" TEXT NOT NULL);";
            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

    public PriceDB open() throws SQLException
    {
        ourHelper=new DBHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }

    public void close() throws SQLException
    {
        ourHelper.close();
    }

    public long createEntry(String crop,String market,String date,String min,String max,String arr)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_CROP,crop);
        cv.put(KEY_MARKET,market);
        cv.put(KEY_DATE,date);
        cv.put(KEY_MIN,min);
        cv.put(KEY_MAX,max);
        cv.put(KEY_ARR,arr);

        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public double getMin(String crop,String market,String date)
    {
        double sum=0;
        double count=0;
        String valid[]=new String[]{KEY_CROP,KEY_MARKET,KEY_DATE,KEY_MIN};
        Cursor c=ourDatabase.query(DATABASE_TABLE,null,null,null,null,null,null);
        int i_market=c.getColumnIndex(KEY_MARKET);
        int i_crop=c.getColumnIndex(KEY_CROP);
        int i_date=c.getColumnIndex(KEY_DATE);
        int i_min=c.getColumnIndex(KEY_MIN);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_crop).equals(crop) && c.getString(i_date).equals(date) && c.getString(i_market).equals(market))
            {
                sum=sum+Integer.parseInt(c.getString(i_min).trim());
                count++;
            }
        }

        sum=sum/count;

        return sum;
    }

    public double getMax(String crop,String market,String date)
    {
        double sum=0;
        double count=0;
        String valid[]=new String[]{KEY_CROP,KEY_MARKET,KEY_DATE,KEY_MAX};
        Cursor c=ourDatabase.query(DATABASE_TABLE,null,null,null,null,null,null);
        int i_market=c.getColumnIndex(KEY_MARKET);
        int i_crop=c.getColumnIndex(KEY_CROP);
        int i_date=c.getColumnIndex(KEY_DATE);
        int i_max=c.getColumnIndex(KEY_MAX);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_crop).equals(crop) && c.getString(i_date).equals(date) && c.getString(i_market).equals(market))
            {
                sum=sum+Integer.parseInt(c.getString(i_max).trim());
                count++;
            }
        }

        sum=sum/count;

        return sum;
    }


    public double getArr(String crop,String market,String date)
    {
        double sum=0;
        double count=0;
        String valid[]=new String[]{KEY_CROP,KEY_MARKET,KEY_DATE,KEY_ARR};
        Cursor c=ourDatabase.query(DATABASE_TABLE,null,null,null,null,null,null);
        int i_market=c.getColumnIndex(KEY_MARKET);
        int i_crop=c.getColumnIndex(KEY_CROP);
        int i_date=c.getColumnIndex(KEY_DATE);
        int i_arr=c.getColumnIndex(KEY_ARR);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_crop).equals(crop) && c.getString(i_date).equals(date) && c.getString(i_market).equals(market))
            {
                sum=sum+Integer.parseInt(c.getString(i_arr).trim());
                count++;
            }
        }

        sum=sum/count;

        return sum;
    }

}
