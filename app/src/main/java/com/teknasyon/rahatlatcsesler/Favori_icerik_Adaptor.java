package com.teknasyon.rahatlatcsesler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.teknasyon.rahatlatcsesler.veritabani.Database;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Favori_icerik_Adaptor extends RecyclerView.Adapter<Favori_icerik_Adaptor.ViewHolder> {
    private Context mContext;
    Database db ;
    ArrayList<HashMap<String, String>> muzikler = new ArrayList<HashMap<String, String>>();
    public Favori_icerik_Adaptor(Context context, ArrayList<HashMap<String, String>> muzikler) {
        this.muzikler = muzikler;
        this.mContext=context;
        //   this.listener = listener;
    }


    @Override
    public Favori_icerik_Adaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitaplik_icerik_satir, parent, false);

          ViewHolder view_holder = new ViewHolder(v);
        db= new Database(mContext);


        return view_holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         public static Map<Integer, MediaPlayer> mediaPlayerMap = new HashMap<Integer, MediaPlayer>(); // Map dizini oluşturuyorum. sayfa kapandığında sesler çalmaya devam etmemesi için burdan kontrol edeceğim.


        public TextView muzik_adi;
        public ImageView muzik_img,favorisec_img_btn,playpause_btn;
        public DiscreteSeekBar seekBar;
        public  MediaPlayer mediaPlayer  ;

        public ViewHolder(View view) {
            super(view);

             muzik_adi = view.findViewById(R.id.sarki_adi);
            muzik_img =  view.findViewById(R.id.sarki_resim);
            favorisec_img_btn =  view.findViewById(R.id.favorisec_img_btn);
            playpause_btn =  view.findViewById(R.id.playpause_btn);
            seekBar =  view.findViewById(R.id.seekBar);
            mediaPlayer = new MediaPlayer();

        }

    }

    private static String FAVORI_muzikid = "mizikid";
    private static String FAVORI_adi = "adi";
    private static String FAVORI_url = "url";
    private static String FAVORI_resim = "resim";

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.muzik_adi.setText(muzikler.get(position).get(FAVORI_adi));

        RequestOptions options = new RequestOptions();
         options.centerCrop() .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext).load(muzikler.get(position).get(FAVORI_resim))

                .apply(options) .into(holder.muzik_img);

        int count = db.favoridevarmi(muzikler.get(position).get(FAVORI_muzikid));// databasedeki favoride kayıtlımı
        if(count >0){//0 dan fazla ise favori var demektir.
            holder.favorisec_img_btn.setImageResource(R.drawable.favori_aktif);

        }else{
            holder.favorisec_img_btn.setImageResource(R.drawable.favori_pasif);
        }
        holder.favorisec_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 int count = db.favoridevarmi(muzikler.get(position).get(FAVORI_muzikid));// databasedeki favoride kayıtlımı
                if(count >0){//0 dan fazla ise favori var demektir.
                    holder.favorisec_img_btn.setImageResource(R.drawable.favori_pasif);
                    db.favori_sil(muzikler.get(position).get(FAVORI_muzikid));
                    muzikler.remove(position);
                    Favoriler.adapter_items.notifyDataSetChanged();
                    if (muzikler.size()==0){
                        Intent intent = new Intent(mContext, Kitaplik.class);
                        mContext.startActivity(intent);

                    }
                }else{
                    holder.favorisec_img_btn.setImageResource(R.drawable.favori_aktif);
                    db.Favori_ekle(muzikler.get(position).get(FAVORI_adi),muzikler.get(position).get(FAVORI_url),muzikler.get(position).get(FAVORI_muzikid),muzikler.get(position).get(FAVORI_resim));


                }

            }
        });

        holder.playpause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // kullanıcıyı bilgilendiriyorum. internetten indirmeden müziği dinlettiğim içiin ara belleğe alırken 2-3 saniylik müziğin uzunluğuna göre bekleme süresi var. bunu kullanıcya progresle bildiriyorum.
                    final ProgressDialog pDialog = new ProgressDialog(mContext);
                    pDialog.setTitle("Lütfen Bekleyin...");
                    pDialog.setMessage("Ses Dosyası Çekiliyor");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();


                    if(!holder.mediaPlayer.isPlaying()) {
                        holder.playpause_btn.setImageResource(R.drawable.pause);


                        holder.mediaPlayer = new MediaPlayer();


                        holder.mediaPlayer.setDataSource(muzikler.get(position).get(FAVORI_url).toString());

                        float volume = (float) (1 - (Math.log(100 - holder.seekBar.getProgress()) / Math.log(100)));  // Seekbar değeri kadar müziğin sesini açıoyu
                        holder.mediaPlayer.setVolume(volume, volume);
                        holder.mediaPlayer.setLooping(true);
                        holder.mediaPlayerMap.put(position, holder.mediaPlayer );// listedeki mediplayerlı  map dizinine  atıyorum. böylelikle active içinden kontrol edebiliyorum.
                        holder.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {

                                mp.start();

                                Thread timerThread = new Thread(){
                                    public void run(){

                                        int currentPosition = holder.mediaPlayer.getCurrentPosition();

                                        if (currentPosition > 0) // Müzik başlamış ise progresi sonlandırıyorum.
                                            pDialog.dismiss();

                                    }
                                };
                                timerThread.start();
                            }
                        });
                        holder.mediaPlayer.prepareAsync();
                    }else {

                        holder.playpause_btn.setImageResource(R.drawable.play);
                        holder.mediaPlayer.pause();
                        Thread timerThread = new Thread(){
                            public void run(){

                                int currentPosition = holder.mediaPlayer.getCurrentPosition();

                                if (currentPosition > 0)
                                    pDialog.dismiss();

                            }
                        };
                        timerThread.start();

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        // Seekbar ile her müziği kendi ses yüksekliğini ayarlayaibliyoruz.
        holder.seekBar.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                float volume = (float) (1 - (Math.log(100 - value) / Math.log(100)));

                holder.mediaPlayer.setVolume(
                        volume, volume);
                return value * 100;
            }
        });

    }

    @Override
    public int getItemCount() {
        return muzikler.size();
    }

}
