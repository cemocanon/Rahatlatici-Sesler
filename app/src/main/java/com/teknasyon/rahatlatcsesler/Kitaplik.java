package com.teknasyon.rahatlatcsesler;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Kitaplik extends AppCompatActivity   implements View.OnClickListener {


    //internet kontrolü başla

    public  boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

    public void connectionMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Lütfen internet bağlantınızı kontrol ediniz.").setPositiveButton("Tamam", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {

                finish();

            }
        });

        final AlertDialog alert = builder.create();
        alert.show();
    }
    //internet kontrülü bitiş

    ArrayList<HashMap<String, String>> Katagoriler = new ArrayList<HashMap<String, String>>();
ImageView kitaplik_img_btn,favori_img_btn;
    Katagori_Adaptor adapter_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anasayfa);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tobbar_txt= findViewById(R.id.tobbar_txt);
        tobbar_txt.setText(getString(R.string.kitaplik));


        RecyclerView   recycler_view = findViewById(R.id.katagori_recyview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recycler_view.setLayoutManager(layoutManager);
          adapter_items = new Katagori_Adaptor(this,Katagoriler);
        recycler_view.setAdapter(adapter_items);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        favori_img_btn = findViewById(R.id.favori_img_btn);
        kitaplik_img_btn = findViewById(R.id.kitaplik_img_btn);
        favori_img_btn.setImageResource(R.drawable.btn_pasif);
        kitaplik_img_btn.setImageResource(R.drawable.btn_aktif);
        favori_img_btn.setOnClickListener(this);
        if(!isOnline())
        {
            connectionMessage();
        }
        else {
        new kitaplikicerik_getir().execute();
        }
    }



    ProgressDialog pDialog;
    PostClass post = new PostClass();
    class kitaplikicerik_getir extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute() {
            //  progress dialog
            pDialog = new ProgressDialog(Kitaplik.this);
            pDialog.setMessage("Kitaplik Dosyaları Getiriliyor...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        Integer sonuc=0;

        protected Void doInBackground(Void... unused) {

            //String sifre_sha1 = Fonksiyonlar.sha1(sifre); //istersek sha1 şifreleme fonksiyonunu kullanabiliriz

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("user"  , "demo"));
            params.add(new BasicNameValuePair("pass"     , "demo"));
            params.add(new BasicNameValuePair("param"    , "category"));

            String json = post.httpPost(getString(R.string.favori_url), "POST", params, 20000);

            Log.d("Gelen Json", "" + json);//Gelen veriyi logluyoruz.Log Catten kontrol edebiliriz
            try {


                Katagoriler.clear();
                if (!json.equals("")) {
                    JSONObject cevap = new JSONObject(json);
                    HashMap servis;
                    // Phone number is agin JSON Object
                    JSONArray cast = cevap.getJSONArray("success");
                    for (int i = 0; i < cast.length(); i++) {
                        servis = new HashMap<>();
                        JSONObject actor = cast.getJSONObject(i);
                        sonuc=1;


                        servis.put("id"  , actor.getString("id"));
                        servis.put("adi"      , actor.getString("title"));
                        servis.put("url"      , actor.getString("url"));
                        Katagoriler.add(servis);

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Sonuç başarılı ise bu kod çalışmıcak çünkü Main activitye yönlenmiş durumda
        protected void onPostExecute(Void unused) {
            // closing progress dialog
            pDialog.dismiss();
            //  categoryAdapter.notifyDataSetChanged();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    if (sonuc == 0) {// Sonuç başarılı değil ise
                        AlertDialog alertDialog = new AlertDialog.Builder(Kitaplik.this).create();
                        alertDialog.setTitle("Kitaplık müzikleri çekilemedi.");
                        alertDialog.setMessage("Kitaplık Sayfasına Yönlendiriliceksiniz."); //Sonuc mesajıyla bilgilendiriyoruz.
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(RESULT_OK, "Tamam", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // ksifre.setText("");
                                Intent intent = new Intent(Kitaplik.this, Kitaplik.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        alertDialog.show();
                    }else{
                        adapter_items.notifyDataSetChanged();
                    }
                }

            });
        }
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
