package com.example.euronba.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.adapters.PlayerListAdapter;
import com.example.euronba.model.Player;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class PlayerListActivity extends AppCompatActivity {

    public ActionBarDrawerToggle actionBarDrawerToggle;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView nv = (NavigationView) findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        // Método que contiene el listener del menú despleglable lateral
        startNavigationListener(nv);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Player> pList = new Player().getAllPlayers();
        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        RecyclerView mainRecycler = (RecyclerView) findViewById(R.id.rvPlayerList);

        // Crea un objeto ScoreboardAdapter a partir del arrayList de partidos
        PlayerListAdapter plAdapter = new PlayerListAdapter(pList, PlayerListActivity.this);

        // Enlaza el objeto recyclerview al adaptador
        mainRecycler.setAdapter(plAdapter);

        // Crea un nuevo Layout para mostrar la lista de los RecyclerView
        mainRecycler.setLayoutManager(new LinearLayoutManager(PlayerListActivity.this));
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startNavigationListener(NavigationView nv) {

        // Implementa el listener para el menú lateral desplegrable.
        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {

                        // Variable que almacena el id de la opción seleccionada.
                        int id = item.getItemId();

                        // Crea una variable de tipo intent que se rellenará según la opción
                        Intent intent = null;

                        // Controla que se haya pulsado sobre la opción "Players"
                        if (id == R.id.menu_players) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(PlayerListActivity.this, PlayerListActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Home"
                        if (id == R.id.menu_home) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(PlayerListActivity.this, MainActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Teams"
                        if (id == R.id.menu_teams) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(PlayerListActivity.this, TeamListActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Standings"
                        if (id == R.id.menu_standings) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(PlayerListActivity.this, StandingsActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Playoffs"
                        if (id == R.id.menu_playoffs) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(PlayerListActivity.this, PlayOffsActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Favorites"
                        if (id == R.id.menu_favorites) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(PlayerListActivity.this, FavoritesActivity.class);
                        }

                        // Controla que intent tenga algún valor de los anteriores
                        if (intent != null)

                            // Inicia la actividad correspondiente
                            startActivity(intent);

                        // Finaliza la actividad actual
                        finish();

                        return true;
                    }
                });
    }
}