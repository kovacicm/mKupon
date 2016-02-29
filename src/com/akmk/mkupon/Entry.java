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

public class Entry {

	  private int IDAkcija;
	  private int KatAkcija;
	  private int PopustAkcija;
	  private double CijenaAkcija;
	  private String LabelAkcija;
	  private Calendar RokAkcija;
	  private String OpisAkcija;
	  private int SlikaAkcija;
	  private int Favorit;


	  public Entry(int IDAkcija, int KatAkcija, int PopustAkcija, double CijenaAkcija, String LabelAkcija, Calendar RokAkcija, String OpisAkcija, int SlikaAkcija, int Favorit) {
	    this.IDAkcija = IDAkcija;
	    this.KatAkcija = KatAkcija;
	    this.PopustAkcija=PopustAkcija;
	    this.CijenaAkcija=CijenaAkcija;
	    this.LabelAkcija=LabelAkcija;
	    this.RokAkcija=RokAkcija;
	    this.OpisAkcija=OpisAkcija;
	    this.SlikaAkcija=SlikaAkcija;
	    this.Favorit=Favorit;
	  }
	  
	  //getters

	  public Integer getID() {
		  	return this.IDAkcija;
	  }
	  
	  public Integer getKat() {
		  	return this.KatAkcija;
	  }

	  public Integer getPop() {
		return this.PopustAkcija;
	  }
	  
	  public Double getCijena() {
		    return this.CijenaAkcija;
	  }
	  
	  public String getLabel() {
		    return this.LabelAkcija;
	  }
	  
	  public Calendar getRok() {
		    return this.RokAkcija;
	  }
	  
	  public String getOpis() {
		    return this.OpisAkcija;
	  }
	  
	  public Integer getSlika() {
			return this.SlikaAkcija;
	  }
	  
	  public int getFavorit() {
			return this.Favorit;
	  }
	  
	  //setters
	  
	  public void setID(Integer ID) {
		  	this.IDAkcija=ID;
	  }
	  
	  public  void setKat(Integer Kat) {
		  	this.KatAkcija=Kat;
	  }

	  public void setPop(Integer Pop) {
		  	this.PopustAkcija=Pop;
	  }
	  
	  public void setCijena(Double Cijena) {
		    this.CijenaAkcija=Cijena;
	  }
	  
	  public void setLabel(String Label) {
		    this.LabelAkcija=Label;
	  }
	  
	  public void setRok(Calendar Rok) {
		    this.RokAkcija=Rok;
	  }
	  
	  public void setOpis(String Opis) {
		    this.OpisAkcija=Opis;
	  }
	  
	  public void setSlika(Integer Slika) {
			this.SlikaAkcija = Slika;
	  }
	  
	  public void setFavorit(int Favorit) {
			this.Favorit=Favorit;
	  }
	  
	  
	}