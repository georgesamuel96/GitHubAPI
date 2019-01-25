package com.example.georgesamuel.githubapi.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.georgesamuel.githubapi.R;
import com.example.georgesamuel.githubapi.rest.LocaleHelper;
import com.example.georgesamuel.githubapi.rest.ResetView;
import com.example.georgesamuel.githubapi.rest.SharedPreferenceConfig;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button logIn;
    private EditText inputUserName;
    private Intent i;
    SharedPreferenceConfig sharedPreference;
    private String userName;
    private RelativeLayout relativeLayout;
    private ConnectivityManager connectivityManager;
    private ResetView resetView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetView = new ResetView(getApplicationContext());
        Paper.init(this);
        String language = Paper.book().read("language");
        if(language == null){
            Paper.book().write("language", "en");
        }
        resetView.updateView((String) Paper.book().read("language"));

        logIn = (Button) findViewById(R.id.btn_login);
        inputUserName = (EditText) findViewById(R.id.input_username);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        sharedPreference = new SharedPreferenceConfig(getApplicationContext());
        if(sharedPreference.getSharedPreferences().first)
        {
            getUser();
            finish();
        }
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.language)
        {
            resetView.updatePreferences();
            finish();
            startActivity(getIntent());
        }
        return true;
    }

    public void getUser(View view) {
        if(!checkNetworkStatus())
        {
            informUser("No internet connection available...");
        }
        else
        {
            getUser();
        }
    }

    private void getUser()
    {
        userName = inputUserName.getText().toString().trim();
        if(userName.length() == 0){
            inputUserName.setError(getText(R.string.username_error));
        }
        else {
            i = new Intent(MainActivity.this, UserActivity.class);
            i.putExtra("STRING_I_NEED", userName);
            startActivity(i);
            sharedPreference.setSharedPreferences(true, userName);
            finish();
        }
    }

    private void informUser(String message)
    {
        Snackbar.make(relativeLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    private boolean checkNetworkStatus()
    {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
