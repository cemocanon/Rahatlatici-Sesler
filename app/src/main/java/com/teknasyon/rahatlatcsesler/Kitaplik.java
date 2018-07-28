package com.teknasyon.rahatlatcsesler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class Kitaplik extends AppCompatActivity   implements View.OnClickListener {
    ArrayList<HashMap<String, String>> Katagoriler = new ArrayList<HashMap<String, String>>();
ImageView favori_img_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anasayfa);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView   recycler_view = findViewById(R.id.katagori_recyview);
            favori_img_btn = findViewById(R.id.favori_img_btn);

        HashMap katagoriveriler  = new HashMap<>();
        katagoriveriler.put("id","1");
        katagoriveriler.put("adi","Kuş");
        katagoriveriler.put("url","http://www.noagergitavan.com/wp-content/uploads/2015/08/fa38eshutterstock_136529732-Bahar-dallari-uzerinde-kucuk-kus.jpg");

        Katagoriler.add(katagoriveriler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recycler_view.setLayoutManager(layoutManager);

        Katagori_Adaptor adapter_items = new Katagori_Adaptor(this,Katagoriler);
      //  recycler_view.setHasFixedSize(true);

        recycler_view.setAdapter(adapter_items);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        favori_img_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.favori_img_btn:
                Intent intent = new Intent(Kitaplik.this, Favoriler.class);
                startActivity(intent);
                break;
    }
    }
}
