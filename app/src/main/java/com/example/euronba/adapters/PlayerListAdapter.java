package com.example.euronba.adapters;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.PlayerActivity;
import com.example.euronba.R;
import com.example.euronba.model.Player;
import com.example.euronba.model.PlayerStats;
import com.example.euronba.model.Scoreboard;
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
        private TextView tvPlayerListName;
        private TextView tvPlayerListPositionJersey;
        private ImageView ivPlayerListTeamLogo;
        private CardView cvPlayerList;

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

        Team tm = new Team();

        pListViewHolder.tvPlayerListName.setText(player.getFirstName() + " " + player.getLastName());
        pListViewHolder.tvPlayerListPositionJersey.setText(player.getPos() + " #" + player.getJersey());
        pListViewHolder.ivPlayerListTeamLogo.setImageResource(tm.getTeamById(player.getTeamId(), activity.getApplicationContext()).getLogo());

        pListViewHolder.cvPlayerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), PlayerActivity.class);

                intent.putExtra(PlayerActivity.EXTRA_PERSONID, player.getPersonId());

                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size(); // one more to add header row
    }

}
