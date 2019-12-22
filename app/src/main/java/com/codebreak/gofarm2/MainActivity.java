package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView textView, load_text, tv;
    ProgressBar load;
    Animation b;
    private static int SPLASH_TIME_OUT = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        tv = findViewById(R.id.tv);
        load = findViewById(R.id.load);
        load_text = findViewById(R.id.load_text);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.appear);
        b = AnimationUtils.loadAnimation(this, R.anim.fadein);

        textView.clearAnimation();
        textView.startAnimation(a);
        tv.clearAnimation();
        tv.startAnimation(a);

        load.clearAnimation();
        load_text.clearAnimation();

        load.startAnimation(a);
        load_text.startAnimation(a);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


               Intent intent = new Intent(MainActivity.this, com.codebreak.gofarm2.HomePage.class);
                startActivity(intent);
                MainActivity.this.finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, SPLASH_TIME_OUT);
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
