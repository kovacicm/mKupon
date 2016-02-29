package com.akmk.mkupon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class Mkupon extends Activity  {
    /** Called when the activity is first created. */

	private TableRow  [] tablerows = new TableRow [10];	
	private Intent postavke, arhivakupnji, info, profil, barkod, izlaz, aktivni;
	private SQLiteDatabase mapdb=null;
	private Cursor kupcursor=null;
	private Cursor adcursor=null;
	private Cursor ncursor=null;
	private Cursor kupnjacursor=null;
	private String tempSQL;
	private Dialog dialog;
	private Integer dodaj;
	
	
	public static final int NOVIKOD = Menu.FIRST+1;
	public static final int RUCNO = Menu.FIRST+2;
	public static final int BARKOD = Menu.FIRST+3;
	public static final int AKTIVNI = Menu.FIRST+4;
	public static final int ARHIVAKUPNJI = Menu.FIRST+5;
	public static final int INFO = Menu.FIRST+6;	
	public static final int PROFIL = Menu.FIRST+7;
	public static final int ODJAVA = Menu.FIRST+8;
	
	
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		int i;
		
		super.onCreate(savedInstanceState);        
        setContentView(R.layout.mkupon);
        
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        
        postavke=new Intent(this, Postavke.class);
        arhivakupnji=new Intent(this, ArhivaKupnji.class);
        info=new Intent(this, ikonzum.class);
        profil=new Intent(this, Postavke.class);
        izlaz=new Intent(this, Login.class);
        aktivni=new Intent(this, Barkod.class);
        
        dodaj=0;
  //      barkod =new Intent(this, CaptureActivity.class);
        
        
        mapdb=(new MapDatabase(this)).getWritableDatabase();
        kupcursor= mapdb.rawQuery("SELECT * "+
    			"FROM kuponi where aktivan= 1",
    			null);
        kupcursor.moveToFirst();

        tempSQL="SELECT * FROM naljepnica where kuponid= "+String.valueOf(kupcursor.getInt(1));
        ncursor= mapdb.rawQuery(tempSQL,
    			null);
 //       ncursor= mapdb.rawQuery("SELECT * "+
 //   			"FROM naljepnica where kuponid= 10345",
 //   			null);
        ncursor.moveToFirst();
        
        ((TextView)findViewById(R.id.ksifra)).setText("Šifra kupona: "+String.valueOf(kupcursor.getInt(1)));
        
        if(kupcursor.getInt(5)==1){
        	((TextView)findViewById(R.id.kstatus)).setText("Status kupona: AKTIVAN");
        }
        
        ((TextView)findViewById(R.id.kotvoren)).setText("Otvoren: "+kupcursor.getString(7));
        ((TextView)findViewById(R.id.kvridi)).setText("Vrijedi do: "+kupcursor.getString(8));
        
        if(kupcursor.getInt(2)==10){
        	((TextView)findViewById(R.id.ktip)).setText("Tip kupona: Popust 20%");
        }
        
        ((TextView)findViewById(R.id.kpop)).setText("Popunjenost: " + String.valueOf(kupcursor.getInt(3))+"/"+String.valueOf(kupcursor.getInt(4)));
        	
        PopuniKupon();
        
        
   }  
        	
	
	private void PopuniKupon(){
		
		int i;
		int sizey=4;  
        int sizex=5;  
        int btnwidth=60;  
        TableLayout tl = (TableLayout)findViewById(R.id.myTableLayout); 
        tl.setClipToPadding(true);
        
        tempSQL="SELECT * FROM naljepnica where kuponid= "+String.valueOf(kupcursor.getInt(1));
        ncursor= mapdb.rawQuery(tempSQL,
    			null);
 //       ncursor= mapdb.rawQuery("SELECT * "+
 //   			"FROM naljepnica where kuponid= 10345",
 //   			null);
        ncursor.moveToFirst();
        
          
        for (int y=0;y<sizey;y++){  
          
        // Rows  
        TableRow tr = new TableRow(this);   
        tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));  
        tr.setPadding(0, 5, 0, 5);
          
        
        //Cells  
        for (int x=0;x<sizex;x++){  
        // Create new cell  
            // new button  
            ImageView b = new ImageView(this);    
            b.setLayoutParams(new LayoutParams(   
                LayoutParams.WRAP_CONTENT,   
                LayoutParams.WRAP_CONTENT));
            
            if(y*4+x<ncursor.getCount()+dodaj){
            b.setBackgroundResource(R.drawable.puno60);
            }
            else{
            	b.setBackgroundResource(R.drawable.praznox);
            }
            b.setPadding(10, 10, 10, 10);
            b.setTag(Integer.toString(y*4+x));
            tr.addView(b); 
 
            }  
        // add row to layout  
        tl.addView(tr,new TableLayout.LayoutParams(   
        LayoutParams.WRAP_CONTENT,   
        LayoutParams.WRAP_CONTENT));  
        }  
        
        
        int count = tl.getChildCount();
        for(i = 0; i < count; i++){
            View v = tl.getChildAt(i);
            if(v instanceof TableRow){
                TableRow row = (TableRow)v;
                int rowCount = row.getChildCount();
                for (int r = 0; r < rowCount; r++){
                    View v2 = row.getChildAt(r);
                    if (v2 instanceof ImageView){
                    	
                    	ImageView b1 = (ImageView)v2;
                    	b1.setOnClickListener(new OnClickListener(){
                			public void onClick(View v){
                				 
                				ImageView clickedView = (ImageView)v;
                	             String t = (String) clickedView.getTag();
                	             InfoKupon(t);
                	             

            
                			}
                		});
                    }
                }
            }
        }
        
	}
	private void InfoKupon(String tag){
		
		 Integer position;
		 String query;
		 int p,q,r;
		Context mContext = this;
        dialog = new Dialog(mContext);
        
       

        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Detalji naljepnice");
        
       
        
        position=Integer.valueOf(tag);
        
        if(position<ncursor.getCount()){
        ncursor.moveToPosition(position);
        tempSQL="SELECT * FROM kupnja where _id= "+String.valueOf(ncursor.getInt(1));
    	kupnjacursor= mapdb.rawQuery(tempSQL,
    			null);
        kupnjacursor.moveToFirst();
        
        query="SELECT * FROM prodavaonice where _id= "+String.valueOf(kupnjacursor.getInt(6));        
        adcursor= mapdb.rawQuery(query,
    			null);
        adcursor.moveToFirst();

        TextView text = (TextView) dialog.findViewById(R.id.kodracuna);
        text.setText("Kod računa: "+String.valueOf(kupnjacursor.getInt(1))); 
        TextView text2 = (TextView) dialog.findViewById(R.id.iznosracuna);
        text2.setText("Iznos računa: " + String.valueOf(kupnjacursor.getDouble(5)));
        TextView text3 = (TextView) dialog.findViewById(R.id.adresa);
        text3.setText("Adresa prodajnog mjesta: "+ adcursor.getString(2));
        TextView text4 = (TextView) dialog.findViewById(R.id.datum);
        text4.setText("Datum kupovine: "+ String.valueOf(kupnjacursor.getInt(2))+"."+String.valueOf(kupnjacursor.getInt(3))+"."+"20"+String.valueOf(kupnjacursor.getInt(4))+".");
      
        p=kupnjacursor.getInt(2);
        q=kupnjacursor.getInt(3);
        r=kupnjacursor.getInt(4);
        
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        ImageView image2 = (ImageView) dialog.findViewById(R.id.divider);
        Button Zatvori = (Button) dialog.findViewById(R.id.zatvori);
        
        Zatvori.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				 
				dialog.dismiss();
	             
			}
		});
        
    	dialog.show();
        }
    }
	
	@Override
	public void onResume() {
	super.onResume();

	SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
    
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	ContextMenu.ContextMenuInfo menuInfo) {
	populateMenu(menu);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	populateMenu(menu);
	return(super.onCreateOptionsMenu(menu));
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		populateMenu(menu);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	return(applyMenuChoice(item) ||
	super.onOptionsItemSelected(item));
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	return(applyMenuChoice(item) ||
	super.onContextItemSelected(item));
	}
	
	private void populateMenu(Menu menu) {
		
		//SubMenu menu1= (SubMenu) menu.add(Menu.NONE, NOVIKOD, Menu.NONE, "Novi kod");
		
		SubMenu fileMenu = menu.addSubMenu("Novi kod");
		fileMenu.setIcon(R.drawable.ic_menu_barcode);
		fileMenu.add(NOVIKOD, RUCNO, 0, "Ručno");
		fileMenu.add(NOVIKOD, BARKOD, 1, "Barkod skener");		
		
	//	menu1.setIcon(R.drawable.ic_menu_barcode);
		MenuItem menu2=menu.add(Menu.NONE, AKTIVNI, Menu.NONE, "Aktivni kuponi");
		menu2.setIcon(R.drawable.ic_menu_mark);
		MenuItem menu3=menu.add(Menu.NONE, ARHIVAKUPNJI, Menu.NONE, "Arhiva kupnji");
		menu3.setIcon(R.drawable.ic_menu_archive);
		MenuItem menu4=menu.add(Menu.NONE, INFO, Menu.NONE, "Info/Promo");
		menu4.setIcon(R.drawable.ic_menu_info_details);							
		MenuItem menu5=menu.add(Menu.NONE, PROFIL, Menu.NONE, "Profil");
		menu5.setIcon(R.drawable.ic_menu_edit);
		MenuItem menu6=menu.add(Menu.NONE, ODJAVA, Menu.NONE, "Odjava");
		menu6.setIcon(R.drawable.ic_menu_exit);
	}
	
	private boolean applyMenuChoice(MenuItem item) {
		switch (item.getItemId()) {
		
		case RUCNO:			
			final AlertDialog.Builder alert = new AlertDialog.Builder(this);
			final EditText input = new EditText(this);			
			alert.setTitle("Novi kod");
			alert.setMessage("Unesite novi kod");
			alert.setView(input);
			alert.setPositiveButton("Unesi", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String value = input.getText().toString().trim();
					dodaj=dodaj+3;
					TableLayout tl = (TableLayout)findViewById(R.id.myTableLayout);
					tl.removeAllViews();
					PopuniKupon();
					//Toast.makeText(getApplicationContext(), value,
							//Toast.LENGTH_SHORT).show();
				}
			});

			alert.setNegativeButton("Odustani",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.cancel();
						}
					});
			alert.show();
			return(true);
		
		case BARKOD:
	//	startActivity(barkod);
			//getListView().setDividerHeight(1);
		return(true);
		
			
		case AKTIVNI:
			startActivityForResult(aktivni,0);
		return(true);
		
		case ARHIVAKUPNJI:
		startActivityForResult(arhivakupnji,0);					
		return(true);
			
		case INFO:
			startActivityForResult(info,0);	
		return(true);
		
		
		case PROFIL:
			//getListView().setDividerHeight(32);
		return(true);
			
		case ODJAVA:
			startActivityForResult(izlaz,0);
		return(true);
		
		}
		return(false);
		}
		
	private void DodajNaljepnice(){
		
	}

	public void UpdateData(int where, int value){

		String FAV="fav";
		
		ContentValues cv=new ContentValues();
		
		cv.put(FAV, value);
		
		mapdb.update("akcije", cv, "_id="+Integer.toString(where) , null);
 }
	
}



