package com.davidwang.githubsearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.MyViewHolder> {
    List<Repository> repositoryList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView descriptionText;
        public TextView languageText;
        public ImageView avatarImage;
        public ImageView favoriteImage;

        public MyViewHolder(View view) {
            super(view);
            nameText = view.findViewById(R.id.itemName);
            descriptionText = view.findViewById(R.id.itemDescription);
            languageText = view.findViewById(R.id.itemLanguage);
            avatarImage = view.findViewById(R.id.itemAvatar);
            favoriteImage = view.findViewById(R.id.itemFavorite);
        }
    }

    public RepositoryAdapter(List<Repository> repositoryList_in) {
        repositoryList = repositoryList_in;
    }

    @Override
    public RepositoryAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Repository repo = repositoryList.get(position);
        holder.nameText.setText(repo.getName());
        holder.descriptionText.setText(repo.getDescription());
        holder.languageText.setText(repo.getLanguage());
//        holder.avatarImage.setImageDrawable(repo.avatar);
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }
}
