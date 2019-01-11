package com.example.georgesamuel.githubapi.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.Pair;

import com.example.georgesamuel.githubapi.R;

public class SharedPreferenceConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preference), context.MODE_PRIVATE);
    }

    public void setSharedPreferences(boolean status, String userName)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preference), status);
        editor.putString("userName", userName);
        editor.commit();
    }

    public Pair<Boolean, String> getSharedPreferences()
    {
        boolean status = false;
        String userName = "Not Found";
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preference), false);
        userName = sharedPreferences.getString("userName", "Not Found");
        Pair<Boolean, String> pair = new Pair<>(status, userName);
        return pair;
    }
}
