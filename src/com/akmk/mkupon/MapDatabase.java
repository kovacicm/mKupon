 
package com.akmk.mkupon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorManager;
import android.util.Log;

public class MapDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="mkupon24";

	//kupon tablica
	public static final String RBKUPON ="rbkupon";
	public static final String TIPKUPON ="tipkupon";
	public static final String NNALJ="nnalj";
	public static final String MAXNALJ="maxnalj";
	public static final String AKTIVAN="aktivan";
	public static final String USERID="userid";
	public static final String OTVOREN="otvoren";
	public static final String VRIDIDO="vridido";
	
	//kupnja tablica
	public static final String KOD="kod";
	public static final String DD="dd";
	public static final String MM="mm";
	public static final String YY="yy";
	public static final String IZNOS="iznos";
	public static final String DUCANID="ducanid";
	
	//user tablica
	public static final String USERNAME="username";
	public static final String PASS="pass";
	public static final String IME="ime";
	public static final String PREZIME="prezime";
	public static final String ADRESA="adresa";
	public static final String GRAD="GRAD";
	
	//ducan tablica
	public static final String DSIFRA="dsifra";
	public static final String DADRESA="dadresa";
	public static final String DGRAD="dgrad";
	
	//naljepnica tablica
	public static final String KUPNJAID="kupnjaid";
	public static final String KUPONID="kuponid";
	
	//tablica akcije
	public static final String KAT="kat";
	public static final String POP="pop";
	public static final String CIJENA="cijena";
	public static final String LABEL="label";
	public static final String DD2="dd2";
	public static final String MM2="mm2";
	public static final String YY2="yy2";
	public static final String OPIS="opis";
	public static final String SLIKA="slika";
	public static final String FAV="fav";
	
	public MapDatabase(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		

		// tablica akcije
		
		db.execSQL("CREATE TABLE kuponi (_id INTEGER PRIMARY KEY AUTOINCREMENT, rbkupon INT, tipkupon INT, nnalj INT, maxnalj INT, aktivan INT, userid INT, otvoren STRING, vridido STRING );");
		
		ContentValues cv=new ContentValues();
		
		cv.put(RBKUPON, 10345); cv.put(TIPKUPON, 10); cv.put(NNALJ, 6);
		cv.put(MAXNALJ, 20); cv.put(AKTIVAN, 1); cv.put(USERID, 1);
		cv.put(OTVOREN, "01.09.2011"); cv.put(VRIDIDO, "01.01.2012");

		db.insert("kuponi", null, cv);
		
		
		//tablica prodavaonice
		
		db.execSQL("CREATE TABLE kupnja (_id INTEGER PRIMARY KEY AUTOINCREMENT, kod INT, dd INT, mm INT, yy INT, iznos DOUBLE, ducanid INT);");
		
		ContentValues cv2=new ContentValues();

		cv2.put(KOD, 12345678); cv2.put(DD, 10); cv2.put(MM, 6);
		cv2.put(YY, 11); cv2.put(IZNOS, 115.36); cv2.put(DUCANID, 1);
		
		db.insert("kupnja", null, cv2);
		
		cv2.put(KOD, 98765432); cv2.put(DD, 15); cv2.put(MM, 7);
		cv2.put(YY, 11); cv2.put(IZNOS, 154.36); cv2.put(DUCANID, 2); 
		
		db.insert("kupnja", null, cv2);
		
		cv2.put(KOD, 23456876); cv2.put(DD, 15); cv2.put(MM, 8);
		cv2.put(YY, 11); cv2.put(IZNOS, 76.36); cv2.put(DUCANID, 1); 
		
		db.insert("kupnja", null, cv2);
		
		cv2.put(KOD, 3454678); cv2.put(DD, 21); cv2.put(MM, 8);
		cv2.put(YY, 11); cv2.put(IZNOS, 376.36); cv2.put(DUCANID, 2); 
		
		db.insert("kupnja", null, cv2);
		
		//korisnici
		
		db.execSQL("CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, username STRING, pass STRING, ime STRING, prezime STRING, adresa STRING, grad STRING);");
		
		ContentValues cv3=new ContentValues();

		cv3.put(USERNAME, "akalajzi"); cv3.put(PASS, "akalajzi"); cv3.put(IME, "Ante");
		cv3.put(PREZIME, "Kalajžić"); cv3.put(ADRESA, "Ježevska 7"); cv3.put(GRAD, "Zagreb");
		
		db.insert("users", null, cv3);
		
		//prodavaonice
		
		db.execSQL("CREATE TABLE prodavaonice (_id INTEGER PRIMARY KEY AUTOINCREMENT, dsifra STRING, dadresa STRING, dgrad STRING);");
		
		ContentValues cv4=new ContentValues();
		
		cv4.put(DSIFRA, "P-203"); cv4.put(DADRESA, "Tuškanova 37"); cv4.put(DGRAD, "ZAGREB");
		
		db.insert("prodavaonice", null, cv4);
		
		
		cv4.put(DSIFRA, "P-203"); cv4.put(DADRESA, "Zmajanska 36"); cv4.put(DGRAD, "ZAGREB");
		
		db.insert("prodavaonice", null, cv4);
		
		
		db.execSQL("CREATE TABLE naljepnica (_id INTEGER PRIMARY KEY AUTOINCREMENT, kupnjaid INT, kuponid INT);");
		
		ContentValues cv5=new ContentValues();
		
		cv5.put(KUPNJAID, 1); cv5.put(KUPONID, 10345); 		
		db.insert("naljepnica", null, cv5);
		cv5.put(KUPNJAID, 1); cv5.put(KUPONID, 10345); 		
		db.insert("naljepnica", null, cv5);
		cv5.put(KUPNJAID, 2); cv5.put(KUPONID, 10345); 		
		db.insert("naljepnica", null, cv5);
		cv5.put(KUPNJAID, 2); cv5.put(KUPONID, 10345); 		
		db.insert("naljepnica", null, cv5);
		cv5.put(KUPNJAID, 2); cv5.put(KUPONID, 10345); 		
		db.insert("naljepnica", null, cv5);
		cv5.put(KUPNJAID, 3); cv5.put(KUPONID, 10345); 		
		db.insert("naljepnica", null, cv5);
		
		
db.execSQL("CREATE TABLE akcije (_id INTEGER PRIMARY KEY AUTOINCREMENT, kat INT, pop INT, cijena DOUBLE, label STRING, dd INT, mm INT, yy INT, opis STRING, slika INT, fav INT );");
		
		ContentValues cv6=new ContentValues();
		
		cv6.put(KAT, 1); cv6.put(POP, 10); cv6.put(CIJENA, 19.99);
		cv6.put(LABEL, "Hamburger");
		cv6.put(DD, 21); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "PIK svježi hamburger 600g"); cv6.put(SLIKA, R.drawable.hamburger); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 5); cv6.put(POP, 12); cv6.put(CIJENA, 5.99);
		cv6.put(LABEL, "Svježi sir");
		cv6.put(DD, 16); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Dukat svježi posni sir 10% 200g"); cv6.put(SLIKA, R.drawable.svjezisir); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 4); cv6.put(POP, 14); cv6.put(CIJENA, 18.99);
		cv6.put(LABEL, "Pivo");
		cv6.put(DD, 8); cv6.put(MM, 9); cv6.put(YY, 2011);
		cv6.put(OPIS, "Ožujsko pivo Q pack 2l"); cv6.put(SLIKA, R.drawable.pivo); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 2); cv6.put(POP, 18); cv6.put(CIJENA, 16.49);
		cv6.put(LABEL, "Sladoled");
		cv6.put(DD, 21); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Ledo Sladoled Twice vanilija i čokolada 2l"); cv6.put(SLIKA, R.drawable.sladoled); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 1); cv6.put(POP, 15); cv6.put(CIJENA, 24.99);
		cv6.put(LABEL, "Piletina");
		cv6.put(DD, 21); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Pileći mix 640 g K Plus"); cv6.put(SLIKA, R.drawable.piletina); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 4); cv6.put(POP, 20); cv6.put(CIJENA, 29.99);
		cv6.put(LABEL, "Vino");
		cv6.put(DD, 21); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Vino duet bib 3l bag in box Agrolaguna"); cv6.put(SLIKA, R.drawable.vino); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 2); cv6.put(POP, 23); cv6.put(CIJENA, 18.99);
		cv6.put(LABEL, "Sladoled");
		cv6.put(DD, 28); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Ledo sladoled Quattro italiano 900 ml"); cv6.put(SLIKA, R.drawable.quattro); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 3); cv6.put(POP, 16); cv6.put(CIJENA, 1.49);
		cv6.put(LABEL, "Paprika");
		cv6.put(DD, 28); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Paprika Babura II klasa 1kg"); cv6.put(SLIKA, R.drawable.paprika); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 3); cv6.put(POP, 12); cv6.put(CIJENA, 2.38);
		cv6.put(LABEL, "Kupus");
		cv6.put(DD, 28); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Kupus svježi klasa II 1kg"); cv6.put(SLIKA, R.drawable.kupus); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 3); cv6.put(POP, 19); cv6.put(CIJENA, 8.90);
		cv6.put(LABEL, "Kruška");
		cv6.put(DD, 28); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Kruška limonera 1 kg"); cv6.put(SLIKA, R.drawable.kruska); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 4); cv6.put(POP, 10); cv6.put(CIJENA, 8.99);
		cv6.put(LABEL, "Pepsi");
		cv6.put(DD, 16); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Pepsi Twist 2l bezalkoholno piće"); cv6.put(SLIKA, R.drawable.pepsi); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 5); cv6.put(POP, 15); cv6.put(CIJENA, 3.90);
		cv6.put(LABEL, "Majoneza");
		cv6.put(DD, 16); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Zvijezda majoneza 90 g"); cv6.put(SLIKA, R.drawable.majoneza); cv6.put(FAV, 0);
		db.insert("akcije", null, cv6);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db2, int oldVersion, int newVersion) {
		android.util.Log.w("akcija", "Upgrading database, which will destroy all old data");
		db2.execSQL("DROP TABLE IF EXISTS score");
		onCreate(db2);
	}
	
	public void updatemaps() {
		
		
		}
}
