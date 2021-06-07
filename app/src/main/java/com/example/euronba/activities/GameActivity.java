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
import com.example.euronba.adapters.BoxscoreAdapter;
import com.example.euronba.controller.RetrieveBoxscore;
import com.example.euronba.model.Boxscore;
import com.example.euronba.model.Team;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public static final String EXTRA_GAMEID = "gameId";
    public static final String EXTRA_SEASONSTAGEID = "seasonStageId";
    public static final String EXTRA_SEASONYEAR = "seasonYear";
    public static final String EXTRA_ARENANAME = "arenaName";
    public static final String EXTRA_ARENACITY = "arenaCity";
    public static final String EXTRA_STATUSNUM = "statusNum";
    public static final String EXTRA_STARTTIME = "startTime";
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
    public static final String EXTRA_DATE = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle data = getIntent().getExtras();

        displayGameInfo(data);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlayerBoxList);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerViewPlayerBoxList2);

        RetrieveBoxscore rpc = new RetrieveBoxscore();

        ArrayList<Boxscore> rpcBoth = rpc.getPlayersChart(data.getString("date"), data.getString("gameId"));

        if (rpcBoth.size() != 0) {
            ArrayList<Boxscore> rpcLocal = new ArrayList<>();
            ArrayList<Boxscore> rpcVisitor = new ArrayList<>();

            for (int i = 0; i < rpcBoth.size(); i++) {
                if (rpcBoth.get(i).getTeamId().equals(data.getString("localTeamId"))) {
                    rpcLocal.add(rpcBoth.get(i));
                }

                if (rpcBoth.get(i).getTeamId().equals(data.getString("visitorTeamId"))) {
                    rpcVisitor.add(rpcBoth.get(i));
                }
            }
            BoxscoreAdapter adapter = new BoxscoreAdapter(rpcLocal, this);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.setAdapter(adapter);

            BoxscoreAdapter adapter2 = new BoxscoreAdapter(rpcVisitor, this);

            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
            recyclerView2.setLayoutManager(linearLayoutManager2);

            recyclerView2.setAdapter(adapter2);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    // Rellena la información del partido con los datos recogidos de la actividad anterior.
    protected void displayGameInfo(Bundle data) {

        TextView tvSummaryText = (TextView) findViewById(R.id.tvGameSummaryText);
        TextView tvLocalTeamName = (TextView) findViewById(R.id.tvGameLocalTeamName);
        TextView tvLocalTeamWL = (TextView) findViewById(R.id.tvGameLocalTeamStandings);
        TextView tvVisitorTeamName = (TextView) findViewById(R.id.tvGameVisitorTeamName);
        TextView tvVisitorTeamWL = (TextView) findViewById(R.id.tvGameVisitorTeamStandings);
        TextView tvLocalScore = (TextView) findViewById(R.id.tvGameLocalScore);
        TextView tvVisitorScore = (TextView) findViewById(R.id.tvGameVisitorScore);
        TextView tvArena = (TextView) findViewById(R.id.tvGameArena);
        TextView tvClock = (TextView) findViewById(R.id.tvGameScoreClock);
        TextView tvDuration = (TextView) findViewById(R.id.tvGameDuration);
        TextView tvGameStart = (TextView) findViewById(R.id.tvGameStartTime);
        ImageView ivLocalTeamLogo = (ImageView) findViewById(R.id.ivGameLocalLogo);
        ImageView ivVisitorTeamLogo = (ImageView) findViewById(R.id.ivGameVisitorLogo);

        //Recuperamos el Bundle con los extras del Intent
        String localTeamId = data.getString("localTeamId");
        String visitorTeamId = data.getString("visitorTeamId");

        Team localTm = new Team().getTeamById(localTeamId, this);
        Team visitorTm = new Team().getTeamById(visitorTeamId, this);

        tvLocalTeamName.setText(localTm.getFullName());
        tvVisitorTeamName.setText(visitorTm.getFullName());

        ivLocalTeamLogo.setImageResource(localTm.getLogo());
        ivVisitorTeamLogo.setImageResource(visitorTm.getLogo());

        tvSummaryText.setText(data.getString("summaryText"));

        tvLocalTeamWL.setText(data.getString("localTeamWL"));
        tvVisitorTeamWL.setText(data.getString("visitorTeamWL"));

        tvLocalScore.setText(data.getString("localTeamScore"));
        tvVisitorScore.setText(data.getString("visitorTeamScore"));

        tvGameStart.setText("Start time: " + data.getString("startTime"));
        tvArena.setText(data.getString("arenaName") + " - " + data.getString("arenaCity"));

        tvDuration.setText("Game duration: " + data.getString("gameDuration"));

        if (data.getString("gameDuration").equals(":")) {
            tvDuration.setText("Yet to start.");
        }

        tvClock.setText(data.getString("currentPeriod"));

    }
}