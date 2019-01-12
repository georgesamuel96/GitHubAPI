package com.example.georgesamuel.githubapi.Activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.georgesamuel.githubapi.R;
import com.example.georgesamuel.githubapi.rest.SharedPreferenceConfig;

public class MainActivity extends AppCompatActivity {

    private Button logIn;
    private EditText inputUserName;
    private Intent i;
    SharedPreferenceConfig sharedPreference;
    private String userName;
    private RelativeLayout relativeLayout;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void getUser(View view) {
        if(!checkNetworkStatus())
        {
            informUser("No internet connection available...");
        }
        else
        {
            getUser();
            sharedPreference.setSharedPreferences(true, userName);
            finish();
        }
    }

    private void getUser()
    {
        userName = inputUserName.getText().toString();
        i = new Intent(MainActivity.this, UserActivity.class);
        i.putExtra("STRING_I_NEED", userName);
        startActivity(i);
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
