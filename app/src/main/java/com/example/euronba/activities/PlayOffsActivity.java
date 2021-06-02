package com.example.euronba.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.euronba.R;
import com.example.euronba.adapters.PlayerListAdapter;
import com.example.euronba.adapters.PlayoffsAdapter;
import com.example.euronba.model.Player;
import com.example.euronba.model.PlayoffsBracket;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class PlayOffsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView eastRecycler;
    RecyclerView westRecycler;
    Spinner spin;
    String[] rounds = { "First Round", "Conference Semifinals", "Conference Finals", "The Finals" };

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

                        if (id == R.id.menu_playoffs) {
                            Intent intent = new Intent(PlayOffsActivity.this, PlayOffsActivity.class);
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

        spin = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, rounds);

        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(this);

}
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        switch (position){
            case 0 : populateRecyclerView("1"); break;
            case 1 : populateRecyclerView("2"); break;
            case 2 : populateRecyclerView("3"); break;
            case 3 : populateRecyclerView("4"); break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        populateRecyclerView("1");
    }

    public void populateRecyclerView(String round){

        ArrayList<PlayoffsBracket> poEastBracket = new PlayoffsBracket().getPlayOffsBracketByYearConfRound(2020,"East", round);

        ArrayList<PlayoffsBracket> poWestBracket = new PlayoffsBracket().getPlayOffsBracketByYearConfRound(2020,"West", round);

        if(round.equals("4")){
            poEastBracket = new PlayoffsBracket().getPlayOffsBracketByYearConfRound(2020,"NBA Finals", round);
        }
        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        eastRecycler = (RecyclerView) findViewById(R.id.rvPlayOffsEast);

        // Crea un objeto ScoreboardAdapter a partir del arrayList de partidos
        PlayoffsAdapter eastAdapter = new PlayoffsAdapter(poEastBracket, this);

        // Enlaza el objeto recyclerview al adaptador
        eastRecycler.setAdapter(eastAdapter);

        // Crea un nuevo Layout para mostrar la lista de los RecyclerView
        eastRecycler.setLayoutManager(new LinearLayoutManager(this));


        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        westRecycler = (RecyclerView) findViewById(R.id.rvPlayOffsWest);

        // Crea un objeto ScoreboardAdapter a partir del arrayList de partidos
        PlayoffsAdapter westAdapter = new PlayoffsAdapter(poWestBracket, this);

        // Enlaza el objeto recyclerview al adaptador
        westRecycler.setAdapter(westAdapter);

        // Enlaza el objeto recyclerview al adaptador
        westRecycler.setLayoutManager(new LinearLayoutManager(this));

    }
}