package com.codebreak.gofarm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DP {

    public static final String KEY_ID = "farmer_id";
    public static final String KEY_DP = "dp_path";


    private final String DATABASE_NAME = "ProfileDB";
    private final String DATABASE_TABLE = "ProfileTable";
    private final int DATABASE_VERSION = 1;

    private DP.DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public DP(Context context) {
        ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
             /*
            CREATE TABLE FarmerTable(user_type TEXT NOT NULL,
                                        farmer_name TEXT NOT NULL, farmer_phn TEXT NOT NULL,
                                        farmer_uid TEXT NOT NULL, farmer_city TEXT NOT NULL,
                                        farmer_id TEXT NOT NULL, farmer_pass TEXT NOT NULL);
            */

            String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ID + " TEXT NOT NULL, "
                    +KEY_DP+" TEXT NOT NULL);";

            sqLiteDatabase.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public DP open() throws SQLException
    {
        ourHelper=new DP.DBHelper(ourContext);
        ourDatabase= ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long createEntry(String id,String path)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_ID,id);
        cv.put(KEY_DP,path);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public String getPath(String check)
    {
        String valid[]=new String[]{KEY_ID,KEY_DP};
        Cursor c=ourDatabase.query(DATABASE_TABLE,valid,null,null,null,null,null);
        int i_id=c.getColumnIndex(KEY_ID);
        int i_dp=c.getColumnIndex(KEY_DP);
        String res="";
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

    public long setPath(String id,String path)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_DP,path);
        return  ourDatabase.update(DATABASE_TABLE,cv,KEY_ID+"=?",new String[]{id});
    }

}
