package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoanSuccess extends AppCompatActivity {

    TextView loan_back,loan_final,loan_m,loan_m1;
    Calendar calendar;
    Date today;
    DateFormat dateFormat;
    String current;
    Animation a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_success);

        String id = getIntent().getStringExtra("id");

        loan_back=findViewById(R.id.loan_back);
        loan_final=findViewById(R.id.loan_final);
        loan_m=findViewById(R.id.loan_m);
        loan_m1=findViewById(R.id.loan_m1);
        a= AnimationUtils.loadAnimation(this,R.anim.fadein);

        loan_final.clearAnimation();
        loan_final.startAnimation(a);
        loan_m.clearAnimation();
        loan_m.startAnimation(a);
        loan_m1.clearAnimation();
        loan_m1.startAnimation(a);

        calendar=Calendar.getInstance();
        today=calendar.getTime();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        current=dateFormat.format(today);

        LoanDB db=new LoanDB(this);
        db.open();
        String ref=db.getRef(id,current);
        db.close();

        loan_m.setText(getResources().getString(R.string.qref)+" "+ref);
        loan_m1.setText(getResources().getString(R.string.qsoon)+
                getResources().getString(R.string.qfur)+"\n"+getResources().getString(R.string.happyfarming)+"\n"+getResources().getString(R.string.teamgo));

        loan_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoanSuccess.this,com.codebreak.gofarm2.FarmerDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

    }
}
