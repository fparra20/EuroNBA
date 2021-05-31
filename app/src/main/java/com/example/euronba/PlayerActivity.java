package com.example.euronba;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.adapters.PlayerStatsAdapter;
import com.example.euronba.controller.RetrievePlayerCareer;
import com.example.euronba.model.Player;
import com.example.euronba.model.Team;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    public static final String EXTRA_PERSONID = "personId";
    ArrayList<Team> teamsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle data = getIntent().getExtras();

        String personId = data.getString("personId");

        fillPlayerInfo(personId);

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

    public void fillPlayerInfo(String personId){

        TextView tvPlayerProfileName = findViewById(R.id.tvPlayerProfileName);
        TextView tvPlayerProfilePos = findViewById(R.id.tvPlayerProfilePos);
        TextView tvPlayerProfileBirthdate = findViewById(R.id.tvPlayerProfileBirthdate);
        TextView tvPlayerProfileDraft = findViewById(R.id.tvPlayerProfileDraft);
        TextView tvPlayerProfileCollege = findViewById(R.id.tvPlayerProfileCollege);
        TextView tvPlayerProfileCountry = findViewById(R.id.tvPlayerProfileCountry);
        TextView tvPlayerProfileHeight = findViewById(R.id.tvPlayerProfileHeight);
        TextView tvPlayerProfileWeight = findViewById(R.id.tvPlayerProfileWeight);
        TextView tvPlayerProfileYearsPro = findViewById(R.id.tvPlayerProfileYearsPro);
        TextView tvPlayerProfileDebutYear = findViewById(R.id.tvPlayerProfileDebutYear);
        ImageView ivPlayerProfileTeamLogo =findViewById(R.id.ivPlayerProfileTeamLogo);

        Player p = new Player().getPlayerProfileFromId(personId);

        tvPlayerProfileName.setText(p.getFirstName() + " " + p.getLastName());
        tvPlayerProfilePos.setText("#"+p.getJersey() +" - " + p.getPos());
        tvPlayerProfileBirthdate.setText(p.getDateOfBirthUTC() +" - Age " + p.getAge());
        if(p.getDraft().getSeasonYear().equals("")){
            tvPlayerProfileDraft.setText("Draft: Not drafted.");
        }

        Team tmDraft = new Team().getTeamById(p.getDraft().getDraftedTeamId(), this.getApplicationContext());

        Team tmCurrent = new Team().getTeamById(p.getTeamId(), this.getApplicationContext());

        tvPlayerProfileDraft.setText("Draft: "+p.getDraft().getSeasonYear() + " by "+ tmDraft.getFullName() + ", Pick " +p.getDraft().getPickNum() +", Round " + p.getDraft().getRoundNum());
        tvPlayerProfileCollege.setText("College: " + p.getCollegeName());
        tvPlayerProfileCountry.setText("Country: " + p.getCountry());
        tvPlayerProfileHeight.setText("Height: " + p.getHeightMeters() + " m");
        tvPlayerProfileWeight.setText("Weight: " + p.getWeightKilograms() + " kg");
        tvPlayerProfileYearsPro.setText("Years Pro: " + p.getYearsPro());
        tvPlayerProfileDebutYear.setText("Debut: " + p.getNbaDebutYear());
        ivPlayerProfileTeamLogo.setImageResource(tmCurrent.getLogo());
    }

    public void fillPlayerStats(String personId){

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlayerProfileSeasons);

        RetrievePlayerCareer rpc = new RetrievePlayerCareer();

        PlayerStatsAdapter adapter = new PlayerStatsAdapter(rpc.getPlayerStatsFromID(personId),this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }
}