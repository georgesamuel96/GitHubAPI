package com.example.georgesamuel.githubapi.Activity;

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
import com.example.georgesamuel.githubapi.rest.SharedPreferenceConfig;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

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
                    userNameTV.setText("No name provided");
                else
                    userNameTV.setText("Username: " + user.getName());
                followersTV.setText("Followers: " + user.getFollowers());
                followingTV.setText("Following: " + user.getFollowing());
                login.setText("LogIn: " + user.getLogin());
                if(user.getEmail() == null)
                    email.setText("No email provided");
                else
                    email.setText("Email: " + user.getEmail());

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
