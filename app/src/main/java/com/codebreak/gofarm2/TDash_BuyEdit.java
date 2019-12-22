package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TDash_BuyEdit extends AppCompatActivity {

    String id;
    RecyclerView farmerlist;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView buyedit_back;
    Button buyedit_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tdash__buy_edit);

        farmerlist=findViewById(R.id.buyedit_list);
        buyedit_back=findViewById(R.id.buyedit_back);
        buyedit_new=findViewById(R.id.buyedit_new);

        id=getIntent().getStringExtra("id");

        farmerlist.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        farmerlist.setLayoutManager(layoutManager);

        String res ="";
        ArrayList<Sell> farmer = new ArrayList<Sell>();

        BuyDB db = new BuyDB(this);
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

        buyedit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TDash_BuyEdit.this,com.codebreak.gofarm2.TraderDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        buyedit_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TDash_BuyEdit.this,com.codebreak.gofarm2.TDash_Buy.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

    }
}
