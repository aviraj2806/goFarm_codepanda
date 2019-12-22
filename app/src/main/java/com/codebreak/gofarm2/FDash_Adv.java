package com.codebreak.gofarm2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class FDash_Adv extends AppCompatActivity {

    String id;
    LinearLayout adv_lfer,adv_lirr,adv_lpro,adv_lpest,adv_lother;
    EditText adv_qfer,adv_qirr,adv_qpro,adv_qpest,adv_qother;
    Button adv_nextfer,adv_nextirr,adv_nextpro,adv_nextpest,adv_nextother,adv_fer,adv_irr,adv_pro,adv_pest,adv_other;
    ImageView adv_backfer,adv_backirr,adv_backpro,adv_backpest,adv_backother;
    TextView adv_back,adv_attachpest;
    Drawable error;
    TextView adv_mfer,adv_mfer1,adv_mirr,adv_mirr1,adv_mpro,adv_mpro1,adv_mpest,adv_mpest1,adv_mother,adv_mother1;
    private static final int REQUEST_CAMERA=100;
    String path;
    ImageView adv_attach;
    Animation a,b,c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_fdash__adv);


        id=getIntent().getStringExtra("id");

        adv_back=findViewById(R.id.adv_back);
        adv_lother=findViewById(R.id.adv_lother);
        adv_lpest=findViewById(R.id.adv_lpest);
        adv_lpro=findViewById(R.id.adv_lpro);
        adv_lirr=findViewById(R.id.adv_lirr);
        adv_lfer=findViewById(R.id.adv_lfer);
        adv_other=findViewById(R.id.adv_other);
        adv_pest=findViewById(R.id.adv_pest);
        adv_pro=findViewById(R.id.adv_pro);
        adv_irr=findViewById(R.id.adv_irr);
        adv_fer=findViewById(R.id.adv_fer);
        adv_qother=findViewById(R.id.adv_qother);
        adv_qpest=findViewById(R.id.adv_qpest);
        adv_qpro=findViewById(R.id.adv_qpro);
        adv_qirr=findViewById(R.id.adv_qirr);
        adv_qfer=findViewById(R.id.adv_qfer);
        adv_nextother=findViewById(R.id.adv_nextother);
        adv_nextpest=findViewById(R.id.adv_nextpest);
        adv_nextpro=findViewById(R.id.adv_nextpro);
        adv_nextirr=findViewById(R.id.adv_nextirr);
        adv_nextfer=findViewById(R.id.adv_nextfer);
        adv_backother=findViewById(R.id.adv_backother);
        adv_backpest=findViewById(R.id.adv_backpest);
        adv_backpro=findViewById(R.id.adv_backpro);
        adv_backirr=findViewById(R.id.adv_backirr);
        adv_backfer=findViewById(R.id.adv_backfer);
        adv_attachpest=findViewById(R.id.adv_attachpest);
        adv_mfer=findViewById(R.id.adv_mfer);
        adv_mfer1=findViewById(R.id.adv_mfer1);
        adv_mirr1=findViewById(R.id.adv_mirr1);
        adv_mirr=findViewById(R.id.adv_mirr);
        adv_mpro1=findViewById(R.id.adv_mpro1);
        adv_mpro=findViewById(R.id.adv_mpro);
        adv_mpest1=findViewById(R.id.adv_mpest1);
        adv_mpest=findViewById(R.id.adv_mpest);
        adv_mother=findViewById(R.id.adv_mother);
        adv_mother1=findViewById(R.id.adv_mother1);
        adv_attach=findViewById(R.id.adv_attach);
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());

        adv_lfer.setVisibility(View.GONE);
        adv_lirr.setVisibility(View.GONE);
        adv_lpro.setVisibility(View.GONE);
        adv_lpest.setVisibility(View.GONE);
        adv_lother.setVisibility(View.GONE);
        adv_mfer.setVisibility(View.GONE);
        adv_mfer1.setVisibility(View.GONE);
        adv_mirr1.setVisibility(View.GONE);
        adv_mirr.setVisibility(View.GONE);
        adv_mpro1.setVisibility(View.GONE);
        adv_mpro.setVisibility(View.GONE);
        adv_mpest1.setVisibility(View.GONE);
        adv_mpest.setVisibility(View.GONE);
        adv_mother1.setVisibility(View.GONE);
        adv_mother.setVisibility(View.GONE);
        adv_attach.setVisibility(View.GONE);

        a= AnimationUtils.loadAnimation(this,R.anim.fadein);
        b=AnimationUtils.loadAnimation(this,R.anim.push_down_in);
        c=AnimationUtils.loadAnimation(this,R.anim.push_right_out);

        adv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FDash_Adv.this,com.codebreak.gofarm2.FarmerDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });
    }

    public void onFer(View v)
    {
        adv_lfer.setVisibility(View.VISIBLE);
        adv_fer.setCompoundDrawables(null,null,null,null);
        adv_fer.setBackground(getResources().getDrawable(R.drawable.upper));




        adv_nextfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ctr=0;
                String query=adv_qfer.getText().toString().trim();

                if(query.equals(""))
                {
                    ctr++;
                    adv_qfer.setError(getResources().getString(R.string.qempty),error);
                    adv_qfer.requestFocus();
                }


                if(ctr==0)
                {

                    Random rndm_method = new Random();
                    String set = "0123456789";
                    String set1="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    int i=0;
                    String fref="";
                    final char[] ref=new char[10];
                    for (i = 0; i < 10; i++) {
                        if(i<3) {
                            ref[i] = set1.charAt(rndm_method.nextInt(set1.length()));
                            fref = fref + ref[i];
                        }
                        else
                        {
                            ref[i] = set.charAt(rndm_method.nextInt(set.length()));
                            fref = fref + ref[i];
                        }
                    }
                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage("9420484497", null, "Farmer ID : "+id+"\n"+"Reference ID : "+fref+"\n"+"Query : "+query+"\n", null, null);


                    adv_mfer.setText(getResources().getString(R.string.qref)+" "+fref+"\n"+getResources().getString(R.string.qsuc)+"\n");
                    adv_mfer1.setText(getResources().getString(R.string.qcall)
                            +"\n"+getResources().getString(R.string.qfur)+"\n"+getResources().getString(R.string.thankyou)+"\n"+getResources().getString(R.string.happyfarming)
                            +"\n"+getResources().getString(R.string.teamgo));
                    adv_mfer.setVisibility(View.VISIBLE);
                    adv_mfer1.setVisibility(View.VISIBLE);
                    adv_qfer.setVisibility(View.GONE);
                    adv_nextfer.setVisibility(View.GONE);

                    adv_mfer.clearAnimation();
                    adv_mfer.startAnimation(a);
                    adv_mfer1.clearAnimation();
                    adv_mfer1.startAnimation(a);

                    adv_qfer.clearAnimation();
                    adv_qfer.startAnimation(c);
                    adv_nextfer.clearAnimation();
                    adv_nextfer.startAnimation(c);
                }
            }
        });

        adv_backfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adv_lfer.setVisibility(View.GONE);
                adv_mfer.setVisibility(View.GONE);
                adv_mfer1.setVisibility(View.GONE);
                adv_qfer.setText("");
                adv_qfer.setVisibility(View.VISIBLE);
                adv_nextfer.setVisibility(View.VISIBLE);
                adv_fer.setBackground(getResources().getDrawable(R.drawable.fac_button));
                adv_fer.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);
            }
        });
    }

    public void onIrr(View v)
    {
        adv_lirr.setVisibility(View.VISIBLE);
        adv_irr.setCompoundDrawables(null,null,null,null);
        adv_irr.setBackground(getResources().getDrawable(R.drawable.upper));



        adv_nextirr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ctr=0;
                String query=adv_qirr.getText().toString().trim();

                if(query.equals(""))
                {
                    ctr++;
                    adv_qirr.setError(getResources().getString(R.string.qempty),error);
                    adv_qirr.requestFocus();
                }


                if(ctr==0)
                {

                    Random rndm_method = new Random();
                    String set = "0123456789";
                    String set1="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    int i=0;
                    String fref="";
                    final char[] ref=new char[10];
                    for (i = 0; i < 10; i++) {
                        if(i<3) {
                            ref[i] = set1.charAt(rndm_method.nextInt(set1.length()));
                            fref = fref + ref[i];
                        }
                        else
                        {
                            ref[i] = set.charAt(rndm_method.nextInt(set.length()));
                            fref = fref + ref[i];
                        }
                    }
                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage("9420484497", null, "Farmer ID : "+id+"\n"+"Reference ID : "+fref+"\n"+"Query : "+query+"\n", null, null);


                    adv_mirr.setText(getResources().getString(R.string.qref)+" "+fref+"\n"+getResources().getString(R.string.qsuc)+"\n");
                    adv_mirr1.setText(getResources().getString(R.string.qcall)
                            +"\n"+getResources().getString(R.string.qfur)+"\n"+getResources().getString(R.string.thankyou)+"\n"+getResources().getString(R.string.happyfarming)
                            +"\n"+getResources().getString(R.string.teamgo));

                    adv_mirr.setVisibility(View.VISIBLE);
                    adv_mirr1.setVisibility(View.VISIBLE);
                    adv_qirr.setVisibility(View.GONE);
                    adv_nextirr.setVisibility(View.GONE);
                    adv_mirr.clearAnimation();
                    adv_mirr.startAnimation(a);
                    adv_mirr1.clearAnimation();
                    adv_mirr1.startAnimation(a);

                    adv_qirr.clearAnimation();
                    adv_qirr.startAnimation(c);
                    adv_nextirr.clearAnimation();
                    adv_nextirr.startAnimation(c);
                }
            }
        });

        adv_backirr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adv_lirr.setVisibility(View.GONE);
                adv_mirr.setVisibility(View.GONE);
                adv_mirr1.setVisibility(View.GONE);
                adv_qirr.setText("");
                adv_qirr.setVisibility(View.VISIBLE);
                adv_nextirr.setVisibility(View.VISIBLE);
                adv_irr.setBackground(getResources().getDrawable(R.drawable.fac_button));
                adv_irr.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);
            }
        });
    }

    public void onPro(View v)
    {
        adv_lpro.setVisibility(View.VISIBLE);
        adv_pro.setCompoundDrawables(null,null,null,null);
        adv_pro.setBackground(getResources().getDrawable(R.drawable.upper));


        adv_nextpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ctr=0;
                String query=adv_qpro.getText().toString().trim();

                if(query.equals(""))
                {
                    ctr++;
                    adv_qpro.setError(getResources().getString(R.string.qempty),error);
                    adv_qpro.requestFocus();
                }


                if(ctr==0)
                {

                    Random rndm_method = new Random();
                    String set = "0123456789";
                    String set1="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    int i=0;
                    String fref="";
                    final char[] ref=new char[10];
                    for (i = 0; i < 10; i++) {
                        if(i<3) {
                            ref[i] = set1.charAt(rndm_method.nextInt(set1.length()));
                            fref = fref + ref[i];
                        }
                        else
                        {
                            ref[i] = set.charAt(rndm_method.nextInt(set.length()));
                            fref = fref + ref[i];
                        }
                    }
                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage("9420484497", null, "Farmer ID : "+id+"\n"+"Reference ID : "+fref+"\n"+"Query : "+query+"\n", null, null);


                    adv_mpro.setText(getResources().getString(R.string.qref)+" "+fref+"\n"+getResources().getString(R.string.qsuc)+"\n");
                    adv_mpro1.setText(getResources().getString(R.string.qcall)
                            +"\n"+getResources().getString(R.string.qfur)+"\n"+getResources().getString(R.string.thankyou)+"\n"+getResources().getString(R.string.happyfarming)
                            +"\n"+getResources().getString(R.string.teamgo));

                   adv_mpro.setVisibility(View.VISIBLE);
                    adv_mpro1.setVisibility(View.VISIBLE);
                    adv_qpro.setVisibility(View.GONE);
                    adv_nextpro.setVisibility(View.GONE);

                    adv_mpro.clearAnimation();
                    adv_mpro.startAnimation(a);
                    adv_mpro1.clearAnimation();
                    adv_mpro1.startAnimation(a);

                    adv_qpro.clearAnimation();
                    adv_qpro.startAnimation(c);
                    adv_nextpro.clearAnimation();
                    adv_nextpro.startAnimation(c);
                }
            }
        });

        adv_backpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adv_lpro.setVisibility(View.GONE);
                adv_mpro.setVisibility(View.GONE);
                adv_mpro1.setVisibility(View.GONE);
                adv_qpro.setText("");
                adv_qpro.setVisibility(View.VISIBLE);
                adv_nextpro.setVisibility(View.VISIBLE);
                adv_pro.setBackground(getResources().getDrawable(R.drawable.fac_button));
                adv_pro.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);
            }
        });
    }

    public void onOther(View v)
    {
        adv_lother.setVisibility(View.VISIBLE);
        adv_other.setCompoundDrawables(null,null,null,null);
        adv_other.setBackground(getResources().getDrawable(R.drawable.upper));


        adv_nextother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ctr=0;
                String query=adv_qother.getText().toString().trim();

                if(query.equals(""))
                {
                    ctr++;
                    adv_qother.setError(getResources().getString(R.string.qempty),error);
                    adv_qother.requestFocus();
                }


                if(ctr==0)
                {
                    Random rndm_method = new Random();
                    String set = "0123456789";
                    String set1="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    int i=0;
                    String fref="";
                    final char[] ref=new char[10];
                    for (i = 0; i < 10; i++) {
                        if(i<3) {
                            ref[i] = set1.charAt(rndm_method.nextInt(set1.length()));
                            fref = fref + ref[i];
                        }
                        else
                        {
                            ref[i] = set.charAt(rndm_method.nextInt(set.length()));
                            fref = fref + ref[i];
                        }
                    }
                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage("9420484497", null, "Farmer ID : "+id+"\n"+"Reference ID : "+fref+"\n"+"Query : "+query+"\n", null, null);


                    adv_mother.setText(getResources().getString(R.string.qref)+" "+fref+"\n"+getResources().getString(R.string.qsuc)+"\n");
                    adv_mother1.setText(getResources().getString(R.string.qcall)
                            +"\n"+getResources().getString(R.string.qfur)+"\n"+getResources().getString(R.string.thankyou)+"\n"+getResources().getString(R.string.happyfarming)
                            +"\n"+getResources().getString(R.string.teamgo));

                    adv_mother.setVisibility(View.VISIBLE);
                    adv_mother1.setVisibility(View.VISIBLE);
                    adv_qother.setVisibility(View.GONE);
                    adv_nextother.setVisibility(View.GONE);

                    adv_mother.clearAnimation();
                    adv_mother.startAnimation(a);
                    adv_mother1.clearAnimation();
                    adv_mother1.startAnimation(a);

                    adv_qother.clearAnimation();
                    adv_qother.startAnimation(c);
                    adv_nextother.clearAnimation();
                    adv_nextother.startAnimation(c);
                }
            }
        });

        adv_backother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adv_lother.setVisibility(View.GONE);
                adv_mother.setVisibility(View.GONE);
                adv_mother1.setVisibility(View.GONE);
                adv_qother.setText("");
                adv_qother.setVisibility(View.VISIBLE);
                adv_nextother.setVisibility(View.VISIBLE);
                adv_other.setBackground(getResources().getDrawable(R.drawable.fac_button));
                adv_other.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);
            }
        });
    }

    public void onPest(View v) {

        adv_lpest.setVisibility(View.VISIBLE);
        adv_pest.setCompoundDrawables(null,null,null,null);
        adv_pest.setBackground(getResources().getDrawable(R.drawable.upper));


        adv_nextpest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ctr=0;
                String query=adv_qpest.getText().toString().trim();

                if(query.equals(""))
                {
                    ctr++;
                    adv_qpest.setError(getResources().getString(R.string.qempty),error);
                    adv_qpest.requestFocus();
                }


                if(ctr==0)
                {

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

                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage("9420484497", null, "Farmer ID : "+id+"\n"+"Reference ID : "+fref+"\n"+"Query : "+query+"\n", null, null);

                    adv_mpest.setText(getResources().getString(R.string.qref)+" "+fref+"\n"+getResources().getString(R.string.qsuc)+"\n");
                    adv_mpest1.setText(getResources().getString(R.string.qcall)
                            +"\n"+getResources().getString(R.string.qfur)+"\n"+getResources().getString(R.string.thankyou)+"\n"+getResources().getString(R.string.happyfarming)
                            +"\n"+getResources().getString(R.string.teamgo));


                    adv_mpest.setVisibility(View.VISIBLE);
                    adv_mpest1.setVisibility(View.VISIBLE);
                    adv_attachpest.setVisibility(View.GONE);
                    adv_qpest.setVisibility(View.GONE);
                    adv_attach.setVisibility(View.GONE);
                    adv_nextpest.setVisibility(View.GONE);

                    adv_mpest.clearAnimation();
                    adv_mpest.startAnimation(a);
                    adv_mpest1.clearAnimation();
                    adv_mpest1.startAnimation(a);

                    adv_qpest.clearAnimation();
                    adv_qpest.startAnimation(c);
                    adv_nextpest.clearAnimation();
                    adv_nextpest.startAnimation(c);
                    adv_attachpest.clearAnimation();
                    adv_attachpest.startAnimation(c);
                }
            }
        });

        adv_backpest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adv_lpest.setVisibility(View.GONE);
                adv_mpest.setVisibility(View.GONE);
                adv_mpest1.setVisibility(View.GONE);
                adv_qpest.setText("");
                adv_attach.setVisibility(View.GONE);
                adv_attachpest.setText(getResources().getString(R.string.qattach));
                adv_attachpest.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.attach,0,0,0);
                adv_qpest.setVisibility(View.VISIBLE);
                adv_attachpest.setVisibility(View.VISIBLE);
                adv_nextpest.setVisibility(View.VISIBLE);
                adv_pest.setBackground(getResources().getDrawable(R.drawable.fac_button));
                adv_pest.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);
            }
        });

    }

    public void onAttach(View v)
    {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);
        overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CAMERA && resultCode==RESULT_OK )
        {
            Bitmap photo=(Bitmap)data.getExtras().get("data");
            adv_attach.setImageBitmap(photo);
            adv_attach.setVisibility(View.VISIBLE);
            adv_attachpest.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check,0);
            adv_attachpest.setText(getResources().getString(R.string.qupload));
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
