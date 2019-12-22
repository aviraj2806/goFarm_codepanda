package com.codebreak.gofarm2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.view.View.GONE;

public class FDash_Loans extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String id;
    Spinner bank_sbank;
    ArrayAdapter sbank;
    Button bank_submit;
    TextView bank_dob,bank_back,bank_msbank,bank_mname,bank_mdob,bank_mpan,bank_mphn,bank_mincome,bank_mamount,bank_mperiod;
    EditText bank_name,bank_phn,bank_pan,bank_amount,bank_period,bank_income;
    Drawable error;
    Calendar calendar;
    TextView textView45,bank_m,bank_m1;
    ScrollView bank_scroll;
    Animation a,b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdash__loans);

        id=getIntent().getStringExtra("id");
        bank_income=findViewById(R.id.bank_income);
        bank_sbank=findViewById(R.id.bank_sbank);
        bank_submit=findViewById(R.id.bank_submit);
        bank_back=findViewById(R.id.bank_back);
        bank_msbank=findViewById(R.id.bank_msbank);
        bank_mname=findViewById(R.id.bank_mname);
        bank_mdob=findViewById(R.id.bank_mdob);
        bank_mpan=findViewById(R.id.bank_mpan);
        bank_mphn=findViewById(R.id.bank_mphn);
        bank_mincome=findViewById(R.id.bank_mincome);
        bank_mamount=findViewById(R.id.bank_mamount);
        bank_mperiod=findViewById(R.id.bank_mperiod);
        bank_name=findViewById(R.id.bank_name);
        bank_dob=findViewById(R.id.bank_dob);
        bank_phn=findViewById(R.id.bank_phn);
        bank_pan=findViewById(R.id.bank_pan);
        bank_amount=findViewById(R.id.bank_amount);
        bank_period=findViewById(R.id.bank_period);
        textView45=findViewById(R.id.textView45);
        bank_m=findViewById(R.id.bank_m);
        bank_m1=findViewById(R.id.bank_m1);
        bank_scroll=findViewById(R.id.bank_scroll);
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        a= AnimationUtils.loadAnimation(this,R.anim.push_down_out);
        b=AnimationUtils.loadAnimation(this,R.anim.fadein);

        bank_m.setVisibility(GONE);
        bank_m1.setVisibility(GONE);
        textView45.setVisibility(GONE);

        bank_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FDash_Loans.this,com.codebreak.gofarm2.FarmerDash.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        sbank=ArrayAdapter.createFromResource(this,R.array.bank,R.layout.text_spinner);
        bank_sbank.setAdapter(sbank);
        bank_sbank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(bank_sbank.getSelectedItem().toString().equals("Select Bank") || bank_sbank.getSelectedItem().toString().equals("बैंक का चयन करें") ||
                bank_sbank.getSelectedItem().toString().equals("बँक निवडा"))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.DD_Basic));
                }
                else
                {
                    ((TextView)view ).setTextColor(getResources().getColor(R.color.DD_Option));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bank_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment dp=new DatePickerFragment();
                dp.show(getSupportFragmentManager(),"date picker");
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);
        String curr= DateFormat.getDateInstance().format(calendar.getTime());
        bank_dob.setText(curr);
        bank_dob.setTextColor(getResources().getColor(R.color.DD_Option));
    }


    public void onLoanSubmit(View v)
    {
        String sbank=bank_sbank.getSelectedItem().toString();
        String name=bank_name.getText().toString().trim();
        String dob=bank_dob.getText().toString().trim();
        String phn=bank_phn.getText().toString().trim();
        String pan=bank_pan.getText().toString().trim();
        String income=bank_income.getText().toString().trim();
        String amount=bank_amount.getText().toString().trim();
        String period=bank_period.getText().toString().trim();

        int ctr=0;

        if(sbank.equals("Select Bank") || sbank.equals("बैंक का चयन करे") || sbank.equals("बँक निवडा"))
        {
            bank_msbank.setError("",error);
            ctr++;
        }
        if(name.equals(""))
        {
            bank_mname.setError("",error);
            ctr++;
        }
        if(dob.equals("Select D.O.B") || dob.equals("डी.ओ.बी. का चयन करें") || dob.equals("डी.ओ.बी. निवडा"))
        {
            bank_mdob.setError("",error);
            ctr++;
        }
        if(phn.equals(""))
        {
            bank_mphn.setError("",error);
            ctr++;
        }
        if(pan.equals(""))
        {
            bank_mpan.setError("",error);
            ctr++;
        }
        if(income.equals(""))
        {
            bank_mincome.setError("",error);
            ctr++;
        }
        if(amount.equals(""))
        {
            bank_mamount.setError("",error);
            ctr++;
        }
        if(period.equals(""))
        {
            bank_mperiod.setError("",error);
            ctr++;
        }

        if(ctr>0)
        {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout));
            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
            toast_text.setText(getResources().getString(R.string.allfields));

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
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
            java.util.Calendar calendar;
            Date today;
            DateFormat dateFormat;
            String current;

            calendar= java.util.Calendar.getInstance();
            today=calendar.getTime();
            dateFormat=new SimpleDateFormat("dd-MM-yyyy");
            current=dateFormat.format(today);

            LoanDB db=new LoanDB(this);
            db.open();
            db.onEntry(id,fref,current);
            db.close();

            bank_m.setVisibility(View.VISIBLE);
            bank_m1.setVisibility(View.VISIBLE);
            textView45.setVisibility(View.VISIBLE);
            bank_scroll.clearAnimation();
            bank_scroll.startAnimation(a);
            bank_m.clearAnimation();
            bank_m.startAnimation(b);
            bank_m1.clearAnimation();
            bank_m1.startAnimation(b);
            textView45.clearAnimation();
            textView45.startAnimation(b);
            bank_scroll.setVisibility(GONE);

            bank_m.setText(getResources().getString(R.string.qref)+" "+fref+"\n");
            bank_m1.setText(getResources().getString(R.string.qcall)+"\n"+getResources().getString(R.string.qfur)
                    +"\n\n"+getResources().getString(R.string.thankyou)+"\n"+getResources().getString(R.string.happyfarming)+"\n"+getResources().getString(R.string.teamgo));
        }
    }

}
