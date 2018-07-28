package com.teknasyon.rahatlatcsesler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class Kitaplik_icerik extends AppCompatActivity {

    public static final String katagori_str = "katagori";
    String Katagori_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitaplik_icerik);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        secilenkatagori();// Hani katagori seçilmişse id sini aldık ve id ye göre içerik getirceğiz

    }

    private void secilenkatagori() {


        if (getIntent().hasExtra(katagori_str)) {
            Katagori_ID = getIntent().getStringExtra(katagori_str);
            Toast.makeText(this, Katagori_ID, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getString(R.string.katagoriyok), Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}