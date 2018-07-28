package com.teknasyon.rahatlatcsesler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teknasyon.rahatlatcsesler.veritabani.Database;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        Database   db = new Database(Splash.this);
        int count = db.getRowCount();// databasedeki favori row sayisi
        if(count > 0){//0 dan fazla ise favori var demektir. o zaman favorilere yönlendiriyruz. Favoriler boş ise Kitaplığa yönlendiriyoruz.
            Thread timerThread = new Thread(){
                public void run(){
                    try{
                        sleep(2000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }finally{
                        Intent intent = new Intent(Splash.this,Favoriler.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timerThread.start();
        }else{
            Thread timerThread = new Thread(){
                public void run(){
                    try{
                        sleep(2000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }finally{
                        Intent intent = new Intent(Splash.this,Kitaplik.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timerThread.start();

        }
    }
}
