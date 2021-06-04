package com.example.euronba.activities;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.adapters.PlayerStatsAdapter;
import com.example.euronba.controller.RetrievePlayerCareer;
import com.example.euronba.model.Favorite;
import com.example.euronba.model.Player;
import com.example.euronba.model.Team;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    public static final String EXTRA_PERSONID = "personId";
    public static final String EXTRA_TEAMURL = "teamUrl";
    Player p;
    ArrayList<Team> teamsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ImageButton favButton = findViewById(R.id.ibFavPlayer);

        Bundle data = getIntent().getExtras();

        String personId = data.getString("personId");

        String teamUrl = data.getString("teamUrl");

        Player p = new Player().getPlayerProfileFromId(personId, teamUrl);

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favTeam = new Favorite();

                favTeam.setId(personId);
                favTeam.setType("player");
                favTeam.setTeamUrl(teamUrl);
                favTeam.setPersonName(p.getFirstName() + " " + p.getLastName());

                favTeam.insertFav(PlayerActivity.this);
            }
        });

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fillPlayerInfo(p);

        fillPlayerStats(personId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void fillPlayerInfo(Player p) {

        TextView tvPlayerProfileName = findViewById(R.id.tvPlayerProfileName);
        TextView tvPlayerProfilePos = findViewById(R.id.tvPlayerProfilePos);
        TextView tvPlayerProfileBirthdate = findViewById(R.id.tvPlayerProfileBirthdate);
        TextView tvPlayerProfileCollege = findViewById(R.id.tvPlayerProfileCollege);
        TextView tvPlayerProfileHeight = findViewById(R.id.tvPlayerProfileHeight);
        TextView tvPlayerProfileWeight = findViewById(R.id.tvPlayerProfileWeight);
        TextView tvPlayerProfileYearsPro = findViewById(R.id.tvPlayerProfileYearsPro);
        ImageView ivPlayerProfileTeamLogo = findViewById(R.id.ivPlayerProfileTeamLogo);

        tvPlayerProfileName.setText(p.getFirstName() + " " + p.getLastName());
        tvPlayerProfilePos.setText("#" + p.getJersey() + " - " + p.getPos());
        tvPlayerProfileBirthdate.setText(p.getDateOfBirthUTC() + " - Age " + p.getAge());

        Team tmCurrent = new Team().getTeamById(p.getTeamId(), this.getApplicationContext());
        tvPlayerProfileCollege.setText("College: " + p.getCollegeName());
        tvPlayerProfileHeight.setText("Height: " + p.getHeightFt() + " m");
        tvPlayerProfileWeight.setText("Weight: " + p.getWeightLbs() + " kg");
        tvPlayerProfileYearsPro.setText("Years Pro: " + p.getYearsPro());
        ivPlayerProfileTeamLogo.setImageResource(tmCurrent.getLogo());
    }

    public void fillPlayerStats(String personId) {

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlayerProfileSeasons);

        RetrievePlayerCareer rpc = new RetrievePlayerCareer();

        PlayerStatsAdapter adapter = new PlayerStatsAdapter(rpc.getPlayerStatsFromID(personId), this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }
}