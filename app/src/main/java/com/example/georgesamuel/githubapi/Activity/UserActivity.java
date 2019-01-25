package com.example.georgesamuel.githubapi.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.georgesamuel.githubapi.R;
import com.example.georgesamuel.githubapi.model.GitHubUser;
import com.example.georgesamuel.githubapi.rest.ImageDownloader;
import com.example.georgesamuel.githubapi.rest.ApiClient;
import com.example.georgesamuel.githubapi.rest.GitHubUserEndPoints;
import com.example.georgesamuel.githubapi.rest.LocaleHelper;
import com.example.georgesamuel.githubapi.rest.ResetView;
import com.example.georgesamuel.githubapi.rest.SharedPreferenceConfig;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private ImageView avatarImg;
    private TextView userNameTV, followersTV, followingTV, login, email;
    private Button ownedrepos;
    private Bundle extras;
    private String newString;
    Bitmap myImg;
    private SharedPreferenceConfig sharedPreference;
    private ConnectivityManager connectivityManager;
    private LinearLayout linearLayout;
    private ResetView resetView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        resetView = new ResetView(getApplicationContext());
        Paper.init(this);
        String language = Paper.book().read("language");
        if(language == null){
            Paper.book().write("language", "en");
        }
        resetView.updateView((String) Paper.book().read("language"));

        avatarImg = (ImageView) findViewById(R.id.avatar);
        userNameTV = (TextView) findViewById(R.id.username);
        followersTV = (TextView) findViewById(R.id.followers);
        followingTV = (TextView) findViewById(R.id.following);
        login = (TextView) findViewById(R.id.login);
        email = (TextView) findViewById(R.id.email);
        ownedrepos = (Button) findViewById(R.id.ownedReposBtn);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        sharedPreference = new SharedPreferenceConfig(getApplicationContext());
        newString = sharedPreference.getSharedPreferences().second;
        Log.v("newString", "user " + newString);
        loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout)
        {
            sharedPreference.setSharedPreferences(false, "Not Found");
            Intent i = new Intent(UserActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else if(id == R.id.language)
        {
            resetView.updatePreferences();
            finish();
            startActivity(getIntent());
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        final GitHubUserEndPoints apiService = ApiClient.getApiClient().create(GitHubUserEndPoints.class);
        Call<GitHubUser> call = apiService.getUser(newString);

        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                GitHubUser user = response.body();
                if(user.getName() == null)
                    userNameTV.setText(getText(R.string.no_name));
                else
                    userNameTV.setText(getText(R.string.user_name) + ": " + user.getName());
                followersTV.setText(getText(R.string.followers) + ": " + user.getFollowers());
                followingTV.setText(getText(R.string.following) + ": " + user.getFollowing());
                login.setText(getText(R.string.login) + ": " + user.getLogin());
                if(user.getEmail() == null)
                    email.setText(getText(R.string.not_email));
                else
                    email.setText(getText(R.string.email) + ": " + user.getEmail());

                /*RequestOptions option;
                option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
                Glide.with(UserActivity.this).load(user.getAvatar()).apply(option).into(avatarImg);
                avatarImg.getLayoutParams().height = 220;
                avatarImg.getLayoutParams().width = 220;*/

                ImageDownloader downloader = new ImageDownloader();
                try
                {
                    myImg = downloader.execute(user.getAvatar()).get();
                    avatarImg.setImageBitmap(myImg);
                    avatarImg.getLayoutParams().height = 220;
                    avatarImg.getLayoutParams().width = 220;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                if(!checkNetworkStatus())
                    informUser("No internet connection available...");
            }
        });
    }

    public void loadOwnRepos(View view) {
        Intent i = new Intent(UserActivity.this, Repositories.class);
        i.putExtra("username", newString);
        startActivity(i);
    }

    private void informUser(String message)
    {
        Snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();
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
