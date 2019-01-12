package com.example.georgesamuel.githubapi.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.georgesamuel.githubapi.Adapter.ReposAdapter;
import com.example.georgesamuel.githubapi.R;
import com.example.georgesamuel.githubapi.model.GitHubRepo;
import com.example.georgesamuel.githubapi.rest.ApiClient;
import com.example.georgesamuel.githubapi.rest.GitHubUserEndPoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repositories extends AppCompatActivity {

    private String receivedUserName;
    private TextView userNameTV;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<GitHubRepo> myDataSource = new ArrayList<>();
    ReposAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        Bundle bundle = getIntent().getExtras();
        receivedUserName = bundle.getString("username");

        userNameTV = (TextView) findViewById(R.id.userNameTV);

        userNameTV.setText("User: " + receivedUserName);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ReposAdapter(myDataSource, getApplicationContext());

        recyclerView.setAdapter(adapter);

        loadRepositories();
    }

    private void loadRepositories() {
        GitHubUserEndPoints apiService = ApiClient.getApiClient().create(GitHubUserEndPoints.class);
        Call<List<GitHubRepo>> call = apiService.getRepo(receivedUserName);
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                myDataSource.clear();
                myDataSource.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {

            }
        });
    }
}
