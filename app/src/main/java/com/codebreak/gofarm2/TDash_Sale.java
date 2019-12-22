package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TDash_Sale extends AppCompatActivity {

    RecyclerView farmerlist;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView hat,hat2;
    TextView sale_back;
    ImageView slist_dp;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tdash__sale);

        hat = findViewById(R.id.hat);
        hat2 = findViewById(R.id.hat2);
        sale_back=findViewById(R.id.sale_back);

        sale_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TDash_Sale.this,com.codebreak.gofarm2.TraderDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });


        farmerlist = findViewById(R.id.farmerlist);
        farmerlist.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        farmerlist.setLayoutManager(layoutManager);

        hat.setVisibility(View.GONE);
        hat2.setVisibility(View.GONE);

        String res ="";
        ArrayList<Sell> farmer = new ArrayList<Sell>();

        SellDB db = new SellDB(this);
        db.open();
        long c = db.getCount();
        db.close();

        if (c == 0) {
            hat.setVisibility(View.VISIBLE);
            hat2.setVisibility(View.VISIBLE);
            hat2.setText("Please try again after some time."+"\n"+"Thank You for your co-operation,"+"\n"+"Team Go Farm.");
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
}
