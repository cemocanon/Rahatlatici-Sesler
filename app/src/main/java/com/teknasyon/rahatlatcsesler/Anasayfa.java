package com.teknasyon.rahatlatcsesler;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Anasayfa extends AppCompatActivity {
    ArrayList<HashMap<String, String>> Katagoriler = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anasayfa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView   recycler_view = (RecyclerView)findViewById(R.id.katagori_recyview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recycler_view.setLayoutManager(layoutManager);

        Katagori_Adaptor adapter_items = new Katagori_Adaptor(Katagoriler);
        recycler_view.setHasFixedSize(true);

        recycler_view.setAdapter(adapter_items);

        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

}
