package com.teknasyon.rahatlatcsesler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Katagori_Adaptor extends RecyclerView.Adapter<Katagori_Adaptor.ViewHolder> {
    public Katagori_Adaptor(ArrayList<HashMap<String, String>> katagoriler) {
        this.Katagoriler = katagoriler;
     //   this.listener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView katagori_adi;
        public ImageView person_img;


        public ViewHolder(View view) {
            super(view);

        //    card_view = (CardView)view.findViewById(R.id.card_view);
            katagori_adi = (TextView)view.findViewById(R.id.katagori_adi);
            person_img = (ImageView) view.findViewById(R.id.katagori_resim);

        }
    }

    ArrayList<HashMap<String, String>> Katagoriler = new ArrayList<HashMap<String, String>>();




    @Override
    public Katagori_Adaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.katagori_satir, parent, false);
        final ViewHolder view_holder = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // listener.onItemClick(v, view_holder.getPosition());
            }
        });

        return view_holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.katagori_adi.setText(Katagoriler.get(position).get(""));
        //holder.person_img.setImageResource(Katagoriler.get(position).get(""));

    }

    @Override
    public int getItemCount() {
        return Katagoriler.size();
    }

}
