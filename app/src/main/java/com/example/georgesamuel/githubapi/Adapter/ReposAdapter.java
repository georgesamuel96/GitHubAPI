package com.example.georgesamuel.githubapi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.georgesamuel.githubapi.R;
import com.example.georgesamuel.githubapi.model.GitHubRepo;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.MyViewHolder>{

    private List<GitHubRepo> repos;
    private Context context;
    private String mDescription;

    public ReposAdapter(List<GitHubRepo> repos, Context context)
    {
        this.context = context;
        this.repos = repos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_repository, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.repoName.setText(repos.get(i).getName());
        mDescription = repos.get(i).getDescription();
        if(mDescription == null)
            myViewHolder.repoDescription.setText("There is no description for this repository!");
        else
            myViewHolder.repoDescription.setText(mDescription);
        myViewHolder.repoLanguage.setText(repos.get(i).getLanguage());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        TextView repoName;
        TextView repoDescription;
        TextView repoLanguage;

        public MyViewHolder(View view)
        {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.repo_item_layout);
            repoName = (TextView) view.findViewById(R.id.repoName);
            repoDescription = (TextView) view.findViewById(R.id.repoDescription);
            repoLanguage = (TextView) view.findViewById(R.id.repoLanguage);
        }
    }
}
