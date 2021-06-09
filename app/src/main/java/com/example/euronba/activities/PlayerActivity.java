package com.example.euronba.activities;


import android.os.Bundle;
import android.view.MenuItem;
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
import com.example.euronba.controller.RetrievePlayerStats;
import com.example.euronba.model.Favorite;
import com.example.euronba.model.Player;
import com.example.euronba.model.PlayerStats;
import com.example.euronba.model.Team;

public class PlayerActivity extends AppCompatActivity {

    // Declara los datos necesarios que llegan de la actividad anterior
    public static final String EXTRA_PERSONID = "personId";
    public static final String EXTRA_TEAMURL = "teamUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // Almacena la barra de herramientas a partir de su ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Establece la barra de herramientas.
        setSupportActionBar(toolbar);

        // Permite que el botón para volver atrás aparezca.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Instancia un objeto Bundle para obtener los datos que provengan de la actividad anterior
        Bundle data = getIntent().getExtras();

        // Almacena el ID del jugador
        String personId = data.getString("personId");

        // Almacena el url del equipo
        String teamUrl = data.getString("teamUrl");

        // Crea un objeto Player a partir de los datos obtenidos.
        Player p = new Player().getPlayerProfileFromId(personId, teamUrl);

        // Rellena la información personal del jugador
        fillPlayerInfo(p);

        // Rellena las estadísticas del jugador
        fillPlayerStats(personId);

        // Controla el botón de favorito.
        fillFavorite(p);
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

    // Método que rellena la información personal del jugador
    public void fillPlayerInfo(Player p) {

        // Instancia un objeto TextView para cada uno de los elementos que queremos rellenar
        TextView tvPlayerProfileName = findViewById(R.id.tvPlayerProfileName);
        TextView tvPlayerProfilePos = findViewById(R.id.tvPlayerProfilePos);
        TextView tvPlayerProfileBirthdate = findViewById(R.id.tvPlayerProfileBirthdate);
        TextView tvPlayerProfileCollege = findViewById(R.id.tvPlayerProfileCollege);
        TextView tvPlayerProfileHeight = findViewById(R.id.tvPlayerProfileHeight);
        TextView tvPlayerProfileWeight = findViewById(R.id.tvPlayerProfileWeight);
        TextView tvPlayerProfileYearsPro = findViewById(R.id.tvPlayerProfileYearsPro);
        ImageView ivPlayerProfileTeamLogo = findViewById(R.id.ivPlayerProfileTeamLogo);


        // Rellenamos todos los TextView a partir de los datos del bundle
        tvPlayerProfileName.setText(p.getFullName());

        tvPlayerProfilePos.setText(getString(R.string.playerJerseyPos, p.getJersey(), p.getPos()));

        tvPlayerProfileBirthdate.setText(getString(R.string.playerBirthAge, p.getDateOfBirthUTC(),p.getAge()));

        // Creamos un objeto Team para obtener el logo.
        Team tmCurrent = new Team().getTeamById(p.getTeamId(), this.getApplicationContext());

        tvPlayerProfileCollege.setText(getString(R.string.playerCollege,p.getCollegeName()));

        tvPlayerProfileHeight.setText(getString(R.string.playerHeight,p.getHeight()));

        tvPlayerProfileWeight.setText(getString(R.string.playerWeight,p.getWeight()));

        tvPlayerProfileYearsPro.setText(getString(R.string.playerYearsPro,p.getYearsPro()));

        ivPlayerProfileTeamLogo.setImageResource(tmCurrent.getLogo());
    }

    // Método que crea la tabla con las estadísticas del jugador
    public void fillPlayerStats(String personId) {

        // Almacena el objeto recyclerview presente en la actividad
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlayerProfileSeasons);

        // Instancia un objeto PlayerStats
        PlayerStats pStats = new PlayerStats();

        // Instancia un adaptador para PlayerStats a partir de la lista generada
        PlayerStatsAdapter adapter = new PlayerStatsAdapter(pStats.getPlayerStatsById(personId), PlayerActivity.this);

        // Crea un nuevo Layout para mostrar la lista de los RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(PlayerActivity.this));

        // Enlaza el adaptador al recyclerview
        recyclerView.setAdapter(adapter);
    }

    // Método que controla el botón de favorito
    public void fillFavorite(Player p) {

        ImageButton favButton = findViewById(R.id.ibFavPlayer);

        // Instancia un objeto de la clase favorito
        Favorite favTeam = new Favorite();

        String personId = p.getPersonId();
        String teamUrl = new Team().getTeamById(p.getTeamId(), PlayerActivity.this).getUrlName();
        String personName = p.getFullName();

        // Inicializa la propiedad Id
        favTeam.setId(personId);

        // Si el equipo está en la tabla de favoritos, muestra el botón estrella amarilla
        if (favTeam.checkFav(PlayerActivity.this)) {
            favButton.setImageResource(android.R.drawable.btn_star_big_on);
        }

        // Si el equipo no está en la tabla de favoritos, muestra el botón estrella apagada
        if (!favTeam.checkFav(PlayerActivity.this)) {
            favButton.setImageResource(android.R.drawable.btn_star_big_off);
        }
        favButton.setOnClickListener(v -> {

            // Almacena en la variable si el equipo está en la lista de favoritos
            boolean isTeamFav = favTeam.checkFav(PlayerActivity.this);

            // Si el equipo está, lo borra de la base de datos y pone la estrella apagada
            if (isTeamFav) {
                favTeam.deleteFav(PlayerActivity.this);
                favButton.setImageResource(android.R.drawable.btn_star_big_off);

                // Muestra un mensaje Toast
                Toast.makeText(PlayerActivity.this, R.string.removedFavorites, Toast.LENGTH_LONG).show();
            }

            // Si no está ya en la base de datos, lo almacena y pone la estrella encendida
            if (!isTeamFav) {

                favTeam.setType("player");
                favTeam.setTeamUrl(teamUrl);
                favTeam.setPersonName(personName);

                favTeam.setFavorite(PlayerActivity.this);

                favButton.setImageResource(android.R.drawable.btn_star_big_on);

                // Muestra un mensaje Toast
                Toast.makeText(PlayerActivity.this, R.string.addedFavorites, Toast.LENGTH_LONG).show();

            }
        });
    }
}