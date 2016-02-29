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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

public class Pocetna extends Activity {
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	private String sUserName;
	private String sPassword;
	private Intent mkupon, novikod, info, arhiva, postavke, izlaz;
	private ScrollView scroll;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocetna);        
        
        Button NoviKod = (Button)findViewById(R.id.novikod);
 //       Button Kupon = (Button)findViewById(R.id.mkupon);
        Button Info = (Button)findViewById(R.id.info);
 //       Button Arhiva = (Button)findViewById(R.id.arhiva);
        Button Postavke = (Button)findViewById(R.id.postavke);
        Button Odjava = (Button)findViewById(R.id.izlaz);
        
        scroll=(ScrollView)findViewById(R.id.scroll);
 	   scroll.setBackgroundResource(R.drawable.bg_new1);
        
        mkupon=new Intent(this, Mkupon.class);
        novikod=new Intent(this, Barkod.class);
        info=new Intent(this, ikonzum.class);
        arhiva=new Intent(this, ArhivaKupnji.class);
        postavke=new Intent(this, Postavke.class);
        izlaz=new Intent(this, Login.class);
        
        NoviKod.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){                       
                startActivity(novikod);
                }  
        } 
        );
        
        
 /*       Kupon.setOnClickListener( new OnClickListener()
                {
        			public void onClick(View viewParam){                       
                        startActivity(mkupon);
                        }  
                } 
        ); */
        
        Info.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){                       
                startActivity(info);
                }  
        } 
); 
       
  /*      Arhiva.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){                       
                startActivity(arhiva);
                }  
        } 
        ); */
        
        Postavke.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){                       
                startActivity(postavke);
                }  
        } 
        ); 
        
        Odjava.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){                       
                startActivity(izlaz);
                }  
        } 
        ); 
        
        
    }
    
    
}