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
import com.example.euronba.model.Player;
import com.example.euronba.model.Team;

import java.util.List;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.TeamListViewHolder> {
    List<Team> teamList;
    Activity activity;

    public TeamListAdapter(List<Team> teamList, Activity activity) {
        this.teamList = teamList;
        this.activity = activity;
    }

    public static class TeamListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTeamListName;
        private ImageView ivTeamListTeamLogo;
        private CardView cvTeamList;

        public TeamListViewHolder(View itemView) {
            super(itemView);

            tvTeamListName = itemView.findViewById(R.id.tvTeamListName);
            ivTeamListTeamLogo = itemView.findViewById(R.id.ivTeamListTeamLogo);
            cvTeamList = itemView.findViewById(R.id.cvTeamList);
        }
    }

    @NonNull
    @Override
    public TeamListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_team, parent, false);

        return new TeamListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamListAdapter.TeamListViewHolder tListViewHolder, int position) {
        Team tm = teamList.get(position);

        tListViewHolder.tvTeamListName.setText(tm.getFullName());
        tListViewHolder.ivTeamListTeamLogo.setImageResource(tm.getLogo());

        tListViewHolder.cvTeamList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), TeamActivity.class);

                intent.putExtra(TeamActivity.EXTRA_TEAMID, tm.getTeamId());

                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamList.size(); // one more to add header row
    }

}
