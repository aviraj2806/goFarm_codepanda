package com.codebreak.gofarm2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FDash_Earn extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    TextView fdash_earnbak,earn_marr1,earn_marr2;
    Button earn_arr;
    ImageView earn_backarr,earn_backinfo;
    LinearLayout earn_larr;
    TextView earn_minfo,earn_minfo1,earn_minfomarket,earn_minfocrop,earn_minfodate,earn_minfomin,earn_minfomax,
            earn_minfoarr,earn_date;
    Button earn_info,earn_infonext;
    LinearLayout earn_linfo;
    Spinner earn_infomarket,earn_infocrop;
    ArrayAdapter market,crop;
    EditText earn_infomin,earn_infomax,earn_infoarr;
    Drawable error;
    String id;
    Calendar calendar,calendar2;
    android.icu.util.Calendar calendar1;
    Date today,tomorrow;
    DateFormat dateFormat;
    String req,current;
    Animation r,f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_fdash__earn);

        id=getIntent().getStringExtra("id");

        earn_infoarr=findViewById(R.id.earn_infoarr);
        earn_infomax=findViewById(R.id.earn_infomax);
        earn_infomin=findViewById(R.id.earn_infomin);
        earn_infocrop=findViewById(R.id.earn_infocrop);
        earn_infomarket=findViewById(R.id.earn_infomarket);
        earn_infonext=findViewById(R.id.earn_infonext);
        earn_info=findViewById(R.id.earn_info);
        earn_date=findViewById(R.id.earn_date);
        earn_minfoarr=findViewById(R.id.earn_minfoarr);
        earn_minfomin=findViewById(R.id.earn_minfomin);
        earn_minfomax=findViewById(R.id.earn_minfomax);
        earn_minfodate=findViewById(R.id.earn_minfodate);
        earn_minfocrop=findViewById(R.id.earn_minfocrop);
        earn_minfomarket=findViewById(R.id.earn_minfomarket);
        earn_minfo=findViewById(R.id.earn_minfo);
        earn_minfo1=findViewById(R.id.earn_minfo1);
        earn_linfo=findViewById(R.id.earn_linfo);
        earn_larr=findViewById(R.id.earn_larr);
        earn_arr=findViewById(R.id.earn_arr);
        earn_backarr=findViewById(R.id.earn_backarr);
        earn_marr1=findViewById(R.id.earn_marr1);
        earn_marr2=findViewById(R.id.earn_marr2);
        earn_backinfo=findViewById(R.id.earn_infoback);
        fdash_earnbak=findViewById(R.id.fdash_earnback);
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        r= AnimationUtils.loadAnimation(this,R.anim.push_right_out);
        f=AnimationUtils.loadAnimation(this,R.anim.fadein);

        calendar=Calendar.getInstance();
        today=calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        tomorrow=calendar.getTime();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        req=dateFormat.format(tomorrow);
        current=dateFormat.format(today);

        fdash_earnbak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FDash_Earn.this,com.codebreak.gofarm2.FarmerDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        earn_larr.setVisibility(GONE);
        earn_linfo.setVisibility(GONE);
        earn_minfo.setVisibility(GONE);
        earn_minfo1.setVisibility(GONE);

        market=ArrayAdapter.createFromResource(this,R.array.market,R.layout.fac_text_spinner);
        earn_infomarket.setAdapter(market);
        earn_infomarket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(earn_infomarket.getSelectedItem().toString().equals(getResources().getString(R.string.myard)))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Basic));
                }
                else
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Option));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        crop=ArrayAdapter.createFromResource(this,R.array.crop,R.layout.fac_text_spinner);
        earn_infocrop.setAdapter(crop);
        earn_infocrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(earn_infocrop.getSelectedItem().equals(getResources().getString(R.string.mmcrop)))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Basic));
                }
                else
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.FAC_Option));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        earn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment dp=new DatePickerFragment();
                dp.show(getSupportFragmentManager(),"date picker");
            }
        });

    }

    public void onEarnArr(View v)
    {
        earn_larr.setVisibility(VISIBLE);
        earn_arr.setBackground(getResources().getDrawable(R.drawable.upper));
        earn_arr.setCompoundDrawables(null,null,null,null);

        int count=0;
        int fin=2;
        CropDB db=new CropDB(this);
        db.open();
        count=db.getEntry(id,current,req);
        db.close();
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

        if(count<2)
        {
            earn_marr1.setText(getResources().getString(R.string.trayagain));
            earn_marr2.setText(getResources().getString(R.string.earn1)+"\n\n"+
                    getResources().getString(R.string.thankyou)+"\n"+getResources().getString(R.string.teamgo));
        }

        if(count>=2)
        {
            earn_marr1.setGravity(Gravity.CENTER);
            earn_marr1.setText(getResources().getString(R.string.congo)+"\n"+getResources().getString(R.string.coupon)+" "+fref+"\n");
            earn_marr2.setText(getResources().getString(R.string.use)+"\n"+getResources().getString(R.string.value)+"\n"+getResources().getString(R.string.teamgo));
        }

        earn_backarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                earn_larr.setVisibility(GONE);
                earn_arr.setBackground(getResources().getDrawable(R.drawable.my_spinner));
                earn_arr.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);
            }
        });




    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        calendar1= android.icu.util.Calendar.getInstance();
        calendar1.set(android.icu.util.Calendar.YEAR,i);
        calendar1.set(android.icu.util.Calendar.MONTH,i1);
        calendar1.set(android.icu.util.Calendar.DAY_OF_MONTH,i2);
        String curr= DateFormat.getDateInstance().format(calendar1.getTime());
        earn_date.setText(curr);
        earn_date.setTextColor(getResources().getColor(R.color.FAC_Option));
    }

    public void onEarnInfo(View v)
    {
        earn_linfo.setVisibility(VISIBLE);
        earn_info.setBackground(getResources().getDrawable(R.drawable.upper));
        earn_info.setCompoundDrawables(null,null,null,null);

        earn_infonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int ctr=0;
                String market=earn_infomarket.getSelectedItem().toString();
                String crop=earn_infocrop.getSelectedItem().toString();
                String date=earn_date.getText().toString().trim();
                String min=earn_infomin.getText().toString().trim();
                String max=earn_infomax.getText().toString().trim();
                String arr=earn_infoarr.getText().toString().trim();

                if(market.equals(getResources().getString(R.string.marr_my)))
                {
                    earn_minfomarket.setError("",error);
                    ctr++;
                }

                if(crop.equals(getResources().getString(R.string.marr_crop)))
                {
                    earn_minfocrop.setError("",error);
                    ctr++;
                }

                if(date.equals(getResources().getString(R.string.select_date)))
                {
                    earn_minfodate.setError("",error);
                    ctr++;
                }

                if(min.equals(""))
                {
                    earn_minfomin.setError("",error);
                    ctr++;
                }

                if(max.equals(""))
                {
                    earn_minfomax.setError("",error);
                    ctr++;
                }

                if(arr.equals(""))
                {
                    earn_minfoarr.setError("",error);
                    ctr++;
                }

                if(ctr==0)
                {
                    earn_minfomarket.setVisibility(GONE);
                    earn_infomarket.setVisibility(GONE);
                    earn_minfocrop.setVisibility(GONE);
                    earn_infocrop.setVisibility(GONE);
                    earn_minfodate.setVisibility(GONE);
                    earn_date.setVisibility(GONE);
                    earn_minfomin.setVisibility(GONE);
                    earn_infomin.setVisibility(GONE);
                    earn_minfomax.setVisibility(GONE);
                    earn_infomax.setVisibility(GONE);
                    earn_minfoarr.setVisibility(GONE);
                    earn_infoarr.setVisibility(GONE);
                    earn_infonext.setVisibility(GONE);

                    earn_minfo.setVisibility(VISIBLE);
                    earn_minfo1.setVisibility(VISIBLE);

                    earn_minfo.clearAnimation();
                    earn_minfo1.clearAnimation();

                    earn_minfo.startAnimation(f);
                    earn_minfo1.startAnimation(f);

                    earn_minfomarket.clearAnimation();
                    earn_infomarket.clearAnimation();
                    earn_minfocrop.clearAnimation();
                    earn_infocrop.clearAnimation();
                    earn_minfodate.clearAnimation();
                    earn_date.clearAnimation();
                    earn_minfomin.clearAnimation();
                    earn_infomin.clearAnimation();
                    earn_minfomax.clearAnimation();
                    earn_infomax.clearAnimation();
                    earn_minfoarr.clearAnimation();
                    earn_infoarr.clearAnimation();
                    earn_infonext.clearAnimation();

                    earn_minfomarket.startAnimation(r);
                    earn_infomarket.startAnimation(r);
                    earn_minfocrop.startAnimation(r);
                    earn_infocrop.startAnimation(r);
                    earn_minfodate.startAnimation(r);
                    earn_date.startAnimation(r);
                    earn_minfomin.startAnimation(r);
                    earn_infomin.startAnimation(r);
                    earn_minfomax.startAnimation(r);
                    earn_infomax.startAnimation(r);
                    earn_minfoarr.startAnimation(r);
                    earn_infoarr.startAnimation(r);
                    earn_infonext.startAnimation(r);

                    PriceDB db=new PriceDB(FDash_Earn.this);
                    db.open();
                    db.createEntry(crop,market,date,min,max,arr);
                    db.close();

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

                    earn_minfo.setText(getResources().getString(R.string.congo)+"\n"+getResources().getString(R.string.coupon)+" "+fref+"\n");
                    earn_minfo1.setText(getResources().getString(R.string.use)+"\n"+getResources().getString(R.string.value)+"\n"+getResources().getString(R.string.teamgo));

                }
            }
        });

        earn_backinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                earn_minfo.setVisibility(GONE);
                earn_minfo1.setVisibility(GONE);

                earn_linfo.setVisibility(GONE);
                earn_info.setBackground(getResources().getDrawable(R.drawable.my_spinner));
                earn_info.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);

                earn_minfomarket.setVisibility(View.VISIBLE);
                earn_infomarket.setVisibility(VISIBLE);
                earn_minfocrop.setVisibility(VISIBLE);
                earn_infocrop.setVisibility(VISIBLE);
                earn_minfodate.setVisibility(VISIBLE);
                earn_date.setVisibility(VISIBLE);
                earn_minfomin.setVisibility(VISIBLE);
                earn_infomin.setVisibility(VISIBLE);
                earn_minfomax.setVisibility(VISIBLE);
                earn_infomax.setVisibility(VISIBLE);
                earn_minfoarr.setVisibility(VISIBLE);
                earn_infoarr.setVisibility(VISIBLE);
                earn_infonext.setVisibility(VISIBLE);

                earn_date.setText(getResources().getString(R.string.select_date));
                earn_date.setTextColor(getResources().getColor(R.color.FAC_Basic));

                earn_infomarket.setAdapter(market);
                earn_infocrop.setAdapter(crop);

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
