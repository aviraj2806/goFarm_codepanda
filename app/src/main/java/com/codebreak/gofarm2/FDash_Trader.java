package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class FDash_Trader extends AppCompatActivity {

    RecyclerView farmerlist;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView hat3,hat4;
    TextView trader_back;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_fdash__trader);

        id = getIntent().getStringExtra("id");

        farmerlist = findViewById(R.id.traderlist);
        hat3 = findViewById(R.id.hat3);
        hat4 = findViewById(R.id.hat4);
        trader_back = findViewById(R.id.trader_back);

        trader_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FDash_Trader.this, com.codebreak.gofarm2.FarmerDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });

        farmerlist.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        farmerlist.setLayoutManager(layoutManager);

        hat3.setVisibility(View.GONE);
        hat4.setVisibility(View.GONE);

        String res = "";
        ArrayList<Sell> farmer = new ArrayList<Sell>();

        BuyDB db = new BuyDB(this);
        db.open();
        long c = db.getCount();
        db.close();

        if (c == 0) {
            hat3.setVisibility(View.VISIBLE);
            hat4.setVisibility(View.VISIBLE);
            hat4.setText(getResources().getString(R.string.trytime) + "\n" +getResources().getString(R.string.thankyou)+ "\n" + getResources().getString(R.string.teamgo));
        } else {

            for (int i = 1; i <= c; i++) {
                String check = Integer.toString(i);
                db.open();
                res = db.getData(check);
                db.close();

                String[] split = res.split(", ");


                farmer.add(new Sell(split[0], split[1], split[2], split[3], split[4], split[5]));

            }

            myAdapter = new SellAdapter(this, farmer);
            farmerlist.setAdapter(myAdapter);

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
