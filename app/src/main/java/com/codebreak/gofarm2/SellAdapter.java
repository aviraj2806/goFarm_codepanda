package com.codebreak.gofarm2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.ViewHolder> {

    private ArrayList<Sell> farmer;
    public SellAdapter (Context context, ArrayList<Sell> list)
    {
        farmer=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView slist_dp;
        TextView slist_name,slist_city,slist_crop,slist_amt,slist_contact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            slist_dp=itemView.findViewById(R.id.slist_dp);
            slist_city=itemView.findViewById(R.id.slist_city);
            slist_name=itemView.findViewById(R.id.slist_name);
            slist_crop=itemView.findViewById(R.id.slist_crop);
            slist_amt=itemView.findViewById(R.id.slist_amt);
            slist_contact=itemView.findViewById(R.id.slist_contact);

        }

    }
    @NonNull
    @Override
    public SellAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.sell_list,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SellAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(farmer.get(position));

        holder.slist_name.setText(farmer.get(position).getName());
        holder.slist_city.setText(farmer.get(position).getCity());
        holder.slist_crop.setText(farmer.get(position).getCrop());
        holder.slist_amt.setText(farmer.get(position).getAmt()+" Quintals");
        holder.slist_contact.setText("Contact : "+farmer.get(position).getContact());

        String res=farmer.get(position).getDp();
        if(res.equals("hello"))
        {
            holder.slist_dp.setImageResource(R.drawable.profilepic);
        }

        else
        {
            File file=new File(res);
            if(file.exists())
            {
                Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                ImageView my=( ImageView)holder.slist_dp;
                my.setImageBitmap(bitmap);
            }

        }


    }

    @Override
    public int getItemCount() {
        return farmer.size();
    }
}

