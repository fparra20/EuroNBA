package com.example.euronba.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.adapters.PlayoffsAdapter;
import com.example.euronba.model.PlayoffsBracket;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class PlayOffsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView eastRecycler;
    RecyclerView westRecycler;
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_offs);

        // Almacena la barra de herramientas a partir de su ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Establece la barra de herramientas.
        setSupportActionBar(toolbar);

        // Permite que el botoón homburguesa (3 rayas, el del menú lateral) aparezca.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Crea el menú lateral
        setDrawerLayout();

        // Rellena el spinner y controla su funcionamiento
        fillSpinner();

        // Por defecto, rellena los datos con la primera ronda
        fillPlayOffs("1");

    }


    // Método que controla el pulsado sobre el botón de abrir el menú lateral
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Método que crea el menú lateral y controla su funcionamiento
    public void setDrawerLayout(){

        // El menú lateral a partir de su ID
        NavigationView nv = findViewById(R.id.navigation_view);

        // Método que contiene el listener del menú despleglable lateral
        startNavigationListener(nv);

        // Almacena el objeto DrawerLayout
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        // Instancia un objeto ActionBarDrawerToggle, necesario para desplegar el menú lateral
        // desde la barra superior.
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // Añade un Listener a la actividad
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Sincroniza el estado del objeto creado con el objeto presente en la actividad
        actionBarDrawerToggle.syncState();
    }

    // Método que controla qué opción del Spinner ha sido elegida
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        switch (position) {

            // Si se elige la primera opción
            case 0:

                // La actividad se rellena con partidos de la primera ronda
                fillPlayOffs("1");
                break;

            // Si se elige la segunda opción
            case 1:

                // La actividad se rellena con partidos de la segunda ronda
                fillPlayOffs("2");
                break;

            // Si se elige la tercera opción
            case 2:

                // La actividad se rellena con partidos de final de conferencia
                fillPlayOffs("3");
                break;

            // Si se elige la cuarta opción
            case 3:

                // La actividad se rellena el partido de la Final
                fillPlayOffs("4");
                break;
        }
    }

    // Método que es llamado cuando aún no se ha seleccionado ninguna opción
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void fillPlayOffs(String round) {

        // Almacena la lista de partidos en el Este
        ArrayList<PlayoffsBracket> poEastBracket = new PlayoffsBracket().getPlayOffsBracketByYearConfRound(2020, "East", round);

        // Almacena la lista de partidos en el Oeste
        ArrayList<PlayoffsBracket> poWestBracket = new PlayoffsBracket().getPlayOffsBracketByYearConfRound(2020, "West", round);

        // Controla que la ronda sea 4, o sea, la final de la liga.
        if (round.equals("4")) {

            // Almacena la final.
            poEastBracket = new PlayoffsBracket().getPlayOffsBracketByYearConfRound(2020, "NBA Finals", round);
        }

        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        eastRecycler = findViewById(R.id.rvPlayOffsEast);

        // Crea un objeto PlayOffsAdapter a partir del arrayList de partidos
        PlayoffsAdapter eastAdapter = new PlayoffsAdapter(poEastBracket, this);

        // Enlaza el objeto recyclerview al adaptador
        eastRecycler.setAdapter(eastAdapter);

        // Crea un nuevo Layout para enlazar al RecyclerView y poder mostrarlo
        eastRecycler.setLayoutManager(new LinearLayoutManager(this));

        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        westRecycler = findViewById(R.id.rvPlayOffsWest);

        // Crea un objeto ScoreboardAdapter a partir del arrayList de partidos
        PlayoffsAdapter westAdapter = new PlayoffsAdapter(poWestBracket, this);

        // Enlaza el objeto recyclerview al adaptador
        westRecycler.setAdapter(westAdapter);

        // Crea un nuevo Layout para enlazar al RecyclerView y poder mostrarlo
        westRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void startNavigationListener(NavigationView nv) {

        // Implementa el listener para el menú lateral desplegrable.
        nv.setNavigationItemSelectedListener(
                item -> {

                    // Variable que almacena el id de la opción seleccionada.
                    int id = item.getItemId();

                    // Crea una variable de tipo intent que se rellenará según la opción
                    Intent intent = null;

                    // Controla que se haya pulsado sobre la opción "Players"
                    if (id == R.id.menu_players) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(PlayOffsActivity.this, PlayerListActivity.class);
                    }

                    // Controla que se haya pulsado sobre la opción "Home"
                    if (id == R.id.menu_home) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(PlayOffsActivity.this, MainActivity.class);
                    }

                    // Controla que se haya pulsado sobre la opción "Teams"
                    if (id == R.id.menu_teams) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(PlayOffsActivity.this, TeamListActivity.class);
                    }

                    // Controla que se haya pulsado sobre la opción "Standings"
                    if (id == R.id.menu_standings) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(PlayOffsActivity.this, StandingsActivity.class);
                    }

                    // Controla que se haya pulsado sobre la opción "Playoffs"
                    if (id == R.id.menu_playoffs) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(PlayOffsActivity.this, PlayOffsActivity.class);
                    }

                    // Controla que se haya pulsado sobre la opción "Favorites"
                    if (id == R.id.menu_favorites) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(PlayOffsActivity.this, FavoritesActivity.class);
                    }

                    // Controla que intent tenga algún valor de los anteriores
                    if (intent != null)

                        // Inicia la actividad correspondiente
                        startActivity(intent);

                    // Finaliza la actividad actual
                    finish();

                    return true;
                });
    }

    public void fillSpinner(){

        String[] rounds = {getString(R.string.firstRound), getString(R.string.secondRound),
                getString(R.string.confFinal), getString(R.string.finals)};

        // Obtiene el objeto Spinner a partir del ID
        spin = findViewById(R.id.spinner1);

        // Genera un adaptador de array para el spinner a partir de la variable rounds
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, rounds);

        // Vincula el adaptador con el spinner
        spin.setAdapter(adapter);

        // Añade un listener al spinner.
        spin.setOnItemSelectedListener(this);
    }
}