package com.example.euronba.activities;

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
import com.example.euronba.adapters.FavoritesListAdapter;
import com.example.euronba.model.Favorite;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView nv = (NavigationView) findViewById(R.id.navigation_view);
        RecyclerView mainRecycler = (RecyclerView) findViewById(R.id.rvFavoritesList);

        setSupportActionBar(toolbar);

        // Método que contiene el listener del menú despleglable lateral
        startNavigationListener(nv);

        fillfavoriteList(mainRecycler);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView mainRecycler = (RecyclerView) findViewById(R.id.rvFavoritesList);

        fillfavoriteList(mainRecycler);
    }

    public void fillfavoriteList(RecyclerView mainRecycler) {

        ArrayList<Favorite> favList = new Favorite().getFavorites(this);
        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        mainRecycler = (RecyclerView) findViewById(R.id.rvFavoritesList);

        // Crea un objeto ScoreboardAdapter a partir del arrayList de partidos
        FavoritesListAdapter favAdapter = new FavoritesListAdapter(favList, FavoritesActivity.this);

        // Enlaza el objeto recyclerview al adaptador
        mainRecycler.setAdapter(favAdapter);

        // Crea un nuevo Layout para mostrar la lista de los RecyclerView
        mainRecycler.setLayoutManager(new LinearLayoutManager(FavoritesActivity.this));

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
                            intent = new Intent(FavoritesActivity.this, PlayerListActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Home"
                        if (id == R.id.menu_home) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(FavoritesActivity.this, MainActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Teams"
                        if (id == R.id.menu_teams) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(FavoritesActivity.this, TeamListActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Standings"
                        if (id == R.id.menu_standings) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(FavoritesActivity.this, StandingsActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Playoffs"
                        if (id == R.id.menu_playoffs) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(FavoritesActivity.this, PlayOffsActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Favorites"
                        if (id == R.id.menu_favorites) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(FavoritesActivity.this, FavoritesActivity.class);
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