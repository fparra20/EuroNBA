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
import com.example.euronba.adapters.StandingsAdapter;
import com.example.euronba.adapters.TeamRosterAdapter;
import com.example.euronba.model.Player;
import com.example.euronba.model.Standings;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class StandingsActivity extends AppCompatActivity {

    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView nv = (NavigationView) findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.menu_players) {
                            Intent intent = new Intent(StandingsActivity.this, PlayerListActivity.class);
                            startActivity(intent);
                        }
                        if (id == R.id.menu_home) {
                            Intent intent = new Intent(StandingsActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        if (id == R.id.menu_teams) {
                            Intent intent = new Intent(StandingsActivity.this, TeamListActivity.class);
                            startActivity(intent);
                        }

                        if (id == R.id.menu_standings) {
                            Intent intent = new Intent(StandingsActivity.this, StandingsActivity.class);
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

        RecyclerView recyclerViewEast = findViewById(R.id.recyclerViewStandingsEast);

        ArrayList<Standings> stEast = new Standings().getStandingsEast();

        StandingsAdapter adapterEast = new StandingsAdapter(stEast, this);

        LinearLayoutManager linearLayoutManagerEast = new LinearLayoutManager(this);

        recyclerViewEast.setLayoutManager(linearLayoutManagerEast);

        recyclerViewEast.setAdapter(adapterEast);

        // OESTE
        RecyclerView recyclerViewWest = findViewById(R.id.recyclerViewStandingsWest);

        ArrayList<Standings> stWest = new Standings().getStandingsWest();

        StandingsAdapter adapterWest = new StandingsAdapter(stWest, this);

        LinearLayoutManager linearLayoutManagerWest = new LinearLayoutManager(this);

        recyclerViewWest.setLayoutManager(linearLayoutManagerWest);

        recyclerViewWest.setAdapter(adapterWest);

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}