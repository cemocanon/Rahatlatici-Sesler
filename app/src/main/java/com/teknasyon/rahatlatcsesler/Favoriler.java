package com.teknasyon.rahatlatcsesler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.teknasyon.rahatlatcsesler.veritabani.Database;

public class Favoriler extends AppCompatActivity implements View.OnClickListener{
    ImageView kitaplik_img_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoriler);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Favori_kontrol();
        kitaplik_img_btn = findViewById(R.id.kitaplik_img_btn);
        kitaplik_img_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kitaplik_img_btn:
                Intent intent = new Intent(Favoriler.this, Kitaplik.class);
                startActivity(intent);
                break;

        }

    }

    private void Favori_kontrol() {
        Database db = new Database(Favoriler.this);
        int count = db.getRowCount();// databasedeki favori row sayisi
        if(count == 0){//0 dan fazla ise favori var demektir. o zaman favorilere yönlendiriyruz. Favoriler boş ise Kitaplığa yönlendiriyoruz.

            Toast.makeText(this, "Favorileriniz Boş. Kitaplıktan Ekleyebilirisiniz. Yönelndirildiniz.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Favoriler.this,Kitaplik.class);
                        startActivity(intent);
                        finish();


        }
    }


}
