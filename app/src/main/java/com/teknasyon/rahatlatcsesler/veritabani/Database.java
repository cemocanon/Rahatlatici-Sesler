package com.teknasyon.rahatlatcsesler.veritabani;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class Database extends SQLiteOpenHelper {

	// Database Versiyonu Güncellediğimizde bu versiyon artaca
	private static final int DATABASE_VERSION = 1;

	// Database ADI
	private static final String DATABASE_ADI = "rahatlaticisesler";//database adi

	private static final String TABLO_ADI = "favoriler";
	private static String FAVORI_ID = "id";
	private static String FAVORI_adi = "adi";
	private static String FAVORI_url = "url";
	private static String FAVORI_resim = "resim";


	public Database(Context context) {
		super(context, DATABASE_ADI, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {  // Databesi olusturuyoruz.Bu methodu biz cagirmiyoruz. Databese de obje olusturdugumuzda otamatik cagiriliyor.



		String CREATE_TABLO = "CREATE TABLE " + TABLO_ADI + "("
				+ FAVORI_ID + " INTEGER PRIMARY KEY,"
				+ FAVORI_adi + " TEXT,"
				+ FAVORI_url + " TEXT,"
				+ FAVORI_resim + " TEXT" + ")";
		db.execSQL(CREATE_TABLO);

	}

	public void Favori_ekle(String adi,String gpslot,String url,String resim) {
		//FAVORIEkle methodu ise adi ustunde Databese veri eklemek icin
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		//values.put(id, adi);
		values.put(FAVORI_adi, adi);
		values.put(FAVORI_url, url);
		values.put(FAVORI_resim, resim);

		db.insert(TABLO_ADI, null, values);
		db.close(); //Database Baglantisini kapattik*/
	}


	public HashMap<String, String> Favori_detay(){
		//Bu methodda favorilerin daha önce eklenip eklenmediğini kontrol ediyor

		//HashMap bir iki boyutlu arraydir diyebiliriz Aslinda bir List interfacedir fakat anlamaniz icin cift boyutlu array olarak dusunebiliriz.anahtar-deger ikililerini bir arada tutmak icin tasarlanmistr.
		//mesala map.put("adi","cemil"); mesala burda anahtar adi degeri cemil.

		HashMap<String,String> kisi = new HashMap<String,String>();
		String selectQuery = "SELECT * FROM " + TABLO_ADI;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		if(cursor.getCount() > 0){
			kisi.put(FAVORI_adi, cursor.getString(1));
			kisi.put(FAVORI_url, cursor.getString(2));
			kisi.put(FAVORI_resim, cursor.getString(3));
		}
		cursor.close();
		db.close();
		// return kitap
		return kisi;
	}


	public int getRowCount() { //tabloda kac satir kayitli oldugunu geri doner

		String countQuery = "SELECT  * FROM " + TABLO_ADI;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		// return row count
		return rowCount;
	}


	public void resettablo(){
		// Tum verileri siler. tabloyu resetler.
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLO_ADI, null, null);
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
