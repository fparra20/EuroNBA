package com.example.euronba.adapters;


import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.model.PlayerStats;
import com.example.euronba.model.Team;

import java.util.List;

public class PlayerStatsAdapter extends RecyclerView.Adapter {
    List<PlayerStats> playerStatsList;
    Activity activity;

    public PlayerStatsAdapter(List<PlayerStats> playerStatsList, Activity activity) {
        this.playerStatsList = playerStatsList;
        this.activity = activity;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPlayerStatsYear;
        private TextView tvPlayerStatsTeam;
        private TextView tvPlayerStatsPts;
        private TextView tvPlayerStatsAst;
        private LinearLayout tableRowPlayer;


        public RowViewHolder(View itemView) {
            super(itemView);

            tvPlayerStatsYear = itemView.findViewById(R.id.tvPlayerStatsYear);
            tvPlayerStatsTeam = itemView.findViewById(R.id.tvPlayerStatsTeam);
            tvPlayerStatsPts = itemView.findViewById(R.id.tvPlayerStatsPts);
            tvPlayerStatsAst = itemView.findViewById(R.id.tvPlayerStatsAst);
            tableRowPlayer = itemView.findViewById(R.id.tableRowPlayer);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_player_seasons, parent, false);

        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            rowViewHolder.tableRowPlayer.setBackgroundResource(R.color.purple_light);

            TextView tv;
            for (int i = 0; i < rowViewHolder.tableRowPlayer.getChildCount(); i++) {
                View view = rowViewHolder.tableRowPlayer.getChildAt(i);
                if (view instanceof TextView) {
                    tv = (TextView) view;
                    tv.setTextColor(view.getContext().getResources().getColor(R.color.white));
                    tv.setTypeface(null, Typeface.BOLD);
                }
            }

            rowViewHolder.tvPlayerStatsYear.setText("Season");
            rowViewHolder.tvPlayerStatsTeam.setText("Team");
            rowViewHolder.tvPlayerStatsPts.setText("Pts");
            rowViewHolder.tvPlayerStatsAst.setText("Ast");
        } else {
            PlayerStats playerStats = playerStatsList.get(rowPos - 1);
            int rowColor = 0;
            if (rowPos % 2 == 0) {
                rowColor = R.color.eblue_mid;
            }

            if (rowPos % 2 != 0) {
                rowColor = R.color.eblue_light;
            }

            rowViewHolder.tableRowPlayer.setBackgroundResource(rowColor);
            Team tm = new Team().getTeamById(playerStats.getTeamId(), activity);

            rowViewHolder.tvPlayerStatsYear.setText(String.valueOf(playerStats.getSeasonYear()));
            rowViewHolder.tvPlayerStatsTeam.setText(tm.getTricode());
            rowViewHolder.tvPlayerStatsPts.setText(playerStats.getPpg());
            rowViewHolder.tvPlayerStatsAst.setText(playerStats.getApg());
        }
    }

    @Override
    public int getItemCount() {
        return playerStatsList.size() + 1; // one more to add header row
    }

}
