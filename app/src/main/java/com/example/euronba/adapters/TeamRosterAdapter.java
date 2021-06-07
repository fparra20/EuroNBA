package com.example.euronba.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.activities.PlayerActivity;
import com.example.euronba.model.Player;
import com.example.euronba.model.Team;

import java.util.List;

public class TeamRosterAdapter extends RecyclerView.Adapter<TeamRosterAdapter.TeamRosterViewHolder> {
    List<Player> teamRoster;
    Activity activity;

    public TeamRosterAdapter(List<Player> teamRoster, Activity activity) {
        this.teamRoster = teamRoster;
        this.activity = activity;
    }

    public class TeamRosterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTeamRosterPlayerName;
        private TextView tvTeamRosterPosition;
        private TextView tvTeamRosterAge;
        private TextView tvTeamRosterCollege;
        private LinearLayout tableRowTeamRoster;


        public TeamRosterViewHolder(View itemView) {
            super(itemView);

            tvTeamRosterPlayerName = itemView.findViewById(R.id.tvTeamRosterPlayerName);
            tvTeamRosterPosition = itemView.findViewById(R.id.tvTeamRosterPosition);
            tvTeamRosterAge = itemView.findViewById(R.id.tvTeamRosterPro);
            tvTeamRosterCollege = itemView.findViewById(R.id.tvTeamRosterCollege);
            tableRowTeamRoster = itemView.findViewById(R.id.tableRowTeamRoster);
        }
    }

    @NonNull
    @Override
    public TeamRosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_team_roster, parent, false);

        return new TeamRosterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamRosterAdapter.TeamRosterViewHolder teamRostervh, int position) {

        int rowPos = teamRostervh.getAdapterPosition();

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            teamRostervh.tableRowTeamRoster.setBackgroundResource(R.color.purple_light);

            TextView tv;
            for (int i = 0; i < teamRostervh.tableRowTeamRoster.getChildCount(); i++) {
                View view = teamRostervh.tableRowTeamRoster.getChildAt(i);
                if (view instanceof TextView) {
                    tv = (TextView) view;
                    tv.setTextColor(view.getContext().getResources().getColor(R.color.white));
                    tv.setTypeface(null, Typeface.BOLD);
                }
            }

        } else {
            Player player = teamRoster.get(rowPos - 1);
            int rowColor = 0;
            if (rowPos % 2 == 0) {
                rowColor = R.color.eblue_mid;
            }

            if (rowPos % 2 != 0) {
                rowColor = R.color.eblue_light;
            }

            teamRostervh.tableRowTeamRoster.setBackgroundResource(rowColor);
            Team tm = new Team().getTeamById(player.getTeamId(), activity);

            teamRostervh.tvTeamRosterPlayerName.setText(player.getFirstName() + " " + player.getLastName());
            teamRostervh.tvTeamRosterPosition.setText(player.getPos());
            teamRostervh.tvTeamRosterAge.setText(player.getYearsPro());
            teamRostervh.tvTeamRosterCollege.setText(player.getCollegeName());

            teamRostervh.tvTeamRosterPlayerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), PlayerActivity.class);

                    intent.putExtra(PlayerActivity.EXTRA_PERSONID, player.getPersonId());
                    intent.putExtra(PlayerActivity.EXTRA_TEAMURL, tm.getUrlName());

                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return teamRoster.size() + 1; // one more to add header row
    }

}
