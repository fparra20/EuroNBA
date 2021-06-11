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

        // Almacena la barra de herramientas a partir de su ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Establece la barra de herramientas.
        setSupportActionBar(toolbar);

        // Permite que el botón para volver atrás aparezca.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Instancia un objeto Bundle para obtener los datos que provengan de la actividad anterior
        Bundle data = getIntent().getExtras();

        // Almacena el ID del equipo
        String teamId = data.getString("teamId");

        // Rellena la información del equipo
        fillTeamInfo(teamId);

        // Rellena la información de los jugadores
        fillTeamRoster(teamId);

        // Controla el botón de favorito
        fillFavorite(teamId);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Controla lo que pasa cuando se pulsa el botón de atrás.
        if (item.getItemId() == android.R.id.home) {

            // Cierra la actividad y vuelve a la anterior.
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // Método que rellena la información del equipo
    public void fillTeamInfo(String teamId) {

        // Obtiene los datos de un equipo según el ID
        Team tm = new Team().getTeamById(teamId, TeamActivity.this);

        // Obitene los objetos que queremos rellenar del Layout
        TextView tvTeamProfileName = findViewById(R.id.tvTeamProfileName);
        TextView tvTeamProfileConference = findViewById(R.id.tvTeamProfileConference);
        TextView tvTeamProfileDivision = findViewById(R.id.tvTeamProfileDivision);
        ImageView ivTeamProfileLogo = findViewById(R.id.ivTeamProfileLogo);

        // Rellena los datos del layout
        tvTeamProfileName.setText(tm.getFullName());
        tvTeamProfileConference.setText(getString(R.string.teamConf,tm.getConfName()));
        tvTeamProfileDivision.setText(getString(R.string.teamDiv,tm.getDivName()));
        ivTeamProfileLogo.setImageResource(tm.getLogo());
    }

    // Método que rellena la plantilla del equipo
    public void fillTeamRoster(String teamId) {

        // Obtiene los datos de un equipo según el ID
        Team tm = new Team().getTeamById(teamId, TeamActivity.this);

        // Obtiene el objeto recyclerview presente en la actividad
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTeamProfileRoster);

        // Instancia un objeto Player
        Player p = new Player();

        // Instancia un adaptador para Team a partir de la lista generada.
        // Este adapter necesita una lista de jugadores, que se optiene desde la clase Player
        TeamRosterAdapter adapter = new TeamRosterAdapter(p.getPlayersByTeamUrl(tm.getUrlName()), this);

        // Crea un nuevo Layout para mostrar la lista de los RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Enlaza el adaptador al recyclerview
        recyclerView.setAdapter(adapter);
    }

    public void fillFavorite(String teamId) {

        ImageButton favButton = findViewById(R.id.ibFavTeam);

        // Instancia un objeto de la clase favorito
        Favorite favTeam = new Favorite();

        // Inicializa la propiedad Id
        favTeam.setId(teamId);

        // Si el equipo está en la tabla de favoritos, muestra el botón estrella amarilla
        if (favTeam.checkFav(TeamActivity.this)) {
            favButton.setImageResource(android.R.drawable.btn_star_big_on);
        }

        // Si el equipo no está en la tabla de favoritos, muestra el botón estrella apagada
        if (!favTeam.checkFav(TeamActivity.this)) {
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
                    Toast.makeText(TeamActivity.this, R.string.removedFavorites, Toast.LENGTH_LONG).show();
                }

                // Si no está ya en la base de datos, lo almacena y pone la estrella encendida
                if (!isTeamFav) {

                    // Para los equipos se inicializa el tipo y el id
                    favTeam.setType("team");

                    // Habiendo inicializado id y type, se añade como favorito
                    favTeam.setFavorite(TeamActivity.this);

                    favButton.setImageResource(android.R.drawable.btn_star_big_on);

                    // Muestra un mensaje Toast
                    Toast.makeText(TeamActivity.this, R.string.addedFavorites, Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}