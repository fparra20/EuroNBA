package com.example.euronba.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.euronba.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class StandingsActivity extends AppCompatActivity {

    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings_poactivity);

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

        // Creamos el adaptador para el viewpager
        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());

        // Creamos el viewpager
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        // Vinculamos el viewpager con el adaptador
        pager.setAdapter(pagerAdapter);

        // Creamos un tablayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Crearemos las dos pestañas y y que cada una muestre un contenido fragment
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new EastStandingsFragment();
                case 1:
                    return new WestStandingsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "EAST";
                case 1:
                    return "WEST";
            }
            return null;
        }
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
                            intent = new Intent(StandingsActivity.this, PlayerListActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Home"
                        if (id == R.id.menu_home) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(StandingsActivity.this, MainActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Teams"
                        if (id == R.id.menu_teams) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(StandingsActivity.this, TeamListActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Standings"
                        if (id == R.id.menu_standings) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(StandingsActivity.this, StandingsActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Playoffs"
                        if (id == R.id.menu_playoffs) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(StandingsActivity.this, PlayOffsActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Favorites"
                        if (id == R.id.menu_favorites) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(StandingsActivity.this, FavoritesActivity.class);
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