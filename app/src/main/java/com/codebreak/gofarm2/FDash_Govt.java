package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
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

import java.util.Locale;
import java.util.Random;

public class FDash_Govt extends AppCompatActivity {

    TextView fdash_govt;
    LinearLayout govt_lscheme,govt_lapply;
    Button govt_scheme,govt_apply,govt_snext,govt_anext;
    Spinner govt_scategory,govt_abank,govt_ascheme;
    EditText govt_aaccount;
    ArrayAdapter category,bank,account,scheme;
    TextView govt_mscategory,govt_mabank,govt_maaccount,govt_mascheme;
    ImageView govt_sback,govt_aback;
    Button govt_pmfby,govt_pmksy,govt_kcc,govt_shcs,govt_rgm;
    Drawable error;
    Animation r,l,f;
    TextView govt_am,govt_am1;
    int display=0;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_fdash__govt);

        id=getIntent().getStringExtra("id");

        fdash_govt=findViewById(R.id.fdash_govtback);
        govt_lscheme=findViewById(R.id.govt_lscheme);
        govt_lapply=findViewById(R.id.govt_lapply);
        govt_scheme=findViewById(R.id.govt_scheme);
        govt_apply=findViewById(R.id.govt_apply);
        govt_snext=findViewById(R.id.govt_snext);
        govt_anext=findViewById(R.id.govt_anext);
        govt_scategory=findViewById(R.id.govt_scategory);
        govt_abank=findViewById(R.id.govt_abank);
        govt_aaccount=findViewById(R.id.govt_aaccount);
        govt_ascheme=findViewById(R.id.govt_ascheme);
        govt_mscategory=findViewById(R.id.govt_mscategory);
        govt_mabank=findViewById(R.id.govt_mabank);
        govt_maaccount=findViewById(R.id.govt_maaccount);
        govt_mascheme=findViewById(R.id.govt_mascheme);
        govt_sback=findViewById(R.id.govt_sback);
        govt_aback=findViewById(R.id.govt_aback);
        govt_shcs=findViewById(R.id.govt_shcs);
        govt_pmksy=findViewById(R.id.govt_pmks);
        govt_pmfby=findViewById(R.id.govt_pmfby);
        govt_rgm=findViewById(R.id.govt_rgm);
        govt_kcc=findViewById(R.id.govt_kcc);
        govt_am=findViewById(R.id.govt_am);
        govt_am1=findViewById(R.id.govt_am1);
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        r= AnimationUtils.loadAnimation(this,R.anim.push_right_out);
        l=AnimationUtils.loadAnimation(this,R.anim.push_left_in);
        f=AnimationUtils.loadAnimation(this,R.anim.fadein);

        fdash_govt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FDash_Govt.this,com.codebreak.gofarm2.FarmerDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        govt_lapply.setVisibility(View.GONE);
        govt_lscheme.setVisibility(View.GONE);
        govt_pmksy.setVisibility(View.GONE);
        govt_pmfby.setVisibility(View.GONE);
        govt_kcc.setVisibility(View.GONE);
        govt_rgm.setVisibility(View.GONE);
        govt_shcs.setVisibility(View.GONE);
        govt_am.setVisibility(View.GONE);
        govt_am1.setVisibility(View.GONE);

    }

    public void onScheme(View v)
    {
        govt_lscheme.setVisibility(View.VISIBLE);
        govt_scheme.setCompoundDrawables(null,null,null,null);
        govt_scheme.setBackground(getResources().getDrawable(R.drawable.upper));

        category=ArrayAdapter.createFromResource(this,R.array.scheme_cat,R.layout.fac_text_spinner);
        govt_scategory.setAdapter(category);
        govt_scategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(govt_scategory.getSelectedItem().toString().equals("Select Category") || govt_scategory.getSelectedItem().toString().equals("श्रेणी का चयन करें") ||
                govt_scategory.getSelectedItem().toString().equals("श्रेणी निवडा"))
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

        govt_snext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cat = govt_scategory.getSelectedItem().toString();


                if (cat.equals("Select Category") || cat.equals("श्रेणी का चयन करें") || cat.equals("श्रेणी निवडा")) {
                    govt_mscategory.setError("", error);
                }

                if (cat.equals("Breeding") || cat.equals("ब्रीडिंग") || cat.equals("पैदास"))
                {
                    govt_rgm.setVisibility(View.VISIBLE);
                    govt_rgm.clearAnimation();
                    govt_rgm.startAnimation(l);

                    govt_snext.setVisibility(View.GONE);
                    govt_scategory.setVisibility(View.GONE);
                    govt_mscategory.setVisibility(View.GONE);

                    govt_snext.clearAnimation();
                    govt_scategory.clearAnimation();
                    govt_mscategory.clearAnimation();

                    govt_snext.startAnimation(r);
                    govt_scategory.startAnimation(r);
                    govt_mscategory.startAnimation(r);
                }

                if(cat.equals("Credit Card") || cat.equals("क्रेडीट कार्ड") || cat.equals("क्रेडिट कार्ड"))
                {
                    govt_kcc.setVisibility(View.VISIBLE);
                    govt_kcc.clearAnimation();
                    govt_kcc.startAnimation(l);

                    govt_snext.setVisibility(View.GONE);
                    govt_scategory.setVisibility(View.GONE);
                    govt_mscategory.setVisibility(View.GONE);

                    govt_snext.clearAnimation();
                    govt_scategory.clearAnimation();
                    govt_mscategory.clearAnimation();

                    govt_snext.startAnimation(r);
                    govt_scategory.startAnimation(r);
                    govt_mscategory.startAnimation(r);
                }

                if(cat.equals("Crop Insurance") || cat.equals("फसल बीमा") || cat.equals("पीक विमा"))
                {
                    govt_pmfby.setVisibility(View.VISIBLE);
                    govt_pmfby.clearAnimation();
                    govt_pmfby.startAnimation(l);

                    govt_snext.setVisibility(View.GONE);
                    govt_scategory.setVisibility(View.GONE);
                    govt_mscategory.setVisibility(View.GONE);

                    govt_snext.clearAnimation();
                    govt_scategory.clearAnimation();
                    govt_mscategory.clearAnimation();

                    govt_snext.startAnimation(r);
                    govt_scategory.startAnimation(r);
                    govt_mscategory.startAnimation(r);
                }

                if(cat.equals("Irrigation") || cat.equals("सिंचाई") || cat.equals("सिंचन"))
                {
                    govt_pmksy.setVisibility(View.VISIBLE);
                    govt_pmksy.clearAnimation();
                    govt_pmksy.startAnimation(l);

                    govt_snext.setVisibility(View.GONE);
                    govt_scategory.setVisibility(View.GONE);
                    govt_mscategory.setVisibility(View.GONE);

                    govt_snext.clearAnimation();
                    govt_scategory.clearAnimation();
                    govt_mscategory.clearAnimation();

                    govt_snext.startAnimation(r);
                    govt_scategory.startAnimation(r);
                    govt_mscategory.startAnimation(r);
                }

                if(cat.equals("Soil Health") || cat.equals("मृदा स्वास्थ्य") || cat.equals("माती आरोग्य"))
                {
                    govt_shcs.setVisibility(View.VISIBLE);
                    govt_shcs.clearAnimation();
                    govt_shcs.startAnimation(l);

                    govt_snext.setVisibility(View.GONE);
                    govt_scategory.setVisibility(View.GONE);
                    govt_mscategory.setVisibility(View.GONE);

                    govt_snext.clearAnimation();
                    govt_scategory.clearAnimation();
                    govt_mscategory.clearAnimation();

                    govt_snext.startAnimation(r);
                    govt_scategory.startAnimation(r);
                    govt_mscategory.startAnimation(r);


                }

                if(cat.equals("Show All") || cat.equals("सब") || cat.equals("सर्व"))
                {
                    govt_rgm.setVisibility(View.VISIBLE);
                    govt_rgm.clearAnimation();
                    govt_rgm.startAnimation(l);

                    govt_kcc.setVisibility(View.VISIBLE);
                    govt_kcc.clearAnimation();
                    govt_kcc.startAnimation(l);

                    govt_pmfby.setVisibility(View.VISIBLE);
                    govt_pmfby.clearAnimation();
                    govt_pmfby.startAnimation(l);

                    govt_pmksy.setVisibility(View.VISIBLE);
                    govt_pmksy.clearAnimation();
                    govt_pmksy.startAnimation(l);

                    govt_shcs.setVisibility(View.VISIBLE);
                    govt_shcs.clearAnimation();
                    govt_shcs.startAnimation(l);

                    govt_snext.setVisibility(View.GONE);
                    govt_scategory.setVisibility(View.GONE);
                    govt_mscategory.setVisibility(View.GONE);

                    govt_snext.clearAnimation();
                    govt_scategory.clearAnimation();
                    govt_mscategory.clearAnimation();

                    govt_snext.startAnimation(r);
                    govt_scategory.startAnimation(r);
                    govt_mscategory.startAnimation(r);

                }

                govt_pmfby.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        display=1;
                        Intent intent=new Intent(FDash_Govt.this,com.codebreak.gofarm2.DisplayScheme.class);
                        intent.putExtra("display",display);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                    }
                });

                govt_pmksy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        display=2;
                        Intent intent=new Intent(FDash_Govt.this,com.codebreak.gofarm2.DisplayScheme.class);
                        intent.putExtra("display",display);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                    }
                });

                govt_kcc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        display=3;
                        Intent intent=new Intent(FDash_Govt.this,com.codebreak.gofarm2.DisplayScheme.class);
                        intent.putExtra("display",display);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                    }
                });

                govt_rgm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        display=4;
                        Intent intent=new Intent(FDash_Govt.this,com.codebreak.gofarm2.DisplayScheme.class);
                        intent.putExtra("display",display);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                    }
                });

                govt_shcs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        display=5;
                        Intent intent=new Intent(FDash_Govt.this,com.codebreak.gofarm2.DisplayScheme.class);
                        intent.putExtra("display",display);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                    }
                });


            }
        });

        govt_sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                govt_lscheme.setVisibility(View.GONE);
                govt_scheme.setBackground(getResources().getDrawable(R.drawable.fac_button));
                govt_scheme.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);

                govt_pmksy.setVisibility(View.GONE);
                govt_pmfby.setVisibility(View.GONE);
                govt_kcc.setVisibility(View.GONE);
                govt_rgm.setVisibility(View.GONE);
                govt_shcs.setVisibility(View.GONE);

                govt_snext.setVisibility(View.VISIBLE);
                govt_scategory.setVisibility(View.VISIBLE);
                govt_mscategory.setVisibility(View.VISIBLE);


            }
        });
    }

    public void onApply(View view)
    {
        govt_lapply.setVisibility(View.VISIBLE);
        govt_apply.setBackground(getResources().getDrawable(R.drawable.upper));
        govt_apply.setCompoundDrawables(null,null,null,null);

        scheme=ArrayAdapter.createFromResource(FDash_Govt.this,R.array.schemes,R.layout.fac_text_spinner);
        govt_ascheme.setAdapter(scheme);
        govt_ascheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(govt_ascheme.getSelectedItem().toString().equals("Select Scheme") || govt_ascheme.getSelectedItem().toString().equals("योजना का चयन करें") ||
                govt_ascheme.getSelectedItem().toString().equals("योजना निवडा"))
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

        bank=ArrayAdapter.createFromResource(FDash_Govt.this,R.array.bank1,R.layout.fac_text_spinner);
        govt_abank.setAdapter(bank);
        govt_abank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(govt_abank.getSelectedItem().toString().equals("Select Bank") || govt_abank.getSelectedItem().toString().equals("बैंक का चयन करें") ||
                govt_abank.getSelectedItem().toString().equals("बँक निवडा"))
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

        govt_anext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sch=govt_ascheme.getSelectedItem().toString();
                String bnk=govt_abank.getSelectedItem().toString();
                String acc=govt_aaccount.getText().toString().trim();
                int ctr=0;

                if(sch.equals("Select Scheme") || sch.equals("योजना का चयन करें") || sch.equals("योजना निवडा"))
                {
                    govt_mascheme.setError("",error);
                    ctr++;
                }

                if(bnk.equals("Select Bank") || bnk.equals("बँक निवडा") || bnk.equals("बैंक का चयन करें"))
                {
                    govt_mabank.setError("",error);
                    ctr++;
                }

                if(acc.equals(""))
                {
                    govt_maaccount.setError("",error);
                    ctr++;
                }

                if(ctr==0)
                {
                    govt_mascheme.setVisibility(View.GONE);
                    govt_ascheme.setVisibility(View.GONE);
                    govt_mabank.setVisibility(View.GONE);
                    govt_abank.setVisibility(View.GONE);
                    govt_maaccount.setVisibility(View.GONE);
                    govt_aaccount.setVisibility(View.GONE);
                    govt_anext.setVisibility(View.GONE);

                    govt_am.setVisibility(View.VISIBLE);
                    govt_am1.setVisibility(View.VISIBLE);

                    govt_mascheme.clearAnimation();
                    govt_ascheme.clearAnimation();
                    govt_mabank.clearAnimation();
                    govt_abank.clearAnimation();
                    govt_maaccount.clearAnimation();
                    govt_aaccount.clearAnimation();
                    govt_anext.clearAnimation();

                    govt_mascheme.startAnimation(r);
                    govt_ascheme.startAnimation(r);
                    govt_mabank.startAnimation(r);
                    govt_abank.startAnimation(r);
                    govt_maaccount.startAnimation(r);
                    govt_aaccount.startAnimation(r);
                    govt_anext.startAnimation(r);

                    govt_am.startAnimation(f);
                    govt_am1.startAnimation(f);

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

                    String info;
                    FarmerDB db=new FarmerDB(FDash_Govt.this);
                    db.open();
                    info=db.getDash(id);
                    db.close();

                    govt_am.setText(getResources().getString(R.string.qref)+" "+fref);
                    govt_am1.setText(getResources().getString(R.string.govt_suc)+"\n"+getResources().getString(R.string.govt_call)+
                            "\n"+getResources().getString(R.string.thankyou)+"\n"+getResources().getString(R.string.teamgo));


                    String split[]=info.split(", ");
                    String name = split[0];
                    String user = split[1];
                    String city = split [3];

                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage("9420484497", null, "Application ID : "+"\n"+fref+"\n"+"Name : "+name+"\n"+
                            "User ID : "+user+"\n"+"City : "+city+"\n"+"Scheme : "+sch+"\n"
                            +"Bank : "+bnk+"\n"+"Account No. : "+acc+"\n", null, null);


                }
            }
        });

        govt_aback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                govt_am.setVisibility(View.GONE);
                govt_am1.setVisibility(View.GONE);

                govt_mascheme.setVisibility(View.VISIBLE);
                govt_ascheme.setVisibility(View.VISIBLE);
                govt_mabank.setVisibility(View.VISIBLE);
                govt_abank.setVisibility(View.VISIBLE);
                govt_maaccount.setVisibility(View.VISIBLE);
                govt_aaccount.setVisibility(View.VISIBLE);
                govt_anext.setVisibility(View.VISIBLE);

                govt_lapply.setVisibility(View.GONE);
                govt_apply.setBackground(getResources().getDrawable(R.drawable.fac_button));
                govt_apply.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.down);
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
