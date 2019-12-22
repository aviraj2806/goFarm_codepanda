package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class FDash_Sell extends AppCompatActivity {

    String id;
    TextView sell_back,sell_final,sell_m,sell_m1,sell_mcrop,sell_mcity,sell_mq;
    Spinner sell_crop,sell_city;
    ArrayAdapter crop,city;
    EditText sell_q;
    Button sell_submit;
    Drawable error;
    Animation r,f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_fdash__sell);

        id=getIntent().getStringExtra("id");

        sell_back=findViewById(R.id.sell_back);
        sell_mq=findViewById(R.id.sell_mq);
        sell_mcrop=findViewById(R.id.sell_mcrop);
        sell_mcity=findViewById(R.id.sell_mcity);
        sell_m1=findViewById(R.id.sell_m1);
        sell_m=findViewById(R.id.sell_m);
        sell_final=findViewById(R.id.sell_final);
        sell_crop=findViewById(R.id.sell_crop);
        sell_city=findViewById(R.id.sell_city);
        sell_q=findViewById(R.id.sell_q);
        sell_submit=findViewById(R.id.sell_submit);
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        r= AnimationUtils.loadAnimation(this,R.anim.push_right_out);
        f=AnimationUtils.loadAnimation(this,R.anim.fadein);

        sell_final.setVisibility(View.GONE);
        sell_m1.setVisibility(View.GONE);
        sell_m.setVisibility(View.GONE);

        crop=ArrayAdapter.createFromResource(this,R.array.crop,R.layout.text_spinner);
        sell_crop.setAdapter(crop);
        sell_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sell_crop.getSelectedItem().equals(getResources().getString(R.string.mmcrop)))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.DD_Basic));
                }
                else
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.DD_Option));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        city=ArrayAdapter.createFromResource(this,R.array.city,R.layout.text_spinner);
        sell_city.setAdapter(city);
        sell_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sell_city.getSelectedItem().equals(getResources().getString(R.string.scity)))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.DD_Basic));
                }
                else
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.DD_Option));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sell_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FDash_Sell.this,com.codebreak.gofarm2.FarmerDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        sell_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=sell_city.getSelectedItem().toString();
                String crop=sell_crop.getSelectedItem().toString();
                String qu=sell_q.getText().toString().trim();
                int ctr=0;
                if(city.equals(getResources().getString(R.string.scity)))
                {
                    sell_mcity.setError("",error);
                    ctr++;
                }

                if(crop.equals(getResources().getString(R.string.marr_crop)))
                {
                    sell_mcrop.setError("",error);
                    ctr++;
                }

                if(qu.equals(""))
                {
                    sell_mq.setError("",error);
                    ctr++;
                }

                if(ctr==0)
                {
                    sell_final.setVisibility(View.VISIBLE);
                    sell_m1.setVisibility(View.VISIBLE);
                    sell_m.setVisibility(View.VISIBLE);

                    sell_city.setVisibility(View.GONE);
                    sell_mcity.setVisibility(View.GONE);
                    sell_crop.setVisibility(View.GONE);
                    sell_mcrop.setVisibility(View.GONE);
                    sell_q.setVisibility(View.GONE);
                    sell_mq.setVisibility(View.GONE);
                    sell_submit.setVisibility(View.GONE);

                    sell_final.clearAnimation();
                    sell_m1.clearAnimation();
                    sell_m.clearAnimation();
                    sell_submit.clearAnimation();

                    sell_city.clearAnimation();
                    sell_mcity.clearAnimation();
                    sell_crop.clearAnimation();
                    sell_mcrop.clearAnimation();
                    sell_q.clearAnimation();
                    sell_mq.clearAnimation();

                    sell_final.startAnimation(f);
                    sell_m1.startAnimation(f);
                    sell_m.startAnimation(f);

                    sell_city.startAnimation(r);
                    sell_mcity.startAnimation(r);
                    sell_crop.startAnimation(r);
                    sell_mcrop.startAnimation(r);
                    sell_q.startAnimation(r);
                    sell_mq.startAnimation(r);
                    sell_submit.startAnimation(r);

                    Random rndm_method = new Random();
                    String set = "0123456789";
                    String set1="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    int i=0;
                    String fref="";
                    final char[] ref=new char[10];
                    for (i = 0; i < 10; i++) {
                        if (i < 3) {
                            ref[i] = set1.charAt(rndm_method.nextInt(set1.length()));
                            fref = fref + ref[i];
                        } else {
                            ref[i] = set.charAt(rndm_method.nextInt(set.length()));
                            fref = fref + ref[i];
                        }

                    }

                    FarmerDB db1=new FarmerDB(FDash_Sell.this);
                    db1.open();
                    String phn= db1.getPhn(id);
                    String name=db1.getName(id);
                    db1.close();

                    DP db2=new DP(FDash_Sell.this);
                    db2.open();
                    String dp=db2.getPath(id);
                    db2.close();

                    if(dp=="")
                    {
                        dp="hello";
                    }

                    SellDB db=new SellDB(FDash_Sell.this);
                    db.open();
                    db.createEntry(crop,city,qu,id,name,phn,dp);
                    db.close();

                    sell_m.setText(getResources().getString(R.string.qref)+" "+fref);
                    sell_m1.setText(getResources().getString(R.string.traderint)+"\n"+getResources().getString(R.string.thankyou)+"\n"+getResources().getString(R.string.teamgo));
                }
            }
        });

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
