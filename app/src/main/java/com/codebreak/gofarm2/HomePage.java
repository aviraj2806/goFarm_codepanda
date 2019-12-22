package com.codebreak.gofarm2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.Locale;
import java.util.Random;

public class HomePage extends AppCompatActivity {

    LinearLayout fpass,log;
    TextView log_forgot;
    Animation ro,li;
    Animation ri,lo;
    ImageView fp_back;
    TextView log_reg,log_sign;
    TextView fp_otpsubmit,fp_submit,fp_motp;
    EditText fp_id,fp_otp;
    final char [] otp=new char[6];
    String phn;
    String fotp="";
    Drawable error;
    TextView fp_suc;
    Animation fi;
    EditText log_id,log_pass;
    TextView hindi,english,marathi;

    ImageView fb,insta,wa,web;

    ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_home_page);

        ExpandableTextView expTv1 = (ExpandableTextView)findViewById(R.id.expand_text_view);
        expTv1.setText(getString(R.string.expand));

        fpass=findViewById(R.id.fpass);
        log=findViewById(R.id.log);
        log_forgot=findViewById(R.id.log_forgot);
        ro= AnimationUtils.loadAnimation(this,R.anim.push_right_out);
        li=AnimationUtils.loadAnimation(this,R.anim.push_left_in);
        ri=AnimationUtils.loadAnimation(this,R.anim.push_right_in);
        lo=AnimationUtils.loadAnimation(this,R.anim.push_left_out);
        log_reg=findViewById(R.id.log_reg);
        log_sign=findViewById(R.id.log_sign);
        fp_back=findViewById(R.id.fp_back);
        fp_id=findViewById(R.id.fp_id);
        fp_otp=findViewById(R.id.fp_otp);
        fp_otpsubmit=findViewById(R.id.fp_otpsubmit);
        fp_submit=findViewById(R.id.fp_submit);
        fp_motp=findViewById(R.id.fp_motp);
        fp_suc=findViewById(R.id.fp_suc);
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        fi=AnimationUtils.loadAnimation(this,R.anim.fadein);
        log_id=findViewById(R.id.log_id);
        log_pass=findViewById(R.id.log_pass);
        hindi=findViewById(R.id.hindi);
        english=findViewById(R.id.english);
        marathi=findViewById(R.id.marathi);
        fb=findViewById(R.id.fb);
        insta=findViewById(R.id.insta);
        web=findViewById(R.id.web);
        wa=findViewById(R.id.wa);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Go-Farm-103777321128193/"));
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/_gofarm?igshid=6kufxev18mav"));
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919764955130"));
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://gofarm.org.in/"));
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });


        log_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (log_pass.getRight() - log_pass.getCompoundDrawables()[2].getBounds().width())) {
                        // your action here
                        if(log_pass.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            log_pass.setInputType( InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            log_pass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.pshow,0);
                        }else {
                            log_pass.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                            log_pass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.phide,0);
                        }
                        return true;
                    }
                }

                return false;
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setLocale("en");

                load=ProgressDialog.show(HomePage.this,"", getResources().getString(R.string.load),true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        load.dismiss();
                        recreate();
                    }
                },1000);
            }
        });

        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setLocale("hi");

                load=ProgressDialog.show(HomePage.this,"", getResources().getString(R.string.load),true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        load.dismiss();
                        recreate();
                    }
                },1000);
            }
        });

        marathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("mr");

                load=ProgressDialog.show(HomePage.this,"", getResources().getString(R.string.load),true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        load.dismiss();
                        recreate();
                    }
                },1000);
            }
        });

        fp_otp.setVisibility(View.GONE);
        fp_otpsubmit.setVisibility(View.GONE);
        fp_motp.setVisibility(View.GONE);
        fp_suc.setVisibility(View.GONE);

        fpass.setVisibility(View.GONE);

        log_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpass.setVisibility(View.VISIBLE);
                fpass.startAnimation(li);
                log.setVisibility(View.GONE);
                log.startAnimation(ro);
            }
        });

        fp_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpass.setVisibility(View.GONE);
                fpass.startAnimation(lo);
                log.setVisibility(View.VISIBLE);
                log.startAnimation(ri);

                fp_otp.setVisibility(View.GONE);
                fp_otpsubmit.setVisibility(View.GONE);
                fp_motp.setVisibility(View.GONE);
                fp_suc.setVisibility(View.GONE);

                fpass.setVisibility(View.GONE);
                fp_id.setVisibility(View.VISIBLE);
                fp_submit.setVisibility(View.VISIBLE);
                fotp="";
                fp_id.setTextColor(getResources().getColor(R.color.DD_Option));
                fp_id.setText("");
                fp_id.setHint(getResources().getString(R.string.fpass_id));
                fp_otp.setTextColor(getResources().getColor(R.color.DD_Option));
                fp_otp.setText("");
                fp_otp.setHint(getResources().getString(R.string.fpass_otp));

                fp_id.setError(null);
                fp_otp.setError(null);
            }
        });

        log_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomePage.this,com.codebreak.gofarm2.Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        log_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomePage.this,com.codebreak.gofarm2.Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        log_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_id.setTextColor(getResources().getColor(R.color.DD_Option));
            }
        });

        log_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_pass.setTextColor(getResources().getColor(R.color.DD_Option));
            }
        });

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

    public void loadLaocale()
    {
        SharedPreferences preferences=getSharedPreferences("Settings",MODE_PRIVATE);
        String language=preferences.getString("My_Lang","");
        setLocale(language);
    }

    public void fpSubmit(View v) {

        String id = fp_id.getText().toString().trim();
        int i = 0;
        int check = 0;

        FarmerDB db = new FarmerDB(this);
        db.open();
        check = db.validUser(id);
        db.close();

        if (check == 1) {
            fp_otpsubmit.setVisibility(View.VISIBLE);
            fp_otp.setVisibility(View.VISIBLE);
            fp_motp.setVisibility(View.VISIBLE);
            fp_submit.setVisibility(View.GONE);

            fp_otp.startAnimation(fi);
            fp_motp.startAnimation(fi);

            db.open();
            phn=db.getPhn(id);
            db.close();

            Random rndm_method = new Random();
            String numbers = "0123456789";

            for (i = 0; i < 6; i++) {
                otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
                fotp = fotp + otp[i];
            }

            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(phn, null, "Your One Time Password(OTP) is : " +
                    fotp + "\n" + "Thanks," + "\n" + "Team Go Farm.", null, null);
        }
        else
        {
            fp_id.setError(getResources().getString(R.string.hp_validuser),error);
            fp_id.requestFocus();
            fp_id.setTextColor(getResources().getColor(R.color.error));
        }
    }

    public void fpOTPSubmit(View v)
    {
        String id=fp_id.getText().toString().trim();
        String pass;

        FarmerDB db=new FarmerDB(this);
        db.open();
        pass=db.getPass(id);
        db.close();

        if(fp_otp.getText().toString().equals(fotp))
        {
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(phn, null, "Your Password is : " +
                    pass+"\n"+"Keep your password safe."+"\n"+"Thanks,"+"\n"+"Team Go Farm.", null, null);

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout));
            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
            toast_text.setText(getResources().getString(R.string.fp_pass_sent));

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();

            fp_id.setVisibility(View.GONE);
            fp_otp.setVisibility(View.GONE);
            fp_otpsubmit.setVisibility(View.GONE);
            fp_motp.setVisibility(View.GONE);

            fp_id.startAnimation(ro);
            fp_otp.startAnimation(ro);
            fp_otpsubmit.startAnimation(ro);
            fp_motp.startAnimation(ro);

            fp_suc.setVisibility(View.VISIBLE);

            fp_suc.startAnimation(fi);

        }

        else
        {
            fp_otp.setError(getResources().getString(R.string.fpass_incotp),error);
            fp_otp.requestFocus();
            fp_otp.setTextColor(getResources().getColor(R.color.error));
        }

    }

    public void btnLogin(View v)
    {
        String valid,user;
        int user_exist;
        String id=log_id.getText().toString().trim();
        String pass=log_pass.getText().toString().trim();

        FarmerDB db=new FarmerDB(this);
        db.open();
        valid=db.getPass(id);
        user=db.getUserType(id);
        user_exist=db.validUser(id);
        db.close();

        if(user_exist==1 && !pass.equals(valid) )
        {
            log_pass.setError(getResources().getString(R.string.lofincpass),error);
            log_pass.requestFocus();
            log_pass.setTextColor(getResources().getColor(R.color.error));

        }

        if(user_exist==1 && pass.equals(valid) && (user.equals("Farmer") || user.equals("किसान") || user.equals("शेतकरी")))
        {
            Intent intent=new Intent(HomePage.this,com.codebreak.gofarm2.FarmerDash.class);
            intent.putExtra("id",id);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            HomePage.this.finish();
        }

        if(user_exist==1 && pass.equals(valid) && (user.equals("Trader") || user.equals("व्यापारी") || user.equals("व्यापारी")))
        {
            Intent intent=new Intent(HomePage.this,com.codebreak.gofarm2.TraderDash.class);
            intent.putExtra("id",id);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            HomePage.this.finish();
        }

        if(user_exist==0 && !id.equals(""))
        {
            log_id.setTextColor(getResources().getColor(R.color.error));
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout));
            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
            toast_text.setText(getResources().getString(R.string.log_invaliduser));

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }

        if(user_exist==0 && id.equals(""))
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
    }
}
