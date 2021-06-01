package com.example.euronba.activities;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.adapters.PlayerStatsAdapter;
import com.example.euronba.adapters.TeamRosterAdapter;
import com.example.euronba.controller.RetrievePlayerCareer;
import com.example.euronba.model.Player;
import com.example.euronba.model.Team;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {

    public static final String EXTRA_TEAMID = "teamId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle data = getIntent().getExtras();

        String personId = data.getString("teamId");

        fillTeamInfo(personId);

        fillTeamRoster(personId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void fillTeamInfo(String teamId) {

        Team tm = new Team().getTeamById(teamId, TeamActivity.this);

        TextView tvTeamProfileName = findViewById(R.id.tvTeamProfileName);
        TextView tvTeamProfileConference = findViewById(R.id.tvTeamProfileConference);
        TextView tvTeamProfileDivision = findViewById(R.id.tvTeamProfileDivision);
        ImageView ivTeamProfileLogo = findViewById(R.id.ivTeamProfileLogo);

        tvTeamProfileName.setText(tm.getFullName());
        tvTeamProfileConference.setText(tm.getConfName());
        tvTeamProfileDivision.setText(tm.getDivName());
        ivTeamProfileLogo.setImageResource(tm.getLogo());
    }

    public void fillTeamRoster(String teamId) {

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTeamProfileRoster);

        ArrayList<Player> teamRoster = new Player().getPlayersByTeamId(teamId);

        TeamRosterAdapter adapter = new TeamRosterAdapter(teamRoster, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }
}