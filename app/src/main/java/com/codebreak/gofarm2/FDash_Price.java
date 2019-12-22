package com.codebreak.gofarm2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Locale;

public class FDash_Price extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    TextView price_back,price_market,price1,price2,price3,price4,price5,price_infocrop,price_infodate,
            price_infoarr,price_infomin,price_infomax,price_mcrop,price_mdate,price_date,price_mmarket;
    Spinner price_crop,price_askmarket;
    ArrayAdapter crop,market;
    LinearLayout price_linfo,price_lask;
    Drawable error;
    Calendar calendar;
    Animation a,b;
    Button price_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_fdash__price);

        price_linfo=findViewById(R.id.price_linfo);
        price_lask=findViewById(R.id.price_lask);
        price_back=findViewById(R.id.price_back);
        price_market=findViewById(R.id.price_market);
        price_infodate=findViewById(R.id.price_infodate);
        price_infoarr=findViewById(R.id.price_infoarr);
        price_infomin=findViewById(R.id.price_infomin);
        price_infomax=findViewById(R.id.price_infomax);
        price_mcrop=findViewById(R.id.price_mcrop);
        price_mdate=findViewById(R.id.price_mdate);
        price_date=findViewById(R.id.price_date);
        price_mmarket=findViewById(R.id.price_mmarket);
        price_crop=findViewById(R.id.price_crop);
        price_infocrop=findViewById(R.id.price_infocrop);
        price_new=findViewById(R.id.price_new);
        price_askmarket=findViewById(R.id.price_askmarket);
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        a= AnimationUtils.loadAnimation(this,R.anim.push_right_out);
        b=AnimationUtils.loadAnimation(this,R.anim.fadein);


        price_linfo.setVisibility(View.GONE);


        price_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FDash_Price.this,com.codebreak.gofarm2.FarmerDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        market=ArrayAdapter.createFromResource(this,R.array.market,R.layout.text_spinner);
        price_askmarket.setAdapter(market);
        price_askmarket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(price_askmarket.getSelectedItem().toString().equals("Select Market Yard") || price_askmarket.getSelectedItem().toString().equals("मार्क यार्ड का चयन करें") ||
                price_askmarket.getSelectedItem().toString().equals("मार्केट यार्ड निवडा")) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.DD_Basic));
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

        crop=ArrayAdapter.createFromResource(this,R.array.crop,R.layout.text_spinner);
        price_crop.setAdapter(crop);
        price_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(price_crop.getSelectedItem().toString().equals("Select Crop") || price_crop.getSelectedItem().toString().equals("फसल का चयन करें") ||
                price_crop.getSelectedItem().toString().equals("पीक निवडा"))
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
        price_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment dp=new DatePickerFragment();
                dp.show(getSupportFragmentManager(),"date picker");
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);
        String curr= DateFormat.getDateInstance().format(calendar.getTime());
        price_date.setText(curr);
        price_date.setTextColor(getResources().getColor(R.color.DD_Option));
    }

    public void onInfo(View v)
    {
        String crop=price_crop.getSelectedItem().toString();
        String market=price_askmarket.getSelectedItem().toString();
        String date=price_date.getText().toString().trim();

        double min=0,max=0,arr=0;
        int ctr=0;

        if(crop.equals("Select Crop") || crop.equals("पीक निवडा") || crop.equals("फसल का चयन करें"))
        {
            price_mcrop.setError("",error);
            ctr++;
        }

        if(market.equals("Select Market Yard") || market.equals("मार्क यार्ड का चयन करें") || market.equals("मार्केट यार्ड निवडा"))
        {
            price_mmarket.setError("",error);
            ctr++;
        }

        if(date.equals("Select Date") || date.equals("तारीख़ चुनें") || date.equals("तारीख निवडा"))
        {
            price_mdate.setError("",error);
            ctr++;
        }


        if(ctr==0) {
            PriceDB db = new PriceDB(this);
            db.open();
            min = db.getMin(crop, market, date);
            max = db.getMax(crop, market, date);
            arr = db.getArr(crop, market, date);
            db.close();


            price_lask.setVisibility(View.GONE);
            price_linfo.setVisibility(View.VISIBLE);

            price_market.setText(market+" "+getResources().getString(R.string.market2)+"   ");
            price_infocrop.setText(crop);
            price_infodate.setText(date);
            price_infomin.setText(Double.toString(min));
            price_infomax.setText(Double.toString(max));
            price_infoarr.setText(Double.toString(arr));

            price_linfo.clearAnimation();
            price_lask.clearAnimation();

            price_linfo.startAnimation(b);
            price_lask.startAnimation(a);

        }

    }

    public void onNew(View v)
    {
        price_linfo.setVisibility(View.GONE);
        price_lask.setVisibility(View.VISIBLE);

        price_linfo.clearAnimation();
        price_lask.clearAnimation();

        price_linfo.startAnimation(a);
        price_lask.startAnimation(b);

        price_date.setText(getResources().getString(R.string.select_date));
        price_date.setTextColor(getResources().getColor(R.color.DD_Basic));

        price_askmarket.setAdapter(market);
        price_crop.setAdapter(crop);
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
