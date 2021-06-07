package com.example.euronba.activities;


import android.media.Image;
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

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fillPlayerInfo(p);

        fillPlayerStats(personId);

        fillFavorite(p, favButton);
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

    public void fillFavorite(Player p, ImageButton favButton){
        // Instancia un objeto de la clase favorito
        Favorite favTeam = new Favorite();

        String personId = p.getPersonId();
        String teamUrl = new Team().getTeamById(p.getTeamId(), PlayerActivity.this).getUrlName();
        String personName = p.getFirstName() + " " + p.getLastName();

        // Inicializa la propiedad Id
        favTeam.setId(personId);

        // Si el equipo está en la tabla de favoritos, muestra el botón estrella amarilla
        if (favTeam.checkFav(PlayerActivity.this) == true) {
            favButton.setImageResource(android.R.drawable.btn_star_big_on);
        }

        // Si el equipo no está en la tabla de favoritos, muestra el botón estrella apagada
        if (favTeam.checkFav(PlayerActivity.this) == false) {
            favButton.setImageResource(android.R.drawable.btn_star_big_off);
        }
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Almacena en la variable si el equipo está en la lista de favoritos
                boolean isTeamFav = favTeam.checkFav(PlayerActivity.this);

                // Si el equipo está, lo borra de la base de datos y pone la estrella apagada
                if (isTeamFav) {
                    favTeam.deleteFav(PlayerActivity.this);
                    favButton.setImageResource(android.R.drawable.btn_star_big_off);
                    Toast.makeText(PlayerActivity.this, "Removed from favorites", Toast.LENGTH_LONG).show();
                }

                // Si no está ya en la base de datos, lo almacena y pone la estrella encendida
                if (!isTeamFav) {

                    favTeam.setType("player");
                    favTeam.setTeamUrl(teamUrl);
                    favTeam.setPersonName(personName);

                    favTeam.insertFav(PlayerActivity.this);

                    favButton.setImageResource(android.R.drawable.btn_star_big_on);

                    Toast.makeText(PlayerActivity.this, "Added to favorites", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}