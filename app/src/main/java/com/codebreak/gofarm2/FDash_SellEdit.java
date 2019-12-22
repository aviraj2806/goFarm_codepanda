package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class FDash_SellEdit extends AppCompatActivity {
    String id;
    RecyclerView farmerlist;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView selledit_back;
    Button selledit_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_fdash__sell_edit);

        id=getIntent().getStringExtra("id");

        selledit_back=findViewById(R.id.selledit_back);
        farmerlist=findViewById(R.id.selledit_list);
        selledit_new=findViewById(R.id.selledit_new);
        farmerlist.setHasFixedSize(true);

        selledit_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(FDash_SellEdit.this,com.codebreak.gofarm2.FDash_Sell.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

        selledit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FDash_SellEdit.this,com.codebreak.gofarm2.FarmerDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        layoutManager = new LinearLayoutManager(this);
        farmerlist.setLayoutManager(layoutManager);

        String res ="";
        ArrayList<Sell> farmer = new ArrayList<Sell>();

        SellDB db = new SellDB(this);
        db.open();
        int c = db.idCount(id);
        db.close();


        for (int i = 1; i <= c; i++) {
            String check = Integer.toString(i);
            db.open();
            res = db.getID(check,id);
            db.close();

            String[] split = res.split(", ");


            farmer.add(new Sell(split[0], split[1], split[2], split[3], split[4], split[5]));

        }

        myAdapter = new SellAdapter(this, farmer);
        farmerlist.setAdapter(myAdapter);


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
