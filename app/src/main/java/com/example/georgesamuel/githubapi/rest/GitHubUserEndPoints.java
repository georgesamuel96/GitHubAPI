package com.example.georgesamuel.githubapi.rest;

import com.example.georgesamuel.githubapi.model.GitHubRepo;
import com.example.georgesamuel.githubapi.model.GitHubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUserEndPoints {

    @GET("/users/{user}")
    Call<GitHubUser> getUser(@Path("user") String user);

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> getRepo(@Path("user") String user);
}
