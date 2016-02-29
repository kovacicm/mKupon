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

public class Postavke extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPreferenceScreen(createPreferenceHierarchy());
    }

    private PreferenceScreen createPreferenceHierarchy() {
        // Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        // Inline preferences
        PreferenceCategory inlinePrefCat = new PreferenceCategory(this);
        inlinePrefCat.setTitle("Općenite postavke");
        root.addPreference(inlinePrefCat);

        // Toggle preference
        CheckBoxPreference togglePref = new CheckBoxPreference(this);
        togglePref.setKey("pozadinski_rad");
        togglePref.setTitle("Rad u pozadini");
        togglePref.setSummary("Da li će aplikacija raditi u pozadini?");
        inlinePrefCat.addPreference(togglePref);
        
        EditTextPreference editTextP = new EditTextPreference(this);
        editTextP.setDialogTitle("Vrijeme (h)");
        editTextP.setKey("freq");
        editTextP.setTitle("Interval sinkronizacije");
        editTextP.setSummary("Vremenski interval dohvata novih akcija");
        inlinePrefCat.addPreference(editTextP);
        
//      // Launch preferences
        PreferenceCategory launchPrefCat = new PreferenceCategory(this);
        launchPrefCat.setTitle("Postavke telefona");
        root.addPreference(launchPrefCat);

        // Dialog based preferences
        PreferenceCategory dialogBasedPrefCat = new PreferenceCategory(this);
        dialogBasedPrefCat.setTitle("Postavke sortiranja");
        root.addPreference(dialogBasedPrefCat);
//
//        // Edit text preference
//        EditTextPreference editTextPref = new EditTextPreference(this);
//        editTextPref.setDialogTitle("Ograničenje popusta");
//        editTextPref.setKey("min_popust");
//        editTextPref.setTitle("Donja granica popusta");
//        editTextPref.setSummary("Minimalni iznos popusta proizvoda na akciji");
//        dialogBasedPrefCat.addPreference(editTextPref);
//        
//     // Edit text preference
//        EditTextPreference editTextPref2 = new EditTextPreference(this);
//        editTextPref2.setDialogTitle("Ograničenje cijene");
//        editTextPref2.setKey("max_cijena");
// //       editTextPref2.setInputType(InputType.TYPE_CLASS_NUMBER);
//        editTextPref2.setTitle("Gornja granica cijene");
//        editTextPref2.setSummary("Maksimalna cijena proizvoda na akciji");
//        dialogBasedPrefCat.addPreference(editTextPref2);
        

        // List preference
        
        CharSequence [] maps={"Uzlazno", "Silazno"};
        CharSequence [] maps2={"0", "1"};
        ListPreference listPref = new ListPreference(this);
        listPref.setEntries(maps);
        listPref.setEntryValues(maps2);
        listPref.setDialogTitle("Način sortiranja");
        listPref.setKey("sort");
        listPref.setTitle("Postavke sortiranja");
        listPref.setSummary("Postavke algoritma sortiranja");
        dialogBasedPrefCat.addPreference(listPref);
//
//
//        /*
//         * The Preferences screenPref serves as a screen break (similar to page
//         * break in word processing). Like for other preference types, we assign
//         * a key here so that it is able to save and restore its instance state.
//         */
//        // Screen preference
//        PreferenceScreen screenPref = getPreferenceManager().createPreferenceScreen(this);
//        screenPref.setKey("screen_preference");
//        screenPref.setTitle(R.string.title_screen_preference);
//        screenPref.setSummary(R.string.summary_screen_preference);
//        launchPrefCat.addPreference(screenPref);

        /*
         * You can add more preferences to screenPref that will be shown on the
         * next screen.
         */

//        // Example of next screen toggle preference
//        CheckBoxPreference nextScreenCheckBoxPref = new CheckBoxPreference(this);
//        nextScreenCheckBoxPref.setKey("next_screen_toggle_preference");
//        nextScreenCheckBoxPref.setTitle(R.string.title_next_screen_toggle_preference);
//        nextScreenCheckBoxPref.setSummary(R.string.summary_next_screen_toggle_preference);
//        screenPref.addPreference(nextScreenCheckBoxPref);

//        // Intent preference
          PreferenceScreen intentPref = getPreferenceManager().createPreferenceScreen(this);
          intentPref.setIntent(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
          intentPref.setTitle("Lokacijske postavke");
          intentPref.setSummary("Promijena lokacijskih postavki telefona");
          launchPrefCat.addPreference(intentPref);

        // Preference attributes
        PreferenceCategory prefAttrsCat = new PreferenceCategory(this);
        prefAttrsCat.setTitle("Kategorije");
        root.addPreference(prefAttrsCat);

        // Visual parent toggle preference
        CheckBoxPreference parentCheckBoxPref = new CheckBoxPreference(this);
        parentCheckBoxPref.setTitle("Smrznuto");
        parentCheckBoxPref.setKey("smrznuto");
        parentCheckBoxPref.setSummary("Sladoled, povrće, riba... ");
        prefAttrsCat.addPreference(parentCheckBoxPref);
        
        CheckBoxPreference parentCheckBoxPref2 = new CheckBoxPreference(this);
        parentCheckBoxPref2.setTitle("Pića");
        parentCheckBoxPref2.setKey("pica");
        parentCheckBoxPref2.setSummary("Bezalkoholna, alkoholna, žestoka...");
        prefAttrsCat.addPreference(parentCheckBoxPref2);
        
        CheckBoxPreference parentCheckBoxPref3 = new CheckBoxPreference(this);
        parentCheckBoxPref3.setTitle("Mliječni proizvodi");
        parentCheckBoxPref3.setKey("mlijecni");
        parentCheckBoxPref3.setSummary("Mlijeko, sir, jogurt, vrhnje...");
        prefAttrsCat.addPreference(parentCheckBoxPref3);
        
        CheckBoxPreference parentCheckBoxPref4 = new CheckBoxPreference(this);
        parentCheckBoxPref4.setTitle("Meso");
        parentCheckBoxPref4.setKey("meso");
        parentCheckBoxPref4.setSummary("Salame, pršut, kobasice...");
        prefAttrsCat.addPreference(parentCheckBoxPref4);
        
        CheckBoxPreference parentCheckBoxPref5 = new CheckBoxPreference(this);
        parentCheckBoxPref5.setTitle("Voće i povrće");
        parentCheckBoxPref5.setKey("voce");
        parentCheckBoxPref5.setSummary("Svježe voće i povrće");
        prefAttrsCat.addPreference(parentCheckBoxPref5);

        // Visual child toggle preference
        // See res/values/attrs.xml for the <declare-styleable> that defines
        // TogglePrefAttrs.
//        TypedArray a = obtainStyledAttributes(R.styleable.TogglePrefAttrs);
//        CheckBoxPreference childCheckBoxPref = new CheckBoxPreference(this);
//        childCheckBoxPref.setTitle(R.string.title_child_preference);
//        childCheckBoxPref.setSummary(R.string.summary_child_preference);
//        childCheckBoxPref.setLayoutResource(
//                a.getResourceId(R.styleable.TogglePrefAttrs_android_preferenceLayoutChild,
//                        0));
//        prefAttrsCat.addPreference(childCheckBoxPref);
//        a.recycle();

        return root;
    }
}