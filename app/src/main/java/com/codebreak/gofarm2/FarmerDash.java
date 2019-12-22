package com.codebreak.gofarm2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FarmerDash extends AppCompatActivity {

    public static int   CAMERA_LOAD_IMAGE=1;
    TextView fdash_logout,fdash_name,fdash_id,fdash_user,fdash_city,fdash_arr,fdash_adv,fdash_bank
            ,fdash_govt,fdash_price,fdash_shop,fdash_edit,fdash_sell,fdash_earn,fdash_trader;
    ImageView fdash_dp;
    String path;
    String id;
    Calendar calendar;
    Date today;
    DateFormat dateFormat;
    String current;
    TextView fdash_trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_farmer_dash);

        fdash_logout=findViewById(R.id.fdash_logout);
        fdash_name=findViewById(R.id.fdash_name);
        fdash_id=findViewById(R.id.fdash_id);
        fdash_trader=findViewById(R.id.fdash_trader);
        fdash_user=findViewById(R.id.fdash_user);
        fdash_city=findViewById(R.id.fdash_city);
        fdash_arr=findViewById(R.id.fdash_arr);
        fdash_adv=findViewById(R.id.fdash_adv);
        fdash_bank=findViewById(R.id.fdash_bank);
        fdash_govt=findViewById(R.id.fdash_govt);
        fdash_price=findViewById(R.id.fdash_price);
        fdash_shop=findViewById(R.id.fdash_shop);
        fdash_dp=findViewById(R.id.fdash_dp);
        fdash_edit=findViewById(R.id.fdash_edit);
        fdash_sell=findViewById(R.id.fdash_sell);
        fdash_earn=findViewById(R.id.fdash_earn);
        fdash_trans=findViewById(R.id.fdash_trans);

        calendar=Calendar.getInstance();
        today=calendar.getTime();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        current=dateFormat.format(today);


        id=getIntent().getStringExtra("id");

        fdash_arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FarmerDash.this,com.codebreak.gofarm2.FDash_Arr.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

        fdash_adv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FarmerDash.this,com.codebreak.gofarm2.FDash_Adv.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

        fdash_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i=0;
                LoanDB db=new LoanDB(FarmerDash.this);
                db.open();
                i=db.validLoan(id,current);
                db.close();

                if(i==0) {
                    Intent intent = new Intent(FarmerDash.this, com.codebreak.gofarm2.FDash_Loans.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }

                if(i==1)
                {
                    Intent intent = new Intent(FarmerDash.this, com.codebreak.gofarm2.LoanSuccess.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });

        fdash_govt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FarmerDash.this,com.codebreak.gofarm2.FDash_Govt.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

        fdash_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FarmerDash.this,com.codebreak.gofarm2.FDash_Price.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

        fdash_earn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FarmerDash.this,com.codebreak.gofarm2.FDash_Earn.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

       fdash_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SellDB db=new SellDB(FarmerDash.this);
                db.open();
                int res=db.isValid(id);
                db.close();

                if(res==0) {
                    Intent intent = new Intent(FarmerDash.this, com.codebreak.gofarm2.FDash_Sell.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }

                else
                {
                    Intent intent = new Intent(FarmerDash.this, com.codebreak.gofarm2.FDash_SellEdit.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });

        fdash_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent=new Intent(FarmerDash.this,com.codebreak.gofarm2.HomePage.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                FarmerDash.this.finish();

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }

                };
                AlertDialog.Builder builder=new AlertDialog.Builder(FarmerDash.this);
                builder.setMessage(getResources().getString(R.string.clog)).setPositiveButton(getResources().getString(R.string.yes),dialog).setNegativeButton(getResources().getString(R.string.no),dialog).show();

            }
        });

        fdash_trader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDash.this, com.codebreak.gofarm2.FDash_Trader.class);
                intent.putExtra("id", id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        fdash_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDash.this, com.codebreak.gofarm2.FDash_Transport.class);
                intent.putExtra("id", id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        String dash;
        FarmerDB db=new FarmerDB(this);
        db.open();
        dash=db.getDash(id);
        db.close();

        String[] split=dash.split(", ");
        fdash_name.setText(split[0]);
        fdash_id.setText(split[1]);
        fdash_user.setText(split[2]);
        fdash_city.setText(split[3]);


        String res;
        DP db1=new DP(this);
        db1.open();
        res=db1.getPath(id);
        db1.close();

        if(res.equals(""))
        {
            fdash_dp.setImageResource(R.drawable.profilepic);
        }

        else
        {
            File file=new File(res);
            if(file.exists())
            {
                Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                ImageView my=( ImageView)findViewById(R.id.fdash_dp);
                my.setImageBitmap(bitmap);
            }
            fdash_edit.setText(getResources().getString(R.string.change));

        }
    }

    public void uploadDP(View v)
    {
        fdash_edit.setText(getResources().getString(R.string.change));
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,101);
        overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==RESULT_OK && data!=null)
        {
            Uri uri=data.getData();
            fdash_dp.setImageURI(uri);
            String[]filePathColumn={MediaStore.Images.Media.DATA};
            Cursor c=getContentResolver().query(uri,filePathColumn,null,null,null);
            c.moveToFirst();
            int columnIndex=c.getColumnIndex(filePathColumn[0]);
            path=c.getString(columnIndex);
            c.close();

            DP db=new DP(this);
            db.open();
            db.createEntry(id,path);
            db.setPath(id,path);
            db.close();

        }
    }
    public void loadLaocale()
    {
        SharedPreferences preferences=getSharedPreferences("Settings",MODE_PRIVATE);
        String language=preferences.getString("My_Lang","");
        setLocale(language);
    }

    private void setLocale(String lang) {

        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }
}
