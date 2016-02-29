package com.akmk.mkupon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.ViewFlipper;

public class Barkod extends Activity {
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	private String sUserName;
	private String sPassword;
	private Intent mkupon, novikod, info, arhiva, postavke, izlaz, pocetna;
	private ScrollView scroll;
	
	public static final int MKUPON = Menu.FIRST+1;
	public static final int ARHIVAKUPNJI = Menu.FIRST+2;
	public static final int INFO = Menu.FIRST+3;	
	public static final int ODJAVA = Menu.FIRST+4;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barkod);        
        
        Button Ponisti = (Button)findViewById(R.id.ponisti);
        
        scroll=(ScrollView)findViewById(R.id.scroll);
 	   scroll.setBackgroundResource(R.drawable.bg_kupon);
        
        mkupon=new Intent(this, Mkupon.class);
        info=new Intent(this, ikonzum.class);
        arhiva=new Intent(this, ArhivaKupnji.class);
        izlaz=new Intent(this, Login.class);
        pocetna=new Intent(this, Pocetna.class);
        
        Ponisti.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){                       
				Aktivacija();
			}
        } 
        );        
        
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
		
		MenuItem menu1=menu.add(Menu.NONE, MKUPON, Menu.NONE, "Mkupon");
		menu1.setIcon(R.drawable.ic_menu_mark);
		MenuItem menu2=menu.add(Menu.NONE, ARHIVAKUPNJI, Menu.NONE, "Arhiva kupnji");
		menu2.setIcon(R.drawable.ic_menu_archive);
		MenuItem menu3=menu.add(Menu.NONE, INFO, Menu.NONE, "Info/Promo");
		menu3.setIcon(R.drawable.ic_menu_info_details);							
		MenuItem menu6=menu.add(Menu.NONE, ODJAVA, Menu.NONE, "Odjava");
		menu6.setIcon(R.drawable.ic_menu_exit);
	}
	
	private boolean applyMenuChoice(MenuItem item) {
		switch (item.getItemId()) {
		
		case MKUPON:
			startActivityForResult(mkupon,0);
		return(true);
		
		
		case ARHIVAKUPNJI:
		startActivityForResult(arhiva,0);					
		return(true);
			
		case INFO:
			startActivityForResult(info,0);	
		return(true);
			
		case ODJAVA:
			startActivityForResult(izlaz,0);
		//getListView().setDividerHeight(32);
		return(true);
		
		}
		return(false);
		}
     
	private void Aktivacija(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Aktivacija popusta");
		alert.setMessage("Želite li stvarno iskoristiti kod?");
		alert.setPositiveButton("Iskoristi", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				UspjesnaAktivacija();
			}
		});

		alert.setNegativeButton("Odustani",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		alert.show();
        
	}
	
	private void UspjesnaAktivacija(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Aktivacija uspijela!");
		alert.setMessage("Popust je uspješno ostvaren");
		alert.setNeutralButton("Zatvori", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				startActivityForResult(pocetna,0);	
			}
		});
		alert.show();
	}
}