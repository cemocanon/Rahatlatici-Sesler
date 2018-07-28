package com.teknasyon.rahatlatcsesler;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Kitaplik_icerik extends AppCompatActivity {

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

    public static final String katagori_str = "katagori";
    ArrayList<HashMap<String, String>> Muzikler = new ArrayList<HashMap<String, String>>();
    private static String FAVORI_muzikid = "mizikid";
    private static String FAVORI_adi = "adi";
    private static String FAVORI_url = "url";
    private static String FAVORI_resim = "resim";
    String Katagori_ID;
    HashMap<String, String> Katagori ;
    Kitaplik_icerik_Adaptor adapter_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitaplik_icerik);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tobbar_txt= findViewById(R.id.tobbar_txt);

        secilenkatagori();// Hani katagori seçilmişse id sini aldık ve id ye göre içerik getirceğiz
        tobbar_txt.setText(Katagori.get("adi")); // Seçilen Katagorinin adını Tobbara yazdırdık.



        RecyclerView recycler_view = findViewById(R.id.muzik_recyview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recycler_view.setLayoutManager(layoutManager);
          adapter_items = new Kitaplik_icerik_Adaptor(this,Muzikler);
        recycler_view.setAdapter(adapter_items);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

    }

    private void secilenkatagori() {


        if (getIntent().hasExtra(katagori_str)) {
            Katagori_ID = getIntent().getStringExtra(katagori_str);

            Katagori= (HashMap<String, String>)getIntent().getExtras().getSerializable(katagori_str);

        }else {
            Toast.makeText(this, getString(R.string.katagoriyok), Toast.LENGTH_SHORT).show();
            finish();
        }
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
            pDialog = new ProgressDialog(Kitaplik_icerik.this);
            pDialog.setMessage("Kitaplik Dosyaları Getiriliyor...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        Integer sonuc=0;

        protected Void doInBackground(Void... unused) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("user"  , "demo"));
            params.add(new BasicNameValuePair("pass"     , "demo"));
            params.add(new BasicNameValuePair("param"    , "categoryItems"));
            params.add(new BasicNameValuePair("catid"    , Katagori.get("id")));

            String json = post.httpPost(getString(R.string.favori_url), "POST", params, 20000);

            Log.d("Gelen Json", "" + json);//Gelen veriyi logluyoruz.Log Catten kontrol edebiliriz
            try {


                Muzikler.clear();
                if (!json.equals("")) {
                    JSONObject cevap = new JSONObject(json);
                    HashMap servis;
                    // Phone number is agin JSON Object
                    JSONArray cast = cevap.getJSONArray("success");
                    for (int i = 0; i < cast.length(); i++) {
                        servis = new HashMap<>();
                        JSONObject actor = cast.getJSONObject(i);
                        sonuc=1;


                        servis.put(FAVORI_muzikid  , actor.getString("id"));
                        servis.put(FAVORI_adi      , actor.getString("title"));
                        servis.put(FAVORI_url      , actor.getString("url"));
                        servis.put(FAVORI_resim    , actor.getString("image"));
                        servis.put("catid"  , actor.getString("catid"));
                        Muzikler.add(servis);

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

         protected void onPostExecute(Void unused) {
             pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (sonuc == 0) {// Sonuç başarılı değil ise
                        AlertDialog alertDialog = new AlertDialog.Builder(Kitaplik_icerik.this).create();
                        alertDialog.setTitle("Kitaplık müzikleri çekilemedi.");
                        alertDialog.setMessage("Kitaplık Sayfasına Yönlendiriliceksiniz."); //Sonuc mesajıyla bilgilendiriyoruz.
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(RESULT_OK, "Tamam", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // ksifre.setText("");
                                Intent intent = new Intent(Kitaplik_icerik.this, Kitaplik.class);
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
    public void onPause(){
        for (int i = 0; i < Kitaplik_icerik_Adaptor.ViewHolder.mediaPlayerMap.size(); i++) {

            if (Kitaplik_icerik_Adaptor.ViewHolder.mediaPlayerMap!=null){
                if(Kitaplik_icerik_Adaptor.ViewHolder.mediaPlayerMap.get(i).isPlaying()) {
                    Kitaplik_icerik_Adaptor.ViewHolder.mediaPlayerMap.get(i).stop();
                }
            }
        }
        finish();
        //media player stops
        super.onPause();
    }

}