package com.teknasyon.rahatlatcsesler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class Katagori_Adaptor extends RecyclerView.Adapter<Katagori_Adaptor.ViewHolder> {
    private Context mContext;
    ArrayList<HashMap<String, String>> Katagoriler = new ArrayList<HashMap<String, String>>();
    public Katagori_Adaptor(Context context,ArrayList<HashMap<String, String>> katagoriler) {
        this.Katagoriler = katagoriler;
        this.mContext=context;
        //   this.listener = listener;
    }


    @Override
    public Katagori_Adaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.katagori_satir, parent, false);

          ViewHolder view_holder = new ViewHolder(v);



        return view_holder;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView katagori_adi;
        public ImageView katagori_img;


        public ViewHolder(View view) {
            super(view);

            //    card_view = (CardView)view.findViewById(R.id.card_view);
            katagori_adi = (TextView)view.findViewById(R.id.katagori_adi);
            katagori_img = (ImageView) view.findViewById(R.id.katagori_resim);


        }

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.katagori_adi.setText(Katagoriler.get(position).get("adi"));
        RequestOptions options = new RequestOptions();
         options.centerCrop() .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(mContext)
                .load(Katagoriler.get(position).get("url"))
                .apply(options)
                .into(holder.katagori_img);

        holder.katagori_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext, Katagoriler.get(position).get("adi"), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, Kitaplik_icerik.class);
                intent.putExtra(Kitaplik_icerik.katagori_str, Katagoriler.get(position).get("id"));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Katagoriler.size();
    }

}
