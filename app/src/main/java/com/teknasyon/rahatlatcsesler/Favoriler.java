package com.teknasyon.rahatlatcsesler;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.teknasyon.rahatlatcsesler.veritabani.Database;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Favoriler extends AppCompatActivity implements View.OnClickListener{
    ImageView favori_img_btn,kitaplik_img_btn;
    TextView kitaplik_txt,favori_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoriler);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tobbar_txt= findViewById(R.id.tobbar_txt);
        tobbar_txt.setText(getString(R.string.favorilerim));

        Favori_kontrol();
        kitaplik_txt = findViewById(R.id.kitaplik_txt);
        favori_txt = findViewById(R.id.favori_txt);
        favori_txt.setTextColor(getResources().getColor(R.color.prgwhite));
        kitaplik_txt.setTextColor(getResources().getColor(R.color.colorPrimary));

        kitaplik_img_btn = findViewById(R.id.kitaplik_img_btn);
        favori_img_btn = findViewById(R.id.favori_img_btn);
        favori_img_btn.setImageResource(R.drawable.btn_aktif);
        kitaplik_img_btn.setImageResource(R.drawable.btn_pasif);
        kitaplik_img_btn.setOnClickListener(this);


        Listeyigetir();
    }

    ArrayList<HashMap<String, String>> Muzikler = new ArrayList<HashMap<String, String>>();
    private static String FAVORI_muzikid    = "mizikid";
    private static String FAVORI_adi        = "adi";
    private static String FAVORI_url        = "url";
    private static String FAVORI_resim      = "resim";
    private void Listeyigetir() {


        RecyclerView recycler_view = findViewById(R.id.muzik_recyview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recycler_view.setLayoutManager(layoutManager);
          adapter_items = new Favori_icerik_Adaptor(this,Muzikler);
        recycler_view.setAdapter(adapter_items);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

    }
    public static Favori_icerik_Adaptor adapter_items;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kitaplik_img_btn:
                Intent intent = new Intent(Favoriler.this, Kitaplik.class);
                startActivity(intent);
                finish();
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

        }else {
           
            Muzikler.addAll(db.Favori_detay()) ;
        }


        new Favorileri_getir().execute();
    }



    ProgressDialog pDialog;
    PostClass post = new PostClass();
    class Favorileri_getir extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute() {
            //  progress dialog
            pDialog = new ProgressDialog(Favoriler.this);
            pDialog.setMessage("Favoriler Getiriliyor...");
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
            params.add(new BasicNameValuePair("param"    , "favorite"));

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

        // Sonuç başarılı ise bu kod çalışmıcak çünkü Main activitye yönlenmiş durumda
        protected void onPostExecute(Void unused) {
            // closing progress dialog
            pDialog.dismiss();
          //  categoryAdapter.notifyDataSetChanged();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    if (sonuc == 0) {// Sonuç başarılı değil ise
                        AlertDialog alertDialog = new AlertDialog.Builder(Favoriler.this).create();
                        alertDialog.setTitle("Favori müzikleriniz Çekilemedi.");
                        alertDialog.setMessage("Kitaplık Sayfasına Yönlendiriliceksiniz."); //Sonuc mesajıyla bilgilendiriyoruz.
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(RESULT_OK, "Tamam", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // ksifre.setText("");
                                Intent intent = new Intent(Favoriler.this, Kitaplik.class);
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
}
