package com.example.euronba.activities;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.adapters.TeamRosterAdapter;
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

        String teamId = data.getString("teamId");

        fillTeamInfo(teamId);

        fillTeamRoster(teamId);

        fillFavorite(teamId, favButton);

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
        tvTeamProfileConference.setText("Conf: " + tm.getConfName());
        tvTeamProfileDivision.setText("Div: " + tm.getDivName());
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

    public void fillFavorite(String teamId, ImageButton favButton) {
        // Instancia un objeto de la clase favorito
        Favorite favTeam = new Favorite();

        // Inicializa la propiedad Id
        favTeam.setId(teamId);

        // Si el equipo está en la tabla de favoritos, muestra el botón estrella amarilla
        if (favTeam.checkFav(TeamActivity.this) == true) {
            favButton.setImageResource(android.R.drawable.btn_star_big_on);
        }

        // Si el equipo no está en la tabla de favoritos, muestra el botón estrella apagada
        if (favTeam.checkFav(TeamActivity.this) == false) {
            favButton.setImageResource(android.R.drawable.btn_star_big_off);
        }
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Almacena en la variable si el equipo está en la lista de favoritos
                boolean isTeamFav = favTeam.checkFav(TeamActivity.this);

                // Si el equipo está, lo borra de la base de datos y pone la estrella apagada
                if (isTeamFav) {
                    favTeam.deleteFav(TeamActivity.this);
                    favButton.setImageResource(android.R.drawable.btn_star_big_off);
                    Toast.makeText(TeamActivity.this, "Removed from favorites", Toast.LENGTH_LONG).show();
                }

                // Si no está ya en la base de datos, lo almacena y pone la estrella encendida
                if (!isTeamFav) {

                    favTeam.setType("team");

                    favTeam.insertFav(TeamActivity.this);

                    favButton.setImageResource(android.R.drawable.btn_star_big_on);

                    Toast.makeText(TeamActivity.this, "Added to favorites", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}