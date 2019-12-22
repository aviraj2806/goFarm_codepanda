package com.codebreak.gofarm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FarmerDB {


    public static final String KEY_USER="user_type";
    public static final String KEY_NAME="farmer_name";
    public static final String KEY_PHN="farmer_phn";
    public static final String KEY_CITY="farmer_city";
    public static final String KEY_UID="farmer_uid";
    public static final String KEY_ID="farmer_id";
    public static final String KEY_PASS="farmer_pass";
    public static final String KEY_DP="farmer_dp";

    private final String DATABASE_NAME="FarmerDB";
    private final String DATABASE_TABLE="FarmerTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public FarmerDB(Context context)
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
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
             /*
            CREATE TABLE FarmerTable(user_type TEXT NOT NULL,
                                        farmer_name TEXT NOT NULL, farmer_phn TEXT NOT NULL,
                                        farmer_uid TEXT NOT NULL, farmer_city TEXT NOT NULL,
                                        farmer_id TEXT NOT NULL, farmer_pass TEXT NOT NULL);
            */

            String sqlCode="CREATE TABLE " + DATABASE_TABLE + " (" + KEY_USER +" TEXT NOT NULL, "
                    +KEY_NAME+" TEXT NOT NULL, " + KEY_PHN+" TEXT NOT NULL, " + KEY_UID+" TEXT NOT NULL, "+KEY_CITY+" TEXT NOT NULL, "
                    +KEY_ID+" TEXT NOT NULL, "+KEY_PASS+" TEXT NOT NULL, "+KEY_DP+" TEXT NOT NULL);";

            sqLiteDatabase.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public FarmerDB open() throws SQLException
    {
        ourHelper=new DBHelper(ourContext);
        ourDatabase= ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long createEntry(String user,String name,String phn, String uid,String city,String id,String pass,String dp)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_USER,user);
        cv.put(KEY_NAME,name);
        cv.put(KEY_PHN,phn);
        cv.put(KEY_UID,uid);
        cv.put(KEY_CITY,city);
        cv.put(KEY_ID,id);
        cv.put(KEY_PASS,pass);
        cv.put(KEY_DP,dp);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public int validUser(String check)
    {
        int i=0;
        String valid[]=new String[]{KEY_ID};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(check))
            {
                i=1;
            }
        }
        return i;
    }

    public String getPass(String check)
    {
        String res="";
        String valid[]=new String[]{KEY_ID,KEY_PASS};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        int i_pass=c.getColumnIndex(KEY_PASS);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(check))
            {
                res=c.getString(i_pass);
                break;
            }
        }
        return res;
    }

    public String getUserType (String check)
    {
        String valid[]=new String[]{KEY_ID,KEY_USER};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        int i_user=c.getColumnIndex(KEY_USER);
        String res="";
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(check))
            {
                res=c.getString(i_user);
                break;
            }
        }
        return res;
    }

    public int checkIdReg (String check)
    {
        int i=0;
        String valid[]=new String[]{KEY_ID};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(check))
            {
                i=1;
            }
        }
        return i;
    }

    public int checkPhnReg (String check)
    {
        int i=0;
        String valid[]=new String[]{KEY_PHN};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_phn=c.getColumnIndex(KEY_PHN);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_phn).equals(check))
            {
                i=1;
            }
        }
        return i;
    }

    public int checkUidReg (String check)
    {
        int i=0;
        String valid[]=new String[]{KEY_UID};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_uid=c.getColumnIndex(KEY_UID);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_uid).equals(check))
            {
                i=1;
            }
        }
        return i;
    }

    public String getPhn(String check)
    {
        String valid[]=new String[]{KEY_PHN,KEY_ID};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_phn=c.getColumnIndex(KEY_PHN);
        int i_id=c.getColumnIndex(KEY_ID);
        String res="";
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(check))
            {
                res=c.getString(i_phn);
                break;
            }
        }
        return res;
    }

    public String getDash(String check)
    {
        String valid[]=new String[]{KEY_NAME,KEY_ID,KEY_USER,KEY_CITY,KEY_DP};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_name=c.getColumnIndex(KEY_NAME);
        int i_id=c.getColumnIndex(KEY_ID);
        int i_user=c.getColumnIndex(KEY_USER);
        int i_city=c.getColumnIndex(KEY_CITY);
        int i_dp=c.getColumnIndex(KEY_DP);
        String res="";
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
            if (c.getString(i_id).equals(check)) {
                res = res + c.getString(i_name) + ", " + c.getString(i_id) + ", " + c.getString(i_user) + ", " + c.getString(i_city)
                        +", "+c.getString(i_dp);
                break;
            }
        }
        return res;
    }

    public void setDP(String check,String path)
    {
        String valid[]=new String[]{KEY_ID,KEY_DP};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(check))
            {
                ContentValues cv=new ContentValues();
                cv.put(KEY_DP,path);
                break;
            }
        }
    }

    public String getDP(String check)
    {
        String valid[]=new String[]{KEY_ID,KEY_DP};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        String res="";
        int i_id=c.getColumnIndex(KEY_ID);
        int i_dp=c.getColumnIndex(KEY_DP);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(check))
            {
                res=c.getString(i_dp);
                break;
            }
        }
        return res;
    }

    public String getName(String check)
    {
        String valid[]=new String[]{KEY_NAME,KEY_ID};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_phn=c.getColumnIndex(KEY_NAME);
        int i_id=c.getColumnIndex(KEY_ID);
        String res="";
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(i_id).equals(check))
            {
                res=c.getString(i_phn);
                break;
            }
        }
        return res;
    }
}
