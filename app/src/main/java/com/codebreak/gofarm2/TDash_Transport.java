package com.codebreak.gofarm2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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

public class TDash_Transport extends AppCompatActivity  {

    TextView ttrans_mfrom,ttrans_mto,ttrans_mcon,ttrans_mload;
    TextView ttrans_from,ttrans_to;
    EditText ttrans_load,ttrans_con;
    Button ttrans_submit;
    String id;
    Drawable error;
    TextView ttrans_back;
    int flag=0;
    double tolat;
    double tolong;
    double fromlat;
    double fromlong;
    Animation ro,fi,fo,di;

    TextView tn1,tn2,tn3,tn4,tn5;
    TextView tc1,tc2,tc3,tc4,tc5;
    Button ttrans_new;

    Button tre1,tre2,tre3,tre4,tre5,tcall1,tcall2,tcall3,tcall4,tcall5;
    LinearLayout tdis1,tdis2,tdis3,tdis4,tdis5;

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
        setContentView(R.layout.activity_tdash__transport);

        ttrans_mfrom=findViewById(R.id.ttrans_mfrom);
        ttrans_mto=findViewById(R.id.ttrans_mto);
        ttrans_mcon=findViewById(R.id.ttrans_mcon);
        ttrans_mload=findViewById(R.id.ttrans_mload);
        ttrans_from=findViewById(R.id.ttrans_from);
        ttrans_to=findViewById(R.id.ttrans_to);
        ttrans_load=findViewById(R.id.ttrans_load);
        ttrans_con=findViewById(R.id.ttrans_con);
        ttrans_submit=findViewById(R.id.ttrans_submit);
        id=getIntent().getStringExtra("id");
        error=getResources().getDrawable(R.drawable.error);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        ttrans_back=findViewById(R.id.ttrans_back);
        tre1=findViewById(R.id.tre1);
        tdis1=findViewById(R.id.tdis1);
        tcall1=findViewById(R.id.tcall1);
        tre2=findViewById(R.id.tre2);
        tdis2=findViewById(R.id.tdis2);
        tcall2=findViewById(R.id.tcall2);
        tre3=findViewById(R.id.tre3);
        tdis3=findViewById(R.id.tdis3);
        tcall3=findViewById(R.id.tcall3);
        tre4=findViewById(R.id.tre4);
        tdis4=findViewById(R.id.tdis4);
        tcall4=findViewById(R.id.tcall4);
        tre5=findViewById(R.id.tre5);
        tdis5=findViewById(R.id.tdis5);
        tcall5=findViewById(R.id.tcall5);
        ro= AnimationUtils.loadAnimation(this,R.anim.push_right_out);
        fi=AnimationUtils.loadAnimation(this,R.anim.fadein);
        tn1=findViewById(R.id.tn1);
        tn2=findViewById(R.id.tn2);
        tn3=findViewById(R.id.tn3);
        tn4=findViewById(R.id.tn4);
        tn5=findViewById(R.id.tn5);
        tc1=findViewById(R.id.tc1);
        tc2=findViewById(R.id.tc2);
        tc3=findViewById(R.id.tc3);
        tc4=findViewById(R.id.tc4);
        tc5=findViewById(R.id.tc5);
        ttrans_new=findViewById(R.id.ttrans_new);
        fo=AnimationUtils.loadAnimation(this,R.anim.fadeout);
        di=AnimationUtils.loadAnimation(this,R.anim.push_down_out);

        tdis1.setVisibility(View.GONE);
        tdis2.setVisibility(View.GONE);
        tdis3.setVisibility(View.GONE);
        tdis4.setVisibility(View.GONE);
        tdis5.setVisibility(View.GONE);
        ttrans_new.setVisibility(View.GONE);

        ttrans_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TDash_Transport.this,com.codebreak.gofarm2.TraderDash.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        ttrans_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String from1,to1,load1,con1;
                from1=ttrans_from.getText().toString().trim();
                to1=ttrans_to.getText().toString().trim();
                load1=ttrans_load.getText().toString().trim();
                con1=ttrans_con.getText().toString().trim();

                int ctr=0;

                if(to1.equals(""))
                {
                    ttrans_mto.setError("",error);
                    ctr++;
                }

                if(from1.equals(""))
                {
                    ttrans_mfrom.setError("",error);
                    ctr++;
                }

                if(con1.equals(""))
                {
                    ttrans_mcon.setError("",error);
                    ctr++;
                }

                if(load1.equals(""))
                {
                    ttrans_mload.setError("",error);
                    ctr++;
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

                    ttrans_mfrom.setVisibility(View.GONE);
                    ttrans_from.setVisibility(View.GONE);
                    ttrans_mto.setVisibility(View.GONE);
                    ttrans_to.setVisibility(View.GONE);
                    ttrans_mcon.setVisibility(View.GONE);
                    ttrans_con.setVisibility(View.GONE);
                    ttrans_mload.setVisibility(View.GONE);
                    ttrans_load.setVisibility(View.GONE);
                    ttrans_submit.setVisibility(View.GONE);

                    ttrans_mfrom.startAnimation(ro);
                    ttrans_from.startAnimation(ro);
                    ttrans_mto.startAnimation(ro);
                    ttrans_to.startAnimation(ro);
                    ttrans_mcon.startAnimation(ro);
                    ttrans_con.startAnimation(ro);
                    ttrans_mload.startAnimation(ro);
                    ttrans_load.startAnimation(ro);
                    ttrans_submit.startAnimation(ro);

                    tdis1.setVisibility(View.VISIBLE);
                    tdis2.setVisibility(View.VISIBLE);
                    tdis3.setVisibility(View.VISIBLE);
                    tdis4.setVisibility(View.VISIBLE);
                    tdis5.setVisibility(View.VISIBLE);
                    // trans_new.setVisibility(View.VISIBLE);

                    tdis1.startAnimation(fi);
                    tdis2.startAnimation(fi);
                    tdis3.startAnimation(fi);
                    tdis4.startAnimation(fi);
                    tdis5.startAnimation(fi);
                    // ttrans_new.startAnimation(fi);

                    tcall1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919420484497"));
                            startActivity(intent);
                        }
                    });

                    tcall2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919764955130"));
                            startActivity(intent);
                            Log.d("trial",tcall2.getText().toString().trim());
                        }
                    });

                    tcall3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919422257146"));
                            startActivity(intent);
                        }
                    });

                    tcall4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+917588703202"));
                            startActivity(intent);
                        }
                    });

                    tcall5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+918975588068"));
                            startActivity(intent);
                        }
                    });

                    tre1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager sm = SmsManager.getDefault();
                            sm.sendTextMessage("9420484497", null, "Transport Application :" +
                                    "\n" +
                                    "UserID : " + id + "\n"
                                    + "Load : " + load1 + "\n" + "Content : "+con1+"\n"+"\n\n"+"Transport : "+tn1.getText().toString().trim()+"\n"
                                    +tc1.getText().toString().trim(), null, null);

                            tdis1.setVisibility(View.GONE);
                            tdis1.startAnimation(fo);

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast2, (ViewGroup) findViewById(R.id.toast_layout2));
                            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text2);
                            toast_text.setText("Requested Successfully");

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
                                            Intent intent=new Intent(TDash_Transport.this,com.codebreak.gofarm2.TraderDash.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                    }
                                }

                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(TDash_Transport.this);
                            builder.setMessage("Request successfully submitted to the Transporter."+"\n"+"You will receive a callback soon for further details."
                                    +"\n"+"Happy Farming."+"\n"+"Team,Go Farm").setPositiveButton("Continue",dialog).setNegativeButton("Exit",dialog).show();

                        }
                    });

                    tre2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager sm = SmsManager.getDefault();
                            sm.sendTextMessage("9420484497", null, "Transport Application :" +
                                    "\n" +
                                    "UserID : " + id + "\n"
                                    + "Load : " + load1 + "\n" + "Content : "+con1+"\n"+"\n\n"+"Transport : "+tn2.getText().toString().trim()+"\n"
                                    +tc2.getText().toString().trim(), null, null);

                            tdis2.setVisibility(View.GONE);
                            tdis2.startAnimation(fo);

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast2, (ViewGroup) findViewById(R.id.toast_layout2));
                            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text2);
                            toast_text.setText("Requested Successfully");

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
                                            Intent intent=new Intent(TDash_Transport.this,com.codebreak.gofarm2.TraderDash.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                    }
                                }

                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(TDash_Transport.this);
                            builder.setMessage("Request successfully submitted to the Transporter."+"\n"+"You will receive a callback soon for further details."
                                    +"\n"+"Happy Farming."+"\n"+"Team,Go Farm").setPositiveButton("Continue",dialog).setNegativeButton("Exit",dialog).show();

                        }
                    });

                    tre3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager sm = SmsManager.getDefault();
                            sm.sendTextMessage("9420484497", null, "Transport Application :" +
                                    "\n" +
                                    "UserID : " + id + "\n"
                                    + "Load : " + load1 + "\n" + "Content : "+con1+"\n"+"\n\n"+"Transport : "+tn3.getText().toString().trim()+"\n"
                                    +tc3.getText().toString().trim(), null, null);

                            tdis3.setVisibility(View.GONE);
                            tdis3.startAnimation(fo);

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast2, (ViewGroup) findViewById(R.id.toast_layout2));
                            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text2);
                            toast_text.setText("Requested Successfully");

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
                                            Intent intent=new Intent(TDash_Transport.this,com.codebreak.gofarm2.TraderDash.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                    }
                                }

                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(TDash_Transport.this);
                            builder.setMessage("Request successfully submitted to the Transporter."+"\n"+"You will receive a callback soon for further details."
                                    +"\n"+"Happy Farming."+"\n"+"Team,Go Farm").setPositiveButton("Continue",dialog).setNegativeButton("Exit",dialog).show();

                        }
                    });

                    tre4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager sm = SmsManager.getDefault();
                            sm.sendTextMessage("9420484497", null, "Transport Application :" +
                                    "\n" +
                                    "UserID : " + id + "\n"
                                    + "Load : " + load1 + "\n" + "Content : "+con1+"\n"+"\n\n"+"Transport : "+tn4.getText().toString().trim()+"\n"
                                    +tc4.getText().toString().trim(), null, null);
                            tdis4.setVisibility(View.GONE);
                            tdis4.startAnimation(fo);

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast2, (ViewGroup) findViewById(R.id.toast_layout2));
                            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text2);
                            toast_text.setText("Requested Successfully");

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
                                            Intent intent=new Intent(TDash_Transport.this,com.codebreak.gofarm2.TraderDash.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                    }
                                }

                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(TDash_Transport.this);
                            builder.setMessage("Request successfully submitted to the Transporter."+"\n"+"You will receive a callback soon for further details."
                                    +"\n"+"Happy Farming."+"\n"+"Team,Go Farm").setPositiveButton("Continue",dialog).setNegativeButton("Exit",dialog).show();

                        }
                    });

                    tre5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager sm = SmsManager.getDefault();
                            sm.sendTextMessage("9420484497", null, "Transport Application :" +
                                    "\n" +
                                    "UserID : " + id + "\n"
                                    + "Load : " + load1 + "\n" + "Content : "+con1+"\n"+"\n\n"+"Transport : "+tn5.getText().toString().trim()+"\n"
                                    +tc5.getText().toString().trim(), null, null);
                            tdis5.setVisibility(View.GONE);
                            tdis5.startAnimation(fo);

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast2, (ViewGroup) findViewById(R.id.toast_layout2));
                            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text2);
                            toast_text.setText("Requested Successfully");

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
                                            Intent intent=new Intent(TDash_Transport.this,com.codebreak.gofarm2.TraderDash.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                    }
                                }

                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(TDash_Transport.this);
                            builder.setMessage("Request successfully submitted to the Transporter."+"\n"+"You will receive a callback soon for further details."
                                    +"\n"+"Happy Farming."+"\n"+"Team,Go Farm").setPositiveButton("Continue",dialog).setNegativeButton("Exit",dialog).show();

                        }
                    });

                   /* trans_new.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ttrans_mfrom.setVisibility(View.VISIBLE);
                            ttrans_from.setVisibility(View.VISIBLE);
                            ttrans_mto.setVisibility(View.VISIBLE);
                            ttrans_to.setVisibility(View.VISIBLE);
                            ttrans_mcon.setVisibility(View.VISIBLE);
                            ttrans_con.setVisibility(View.VISIBLE);
                            ttrans_mload.setVisibility(View.VISIBLE);
                            ttrans_load.setVisibility(View.VISIBLE);
                            ttrans_submit.setVisibility(View.VISIBLE);

                            ttrans_mfrom.startAnimation(fi);
                            ttrans_from.startAnimation(fi);
                            ttrans_mto.startAnimation(fi);
                            ttrans_to.startAnimation(fi);
                            ttrans_mcon.startAnimation(fi);
                            ttrans_con.startAnimation(fi);
                            ttrans_mload.startAnimation(fi);
                            ttrans_load.startAnimation(fi);
                            ttrans_submit.startAnimation(fi);

                            tdis1.setVisibility(View.GONE);
                            tdis2.setVisibility(View.GONE);
                            tdis3.setVisibility(View.GONE);
                            tdis4.setVisibility(View.GONE);
                            tdis5.setVisibility(View.GONE);
                            ttrans_new.setVisibility(View.GONE);

                            tdis1.startAnimation(di);
                            tdis2.startAnimation(di);
                            tdis3.startAnimation(di);
                            tdis4.startAnimation(di);
                            tdis5.startAnimation(di);
                            ttrans_new.startAnimation(di);
                        }
                    }); */
                }
            }
        });

        getLocationPermission();

        ttrans_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                showAutocomplete();
            }
        });

        ttrans_to.setOnClickListener(new View.OnClickListener() {
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

                    ttrans_from.setText(place.getName() + ", " + place.getAddress());
                    flag=0;
                    fromlat=place.getLatLng().latitude;
                    fromlong=place.getLatLng().longitude;
                }

                if(flag==2) {

                    ttrans_to.setText(place.getName() + ", " + place.getAddress());
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

                            Geocoder geo = new Geocoder(TDash_Transport.this, Locale.getDefault());
                            try {
                                addresses = geo.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                                Log.d("trial", String.valueOf(addresses.get(0)));
                                ttrans_from.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                                fromlat=currentLocation.getLatitude();
                                fromlong=currentLocation.getLongitude();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(TDash_Transport.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }
}
