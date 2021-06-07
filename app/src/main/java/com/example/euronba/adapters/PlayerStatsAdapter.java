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
        private TextView tvPlayerStatsSeasonYear;
        private TextView tvPlayerStatsTeam;
        private TextView tvPlayerStatsGp;
        private TextView tvPlayerStatsMpg;

        private TextView tvPlayerStatsPpg;
        private TextView tvPlayerStatsRpg;
        private TextView tvPlayerStatsApg;
        private TextView tvPlayerStatsBpg;
        private TextView tvPlayerStatsFgp;
        private TextView tvPlayerStatsFtp;
        private TextView tvPlayerStatsTpp;
        private TextView tvPlayerStatsTopg;
        private TextView tvPlayerStatsPlusMinus;

        private LinearLayout tableRowPlayer;


        public RowViewHolder(View itemView) {
            super(itemView);

            tvPlayerStatsSeasonYear = itemView.findViewById(R.id.tvPlayerStatsSeasonYear);
            tvPlayerStatsTeam = itemView.findViewById(R.id.tvPlayerStatsTeam);
            tvPlayerStatsGp = itemView.findViewById(R.id.tvPlayerStatsGp);
            tvPlayerStatsMpg = itemView.findViewById(R.id.tvPlayerStatsMpg);
            tvPlayerStatsPpg = itemView.findViewById(R.id.tvPlayerStatsPpg);
            tvPlayerStatsRpg = itemView.findViewById(R.id.tvPlayerStatsRpg);
            tvPlayerStatsApg = itemView.findViewById(R.id.tvPlayerStatsApg);
            tvPlayerStatsBpg = itemView.findViewById(R.id.tvPlayerStatsBpg);
            tvPlayerStatsFgp = itemView.findViewById(R.id.tvPlayerStatsFgp);
            tvPlayerStatsFtp = itemView.findViewById(R.id.tvPlayerStatsFtp);
            tvPlayerStatsTpp = itemView.findViewById(R.id.tvPlayerStatsTpp);
            tvPlayerStatsTopg = itemView.findViewById(R.id.tvPlayerStatsTopg);
            tvPlayerStatsPlusMinus = itemView.findViewById(R.id.tvPlayerStatsPlusMinus);


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

            rowViewHolder.tvPlayerStatsSeasonYear.setText(String.valueOf(playerStats.getSeasonYear()));

            // En ocasiones un jugador ha jugado en más de un equipo en una misma temporada
            // en estos se devuelve un equipo vacío, en el que se suman sus estadísticas totales, TOT
            if (tm.getTricode() == null)
                rowViewHolder.tvPlayerStatsTeam.setText("TOT");
            else
                rowViewHolder.tvPlayerStatsTeam.setText(tm.getTricode());

            rowViewHolder.tvPlayerStatsGp.setText(playerStats.getGamesPlayed());
            rowViewHolder.tvPlayerStatsMpg.setText(playerStats.getMpg());
            rowViewHolder.tvPlayerStatsPpg.setText(playerStats.getPpg());
            rowViewHolder.tvPlayerStatsRpg.setText(playerStats.getRpg());
            rowViewHolder.tvPlayerStatsApg.setText(playerStats.getApg());
            rowViewHolder.tvPlayerStatsBpg.setText(playerStats.getBpg());
            rowViewHolder.tvPlayerStatsFgp.setText(playerStats.getFgp());
            rowViewHolder.tvPlayerStatsTpp.setText(playerStats.getTpp());
            rowViewHolder.tvPlayerStatsTopg.setText(playerStats.getTopg());
            rowViewHolder.tvPlayerStatsPlusMinus.setText(playerStats.getPlusMinus());
        }
    }

    @Override
    public int getItemCount() {
        return playerStatsList.size() + 1; // one more to add header row
    }

}
