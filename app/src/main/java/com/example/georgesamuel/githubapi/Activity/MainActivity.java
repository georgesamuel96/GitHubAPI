package com.example.georgesamuel.githubapi.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.georgesamuel.githubapi.R;
import com.example.georgesamuel.githubapi.rest.SharedPreferenceConfig;

public class MainActivity extends AppCompatActivity {

    private Button logIn;
    private EditText inputUserName;
    private Intent i;
    SharedPreferenceConfig sharedPreference;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logIn = (Button) findViewById(R.id.btn_login);
        inputUserName = (EditText) findViewById(R.id.input_username);

        sharedPreference = new SharedPreferenceConfig(getApplicationContext());
        if(sharedPreference.getSharedPreferences().first)
        {
            getUser();
            finish();
        }

    }

    public void getUser(View view) {
       getUser();
        sharedPreference.setSharedPreferences(true, userName);
        finish();
    }

    private void getUser()
    {
        userName = inputUserName.getText().toString();
        i = new Intent(MainActivity.this, UserActivity.class);
        i.putExtra("STRING_I_NEED", userName);
        startActivity(i);
    }
}
