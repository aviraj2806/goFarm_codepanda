package com.codebreak.gofarm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CropDB {

    public static final String KEY_ID="farmer_id";
    public static final String KEY_CROP="crop_name";
    public static final String KEY_MARKET="market_name";
    public static final String KEY_AMOUNT="crop_amount";
    public static final String KEY_DATE="date";

    private final String DATABASE_NAME="CropDB";
    private final String DATABASE_TABLE="CropTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourhelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public CropDB (Context context)
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
        public void onCreate(SQLiteDatabase sqLiteDatabase)
        {
            String sqlCode="CREATE TABLE "+DATABASE_TABLE+" ("+KEY_ID+" TEXT NOT NULL, "+KEY_MARKET+" TEXT NOT NULL, "
                    +KEY_CROP+" TEXT NOT NULL, "+KEY_AMOUNT+" TEXT NOT NULL, "+KEY_DATE+" TEXT NOT NULL);";
            sqLiteDatabase.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public CropDB open() throws SQLException
    {
        ourhelper=new DBHelper(ourContext);
        ourDatabase=ourhelper.getWritableDatabase();
        return this;
    }

    public void close() throws  SQLException
    {
        ourhelper.close();
    }

    public long createEntry(String id,String market,String crop,String amount,String date)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_ID,id);
        cv.put(KEY_MARKET,market);
        cv.put(KEY_CROP,crop);
        cv.put(KEY_AMOUNT,amount);
        cv.put(KEY_DATE,date);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public int getStat(String crop,String market,String date)
    {
        int sum=0;
        String valid[] = new String[]{KEY_MARKET, KEY_CROP, KEY_AMOUNT, KEY_DATE};
        Cursor c = ourDatabase.query(DATABASE_TABLE, valid, null, null, null, null, null);
        String res = "";
        int i_market = c.getColumnIndex(KEY_MARKET);
        int i_crop = c.getColumnIndex(KEY_CROP);
        int i_amount = c.getColumnIndex(KEY_AMOUNT);
        int i_date = c.getColumnIndex(KEY_DATE);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if(c.getString(i_date).equals(date) ) {
                if (c.getString(i_crop).equals(crop) && c.getString(i_market).equals(market)) {
                    sum = sum + Integer.parseInt(c.getString(i_amount).trim());
                }
            }
        }

        return sum;
    }

    public int getEntry(String id,String today,String tom)
    {
        int count=0;
        String valid[]=new String[]{KEY_ID,KEY_DATE};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        int i_date=c.getColumnIndex(KEY_DATE);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(id) && c.getString(i_date).equals(today))
            {
                count++;
            }
            if(c.getString(i_id).equals(id) && c.getString(i_date).equals(tom))
            {
                count++;
            }
        }

        return count;
    }

}
