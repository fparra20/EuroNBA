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

        // Almacena la barra de herramientas a partir de su ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Establece la barra de herramientas.
        setSupportActionBar(toolbar);

        // Permite que el botoón homburguesa (3 rayas, el del menú lateral) aparezca.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Crea el menú lateral
        setDrawerLayout();

        // Creamos el adaptador para el viewpager
        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());

        // Creamos el viewpager
        ViewPager pager = findViewById(R.id.pager);

        // Vinculamos el viewpager con el adaptador
        pager.setAdapter(pagerAdapter);

        // Creamos un tablayout
        TabLayout tabLayout = findViewById(R.id.tabs);

        // Añadimos el viewPager al tabLayout
        tabLayout.setupWithViewPager(pager);
    }

    // Método que crea el menú lateral y controla su funcionamiento
    private void setDrawerLayout() {

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

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Controla el funcionamiento de las pestañas
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        // Método que controla qué fragment se muestra a partir de la pestaña seleccionada
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {

                // En caso de seleccionar la primera pestaña
                case 0:

                    // Se muestra la clasificación de la conferencia Este
                    return new EastStandingsFragment();

                // En caso de seleccionar la segunda pestaña
                case 1:

                    // Se muestra la clasificación de la conferencia Oeste
                    return new WestStandingsFragment();
            }
            return null;
        }

        // Indica las pestañas que hay
        @Override
        public int getCount() {
            return 2;
        }

        // Indica el título de las pestañas
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {

                // Para la primera pestaña, conferencia Este
                case 0:
                    return getString(R.string.east);

                // Para la segunda pestaña, conferencia Oeste
                case 1:
                    return getString(R.string.west);
            }
            return null;
        }
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
                });
    }
}