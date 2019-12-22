package com.codebreak.gofarm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BuyDB {

    public static final String KEY_ID="farmer_id";
    public static final String KEY_CROP="crop";
    public static final String KEY_MARKET="market";
    public static final String KEY_AMT="amount";
    public static  final String KEY_NAME="name";
    public static final String KEY_PHN="phone";
    public static final String KEY_DP="dp";
    public static final String KEY_COUNT="count";

    private final String DATABASE_NAME="BuyDB";
    private final String DATABASE_TABLE="BuyTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public BuyDB(Context context)
    {
        ourContext=context;
    }

    private class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper (Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode="CREATE TABLE "+DATABASE_TABLE+" ("+KEY_COUNT +" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_ID+" TEXT NOT NULL, "+KEY_CROP+" TEXT NOT NULL, "+
                    KEY_MARKET+" TEXT NOT NULL, "+KEY_NAME+" TEXT NOT NULL, "+KEY_PHN+" TEXT NOT NULL, "+KEY_DP+" TEXT NOT NULL, "+KEY_AMT+" TEXT NOT NULL);";
            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        }
    }

    public BuyDB open() throws SQLException
    {
        ourHelper=new DBHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long createEntry(String crop,String market,String amt,String id,String name,String phn,String dp)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_ID,id);
        cv.put(KEY_CROP,crop);
        cv.put(KEY_MARKET,market);
        cv.put(KEY_AMT,amt);
        cv.put(KEY_NAME,name);
        cv.put(KEY_PHN,phn);
        cv.put(KEY_DP,dp);

        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public long getCount()
    {
        SQLiteDatabase sd=ourHelper.getReadableDatabase();
        long count= DatabaseUtils.queryNumEntries(sd,DATABASE_TABLE);
        return count;
    }

    public String getData(String check)
    {
        String valid[]=new String[]{KEY_COUNT,KEY_NAME,KEY_MARKET,KEY_CROP,KEY_AMT,KEY_PHN,KEY_DP};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        String res="";
        int i_count=c.getColumnIndex(KEY_COUNT);
        int i_name=c.getColumnIndex(KEY_NAME);
        int i_city=c.getColumnIndex(KEY_MARKET);
        int i_crop=c.getColumnIndex(KEY_CROP);
        int i_amt=c.getColumnIndex(KEY_AMT);
        int i_phn=c.getColumnIndex(KEY_PHN);
        int i_dp=c.getColumnIndex(KEY_DP);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_count).equals(check))
            {
                res=c.getString(i_name)+", "+c.getString(i_city)+", "+c.getString(i_crop)+", "+c.getString(i_amt)+", "+c.getString(i_phn)+", "+c.getString(i_dp);
                break;
            }
        }
        return res;
    }

    public int isValid(String check)
    {
        int res=0;
        String valid[]=new String[]{KEY_ID};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(check))
            {
                res=1;
                break;
            }
        }

        return res;
    }


    public int idCount(String check)
    {
        int res=0;
        String valid[]=new String[]{KEY_ID};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(check))
            {
                res++;
            }
        }

        return res;
    }

    public String getID(String check,String id)
    {
        String valid[]=new String[]{KEY_ID,KEY_COUNT,KEY_NAME,KEY_MARKET,KEY_CROP,KEY_AMT,KEY_PHN,KEY_DP};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        String res="";
        int i_count=c.getColumnIndex(KEY_COUNT);
        int i_name=c.getColumnIndex(KEY_NAME);
        int i_city=c.getColumnIndex(KEY_MARKET);
        int i_crop=c.getColumnIndex(KEY_CROP);
        int i_amt=c.getColumnIndex(KEY_AMT);
        int i_phn=c.getColumnIndex(KEY_PHN);
        int i_dp=c.getColumnIndex(KEY_DP);
        int i_id=c.getColumnIndex(KEY_ID);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_count).equals(check) && c.getString(i_id).equals(id))
            {
                res=c.getString(i_name)+", "+c.getString(i_city)+", "+c.getString(i_crop)+", "+c.getString(i_amt)+", "+c.getString(i_phn)+", "+c.getString(i_dp);
                break;
            }
        }
        return res;
    }

}
