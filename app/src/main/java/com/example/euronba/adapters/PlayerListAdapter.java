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
import com.example.euronba.model.Player;
import com.example.euronba.model.Team;

import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerListViewHolder> {
    List<Player> playerList;
    Activity activity;

    public PlayerListAdapter(List<Player> playerList, Activity activity) {
        this.playerList = playerList;
        this.activity = activity;
    }

    public static class PlayerListViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPlayerListName;
        private final TextView tvPlayerListPositionJersey;
        private final ImageView ivPlayerListTeamLogo;
        private final CardView cvPlayerList;

        public PlayerListViewHolder(View itemView) {
            super(itemView);

            tvPlayerListName = itemView.findViewById(R.id.tvPlayerListName);
            tvPlayerListPositionJersey = itemView.findViewById(R.id.tvPlayerListPositionJersey);
            ivPlayerListTeamLogo = itemView.findViewById(R.id.ivPlayerListTeamLogo);
            cvPlayerList = itemView.findViewById(R.id.cvPlayerList);
        }
    }

    @NonNull
    @Override
    public PlayerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_player, parent, false);

        return new PlayerListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerListAdapter.PlayerListViewHolder pListViewHolder, int position) {
        Player player = playerList.get(position);

        Team tm = new Team().getTeamById(player.getTeamId(), activity);

        pListViewHolder.tvPlayerListName.setText(player.getFullName());
        pListViewHolder.tvPlayerListPositionJersey.setText(activity.getString(R.string.teamListJerseyPos,player.getPos(),player.getJersey()));
        pListViewHolder.ivPlayerListTeamLogo.setImageResource(tm.getLogo());

        pListViewHolder.cvPlayerList.setOnClickListener(v -> {
            Intent intent = new Intent(activity.getApplicationContext(), PlayerActivity.class);

            intent.putExtra(PlayerActivity.EXTRA_PERSONID, player.getPersonId());
            intent.putExtra(PlayerActivity.EXTRA_TEAMURL, tm.getUrlName());

            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size(); // one more to add header row
    }

}
