package com.example.euronba.activities;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.adapters.PlayerStatsAdapter;
import com.example.euronba.adapters.TeamRosterAdapter;
import com.example.euronba.controller.RetrievePlayerCareer;
import com.example.euronba.model.Favorite;
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
        ImageButton favButton = findViewById(R.id.ibFavTeam);

        Bundle data = getIntent().getExtras();

        String personId = data.getString("teamId");

        fillTeamInfo(personId);

        fillTeamRoster(personId);

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favTeam = new Favorite();

                favTeam.setId(data.getString("teamId"));
                favTeam.setType("team");

                favTeam.insertFav(TeamActivity.this);

                favButton.setImageResource(android.R.drawable.star_on);

                Toast.makeText(TeamActivity.this, "Añadido!!", Toast.LENGTH_LONG).show();
            }
        });

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        tvTeamProfileConference.setText("Conf: "+tm.getConfName());
        tvTeamProfileDivision.setText("Div: "+tm.getDivName());
        ivTeamProfileLogo.setImageResource(tm.getLogo());
    }

    public void fillTeamRoster(String teamId) {

        Team tm = new Team().getTeamById(teamId, TeamActivity.this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTeamProfileRoster);

        ArrayList<Player> teamRoster = new Player().getPlayersByTeamUrl(tm.getUrlName());

        TeamRosterAdapter adapter = new TeamRosterAdapter(teamRoster, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }
}