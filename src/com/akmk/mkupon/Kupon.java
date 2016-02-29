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

public class Kupon {

	  private int IDkupon;
	  private int tipkupon;
	  private int nnalj;
	  private int maxnalj;
	  private int aktivan;
	  private String otvoren;
	  private String vridido;


	  public Kupon(int IDkupnja, int tipkupon, int nnalj, int maxnalj, int aktivan, String otvoren, String vridido) {
	    
		this.IDkupon=IDkupon;  
		this.tipkupon = tipkupon;
	    this.nnalj = nnalj;
	    this.maxnalj=maxnalj;
	    this.aktivan=aktivan;
	    this.otvoren=otvoren;
	    this.vridido=vridido;
	  }
	  
	  //getters

	  public Integer getTip() {
		  	return this.tipkupon;
	  }
	  
	  public Integer getNnalj() {
		  	return this.nnalj;
	  }

	  public Integer getMaxnalj() {
		return this.maxnalj;
	  }
	  
	  public Integer getAktivan() {
		    return this.aktivan;
	  }
	  
	  public String getOtvoren() {
		  	return this.otvoren;
	  }
	  
	  public String getVridido() {
		  	return this.vridido;
	  }
	   
	  
	  //setters
	  
	  public void setTip(Integer tip) {
		  	this.tipkupon=tip;
	  }
	  
	  public  void setNnalj(Integer nnalj) {
		  	this.nnalj=nnalj;
	  }

	  public void setMaxnalj(Integer maxnalj) {
		  	this.maxnalj=maxnalj;
	  }
	  
	  public void setAktivna(Integer aktivan) {
		  	this.aktivan=aktivan;
	  }
	  
	  public void setOtvoren(String otv) {
		    this.otvoren=otv;
	  }
	  
	  public  void setVridi(String vridi) {
		  	this.vridido=vridi;
	  }
	    
	  
	  
	}