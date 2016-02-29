package com.akmk.mkupon;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.text.InputType;
import android.widget.EditText;

public class Kupnja {

	  private int IDkupnja;
	  private int kod;
	  private Calendar datum;
	  private double iznos;
	  private String adresa;

	  public Kupnja(int IDkupnja, int kod, Calendar datum, double iznos, String adresa) {
	    
		this.IDkupnja=IDkupnja;  
		this.kod = kod;
	    this.datum=datum;
	    this.iznos=iznos;
	    this.adresa=adresa;
	  }
	  
	  //getters

	  public Integer getKod() {
		  	return this.kod;
	  }
	  
	  public Calendar getDatum() {
		  	return this.datum;
	  }
	  
	  public Double getIznos() {
		  	return this.iznos;
	  }
	  
	  public String getAdresa() {
		  	return this.adresa;
	  }
	   
	  
	  //setters
	  
	  public void setKod(Integer kod) {
		  	this.kod=kod;
	  }
	  
	  public  void setDatum(Calendar datum) {
		  	this.datum=datum;
	  }
	  
	  public void setIznos(Double Cijena) {
		    this.iznos=Cijena;
	  }
	  
	  public  void setAdresa(String adresa) {
		  	this.adresa=adresa;
	  }
	    
	  
	  
	}