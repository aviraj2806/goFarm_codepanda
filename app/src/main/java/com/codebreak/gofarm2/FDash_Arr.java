package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FDash_Arr extends AppCompatActivity {

    TextView arr_back,arr_mstatus,arr_msell,arr_msellcrop,arr_msellmarket,arr_msellq,arr_msell1,
            arr_mstatusmarket,arr_mstatuscrop,arr_mstatus1;
    Button arr_status,arr_statusnext,arr_sell,arr_sellnext;
    Spinner arr_statuscrop,arr_status_market,arr_sellcrop,arr_sellmarket;
    ArrayAdapter statcrop,statmarket,sellcrop,sellmarket;
    ImageView arr_statusback,arr_sellback;
    EditText arr_sellq;
    LinearLayout arr_lstatus,arr_lsell;
    String id;
    Drawable error;
    Calendar calendar;
    Date today,tomorrow;
    DateFormat dateFormat;
    String req,current,time;
    Animation a,b,c,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_fdash__arr);

        arr_status=findViewById(R.id.arr_status);
        arr_back=findViewById(R.id.arr_back);
        arr_mstatus=findViewById(R.id.arr_mstatus);
        arr_statusnext=findViewById(R.id.arrs_statusnext);
        arr_statuscrop=findViewById(R.id.arr_statuscrop);
        arr_status_market=findViewById(R.id.arr_statusmarket);
        arr_statusback=findViewById(R.id.arr_statusback);
        arr_lstatus=findViewById(R.id.arr_lstatus);
        arr_sell=findViewById(R.id.arr_sell);
        arr_msell=findViewById(R.id.arr_msell);
        arr_sellnext=findViewById(R.id.arrs_sellnext);
        arr_sellmarket=findViewById(R.id.arr_sellmarket);
        arr_sellcrop=findViewById(R.id.arr_sellcrop);
        arr_sellq=findViewById(R.id.arr_sellq);
        arr_sellback=findViewById(R.id.arr_sellback);
        arr_lsell=findViewById(R.id.arr_lsell);
        arr_msellcrop=findViewById(R.id.arr_msellcrop);
        arr_msellmarket=findViewById(R.id.arr_msellmarket);
        arr_msellq=findViewById(R.id.arr_msellq);
        arr_msell1=findViewById(R.id.arr_msell1);
        arr_mstatusmarket=findViewById(R.id.arr_mstatusmarket);
        arr_mstatuscrop=findViewById(R.id.arr_mstatuscrop);
        arr_mstatus1=findViewById(R.id.arr_mstatus1);
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        a= AnimationUtils.loadAnimation(this,R.anim.fadein);
        b=AnimationUtils.loadAnimation(this,R.anim.push_down_in);
        c=AnimationUtils.loadAnimation(this,R.anim.push_right_out);
        d=AnimationUtils.loadAnimation(this,R.anim.push_up_out);

        id=getIntent().getStringExtra("id");

        arr_lstatus.setVisibility(View.GONE);
        arr_mstatus.setVisibility(View.GONE);
        arr_lsell.setVisibility(View.GONE);
        arr_msell.setVisibility(View.GONE);
        arr_msell1.setVisibility(View.GONE);
        arr_mstatus1.setVisibility(View.GONE);

        calendar=Calendar.getInstance();
        today=calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        tomorrow=calendar.getTime();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        req=dateFormat.format(tomorrow);
        current=dateFormat.format(today);

        arr_sellq.setHint(getResources().getString(R.string.arr_quan));

        SimpleDateFormat current1=new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        time=current1.format(new Date());

        arr_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FDash_Arr.this,com.codebreak.gofarm2.FarmerDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });
    }

    public void onSell(View v)
    {
        arr_lsell.setVisibility(View.VISIBLE);
        arr_sell.setCompoundDrawables(null,null,null,null);
        arr_sell.setBackground(getResources().getDrawable(R.drawable.upper));
        arr_msellcrop.setVisibility(View.VISIBLE);
        arr_msellmarket.setVisibility(View.VISIBLE);


        sellcrop=ArrayAdapter.createFromResource(FDash_Arr.this,R.array.crop,R.layout.fac_text_spinner);
        arr_sellcrop.setAdapter(sellcrop);
        arr_sellcrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(arr_sellcrop.getSelectedItem().toString().equals("Select Crop") || arr_sellcrop.getSelectedItem().toString().equals("फसल का चयन करें") ||
                arr_sellcrop.getSelectedItem().toString().equals("पीक निवडा"))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Basic));
                }
                else
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Option));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sellmarket=ArrayAdapter.createFromResource(FDash_Arr.this,R.array.market,R.layout.fac_text_spinner);
        arr_sellmarket.setAdapter(sellmarket);
        arr_sellmarket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(arr_sellmarket.getSelectedItem().toString().equals("Select Market Yard") || arr_sellmarket.getSelectedItem().toString().equals("मार्क यार्ड का चयन करें") ||
                arr_sellmarket.getSelectedItem().toString().equals("मार्केट यार्ड निवडा"))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Basic));
                }
                else
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Option));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        arr_sellnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String crop = arr_sellcrop.getSelectedItem().toString();
                String market = arr_sellmarket.getSelectedItem().toString();
                String amount = arr_sellq.getText().toString().trim();

                int ctr = 0;
                if (market.equals("Select Market Yard") || market.equals("मार्क यार्ड का चयन करें") || market.equals("मार्केट यार्ड निवडा")) {
                    arr_msellmarket.setError("", error);
                    ctr++;
                }
                if (crop.equals("Select Crop") || crop.equals("फसल का चयन करें") || crop.equals("पीक निवडा")) {
                    arr_msellcrop.setError("", error);
                    ctr++;
                }
                if (amount.equals("")) {
                    arr_msellq.setError("", error);
                    ctr++;
                }

                if (ctr > 0) {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout));
                    TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
                    toast_text.setText(getResources().getString(R.string.allfields));

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }

                if (ctr == 0) {
                    arr_sellcrop.setVisibility(View.GONE);
                    arr_sellmarket.setVisibility(View.GONE);
                    arr_sellq.setVisibility(View.GONE);
                    arr_sellnext.setVisibility(View.GONE);
                    arr_msellmarket.setVisibility(View.GONE);
                    arr_msellcrop.setVisibility(View.GONE);
                    arr_msellq.setVisibility(View.GONE);

                    arr_sellcrop.clearAnimation();
                    arr_sellcrop.startAnimation(c);
                    arr_msellmarket.clearAnimation();
                    arr_msellmarket.startAnimation(c);
                    arr_sellmarket.clearAnimation();
                    arr_sellmarket.startAnimation(c);
                    arr_sellq.clearAnimation();
                    arr_sellq.startAnimation(c);
                    arr_sellnext.clearAnimation();
                    arr_sellnext.startAnimation(c);
                    arr_msellcrop.clearAnimation();
                    arr_msellcrop.startAnimation(c);
                    arr_msellq.clearAnimation();
                    arr_msellq.startAnimation(c);


                    CropDB db = new CropDB(FDash_Arr.this);
                    db.open();
                    db.createEntry(id, market, crop, amount,req);
                    db.close();

                    arr_msell.setVisibility(View.VISIBLE);
                    arr_msell1.setVisibility(View.VISIBLE);

                    arr_msell.clearAnimation();
                    arr_msell.startAnimation(a);
                    arr_msell1.clearAnimation();
                    arr_msell1.startAnimation(a);

                    arr_msell.setText(amount+" "+getResources().getString(R.string.quan)+" "+crop+" "+getResources().getString(R.string.arrsuc)+" "+market+" "+getResources().getString(R.string.arr_final)+"\n");
                    arr_msell1.setText(getResources().getString(R.string.arr_m2)
                            +"\n"+getResources().getString(R.string.arr_m3)+"\n"+getResources().getString(R.string.happyfarming)+"\n"+getResources().getString(R.string.gofarm));
                }
            }
        });

        arr_sellback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr_sellmarket.setVisibility(View.VISIBLE);
                arr_sellcrop.setVisibility(View.VISIBLE);
                arr_sellq.setVisibility(View.VISIBLE);
                arr_sellnext.setVisibility(View.VISIBLE);
                arr_msell.setVisibility(View.GONE);
                arr_lsell.setVisibility(View.GONE);
                arr_msell1.setVisibility(View.GONE);
                arr_msellq.setVisibility(View.VISIBLE);
                arr_sell.setBackground(getResources().getDrawable(R.drawable.fac_button));
                arr_sell.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);

                arr_msellcrop.setError(null);
                arr_msellq.setError(null);
                arr_msellmarket.setError(null);
            }
        });
    }

    public void onStatus(View v)
    {
        arr_lstatus.setVisibility(View.VISIBLE);
        arr_status.setCompoundDrawables(null,null,null,null);
        arr_status.setBackground(getResources().getDrawable(R.drawable.upper));
        arr_mstatuscrop.setVisibility(View.VISIBLE);
        arr_mstatusmarket.setVisibility(View.VISIBLE);


        statcrop=ArrayAdapter.createFromResource(FDash_Arr.this,R.array.crop,R.layout.fac_text_spinner);
        arr_statuscrop.setAdapter(statcrop);
        arr_statuscrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(arr_statuscrop.getSelectedItem().toString().equals("Select Crop") || arr_statuscrop.getSelectedItem().toString().equals("फसल का चयन करें") ||
                        arr_statuscrop.getSelectedItem().toString().equals("पीक निवडा"))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Basic));
                }
                else
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Option));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        statmarket=ArrayAdapter.createFromResource(FDash_Arr.this,R.array.market,R.layout.fac_text_spinner);
        arr_status_market.setAdapter(statmarket);
        arr_status_market.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(arr_status_market.getSelectedItem().toString().equals("Select Market Yard") || arr_status_market.getSelectedItem().toString().equals("मार्क यार्ड का चयन करें") ||
                        arr_status_market.getSelectedItem().toString().equals("मार्केट यार्ड निवडा"))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Basic));
                }
                else
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Option));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        arr_statusnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String market=arr_status_market.getSelectedItem().toString();
                String crop=arr_statuscrop.getSelectedItem().toString();

                int ctr=0;

                if(market.equals("Select Market Yard") || market.equals("मार्क यार्ड का चयन करें") || market.equals("मार्केट यार्ड निवडा"))
                {
                    arr_mstatusmarket.setError("",error);
                    ctr++;
                }
                if(crop.equals("Select Crop") || crop.equals("फसल का चयन करें") || crop.equals("पीक निवडा"))
                {
                    arr_mstatuscrop.setError("",error);
                    ctr++;
                }

                if(ctr>0)
                {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout));
                    TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
                    toast_text.setText(getResources().getString(R.string.allfields));

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }

                if(ctr==0)
                {
                    int tamount=0;

                    CropDB db=new CropDB(FDash_Arr.this);
                    db.open();
                    tamount=db.getStat(crop,market,req);
                    db.close();

                    arr_statuscrop.setVisibility(View.GONE);
                    arr_statusnext.setVisibility(View.GONE);
                    arr_status_market.setVisibility(View.GONE);
                    arr_mstatus.setVisibility(View.VISIBLE);
                    arr_mstatusmarket.setVisibility(View.GONE);
                    arr_mstatuscrop.setVisibility(View.GONE);
                    arr_mstatus1.setVisibility(View.VISIBLE);
                    arr_mstatus.clearAnimation();
                    arr_mstatus.startAnimation(a);
                    arr_statuscrop.clearAnimation();
                    arr_statuscrop.startAnimation(c);
                    arr_status_market.clearAnimation();
                    arr_status_market.startAnimation(c);
                    arr_statusnext.clearAnimation();
                    arr_statusnext.startAnimation(c);
                    arr_mstatuscrop.clearAnimation();
                    arr_mstatuscrop.startAnimation(c);
                    arr_mstatusmarket.clearAnimation();
                    arr_mstatusmarket.startAnimation(c);
                    arr_mstatus1.clearAnimation();
                    arr_mstatus1.startAnimation(a);


                    arr_mstatus.setText(getResources().getString(R.string.arr_m4)+current+"\n"+"("+time+" IST), "+tamount+" "+getResources().getString(R.string.quan)+" "+crop+" "+
                            getResources().getString(R.string.arr_m6)+" "+market+getResources().getString(R.string.arr_final)+"\n");
                    arr_mstatus1.setText(getResources().getString(R.string.arr_m2)
                            +"\n"+getResources().getString(R.string.happyfarming)+"\n"+getResources().getString(R.string.gofarm));
                }

            }
        });

        arr_statusback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr_mstatus.setVisibility(View.GONE);
                arr_statuscrop.setVisibility(View.VISIBLE);
                arr_status_market.setVisibility(View.VISIBLE);
                arr_statusnext.setVisibility(View.VISIBLE);
                arr_lstatus.setVisibility(View.GONE);
                arr_status.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);
                arr_status.setBackground(getResources().getDrawable(R.drawable.fac_button));
                arr_mstatus1.setVisibility(View.GONE);

                arr_mstatusmarket.setError(null);
                arr_mstatuscrop.setError(null);

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
