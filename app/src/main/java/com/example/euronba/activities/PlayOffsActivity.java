package com.example.euronba.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.euronba.R;
import com.example.euronba.adapters.PlayerListAdapter;
import com.example.euronba.adapters.PlayoffsAdapter;
import com.example.euronba.model.Player;
import com.example.euronba.model.PlayoffsBracket;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class PlayOffsActivity extends AppCompatActivity {

    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_offs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView nv = (NavigationView) findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.menu_players) {
                            Intent intent = new Intent(PlayOffsActivity.this, PlayerListActivity.class);
                            startActivity(intent);
                        }
                        if (id == R.id.menu_home) {
                            Intent intent = new Intent(PlayOffsActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        if (id == R.id.menu_teams) {
                            Intent intent = new Intent(PlayOffsActivity.this, TeamListActivity.class);
                            startActivity(intent);
                        }

                        if (id == R.id.menu_standings) {
                            Intent intent = new Intent(PlayOffsActivity.this, StandingsPOActivity.class);
                            startActivity(intent);
                        }
                        finish();
                        return true;
                    }
                });

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayList<PlayoffsBracket> poEastBracket = new PlayoffsBracket().getPlayOffsBracketByYear(2020);

        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        RecyclerView mainRecycler = (RecyclerView) findViewById(R.id.rvPlayOffsEast);

        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        RecyclerView secondRecycler = (RecyclerView) findViewById(R.id.rvPlayOffsWest);

        // Crea un objeto ScoreboardAdapter a partir del arrayList de partidos
        PlayoffsAdapter plAdapter = new PlayoffsAdapter(poEastBracket, this);

        // Enlaza el objeto recyclerview al adaptador
        mainRecycler.setAdapter(plAdapter);

        // Enlaza el objeto recyclerview al adaptador
        secondRecycler.setAdapter(plAdapter);

        // Crea un nuevo Layout para mostrar la lista de los RecyclerView
        mainRecycler.setLayoutManager(new LinearLayoutManager(this));

        // Enlaza el objeto recyclerview al adaptador
        secondRecycler.setLayoutManager(new LinearLayoutManager(this));



    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}