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
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ikonzum extends ListActivity  {
    /** Called when the activity is first created. */
	
	private DisplayMetrics dm;
	private Calendar tempD;
	private TextView label, popust, cijena, opis;
	private boolean  [] kategorija = {true, true, true, true, true, true};
	private boolean [] kat = {true, true, true, true, true, true};
	private ImageView icon, akcija;
	private Intent mkupon, arhiva, postavke, izlaz;
	private Integer movedir, listcount;
	private List<Entry> EntriesList = new ArrayList<Entry>();
	private List<Entry> EntriesListTemp = new ArrayList<Entry>();
	private Entry [] EntryArray = new Entry [12];
	private SQLiteDatabase mapdb=null;
	private Cursor mapscursor=null;
	private ImageView refresh;
	
	public static final int MKUPON = Menu.FIRST+1;
	public static final int SORTER = Menu.FIRST+2;
	public static final int FILTER = Menu.FIRST+3;
	public static final int ARHIVA = Menu.FIRST+4;	
	public static final int PROFIL = Menu.FIRST+5;
	public static final int IZLAZ = Menu.FIRST+6;
			
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		int i;
		
		super.onCreate(savedInstanceState);        
        setContentView(R.layout.info);
        
        listcount=12;
        
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        
        kategorija[0]=prefs.getBoolean("meso", true);
        kategorija[1]=prefs.getBoolean("smrznuto", true);
        kategorija[2]=prefs.getBoolean("voce", true);
        kategorija[3]=prefs.getBoolean("pica", true);
        kategorija[4]=prefs.getBoolean("mlijecni", true);
        
        refresh=(ImageView)findViewById(R.id.refresh);
        refresh.setBackgroundResource(R.drawable.refresh);
        refresh.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				
				
				Context context = getApplicationContext();
				CharSequence text = "Dohvaćam nove akcije. Pričekajte...";
				int duration = Toast.LENGTH_LONG;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			
			}
		});
        
        for (i=0;i<5;i++){
        	kat[i]=kategorija[i];
        }
        
        mkupon=new Intent(this, Mkupon.class); 
        arhiva=new Intent(this, ArhivaKupnji.class);
        postavke=new Intent(this, Postavke.class);
        izlaz=new Intent(this, Login.class);
        
        registerForContextMenu(getListView());
        
        mapdb=(new MapDatabase(this)).getWritableDatabase();
        mapscursor= mapdb.rawQuery("SELECT * "+
    			"FROM akcije",
    			null);
        mapscursor.moveToFirst();
        
        for(i=0; i<12; i++){ 
        	tempD=Calendar.getInstance();
        	tempD.set(Calendar.DAY_OF_MONTH, mapscursor.getInt(5));
        	tempD.set(Calendar.MONTH, mapscursor.getInt(6));
        	tempD.set(Calendar.YEAR, mapscursor.getInt(7));
        	EntryArray[i]=new Entry(mapscursor.getInt(0), mapscursor.getInt(1), mapscursor.getInt(2), mapscursor.getDouble(3), mapscursor.getString(4), tempD, mapscursor.getString(8), mapscursor.getInt(9), mapscursor.getInt(10));
        	EntriesList.add(EntryArray[i]);
        	EntriesListTemp.add(EntryArray[i]);
        	mapscursor.moveToNext();
        }

        FilterList();
        setListAdapter(new IconicAdapter());
        
        
        
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
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
	super.onActivityResult(requestCode, resultCode, data); 
	if (resultCode == 123) { 
	Intent in = new Intent();
	setResult(123,in);	
	this.finish(); 
	} 

	} 
	
	public void sort(final String field, List<Entry> itemLocationList) {
	    Collections.sort(itemLocationList, new Comparator<Entry>() {
	        @Override
	        public int compare(Entry o1, Entry o2) {
	            if(field.equals("naslov")) {
	                return o1.getLabel().compareTo(o2.getLabel());
	            } if(field.equals("cijena")) {
	                return o1.getCijena().compareTo(o2.getCijena());
	            } else if(field.equals("popust")) {
	                return o1.getPop().compareTo(o2.getPop());
	            } 
	            else if(field.equals("datum")) {
	                return o1.getRok().compareTo(o2.getRok());
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
		
		for (position=0; position<maxpos; position++){
			
//			if(((!favorit)||(favorit&&EntriesListTemp.get(position).getFavorit()))
//				 
//				&&	kat[EntriesListTemp.get(position).getKat()-1]){	
//							
//			}
			if(kat[EntriesListTemp.get(position).getKat()-1]){
				
			}
			
			else{
				
				EntriesListTemp.remove(position);
				maxpos=maxpos-1;
				position=position-1;
			}

		}
	}
	
	private void FillList(){
		
		int position;
		
		EntriesListTemp.clear();
		
		for (position=0; position<12; position++){			
			EntriesListTemp.add(EntriesList.get(position));
		}
		
		listcount=12;
		
	}
	
	private void populateMenu(Menu menu) {
		MenuItem menu1= menu.add(Menu.NONE, MKUPON, Menu.NONE, "mKupon");
		menu1.setIcon(R.drawable.ic_menu_mark);
		MenuItem menu2=menu.add(Menu.NONE, SORTER, Menu.NONE, "Sortiraj");
		menu2.setIcon(R.drawable.ic_menu_sort_alphabetically);
		MenuItem menu3=menu.add(Menu.NONE, FILTER, Menu.NONE, "Filtriraj");
		menu3.setIcon(R.drawable.ic_menu_agenda);
		MenuItem menu4=menu.add(Menu.NONE, ARHIVA, Menu.NONE, "Arhiva kupnji");
		menu4.setIcon(R.drawable.ic_menu_archive);							
		MenuItem menu5=menu.add(Menu.NONE, PROFIL, Menu.NONE, "Profil");
		menu5.setIcon(R.drawable.ic_menu_edit);
		MenuItem menu6=menu.add(Menu.NONE, IZLAZ, Menu.NONE, "Izlaz");
		menu6.setIcon(R.drawable.ic_menu_exit);
	}
	
	private boolean applyMenuChoice(MenuItem item) {
		switch (item.getItemId()) {
		case MKUPON:
		startActivityForResult(mkupon,0);
		return(true);
			
		case SORTER:				
			
			CharSequence [] maps={"Abecedi", "Cijeni", "Popustu", "Datumu"};
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
					
					
					if(movedir==0){//abecedi
						
						sort("naslov", EntriesListTemp);
						setListAdapter(new IconicAdapter());

					}
					if(movedir==1){//cijeni
						
						sort("cijena", EntriesListTemp);
						setListAdapter(new IconicAdapter());
						
					}
					if(movedir==2){//popustu
						
						
						sort("popust", EntriesListTemp);
						setListAdapter(new IconicAdapter());
						
					}
					if(movedir==3){//datumu
						
						sort("datum", EntriesListTemp);
						setListAdapter(new IconicAdapter());
						
					}
					    			
					
				}
				})
				.show();

		return(true);
		
		case FILTER:
			
			CharSequence [] maps2={"Meso", "Smrznuto","Voće i povrće", "Pića", "Mliječni proizvodi"};
			
			 new AlertDialog.Builder(this)
				.setTitle("Filtriraj po kategoriji")
				.setMultiChoiceItems(maps2,kat, new DialogInterface.OnMultiChoiceClickListener(){
					public void onClick(DialogInterface dlg, int sumthin, boolean value) {
						
						kat[sumthin]=value;
						
						}	
				})
				.setNegativeButton("Poništi", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
	
				}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
						
						FillList();
						FilterList();
						setListAdapter(new IconicAdapter());					    			
					
				}
				})
				.show();
				
		return(true);
			
		case ARHIVA:
		startActivityForResult(arhiva,0);
		return(true);
		
		case PROFIL:
		startActivityForResult(arhiva,0);
		return(true);
			
		case IZLAZ:
			
		return(true);
		
		}
		return(false);
		}
		
	
	public void onListItemClick(ListView parent, View v,
			int position, long id) {
		
			movedir=position;
			
			if(EntriesList.get(movedir).getFavorit()==1){
				
				new AlertDialog.Builder(this)
				.setTitle("Ukloni iz favorita?")
				
				.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {

				}
				})
				.setPositiveButton("Da", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
					    
					//EntriesList.get(movedir).setFavorit(0);
					UpdateData(movedir+1,0);
				}
				})
				.show();
				
			}
			else{
			
			new AlertDialog.Builder(this)
			.setTitle("Dodaj u favorite?")
			
			.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {

			}
			})
			.setPositiveButton("Da", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {
				    
				//EntriesList.get(movedir).setFavorit(1);
				UpdateData(movedir+1,1);
				
			}
			})
			.show();
			
			}
		
			}
	
	class IconicAdapter extends ArrayAdapter {
		IconicAdapter() {
			
		super(ikonzum.this, R.layout.row, EntriesListTemp);
		}
		public View getView(int position, View convertView,
		ViewGroup parent) {
				
		LayoutInflater inflater=getLayoutInflater();
		View row=inflater.inflate(R.layout.row, parent, false);		
			
		TextView label=(TextView)row.findViewById(R.id.label);
		label.setText(EntriesListTemp.get(position).getLabel());
		
		TextView opis=(TextView)row.findViewById(R.id.opis);
		opis.setText(EntriesListTemp.get(position).getOpis());
		
		TextView rok=(TextView)row.findViewById(R.id.rok);
		rok.setText("Vrijedi do: " + Integer.toString(EntriesListTemp.get(position).getRok().get(Calendar.DAY_OF_MONTH))+"."+Integer.toString(EntriesListTemp.get(position).getRok().get(Calendar.MONTH))+"."+Integer.toString(EntriesListTemp.get(position).getRok().get(Calendar.YEAR))+".");
		
		TextView cijena=(TextView)row.findViewById(R.id.cijena);
		cijena.setText(Double.toString(EntriesListTemp.get(position).getCijena())+" kn");
		
		ImageView icon=(ImageView)row.findViewById(R.id.icon);
		icon.setImageResource(EntriesListTemp.get(position).getSlika());
		
	//	ImageView popust=(ImageView)row.findViewById(R.id.popust);
	//	TextView izpopust=(TextView)row.findViewById(R.id.izpopust);
	//	izpopust.setText("-"+EntriesListTemp.get(position).getPop().toString()+"%");
		
		return(row);
		

		
		
		
//		TextView popust=(TextView)row.findViewById(R.id.popust);
//		popust.setText(PopustAkcija[position]);
		
		
		
//		ImageView akcija=(ImageView)findViewById(R.id.akcija);
//		akcija.setImageResource(R.drawable.akcija);
		
			
//		return(row);
		
		
		
		}
		}

	public void UpdateData(int where, int value){

		String FAV="fav";
		
		ContentValues cv=new ContentValues();
		
		cv.put(FAV, value);
		
		mapdb.update("akcije", cv, "_id="+Integer.toString(where) , null);
 }
	
		
}



