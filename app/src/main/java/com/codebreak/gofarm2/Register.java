package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Register extends AppCompatActivity {

    Spinner reg_user,reg_city;
    ArrayAdapter reg_user1,reg_city1;
    EditText reg_name,reg_phn,reg_uid,reg_id,reg_pass,reg_cpass;
    Button reg_submit;
    TextView reg_back;
    TextView reg_muser,reg_mname,reg_mphn,reg_muid,reg_mcity,reg_mid,reg_mpass,reg_mcpass;
    Drawable error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_register);

        reg_user=findViewById(R.id.reg_user);
        reg_city=findViewById(R.id.reg_city);
        reg_name=findViewById(R.id.reg_name);
        reg_phn=findViewById(R.id.reg_phn);
        reg_uid=findViewById(R.id.reg_uid);
        reg_id=findViewById(R.id.reg_id);
        reg_pass=findViewById(R.id.reg_pass);
        reg_cpass=findViewById(R.id.reg_cpass);
        reg_back=findViewById(R.id.reg_back);
        reg_submit=findViewById(R.id.reg_submit);
        reg_muser=findViewById(R.id.reg_muser);
        reg_mname=findViewById(R.id.reg_mname);
        reg_mphn=findViewById(R.id.reg_mphn);
        reg_mcity=findViewById(R.id.reg_mcity);
        reg_muid=findViewById(R.id.reg_muid);
        reg_mid=findViewById(R.id.reg_mid);
        reg_mpass=findViewById(R.id.reg_mpass);
        reg_mcpass=findViewById(R.id.reg_mcpass);
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());

        reg_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (reg_pass.getRight() - reg_pass.getCompoundDrawables()[2].getBounds().width())) {
                        // your action here
                        if(reg_pass.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            reg_pass.setInputType( InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            reg_pass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.pshow,0);
                        }else {
                            reg_pass.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                            reg_pass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.phide,0);
                        }
                        return true;
                    }
                }

                return false;
            }
        });

        reg_cpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (reg_cpass.getRight() - reg_cpass.getCompoundDrawables()[2].getBounds().width())) {
                        // your action here
                        if(reg_cpass.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            reg_cpass.setInputType( InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            reg_cpass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.pshow,0);
                        }else {
                            reg_cpass.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                            reg_cpass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.phide,0);
                        }
                        return true;
                    }
                }

                return false;
            }
        });


        reg_user1=ArrayAdapter.createFromResource(this,R.array.select,R.layout.text_spinner);
        reg_user.setAdapter(reg_user1);
        reg_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(reg_user.getSelectedItem().toString().equals("Select User") || reg_user.getSelectedItem().toString().equals("उपयोगकर्ता का चयन करें") ||
                reg_user.getSelectedItem().toString().equals("वापरकर्ता निवडा"))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.DD_Basic));
                }
                else
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.DD_Option));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        reg_city1=ArrayAdapter.createFromResource(this,R.array.city,R.layout.text_spinner);
        reg_city.setAdapter(reg_city1);
        reg_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(reg_city.getSelectedItem().toString().equals("Select City") || reg_city.getSelectedItem().toString().equals("शहर चुनें") ||
                reg_city.getSelectedItem().toString().equals("शहर निवडा"))
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.DD_Basic));
                }
                else
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.DD_Option));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        reg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,com.codebreak.gofarm2.HomePage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
            }
        });
    }

    @SuppressLint("NewApi")
    public void btnSubmit(View v)
    {
        int ctr=0;
        String user,name,phn,uid,city,id,pass,cpass;

        user=reg_user.getSelectedItem().toString();
        name=reg_name.getText().toString().trim();
        phn=reg_phn.getText().toString().trim();
        uid=reg_uid.getText().toString().trim();
        city=reg_city.getSelectedItem().toString();
        id=reg_id.getText().toString().trim();
        pass=reg_pass.getText().toString().trim();
        cpass=reg_cpass.getText().toString().trim();
        String dp="123";

        if(!pass.equals(cpass))
        {
            reg_cpass.setError("Password Mismatch",error);
            reg_cpass.setTextColor(getResources().getColor(R.color.error));
        }

        if(user.equals("Select User"))
        {
            ctr++;
            reg_muser.setError("",error);
        }

        if(name.equals(""))
        {
            ctr++;
            reg_mname.setError("",error);
        }

        if(phn.equals(""))
        {
            ctr++;
            reg_mphn.setError("",error);
        }

        if(uid.equals(""))
        {
            ctr++;
            reg_muid.setError("",error);
        }

        if(city.equals("Select City"))
        {
            ctr++;
            reg_mcity.setError("",error);
        }

        if(id.equals(""))
        {
            ctr++;
            reg_mid.setError("",error);
        }

        if(pass.equals(""))
        {
            ctr++;
            reg_mpass.setError("",error);
        }

        if(cpass.equals(""))
        {
            ctr++;
            reg_mcpass.setError("",error);
        }

        if(ctr>0)
        {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout));
            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
            toast_text.setText("Please enter all fields");

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }

        if(ctr==0 && cpass.equals(pass))
        {
            int check_id,check_phn,check_uid;
            FarmerDB db=new FarmerDB(this);
            db.open();
            check_id=db.checkIdReg(id);
            check_phn=db.checkPhnReg(phn);
            check_uid=db.checkUidReg(uid);
            db.close();

            if(check_id==1)
            {
                reg_id.setError("Username already taken",error);
                reg_id.requestFocus();
                reg_id.setTextColor(getResources().getColor(R.color.error));
            }
            if(check_uid==1)
            {
                reg_uid.setError("UID already registered",error);
                reg_uid.requestFocus();
                reg_uid.setTextColor(getResources().getColor(R.color.error));
            }
            if(check_phn==1)
            {
                reg_phn.setError("Contact already registered",error);
                reg_phn.requestFocus();
                reg_phn.setTextColor(getResources().getColor(R.color.error));
            }
            if(check_id==0 && check_phn==0 && check_uid==0) {

                db.open();
                db.createEntry(user,name,phn,uid,city,id,pass,dp);
                db.close();

                SmsManager sm = SmsManager.getDefault();
                sm.sendTextMessage(phn, null, "Thank You for registering with Go Farm !!!" +
                        "\n" + "We are glad to serve you." + "\n\n" +
                        "UserID : " + id + "\n"
                        + "Password : " + pass + "\n\n" + "Please note your login details.", null, null);


                Intent intent = new Intent(Register.this, com.codebreak.gofarm2.HomePage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout));
                TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
                toast_text.setText(getResources().getString(R.string.reg_toast));

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
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
