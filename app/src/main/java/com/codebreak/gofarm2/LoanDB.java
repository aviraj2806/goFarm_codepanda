package com.codebreak.gofarm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoanDB {


    public static final String KEY_ID = "farmer_id";
    public static final String KEY_REF = "loan_ref";
    public static final String KEY_DATE="loan_date";

    private final String DATABASE_NAME="LoanDB";
    private final String DATABASE_TABLE="LoanTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public LoanDB (Context context)
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
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            String sqlCode="CREATE TABLE "+DATABASE_TABLE+" ("+KEY_ID+" TEXT NOT NULL, "+KEY_REF+" TEXT NOT NULL, "+KEY_DATE+" TEXT NOT NULL);";
            sqLiteDatabase.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }
    public LoanDB open() throws SQLException
    {
        ourHelper=new DBHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }

    public void close() throws SQLException
    {
        ourHelper.close();
    }

    public long onEntry(String id,String ref,String date)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_ID,id);
        cv.put(KEY_REF,ref);
        cv.put(KEY_DATE,date);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public String getRef(String id,String date)
    {
        String valid[]=new String[]{KEY_ID,KEY_REF,KEY_DATE};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        int i_ref=c.getColumnIndex(KEY_REF);
        int i_date=c.getColumnIndex(KEY_DATE);
        String res="";
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(id) && c.getString(i_date).equals(date))
            {
                res=c.getString(i_ref);
                break;
            }
        }
        return res;
    }

    public int validLoan(String id,String date)
    {
        String valid[]=new String[]{KEY_ID,KEY_DATE};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        int i_date=c.getColumnIndex(KEY_DATE);
        int res=0;
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(id) && c.getString(i_date).equals(date))
            {
                res=1;
                break;
            }
        }
        return res;
    }

}
