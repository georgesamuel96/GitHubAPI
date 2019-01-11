package com.example.georgesamuel.githubapi.model;

import com.google.gson.annotations.SerializedName;

public class GitHubUser {

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("followers")
    private String followers;

    @SerializedName("following")
    private String following;

    @SerializedName("avatar_url")
    private String avatar;

    @SerializedName("login")
    private String login;

    public GitHubUser(String email, String avatar, String following, String followers, String name, String login) {
        this.name = name;
        this.email = email;
        this.followers = followers;
        this.following = following;
        this.avatar = avatar;
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
