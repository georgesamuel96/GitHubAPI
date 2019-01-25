package com.example.georgesamuel.githubapi.rest;

import android.content.Context;
import android.content.SharedPreferences;

import io.paperdb.Paper;

public class ResetView {

    private Context context;
    private static final String Prefs_Language = "PrefsFile";
    private String prefLang;

    public ResetView(Context context){
        this.context = context;
    }
    public void updateView(String language) {
        Context ctx = LocaleHelper.setLocale(context, language);
    }

    public void updatePreferences(){
        SharedPreferences preferences = context.getSharedPreferences(Prefs_Language, 0);
        if(preferences.contains("language"))
        {
            prefLang = preferences.getString("language", "en");
        }
        else
        {
            prefLang = "en";
        }

        SharedPreferences.Editor editor = preferences.edit();
        if(prefLang == "en") {
            editor.putString("language", "ar");
            Paper.book().write("language", "ar");
            updateView((String) Paper.book().read("language"));
        }
        else {
            editor.putString("language", "en");
            Paper.book().write("language", "en");
            updateView((String) Paper.book().read("language"));
        }
        editor.commit();
    }
}
