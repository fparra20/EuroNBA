package com.example.euronba.adapters;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.activities.PlayerActivity;
import com.example.euronba.activities.TeamActivity;
import com.example.euronba.model.Favorite;
import com.example.euronba.model.Team;

import java.util.List;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.FavoritesListViewHolder> {
    List<Favorite> favList;
    Activity activity;

    public FavoritesListAdapter(List<Favorite> favList, Activity activity) {
        this.favList = favList;
        this.activity = activity;
    }

    public static class FavoritesListViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPlayerListName;
        private final ImageView ivPlayerListTeamLogo;
        private final CardView cvPlayerList;

        public FavoritesListViewHolder(View itemView) {
            super(itemView);

            tvPlayerListName = itemView.findViewById(R.id.tvPlayerListName);
            ivPlayerListTeamLogo = itemView.findViewById(R.id.ivPlayerListTeamLogo);
            cvPlayerList = itemView.findViewById(R.id.cvPlayerList);
        }
    }

    @NonNull
    @Override
    public FavoritesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_player, parent, false);

        return new FavoritesListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesListAdapter.FavoritesListViewHolder pListViewHolder, int position) {
        Favorite fav = favList.get(position);

        if (fav.getType().equals("team")) {
            Team tm = new Team().getTeamById(fav.getId(), activity);
            pListViewHolder.tvPlayerListName.setText(tm.getFullName());
            pListViewHolder.ivPlayerListTeamLogo.setImageResource(tm.getLogo());
        }

        if (fav.getType().equals("player")) {
            pListViewHolder.tvPlayerListName.setText(fav.getPersonName());
        }
        pListViewHolder.cvPlayerList.setOnClickListener(v -> {
            Intent intent = null;
            if (fav.getType().equals("team")) {
                intent = new Intent(activity.getApplicationContext(), TeamActivity.class);
                intent.putExtra(TeamActivity.EXTRA_TEAMID, fav.getId());
            }

            if (fav.getType().equals("player")) {
                intent = new Intent(activity.getApplicationContext(), PlayerActivity.class);
                intent.putExtra(PlayerActivity.EXTRA_PERSONID, fav.getId());
                intent.putExtra(PlayerActivity.EXTRA_TEAMURL, fav.getTeamUrl());
            }
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favList.size(); // one more to add header row
    }

}
