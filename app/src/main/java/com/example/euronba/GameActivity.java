package com.example.euronba;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.euronba.model.Team;
import com.example.euronba.model.TeamScore;

public class GameActivity extends AppCompatActivity {

    public static final String EXTRA_GAMEID = "gameId";
    public static final String EXTRA_SEASONSTAGEID = "seasonStageId";
    public static final String EXTRA_SEASONYEAR = "seasonYear";
    public static final String EXTRA_ARENANAME = "arenaName";
    public static final String EXTRA_ARENACITY = "arenaCity";
    public static final String EXTRA_STATUSNUM = "statusNum";
    public static final String EXTRA_STARTTIME = "startTimeUTC";
    public static final String EXTRA_CLOCK = "clock";
    public static final String EXTRA_GAMEDURATION = "gameDuration";
    public static final String EXTRA_CURRENTPERIOD = "currentPeriod";
    public static final String EXTRA_VISITORTEAMID = "visitorTeamId";
    public static final String EXTRA_VISITORTEAMWL = "visitorTeamWL";
    public static final String EXTRA_VISITORTEAMSCORE = "visitorTeamScore";
    public static final String EXTRA_LOCALTEAMID = "localTeamId";
    public static final String EXTRA_LOCALTEAMWL = "localTeamWL";
    public static final String EXTRA_LOCALTEAMSCORE = "localTeamScore";
    public static final String EXTRA_SUMMARYTEXT = "summaryText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView tvSummaryText = (TextView) findViewById(R.id.tvGameSummaryText);
        TextView tvLocalTeamName = (TextView) findViewById(R.id.tvGameLocalTeamName);
        TextView tvLocalTeamWL = (TextView) findViewById(R.id.tvGameLocalTeamStandings);
        TextView tvVisitorTeamName = (TextView) findViewById(R.id.tvGameVisitorTeamName);
        TextView tvVisitorTeamWL = (TextView) findViewById(R.id.tvGameVisitorTeamStandings);
        TextView tvLocalScore = (TextView) findViewById(R.id.tvGameLocalScore);
        TextView tvVisitorScore = (TextView) findViewById(R.id.tvGameVisitorScore);
        TextView tvArena  = (TextView) findViewById(R.id.tvGameArena);
        TextView tvClock = (TextView) findViewById(R.id.tvGameScoreClock);
        TextView tvDuration = (TextView) findViewById(R.id.tvGameDuration);
        TextView tvGameStart = (TextView) findViewById(R.id.tvGameStartTime);
        ImageView ivLocalTeamLogo = (ImageView) findViewById(R.id.ivGameLocalLogo);
        ImageView ivVisitorTeamLogo = (ImageView) findViewById(R.id.ivGameVisitorLogo);

        //Recuperamos el Bundle con los extras del Intent
        Bundle data = getIntent().getExtras();
        String localTeamId = data.getString(EXTRA_LOCALTEAMID);
        String visitorTeamId = data.getString(EXTRA_VISITORTEAMID);

        Team localTm = new Team().getTeamById(localTeamId, this);
        Team visitorTm = new Team().getTeamById(visitorTeamId, this);

        tvLocalTeamName.setText(localTm.getFullName());
        tvVisitorTeamName.setText(visitorTm.getFullName());

        ivLocalTeamLogo.setImageResource(localTm.getLogo());
        ivVisitorTeamLogo.setImageResource(visitorTm.getLogo());

        tvSummaryText.setText(data.getString(EXTRA_SUMMARYTEXT));

        tvLocalTeamWL.setText(data.getString(EXTRA_LOCALTEAMWL));
        tvVisitorTeamWL.setText(data.getString(EXTRA_VISITORTEAMWL));

        tvLocalScore.setText(data.getString(EXTRA_LOCALTEAMSCORE));
        tvVisitorScore.setText(data.getString(EXTRA_VISITORTEAMSCORE));

        tvGameStart.setText("Start time: " + data.getString(EXTRA_STARTTIME));
        tvArena.setText(data.getString(EXTRA_ARENANAME) + " - " + data.getString(EXTRA_ARENACITY));

        tvDuration.setText("Game duration: "+ data.getString(EXTRA_GAMEDURATION));

        if(data.getString(EXTRA_GAMEDURATION).equals(":")){
            tvDuration.setText("Yet to start.");
        }

        tvClock.setText(data.getString(EXTRA_CURRENTPERIOD));

        LinearLayout cv = (LinearLayout) findViewById(R.id.gameLayout);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActi = new Intent(getApplicationContext(), PlayerActivity.class);

                startActivity(newActi);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}