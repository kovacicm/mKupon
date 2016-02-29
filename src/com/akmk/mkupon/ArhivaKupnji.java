package com.akmk.mkupon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ArhivaKupnji extends ListActivity  {
    /** Called when the activity is first created. */
	
	private Calendar datumkupnje;
	private Calendar pocetak, kraj;
	private TextView label, popust, cijena, opis;
	private ImageView icon, akcija;
	private Intent kupon, arhivakupona, profil, postavke, aktivni,izlaz;
	private Integer movedir, listcount;
	private List<Kupnja> EntriesList = new ArrayList<Kupnja>();
	private List<Kupnja> EntriesListTemp = new ArrayList<Kupnja>();
	private Kupnja [] EntryArray = new Kupnja [12];
	private SQLiteDatabase mapdb=null;
	private Cursor kupnjacursor=null;
	private Cursor adresacursor=null;
	private String tempSQL, adresa;
	private int mYear, m2Year;
    private int mMonth, m2Month;
    private int mDay, m2Day;
	
	
	public static final int KUPON = Menu.FIRST+1;
	public static final int SORTIRAJ = Menu.FIRST+2;
	public static final int STATISTIKA = Menu.FIRST+3;
	public static final int INTERVAL = Menu.FIRST+4;
//	public static final int POCETNI = Menu.FIRST+5;
//	public static final int ZAVRSNI = Menu.FIRST+6;
	public static final int POSTAVKE = Menu.FIRST+5;
	public static final int IZLAZ = Menu.FIRST+6;
	public static final int DATE_DIALOG_ID = 0;
	
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		int i;
		
		super.onCreate(savedInstanceState);        
        setContentView(R.layout.arhivakupnji);
        
        
        
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
              
        kupon=new Intent(this, Mkupon.class); 
        arhivakupona=new Intent(this, Mkupon.class);
        postavke=new Intent(this, Mkupon.class);
        profil=new Intent(this, Mkupon.class);
        aktivni=new Intent(this, Mkupon.class);
        izlaz=new Intent(this, Login.class);
        
        registerForContextMenu(getListView());
        
        mapdb=(new MapDatabase(this)).getWritableDatabase();
        kupnjacursor= mapdb.rawQuery("SELECT * "+
    			"FROM kupnja",
    			null);
        kupnjacursor.moveToFirst();
        listcount=kupnjacursor.getCount();
        
//        final Calendar c = Calendar.getInstance();
//        mYear = c.get(Calendar.YEAR);
//        mMonth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);
//        
////        SetPocetak();
//        
//        final Calendar c2 = Calendar.getInstance();
//        m2Year = c2.get(Calendar.YEAR);
//        m2Month = c2.get(Calendar.MONTH-6);
//        m2Day = c2.get(Calendar.DAY_OF_MONTH);
//        
////        SetKraj();
        
        for(i=0; i<kupnjacursor.getCount(); i++){ 
        	
        	datumkupnje=Calendar.getInstance();
        	datumkupnje.set(Calendar.DAY_OF_MONTH, kupnjacursor.getInt(2));
        	datumkupnje.set(Calendar.MONTH, kupnjacursor.getInt(3));
        	datumkupnje.set(Calendar.YEAR, kupnjacursor.getInt(4));
        	
        	tempSQL="SELECT * FROM prodavaonice where _id= "+String.valueOf(kupnjacursor.getInt(6));
            adresacursor= mapdb.rawQuery(tempSQL,null);
            adresacursor.moveToFirst();
            adresa=adresacursor.getString(2)+", "+adresacursor.getString(3);
        	
        	EntryArray[i]=new Kupnja(kupnjacursor.getInt(0), kupnjacursor.getInt(1), datumkupnje, kupnjacursor.getDouble(5),adresa);
        	EntriesList.add(EntryArray[i]);
        	
 //       	if(datumkupnje.after(pocetak)&&datumkupnje.before(kraj)){
        	EntriesListTemp.add(EntryArray[i]);
 //       	}
        	kupnjacursor.moveToNext();
        	
        	
        }
        
        FilterList();
        setListAdapter(new IconicAdapter());
        
    }
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DATE_DIALOG_ID:
	        return new DatePickerDialog(this,
	                    mDateSetListener,
	                    mYear, mMonth, mDay);
	    }
	    return null;
	}
	
	@Override
	public void onResume() {
	super.onResume();

	SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
    setListAdapter(new IconicAdapter());
    
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
	
	
	
	public void sort(final String field, List<Kupnja> itemLocationList) {
	    Collections.sort(itemLocationList, new Comparator<Kupnja>() {
	        @Override
	        public int compare(Kupnja o1, Kupnja o2) {
	            if(field.equals("cijena")) {
	                return o1.getIznos().compareTo(o2.getIznos());
	            } if(field.equals("adresa")) {
	                return o1.getAdresa().compareTo(o2.getAdresa());
	            } if(field.equals("datum")) {
	                return o1.getDatum().compareTo(o2.getDatum());
	            } 	            
	            else {
	            	return 0;
	            }
	        }           
	    });
	    
	//    Collections.reverse(itemLocationList);
	}
	
	private void FilterList(){
		
		int position, maxpos;
		
		maxpos=listcount;
		
//		for (position=0; position<maxpos; position++){
//			
//			if(maxpos>0){	
//				
//			}
//			
//			else{
//				
//				EntriesListTemp.remove(position);
//				maxpos=maxpos-1;
//				position=position-1;
//			}
//
//		}
	}
	
	private void setPocetak(){
		
		
	}
	
	private void setKraj(){
		
	}
	
	private void FillList(){
		
		int position;
		
		EntriesListTemp.clear();
		
		for (position=0; position<kupnjacursor.getCount(); position++){			
			EntriesListTemp.add(EntriesList.get(position));
		}
		
		listcount=12;
		
	}
	
	private void populateMenu(Menu menu) {
		MenuItem menu1= menu.add(Menu.NONE, KUPON, Menu.NONE, "Kupon");
		menu1.setIcon(R.drawable.ic_menu_mark);
		MenuItem menu2=menu.add(Menu.NONE, SORTIRAJ, Menu.NONE, "Sortiraj");
		menu2.setIcon(R.drawable.ic_menu_sort_alphabetically);
		MenuItem menu3=menu.add(Menu.NONE, STATISTIKA, Menu.NONE, "Statistika");
		menu3.setIcon(R.drawable.ic_menu_archive);
		MenuItem menu4=menu.add(Menu.NONE, INTERVAL, Menu.NONE, "Interval");
		menu4.setIcon(R.drawable.ic_menu_agenda);							
		MenuItem menu5=menu.add(Menu.NONE, POSTAVKE, Menu.NONE, "Postavke");
		menu5.setIcon(R.drawable.ic_menu_manage);
		MenuItem menu6=menu.add(Menu.NONE, IZLAZ, Menu.NONE, "Izlaz");
		menu6.setIcon(R.drawable.ic_menu_exit);
	}
	
	private boolean applyMenuChoice(MenuItem item) {
		switch (item.getItemId()) {
		case KUPON:
		startActivity(kupon);
		return(true);
		
		case SORTIRAJ:
			CharSequence [] maps={"Iznosu računa", "Adresi prodavaonice", "Datumu kupnje"};
			movedir=0;
			
			 new AlertDialog.Builder(this)
				.setTitle("Sortiraj prema")
				.setSingleChoiceItems(maps,movedir, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dlg, int sumthin) {
						
						movedir=sumthin;

						}	
				})
				.setNegativeButton("Poništi", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
	
				}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
					
					
					if(movedir==0){//cijeni
						
						sort("cijena", EntriesListTemp);
						setListAdapter(new IconicAdapter());
						
					}
					if(movedir==1){//popustu
						
						
						sort("adresa", EntriesListTemp);
						setListAdapter(new IconicAdapter());
						
					}
					if(movedir==2){//datumu
						
						sort("datum", EntriesListTemp);
						setListAdapter(new IconicAdapter());
						
					}
					    			
					
				}
				})
				.show();
			return(true);
			
		case STATISTIKA:
			startActivity(kupon);
			return(true);	
			
		case INTERVAL:
			showDialog(DATE_DIALOG_ID);			
		return(true);
		
		case POSTAVKE:
		startActivity(postavke);
		return(true);
			
		case IZLAZ:
			startActivity(izlaz);
		return(true);
		
		}
		return(false);
		}
		
	
	public void onListItemClick(ListView parent, View v,
			int position, long id) {
		
		}
	
	class IconicAdapter extends ArrayAdapter {
		IconicAdapter() {
			
		super(ArhivaKupnji.this, R.layout.row, EntriesListTemp);
		}
		public View getView(int position, View convertView,
		ViewGroup parent) {
				
		LayoutInflater inflater=getLayoutInflater();
		View row=inflater.inflate(R.layout.row2, parent, false);		
			
		TextView label=(TextView)row.findViewById(R.id.kodracuna);
		label.setText("Kod računa: "+Integer.toString(EntriesListTemp.get(position).getKod()));
		
		TextView opis=(TextView)row.findViewById(R.id.datumkupnje);
		opis.setText("Datum kupnje: "+Integer.toString(EntriesListTemp.get(position).getDatum().get(Calendar.DAY_OF_MONTH))+"."+Integer.toString(EntriesListTemp.get(position).getDatum().get(Calendar.MONTH))+"."+Integer.toString(EntriesListTemp.get(position).getDatum().get(Calendar.YEAR))+".");
		
		TextView rok=(TextView)row.findViewById(R.id.adresa);
		
		rok.setText(EntriesListTemp.get(position).getAdresa());
		
		TextView cijena=(TextView)row.findViewById(R.id.cijena);
		cijena.setText(Double.toString(EntriesListTemp.get(position).getIznos())+" kn");
		

		return(row);
			
		
//		TextView popust=(TextView)row.findViewById(R.id.popust);
//		popust.setText(PopustAkcija[position]);
		
		
		
//		ImageView akcija=(ImageView)findViewById(R.id.akcija);
//		akcija.setImageResource(R.drawable.akcija);
		
			
//		return(row);
		
		
		
		}
		}

	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, 
                                  int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
            }
        };
		
}



