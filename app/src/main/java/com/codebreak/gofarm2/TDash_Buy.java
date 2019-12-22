package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.Random;

public class TDash_Buy extends AppCompatActivity {

    TextView buy_back, buy_m, buy_m1, buy_mcrop, buy_mcity, buy_mq, buy_final;
    Spinner buy_crop, buy_city;
    ArrayAdapter crop, city;
    Button buy_submit;
    EditText buy_q;
    String id;
    Drawable error;
    Animation r, f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tdash__buy);

        id = getIntent().getStringExtra("id");

        buy_q = findViewById(R.id.buy_q);
        buy_back = findViewById(R.id.buy_back);
        buy_mcrop = findViewById(R.id.buy_mcrop);
        buy_mcity = findViewById(R.id.buy_mcity);
        buy_m1 = findViewById(R.id.buy_m1);
        buy_m = findViewById(R.id.buy_m);
        buy_mq = findViewById(R.id.buy_mq);
        buy_crop = findViewById(R.id.buy_crop);
        buy_city = findViewById(R.id.buy_city);
        buy_submit = findViewById(R.id.buy_submit);
        buy_final = findViewById(R.id.buy_final);
        error = getResources().getDrawable(R.drawable.error);
        error.setBounds(0, 0, error.getIntrinsicWidth(), error.getIntrinsicHeight());
        r = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
        f = AnimationUtils.loadAnimation(this, R.anim.fadein);

        buy_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TDash_Buy.this, com.codebreak.gofarm2.TraderDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });

        crop=ArrayAdapter.createFromResource(this,R.array.crop,R.layout.text_spinner);
        buy_crop.setAdapter(crop);
        buy_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(buy_crop.getSelectedItem().equals("Select Crop"))
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
        buy_city.setAdapter(city);
        buy_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(buy_city.getSelectedItem().equals("Select City"))
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

        buy_final.setVisibility(View.GONE);
        buy_m.setVisibility(View.GONE);
        buy_m1.setVisibility(View.GONE);

        buy_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = buy_city.getSelectedItem().toString();
                String crop = buy_crop.getSelectedItem().toString();
                String qu = buy_q.getText().toString().trim();
                int ctr = 0;
                if (city.equals("Select City")) {
                    buy_mcity.setError("", error);
                    ctr++;
                }

                if (crop.equals("Select Crop")) {
                    buy_mcrop.setError("", error);
                    ctr++;
                }

                if (qu.equals("")) {
                    buy_mq.setError("", error);
                    ctr++;
                }

                if (ctr == 0) {
                    buy_final.setVisibility(View.VISIBLE);
                    buy_m1.setVisibility(View.VISIBLE);
                    buy_m.setVisibility(View.VISIBLE);

                    buy_city.setVisibility(View.GONE);
                    buy_mcity.setVisibility(View.GONE);
                    buy_crop.setVisibility(View.GONE);
                    buy_mcrop.setVisibility(View.GONE);
                    buy_q.setVisibility(View.GONE);
                    buy_mq.setVisibility(View.GONE);
                    buy_submit.setVisibility(View.GONE);

                    buy_final.clearAnimation();
                    buy_m1.clearAnimation();
                    buy_m.clearAnimation();
                    buy_submit.clearAnimation();

                    buy_city.clearAnimation();
                    buy_mcity.clearAnimation();
                    buy_crop.clearAnimation();
                    buy_mcrop.clearAnimation();
                    buy_q.clearAnimation();
                    buy_mq.clearAnimation();

                    buy_final.startAnimation(f);
                    buy_m1.startAnimation(f);
                    buy_m.startAnimation(f);

                    buy_city.startAnimation(r);
                    buy_mcity.startAnimation(r);
                    buy_crop.startAnimation(r);
                    buy_mcrop.startAnimation(r);
                    buy_q.startAnimation(r);
                    buy_mq.startAnimation(r);
                    buy_submit.startAnimation(r);

                    Random rndm_method = new Random();
                    String set = "0123456789";
                    String set1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    int i = 0;
                    String fref = "";
                    final char[] ref = new char[10];
                    for (i = 0; i < 10; i++) {
                        if (i < 3) {
                            ref[i] = set1.charAt(rndm_method.nextInt(set1.length()));
                            fref = fref + ref[i];
                        } else {
                            ref[i] = set.charAt(rndm_method.nextInt(set.length()));
                            fref = fref + ref[i];
                        }

                    }

                    FarmerDB db1=new FarmerDB(TDash_Buy.this);
                    db1.open();
                    String phn= db1.getPhn(id);
                    String name=db1.getName(id);
                    db1.close();

                    DP db2=new DP(TDash_Buy.this);
                    db2.open();
                    String dp=db2.getPath(id);
                    db2.close();

                    if(dp=="")
                    {
                        dp="hello";
                    }

                    BuyDB db = new BuyDB(TDash_Buy.this);
                    db.open();
                    db.createEntry(crop, city, qu, id,name,phn,dp);
                    db.close();

                    buy_m.setText("Refference ID : " + fref);
                    buy_m1.setText("We will contact you as soon as any Farmer shows interest." + "\n" + "Thank You for your co-operation," + "\n" + "Team Go Farm.");
                }
            }
        });

    }
}
