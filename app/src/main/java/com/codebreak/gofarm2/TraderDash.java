package com.codebreak.gofarm2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TraderDash extends AppCompatActivity {


    TextView tdash_edit,tdash_logout,tdash_name,tdash_id,tdash_user,tdash_city,tdash_buy,tdash_demand,tdash_trans;
    ImageView tdash_dp; String path;
    String id;
    Calendar calendar;
    Date today;
    DateFormat dateFormat;
    String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trader_dash);

        id=getIntent().getStringExtra("id");

        tdash_edit=findViewById(R.id.tdash_edit);
        tdash_logout=findViewById(R.id.tdash_logout);
        tdash_name=findViewById(R.id.tdash_name);
        tdash_id=findViewById(R.id.tdash_id);
        tdash_user=findViewById(R.id.tdash_user);
        tdash_city=findViewById(R.id.tdash_city);
        tdash_buy=findViewById(R.id.tdash_buy);
        tdash_demand=findViewById(R.id.tdash_demand);
        tdash_dp=findViewById(R.id.tdash_dp);
        tdash_trans=findViewById(R.id.tdash_trans);

        calendar=Calendar.getInstance();
        today=calendar.getTime();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        current=dateFormat.format(today);

        tdash_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent=new Intent(TraderDash.this,com.codebreak.gofarm2.HomePage.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                TraderDash.this.finish();

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }

                };
                AlertDialog.Builder builder=new AlertDialog.Builder(TraderDash.this);
                builder.setMessage("Are you sure you want to logout?").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();

            }
        });

        tdash_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TraderDash.this,com.codebreak.gofarm2.TDash_Transport.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

        tdash_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BuyDB db=new BuyDB(TraderDash.this);
                db.open();
                int res=db.isValid(id);
                db.close();
                if(res==0) {
                    Intent intent = new Intent(TraderDash.this, com.codebreak.gofarm2.TDash_Buy.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                else
                {
                    Intent intent = new Intent(TraderDash.this, com.codebreak.gofarm2.TDash_BuyEdit.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
        tdash_demand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TraderDash.this,com.codebreak.gofarm2.TDash_Sale.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

        String dash;
        FarmerDB db=new FarmerDB(this);
        db.open();
        dash=db.getDash(id);
        db.close();

        String split[]=dash.split(", ");
        tdash_name.setText(split[0]);
        tdash_id.setText(split[1]);
        tdash_user.setText(split[2]);
        tdash_city.setText(split[3]);


        String res;
        DP db1=new DP(this);
        db1.open();
        res=db1.getPath(id);
        db1.close();

        if(res.equals(""))
        {
            tdash_dp.setImageResource(R.drawable.profilepic);
        }

        else
        {
            File file=new File(res);
            if(file.exists())
            {
                Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                ImageView my=( ImageView)findViewById(R.id.tdash_dp);
                my.setImageBitmap(bitmap);
            }
            tdash_edit.setText("Change Profile Picture");

        }
        tdash_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tdash_edit.setText(getResources().getString(R.string.change));
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,101);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        tdash_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tdash_edit.setText(getResources().getString(R.string.change));
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,101);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==RESULT_OK && data!=null)
        {
            Uri uri=data.getData();
            tdash_dp.setImageURI(uri);
            String[]filePathColumn={MediaStore.Images.Media.DATA};
            Cursor c=getContentResolver().query(uri,filePathColumn,null,null,null);
            c.moveToFirst();
            int columnIndex=c.getColumnIndex(filePathColumn[0]);
            path=c.getString(columnIndex);
            c.close();

            DP db=new DP(this);
            db.open();
            db.createEntry(id,path);
            db.setPath(id,path);
            db.close();

        }
    }

    public void uploadDPTD(View v)
    {
        tdash_edit.setText("Change Profile Picture");
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,101);
        overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
    }


}
