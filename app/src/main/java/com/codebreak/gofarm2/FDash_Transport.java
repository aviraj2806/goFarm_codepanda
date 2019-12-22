package com.codebreak.gofarm2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocomplete;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FDash_Transport extends AppCompatActivity  {

    TextView trans_mfrom,trans_mto,trans_mcon,trans_mload;
    TextView trans_from,trans_to;
    EditText trans_load,trans_con;
    Button trans_submit;
    String id;
    Drawable error;
    TextView trans_back;
    int flag=0;
    double tolat;
    double tolong;
    double fromlat;
    double fromlong;
    Animation ro,fi,fo,di;

    TextView n1,n2,n3,n4,n5;
    TextView c1,c2,c3,c4,c5;
    Button trans_new;

    Button re1,re2,re3,re4,re5,call1,call2,call3,call4,call5;
    LinearLayout dis1,dis2,dis3,dis4,dis5;

    ProgressDialog load;

    String fref="";

    List<Address> addresses;
    private static final String TAG = "MapActivity";
    final int AUTOCOMPLETE_REQUEST = 2;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLaocale();
        setContentView(R.layout.activity_fdash__transport);

        load=ProgressDialog.show(FDash_Transport.this,"", getResources().getString(R.string.cloc),true);
        trans_mfrom=findViewById(R.id.trans_mfrom);
        trans_mto=findViewById(R.id.trans_mto);
        trans_mcon=findViewById(R.id.trans_mcon);
        trans_mload=findViewById(R.id.trans_mload);
        trans_from=findViewById(R.id.trans_from);
        trans_to=findViewById(R.id.trans_to);
        trans_load=findViewById(R.id.trans_load);
        trans_con=findViewById(R.id.trans_con);
        trans_submit=findViewById(R.id.trans_submit);
        id=getIntent().getStringExtra("id");
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        trans_back=findViewById(R.id.trans_back);
        re1=findViewById(R.id.re1);
        dis1=findViewById(R.id.dis1);
        call1=findViewById(R.id.call1);
        re2=findViewById(R.id.re2);
        dis2=findViewById(R.id.dis2);
        call2=findViewById(R.id.call2);
        re3=findViewById(R.id.re3);
        dis3=findViewById(R.id.dis3);
        call3=findViewById(R.id.call3);
        re4=findViewById(R.id.re4);
        dis4=findViewById(R.id.dis4);
        call4=findViewById(R.id.call4);
        re5=findViewById(R.id.re5);
        dis5=findViewById(R.id.dis5);
        call5=findViewById(R.id.call5);
        ro= AnimationUtils.loadAnimation(this,R.anim.push_right_out);
        fi=AnimationUtils.loadAnimation(this,R.anim.fadein);
        n1=findViewById(R.id.n1);
        n2=findViewById(R.id.n2);
        n3=findViewById(R.id.n3);
        n4=findViewById(R.id.n4);
        n5=findViewById(R.id.n5);
        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        c4=findViewById(R.id.c4);
        c5=findViewById(R.id.c5);
        trans_new=findViewById(R.id.trans_new);
        fo=AnimationUtils.loadAnimation(this,R.anim.fadeout);
        di=AnimationUtils.loadAnimation(this,R.anim.push_down_out);

        dis1.setVisibility(View.GONE);
        dis2.setVisibility(View.GONE);
        dis3.setVisibility(View.GONE);
        dis4.setVisibility(View.GONE);
        dis5.setVisibility(View.GONE);
        trans_new.setVisibility(View.GONE);

        trans_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FDash_Transport.this,com.codebreak.gofarm2.FarmerDash.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        trans_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String from1,to1,load1,con1;
                from1=trans_from.getText().toString().trim();
                to1=trans_to.getText().toString().trim();
                load1=trans_load.getText().toString().trim();
                con1=trans_con.getText().toString().trim();

                int ctr=0;

                if(to1.equals(""))
                {
                    trans_mto.setError("",error);
                    ctr++;
                }

                if(from1.equals(""))
                {
                    trans_mfrom.setError("",error);
                    ctr++;
                }

                if(con1.equals(""))
                {
                    trans_mcon.setError("",error);
                    ctr++;
                }

                if(load1.equals(""))
                {
                    trans_mload.setError("",error);
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
                    final char[] ref=new char[10];
                    for (i = 0; i < 10; i++) {
                        if(i<3) {
                            ref[i] = set1.charAt(rndm_method.nextInt(set1.length()));
                            fref = fref + ref[i];
                        }
                        else
                        {
                            ref[i] = set.charAt(rndm_method.nextInt(set.length()));
                            fref = fref + ref[i];
                        }
                    }

                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage("9420484497", null, "Transport Application :" +
                            "\n" + "From : " +fromlat+"(lat),"+fromlong+"(long)"+ "\n" +"To : " +tolat+"(lat),"+tolong+"(long)"+"\n\n"+
                            "UserID : " + id + "\n"
                            + "Load : " + load1 + "\n" + "Content : "+con1, null, null);

                    trans_mfrom.setVisibility(View.GONE);
                    trans_from.setVisibility(View.GONE);
                    trans_mto.setVisibility(View.GONE);
                    trans_to.setVisibility(View.GONE);
                    trans_mcon.setVisibility(View.GONE);
                    trans_con.setVisibility(View.GONE);
                    trans_mload.setVisibility(View.GONE);
                    trans_load.setVisibility(View.GONE);
                    trans_submit.setVisibility(View.GONE);

                    trans_mfrom.startAnimation(ro);
                    trans_from.startAnimation(ro);
                    trans_mto.startAnimation(ro);
                    trans_to.startAnimation(ro);
                    trans_mcon.startAnimation(ro);
                    trans_con.startAnimation(ro);
                    trans_mload.startAnimation(ro);
                    trans_load.startAnimation(ro);
                    trans_submit.startAnimation(ro);

                    dis1.setVisibility(View.VISIBLE);
                    dis2.setVisibility(View.VISIBLE);
                    dis3.setVisibility(View.VISIBLE);
                    dis4.setVisibility(View.VISIBLE);
                    dis5.setVisibility(View.VISIBLE);
                   // trans_new.setVisibility(View.VISIBLE);

                    dis1.startAnimation(fi);
                    dis2.startAnimation(fi);
                    dis3.startAnimation(fi);
                    dis4.startAnimation(fi);
                    dis5.startAnimation(fi);
                   // trans_new.startAnimation(fi);

                    call1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919420484497"));
                            startActivity(intent);
                        }
                    });

                    call2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919764955130"));
                            startActivity(intent);
                            Log.d("trial",call2.getText().toString().trim());
                        }
                    });

                    call3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919422257146"));
                            startActivity(intent);
                        }
                    });

                    call4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+917588703202"));
                            startActivity(intent);
                        }
                    });

                    call5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+918975588068"));
                            startActivity(intent);
                        }
                    });

                    re1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            SmsManager sm = SmsManager.getDefault();
                            sm.sendTextMessage("9420484497", null, "Transport Application :" +
                                    "\n" +
                                    "UserID : " + id + "\n"
                                    + "Load : " + load1 + "\n" + "Content : "+con1+"\n"+"\n\n"+"Transport : "+n1.getText().toString().trim()+"\n"
                                    +c1.getText().toString().trim(), null, null);

                            dis1.setVisibility(View.GONE);
                            dis1.startAnimation(fo);


                            DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Intent intent=new Intent(FDash_Transport.this,com.codebreak.gofarm2.FarmerDash.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                    }
                                }

                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(FDash_Transport.this);
                            builder.setMessage(getResources().getString(R.string.subreq)+"\n"+getResources().getString(R.string.qfur)
                                    +"\n"+getResources().getString(R.string.happyfarming)+"\n"+getResources().getString(R.string.teamgo))
                                    .setPositiveButton(getResources().getString(R.string.cont),dialog)
                                    .setNegativeButton(getResources().getString(R.string.exit),dialog)
                                    .show();



                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast2, (ViewGroup) findViewById(R.id.toast_layout2));
                            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text2);
                            toast_text.setText(getResources().getString(R.string.reqsuc));

                            Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }
                    });

                    re2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager sm = SmsManager.getDefault();
                            sm.sendTextMessage("9420484497", null, "Transport Application :" +
                                    "\n" +
                                    "UserID : " + id + "\n"
                                    + "Load : " + load1 + "\n" + "Content : "+con1+"\n"+"\n\n"+"Transport : "+n2.getText().toString().trim()+"\n"
                                    +c2.getText().toString().trim(), null, null);

                            dis2.setVisibility(View.GONE);
                            dis2.startAnimation(fo);

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast2, (ViewGroup) findViewById(R.id.toast_layout2));
                            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text2);
                            toast_text.setText(getResources().getString(R.string.reqsuc));

                            Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                            DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Intent intent=new Intent(FDash_Transport.this,com.codebreak.gofarm2.FarmerDash.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                    }
                                }

                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(FDash_Transport.this);
                            builder.setMessage(getResources().getString(R.string.subreq)+"\n"+getResources().getString(R.string.qfur)
                                    +"\n"+getResources().getString(R.string.happyfarming)+"\n"+getResources().getString(R.string.teamgo))
                                    .setPositiveButton(getResources().getString(R.string.cont),dialog)
                                    .setNegativeButton(getResources().getString(R.string.exit),dialog)
                                    .show();
                        }
                    });

                    re3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager sm = SmsManager.getDefault();
                            sm.sendTextMessage("9420484497", null, "Transport Application :" +
                                    "\n" +
                                    "UserID : " + id + "\n"
                                    + "Load : " + load1 + "\n" + "Content : "+con1+"\n"+"\n\n"+"Transport : "+n3.getText().toString().trim()+"\n"
                                    +c3.getText().toString().trim(), null, null);

                            dis3.setVisibility(View.GONE);
                            dis3.startAnimation(fo);

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast2, (ViewGroup) findViewById(R.id.toast_layout2));
                            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text2);
                            toast_text.setText(getResources().getString(R.string.reqsuc));

                            Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                            DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Intent intent=new Intent(FDash_Transport.this,com.codebreak.gofarm2.FarmerDash.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                    }
                                }

                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(FDash_Transport.this);
                            builder.setMessage(getResources().getString(R.string.subreq)+"\n"+getResources().getString(R.string.qfur)
                                    +"\n"+getResources().getString(R.string.happyfarming)+"\n"+getResources().getString(R.string.teamgo))
                                    .setPositiveButton(getResources().getString(R.string.cont),dialog)
                                    .setNegativeButton(getResources().getString(R.string.exit),dialog)
                                    .show();
                        }
                    });

                    re4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager sm = SmsManager.getDefault();
                            sm.sendTextMessage("9420484497", null, "Transport Application :" +
                                    "\n" +
                                    "UserID : " + id + "\n"
                                    + "Load : " + load1 + "\n" + "Content : "+con1+"\n"+"\n\n"+"Transport : "+n4.getText().toString().trim()+"\n"
                                    +c4.getText().toString().trim(), null, null);
                            dis4.setVisibility(View.GONE);
                            dis4.startAnimation(fo);

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast2, (ViewGroup) findViewById(R.id.toast_layout2));
                            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text2);
                            toast_text.setText(getResources().getString(R.string.reqsuc));

                            Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                            DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Intent intent=new Intent(FDash_Transport.this,com.codebreak.gofarm2.FarmerDash.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                    }
                                }

                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(FDash_Transport.this);
                            builder.setMessage(getResources().getString(R.string.subreq)+"\n"+getResources().getString(R.string.qfur)
                                    +"\n"+getResources().getString(R.string.happyfarming)+"\n"+getResources().getString(R.string.teamgo))
                                    .setPositiveButton(getResources().getString(R.string.cont),dialog)
                                    .setNegativeButton(getResources().getString(R.string.exit),dialog)
                                    .show();
                        }
                    });

                    re5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager sm = SmsManager.getDefault();
                            sm.sendTextMessage("9420484497", null, "Transport Application :" +
                                    "\n" +
                                    "UserID : " + id + "\n"
                                    + "Load : " + load1 + "\n" + "Content : "+con1+"\n"+"\n\n"+"Transport : "+n5.getText().toString().trim()+"\n"
                                    +c5.getText().toString().trim(), null, null);
                            dis5.setVisibility(View.GONE);
                            dis5.startAnimation(fo);

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast2, (ViewGroup) findViewById(R.id.toast_layout2));
                            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text2);
                            toast_text.setText(getResources().getString(R.string.reqsuc));

                            Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                            DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Intent intent=new Intent(FDash_Transport.this,com.codebreak.gofarm2.FarmerDash.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                    }
                                }

                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(FDash_Transport.this);
                            builder.setMessage(getResources().getString(R.string.subreq)+"\n"+getResources().getString(R.string.qfur)
                                    +"\n"+getResources().getString(R.string.happyfarming)+"\n"+getResources().getString(R.string.teamgo))
                                    .setPositiveButton(getResources().getString(R.string.cont),dialog)
                                    .setNegativeButton(getResources().getString(R.string.exit),dialog)
                                    .show();

                        }
                    });

                   /* trans_new.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            trans_mfrom.setVisibility(View.VISIBLE);
                            trans_from.setVisibility(View.VISIBLE);
                            trans_mto.setVisibility(View.VISIBLE);
                            trans_to.setVisibility(View.VISIBLE);
                            trans_mcon.setVisibility(View.VISIBLE);
                            trans_con.setVisibility(View.VISIBLE);
                            trans_mload.setVisibility(View.VISIBLE);
                            trans_load.setVisibility(View.VISIBLE);
                            trans_submit.setVisibility(View.VISIBLE);

                            trans_mfrom.startAnimation(fi);
                            trans_from.startAnimation(fi);
                            trans_mto.startAnimation(fi);
                            trans_to.startAnimation(fi);
                            trans_mcon.startAnimation(fi);
                            trans_con.startAnimation(fi);
                            trans_mload.startAnimation(fi);
                            trans_load.startAnimation(fi);
                            trans_submit.startAnimation(fi);

                            dis1.setVisibility(View.GONE);
                            dis2.setVisibility(View.GONE);
                            dis3.setVisibility(View.GONE);
                            dis4.setVisibility(View.GONE);
                            dis5.setVisibility(View.GONE);
                            trans_new.setVisibility(View.GONE);

                            dis1.startAnimation(di);
                            dis2.startAnimation(di);
                            dis3.startAnimation(di);
                            dis4.startAnimation(di);
                            dis5.startAnimation(di);
                            trans_new.startAnimation(di);
                        }
                    }); */
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                load.dismiss();
                getLocationPermission();
            }
        },2000);

        trans_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                showAutocomplete();
            }
        });

        trans_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=2;
                showAutocomplete();
            }
        });
    }

    private void showAutocomplete() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Place place;
        String source;
        if (resultCode == RESULT_OK) {
            if (requestCode == AUTOCOMPLETE_REQUEST) {
                place = PlaceAutocomplete.getPlace(this, data);
                source = getString(R.string.autocomplete);

                if(flag==1) {

                    trans_from.setText(place.getName() + ", " + place.getAddress());
                    flag=0;
                    fromlat=place.getLatLng().latitude;
                    fromlong=place.getLatLng().longitude;
                }

                if(flag==2) {

                    trans_to.setText(place.getName() + ", " + place.getAddress());
                    flag=0;
                    tolat=place.getLatLng().latitude;
                    tolong=place.getLatLng().longitude;
                }

            } else {
                return;
            }
        }
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                getDeviceLocation();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            Log.d("trial", String.valueOf(currentLocation));

                            Geocoder geo = new Geocoder(FDash_Transport.this, Locale.getDefault());
                            try {
                                addresses = geo.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                                Log.d("trial", String.valueOf(addresses.get(0)));
                                trans_from.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                                fromlat=currentLocation.getLatitude();
                                fromlong=currentLocation.getLongitude();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(FDash_Transport.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
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
