package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class DisplayScheme extends AppCompatActivity {

    TextView ds_back,ds_head,ds_object,ds_link,ds_into;
    int display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_display_scheme);

        ds_back=findViewById(R.id.ds_back);
        ds_into=findViewById(R.id.ds_intro);
        ds_head=findViewById(R.id.ds_head);
        ds_object=findViewById(R.id.ds_object);
        ds_link=findViewById(R.id.ds_link);
        display=getIntent().getIntExtra("display",0);

        if(display==1)
        {
            ds_back.setText(getResources().getString(R.string.p_m_f_b_y));
            ds_into.setText(getResources().getString(R.string.pmfby));
            ds_head.setText(R.string.pmfby_head);
            ds_object.setText(R.string.pmfby_object);
            ds_link.setText(R.string.pmfby_link);
        }

        if(display==2)
        {
            ds_back.setText(getResources().getString(R.string.p_m_k_s_y));
            ds_into.setText(getResources().getString(R.string.pmsky));
            ds_head.setText(R.string.pmsky_head);
            ds_object.setText(R.string.pmksy_object);
            ds_link.setText(R.string.pmksy_link);
        }

        if(display==3)
        {
            ds_back.setText(getResources().getString(R.string.k_c_c));
            ds_into.setText(getResources().getString(R.string.kcc));
            ds_head.setText(R.string.kcc_head);
            ds_object.setText(R.string.kcc_object);
            ds_link.setText(R.string.kcc_link);
        }

        if(display==4)
        {
            ds_back.setText(getResources().getString(R.string.r_g_m));
            ds_into.setText(getResources().getString(R.string.rgm));
            ds_head.setText(R.string.rgm_head);
            ds_object.setText(R.string.rgm_object);
            ds_link.setText(R.string.rgm_link);
        }

        if(display==5)
        {
            ds_back.setText(getResources().getString(R.string.s_h_c_s));
            ds_into.setText(getResources().getString(R.string.shcs));
            ds_head.setText(R.string.shcs_head);
            ds_object.setText(R.string.shcs_object);
            ds_link.setText(R.string.shcs_link);
        }

        ds_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DisplayScheme.this,com.codebreak.gofarm2.FDash_Govt.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        ds_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(display==1)
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.pmfby_link)));
                    startActivity(intent);
                }

                if(display==5)
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.shcs_link)));
                    startActivity(intent);
                }

                if(display==4)
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.rgm_link)));
                    startActivity(intent);
                }

                if(display==3)
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.kcc_link)));
                    startActivity(intent);
                }

                if(display==2)
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.pmksy_link)));
                    startActivity(intent);
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
