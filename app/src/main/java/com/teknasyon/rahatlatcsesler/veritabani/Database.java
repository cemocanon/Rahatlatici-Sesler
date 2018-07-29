package com.teknasyon.rahatlatcsesler.veritabani;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper {

	// Database Versiyonu Güncellediğimizde bu versiyon artacak
	private static final int DATABASE_VERSION = 1;

	// Database ADI
	private static final String DATABASE_ADI = "rahatlaticisesler";//database adi

	private static final String TABLO_ADI = "favoriler";
	private static String FAVORI_ID = "id";
	private static String FAVORI_muzikid = "mizikid";
	private static String FAVORI_adi = "adi";
	private static String FAVORI_url = "url";
	private static String FAVORI_resim = "resim";


	public Database(Context context) {
		super(context, DATABASE_ADI, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {  // Databesi olusturuyoruz.



		String CREATE_TABLO = "CREATE TABLE " + TABLO_ADI + "("
				+ FAVORI_ID + " INTEGER PRIMARY KEY,"
				+ FAVORI_adi + " TEXT,"
				+ FAVORI_url + " TEXT,"
				+ FAVORI_muzikid + " TEXT,"
				+ FAVORI_resim + " TEXT" + ")";
		db.execSQL(CREATE_TABLO);

	}

	public void Favori_ekle(String adi,String url,String muzik_id,String resim) {
		//FAVORIEkle methodu ise adi ustunde Databese veri eklemek icin
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FAVORI_adi, adi);
		values.put(FAVORI_url, url);
		values.put(FAVORI_muzikid, muzik_id);
		values.put(FAVORI_resim, resim);

		db.insert(TABLO_ADI, null, values);
		db.close(); //Database Baglantisini kapattik*/
	}


	public ArrayList<HashMap<String, String>> Favori_detay(){
		//Bu methodda favorileri çekiyoruz

		//HashMap bir iki boyutlu arraydir diyebiliriz Aslinda bir List interfacedir fakat anlamaniz icin cift boyutlu array olarak dusunebiliriz.anahtar-deger ikililerini bir arada tutmak icin tasarlanmistr.
		//mesala map.put("adi","cemil"); mesala burda anahtar adi degeri cemil.
		ArrayList<HashMap<String, String>> Muzikler = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> muzik;
		String selectQuery = "SELECT * FROM " + TABLO_ADI;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		while(cursor.moveToNext()){
            muzik = new HashMap<String,String>();
            muzik.put(FAVORI_adi, cursor.getString(1));
			muzik.put(FAVORI_url, cursor.getString(2));
			muzik.put(FAVORI_muzikid, cursor.getString(3));
			muzik.put(FAVORI_resim, cursor.getString(4));
			Muzikler.add(muzik);
		}
		cursor.close();
		db.close();
		return Muzikler;
	}


	public int getRowCount() { //tabloda kac satir kayitli oldugunu geri doner

		String countQuery = "SELECT  * FROM " + TABLO_ADI;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		return rowCount;
	}
	public int favoridevarmi(String muzik_id) { //tabloda kac satir kayitli oldugunu geri doner

		String countQuery = "SELECT  * FROM " + TABLO_ADI +" where "+FAVORI_muzikid +"= "+muzik_id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
 		return rowCount;
	}

	public void favori_sil(String muzikid){ //id si belli olan row u silmek için

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLO_ADI, FAVORI_muzikid + " = "+muzikid,
				null);
		db.close();
	}
	public void resettablo(){
		// Tum verileri siler. tabloyu resetler.
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLO_ADI, null, null);
		db.close();
	}

	//DAtabes güncellendiğinde bu method çalışır
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
