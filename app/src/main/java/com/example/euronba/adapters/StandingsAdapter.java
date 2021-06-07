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
import com.example.euronba.activities.TeamActivity;
import com.example.euronba.model.Standings;
import com.example.euronba.model.Team;

import java.util.List;

public class StandingsAdapter extends RecyclerView.Adapter<StandingsAdapter.StandingsViewHolder> {
    List<Standings> standings;
    Activity activity;

    public StandingsAdapter(List<Standings> standings, Activity activity) {
        this.standings = standings;
        this.activity = activity;
    }

    public class StandingsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStandingsName;
        private TextView tvStandingsWin;
        private TextView tvStandingsLosses;
        private TextView tvStandingsPct;
        private TextView tvStandingsStreak;
        private TextView tvStandingsLastTen;
        private LinearLayout tableRowStandings;


        public StandingsViewHolder(View itemView) {
            super(itemView);

            tvStandingsName = itemView.findViewById(R.id.tvStandingsName);
            tvStandingsWin = itemView.findViewById(R.id.tvStandingsWin);
            tvStandingsLosses = itemView.findViewById(R.id.tvStandingsLosses);
            tvStandingsPct = itemView.findViewById(R.id.tvStandingsPct);
            tvStandingsLosses = itemView.findViewById(R.id.tvStandingsLosses);
            tvStandingsStreak = itemView.findViewById(R.id.tvStandingsStreak);
            tvStandingsLastTen = itemView.findViewById(R.id.tvStandingsLastTen);
            tableRowStandings = itemView.findViewById(R.id.tableRowStandings);
        }
    }

    @NonNull
    @Override
    public StandingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_standings, parent, false);

        return new StandingsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StandingsAdapter.StandingsViewHolder standingsViewHolder, int position) {

        int rowPos = standingsViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            standingsViewHolder.tableRowStandings.setBackgroundResource(R.color.purple_light);

            TextView tv;
            for (int i = 0; i < standingsViewHolder.tableRowStandings.getChildCount(); i++) {
                View view = standingsViewHolder.tableRowStandings.getChildAt(i);
                if (view instanceof TextView) {
                    tv = (TextView) view;
                    tv.setTextColor(view.getContext().getResources().getColor(R.color.white));
                    tv.setTypeface(null, Typeface.BOLD);
                }
            }

            standingsViewHolder.tvStandingsName.setText("Team");
            standingsViewHolder.tvStandingsWin.setText("W");
            standingsViewHolder.tvStandingsLosses.setText("L");
            standingsViewHolder.tvStandingsPct.setText("%");
            standingsViewHolder.tvStandingsStreak.setText("Str");
            standingsViewHolder.tvStandingsLastTen.setText("L10");
        } else {
            Standings st = standings.get(rowPos - 1);
            int rowColor = 0;
            if (rowPos % 2 == 0) {
                rowColor = R.color.eblue_mid;
            }

            if (rowPos % 2 != 0) {
                rowColor = R.color.eblue_light;
            }

            standingsViewHolder.tableRowStandings.setBackgroundResource(rowColor);
            Team tm = new Team().getTeamById(st.getTeamId(), activity);

            standingsViewHolder.tvStandingsName.setText(tm.getNickname());
            standingsViewHolder.tvStandingsName.setTypeface(null, Typeface.BOLD);
            standingsViewHolder.tvStandingsWin.setText(st.getWin());
            standingsViewHolder.tvStandingsLosses.setText(st.getLoss());
            standingsViewHolder.tvStandingsPct.setText(st.getWinPct());
            standingsViewHolder.tvStandingsStreak.setText(st.getStreak());
            standingsViewHolder.tvStandingsLastTen.setText(st.getLastTenWin() + "-" + st.getLastTenLoss());

            standingsViewHolder.tvStandingsName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), TeamActivity.class);

                    intent.putExtra(TeamActivity.EXTRA_TEAMID, st.getTeamId());

                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return standings.size() + 1; // one more to add header row
    }

}
