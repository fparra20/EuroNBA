package com.example.euronba.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.adapters.ScoreboardAdapter;
import com.example.euronba.model.Scoreboard;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public ActionBarDrawerToggle actionBarDrawerToggle;

    ArrayList<Scoreboard> scoreboardList;

    // Crea un objeto textView para mostrar la fecha a partir del objeto presente en el layout
    TextView tvDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Crea un objeto ImageButton para el calendario a partir del objeto presente en el layout
        ImageButton ibCalendar = (ImageButton) findViewById(R.id.ibCalendar);

        tvDay = (TextView) findViewById(R.id.tvDay);

        String date = getTodayDate();

        // Llama al método que genera el recyclerview según la fecha
        showGamesByDate(date);


        // Añade un ClickListener al botón del calendario
        ibCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si se ha pulsado el botón con el calendario
                if (v == ibCalendar) {

                    // Instancia 3 variables de tipo int, para guardar día mes y año actual.
                    int mYear, mMonth, mDay;

                    String[] date = tvDay.getText().toString().split(" - ");

                    mYear = Integer.parseInt(date[2]);
                    mMonth = Integer.parseInt(date[1]) - 1;
                    mDay = Integer.parseInt(date[0]);

                    // Instancia un objeto DatePickerDialog, con el que podremos seleccionar la fecha
                    // y recibir todos los partidos jugados en la misma
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_HOLO_DARK,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                // Indica lo que pasa cuando se cambia de fecha.
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    // Almacena el año seleccionado como String
                                    String y = String.valueOf(year);

                                    // Almacena el mes seleccionado como String, se le suma 1 porque
                                    // empieza en 0
                                    String m = String.valueOf(monthOfYear + 1);

                                    // ALmacena el día seleccionado como String
                                    String d = String.valueOf(dayOfMonth);

                                    // Comprueba si el mes tiene un sólo número, en cuyo caso le
                                    // añade un 0 al principio.
                                    if (m.length() == 1) {
                                        m = "0" + m;
                                    }
                                    // Comprueba si el día tiene un sólo número, en cuyo caso le
                                    // añade un 0 al principio.
                                    if (d.length() == 1) {
                                        d = "0" + d;
                                    }

                                    tvDay.setText(d + " - " + m + " - " + y);

                                    showGamesByDate(y + m + d);
                                }
                            }, mYear, mMonth, mDay);

                    // Muestra el diálogo de selección de fecha
                    datePickerDialog.show();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.menu_players) {
            System.out.println("yee");
            Intent intent = new Intent(this.getApplicationContext(), PlayerListActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String[] date = tvDay.getText().toString().split(" - ");

        String y = date[2];
        String m = date[1];
        String d = date[0];

        showGamesByDate(y+m+d);
    }

    protected void showGamesByDate(String date) {

        Scoreboard scb = new Scoreboard();

        scoreboardList = scb.getScoreboardListByDate(date);

        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        RecyclerView mainRecycler = (RecyclerView) findViewById(R.id.rvScoreboard);

        // Crea un objeto ScoreboardAdapter a partir del arrayList de partidos
        ScoreboardAdapter sbAdapter = new ScoreboardAdapter(scoreboardList, this);

        // Enlaza el objeto recyclerview al adaptador
        mainRecycler.setAdapter(sbAdapter);

        // Crea un nuevo Layout para mostrar la lista de los RecyclerView
        mainRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    protected String getTodayDate() {

        // Recoge la fecha completa actual
        final Calendar c = Calendar.getInstance();
// drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer

        // Comprueba que ya ha pasado el mediodía
        // Si no, resta un día a la fecha actual.
        // Sirve para mostrar los partidos de la noche anterior hasta las 15PM y los de la
        // próxima a partir de entonces.
        if (c.get(Calendar.HOUR_OF_DAY) < 14) {
            c.add(Calendar.DAY_OF_YEAR, -1);
        }

        // Guarda el año actual.
        String y = String.valueOf(c.get(Calendar.YEAR));

        // Guarda el mes actual (es necesario sumar 1 porque empieza en 0)
        String m = String.valueOf(c.get(Calendar.MONTH) + 1);

        // Guarda el día actual.
        String d = String.valueOf(c.get(Calendar.DAY_OF_MONTH));

        // Añade un 0 al principio del mes, si está entre 1 y 9
        // El objetivo es que, por ejemplo, aparezca 01 en vez de 1, si se trata de enero
        if (m.length() == 1) {
            m = "0" + m;
        }

        // Añade un 0 al principio del día, si está entre 1 y 9.
        if (d.length() == 1) {
            d = "0" + d;
        }

        tvDay.setText(d + " - " + m + " - " + y);

        return y + m + d;
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
                            intent = new Intent(MainActivity.this, PlayerListActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Home"
                        if (id == R.id.menu_home) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(MainActivity.this, MainActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Teams"
                        if (id == R.id.menu_teams) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(MainActivity.this, TeamListActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Standings"
                        if (id == R.id.menu_standings) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(MainActivity.this, StandingsActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Playoffs"
                        if (id == R.id.menu_playoffs) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(MainActivity.this, PlayOffsActivity.class);
                        }

                        // Controla que se haya pulsado sobre la opción "Favorites"
                        if (id == R.id.menu_favorites) {

                            // Crea un intent que abre la actividad correspondiente
                            intent = new Intent(MainActivity.this, FavoritesActivity.class);
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