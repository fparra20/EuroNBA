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
import com.example.euronba.adapters.TeamListAdapter;
import com.example.euronba.model.Team;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class TeamListActivity extends AppCompatActivity {


    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        // Almacena la barra de herramientas a partir de su ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Establece la barra de herramientas.
        setSupportActionBar(toolbar);

        // Permite que el botoón homburguesa (3 rayas, el del menú lateral) aparezca.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Crea el menú lateral
        setDrawerLayout();

        // Rellena la lista de equipos
        fillTeamList();
    }

    private void fillTeamList() {

        // Obtiene la lista de equipos
        ArrayList<Team> tList = new Team().getAllTeams(TeamListActivity.this);

        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        RecyclerView mainRecycler = findViewById(R.id.rvTeamList);

        // Crea un objeto ScoreboardAdapter a partir del arrayList de partidos
        TeamListAdapter tmAdapter = new TeamListAdapter(tList, TeamListActivity.this);

        // Enlaza el objeto recyclerview al adaptador
        mainRecycler.setAdapter(tmAdapter);

        // Crea un nuevo Layout para mostrar la lista de los RecyclerView
        mainRecycler.setLayoutManager(new LinearLayoutManager(TeamListActivity.this));

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
                        intent = new Intent(TeamListActivity.this, PlayerListActivity.class);
                    }

                    // Controla que se haya pulsado sobre la opción "Home"
                    if (id == R.id.menu_home) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(TeamListActivity.this, MainActivity.class);
                    }

                    // Controla que se haya pulsado sobre la opción "Teams"
                    if (id == R.id.menu_teams) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(TeamListActivity.this, TeamListActivity.class);
                    }

                    // Controla que se haya pulsado sobre la opción "Standings"
                    if (id == R.id.menu_standings) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(TeamListActivity.this, StandingsActivity.class);
                    }

                    // Controla que se haya pulsado sobre la opción "Playoffs"
                    if (id == R.id.menu_playoffs) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(TeamListActivity.this, PlayOffsActivity.class);
                    }

                    // Controla que se haya pulsado sobre la opción "Favorites"
                    if (id == R.id.menu_favorites) {

                        // Crea un intent que abre la actividad correspondiente
                        intent = new Intent(TeamListActivity.this, FavoritesActivity.class);
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

}