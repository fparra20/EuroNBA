package com.example.euronba.adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.model.PlayoffsBracket;
import com.example.euronba.model.Team;

import java.util.ArrayList;

public class PlayoffsAdapter extends RecyclerView.Adapter<PlayoffsAdapter.PlayoffsViewHolder> {
    ArrayList<PlayoffsBracket> poBracket;
    Activity activity;

    public PlayoffsAdapter(ArrayList<PlayoffsBracket> poBracket, Activity activity) {
        this.poBracket = poBracket;
        this.activity = activity;
    }

    public static class PlayoffsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPlayOffsTopSeed;
        private TextView tvPlayOffsTopTricode;
        private TextView tvPlayOffsTopWins;
        private ImageView ivPlayOffsTopLogo;

        private TextView tvPlayOffsBottomSeed;
        private TextView tvPlayOffsBottomTricode;
        private TextView tvPlayOffsBottomWins;
        private ImageView ivPlayOffsBottomLogo;

        public PlayoffsViewHolder(View itemView) {
            super(itemView);

            tvPlayOffsTopSeed = itemView.findViewById(R.id.tvPlayOffsTopSeed);
            tvPlayOffsTopTricode = itemView.findViewById(R.id.tvPlayOffsTopTricode);
            tvPlayOffsTopWins = itemView.findViewById(R.id.tvPlayOffsTopWins);
            ivPlayOffsTopLogo = itemView.findViewById(R.id.ivPlayOffsTopLogo);

            tvPlayOffsBottomSeed = itemView.findViewById(R.id.tvPlayOffsBottomSeed);
            tvPlayOffsBottomTricode = itemView.findViewById(R.id.tvPlayOffsBottomTricode);
            tvPlayOffsBottomWins = itemView.findViewById(R.id.tvPlayOffsBottomWins);
            ivPlayOffsBottomLogo = itemView.findViewById(R.id.ivPlayOffsBottomLogo);
        }
    }

    @NonNull
    @Override
    public PlayoffsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_playoff_series, parent, false);

        return new PlayoffsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayoffsAdapter.PlayoffsViewHolder pListViewHolder, int position) {
        PlayoffsBracket poSeries = poBracket.get(position);

        Team tmBottom = new Team().getTeamById(poSeries.getBottomRow().getTeamId(), activity);
        Team tmTop = new Team().getTeamById(poSeries.getTopRow().getTeamId(), activity);

        pListViewHolder.tvPlayOffsTopTricode.setText(tmTop.getTricode());
        pListViewHolder.tvPlayOffsTopSeed.setText(poSeries.getTopRow().getSeedNum());
        pListViewHolder.tvPlayOffsTopWins.setText(poSeries.getTopRow().getWins());
        pListViewHolder.ivPlayOffsTopLogo.setImageResource(tmTop.getLogo());

        pListViewHolder.tvPlayOffsBottomTricode.setText(tmBottom.getTricode());
        pListViewHolder.tvPlayOffsBottomSeed.setText(poSeries.getBottomRow().getSeedNum());
        pListViewHolder.tvPlayOffsBottomWins.setText(poSeries.getBottomRow().getWins());
        pListViewHolder.ivPlayOffsBottomLogo.setImageResource(tmBottom.getLogo());
    }

    @Override
    public int getItemCount() {
        return poBracket.size(); // one more to add header row
    }

}
