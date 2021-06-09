package com.example.euronba.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

        // Almacena la barra de herramientas a partir de su ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Establece la barra de herramientas.
        setSupportActionBar(toolbar);

        // Permite que el botoón homburguesa (3 rayas, el del menú lateral) aparezca.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtiene un String con la fecha actual formateada en YYYYMMDD
        String date = getTodayDate();

        // Llama al método que genera el recyclerview según la fecha
        fillGamesByDate(date);

        // Crea el menú lateral
        setDrawerLayout();

        // Crea el botón de calendario y su funcionamiento
        setCalendarButton();

    }

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

    public void setCalendarButton(){
        // Crea un objeto ImageButton para el calendario a partir del objeto presente en el layout
        ImageButton ibCalendar = findViewById(R.id.ibCalendar);

        // Instancia el objeto TextView de la fecha.
        tvDay = findViewById(R.id.tvDay);

        // Añade un ClickListener al botón del calendario
        ibCalendar.setOnClickListener(v -> {
            // Si se ha pulsado el botón con el calendario
            if (v == ibCalendar) {

                // Instancia 3 variables de tipo int, para guardar día mes y año actual.
                int mYear, mMonth, mDay;

                // Obtiene el valor del TextView de la fecha y lo divide en año, mes y día
                String[] dateSplit = tvDay.getText().toString().split(" - ");

                mYear = Integer.parseInt(dateSplit[2]);
                mMonth = Integer.parseInt(dateSplit[1]) - 1;
                mDay = Integer.parseInt(dateSplit[0]);

                // Instancia un objeto DatePickerDialog, con el que podremos seleccionar la fecha
                // y recibir todos los partidos jugados en la misma
                // Indica lo que pasa cuando se cambia de fecha.
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_HOLO_DARK,
                        (view, year, monthOfYear, dayOfMonth) -> {

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

                            tvDay.setText(getString(R.string.mainDate,d,m,y));

                            fillGamesByDate(y + m + d);
                        }, mYear, mMonth, mDay);

                // Muestra el diálogo de selección de fecha
                datePickerDialog.show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        String[] date = tvDay.getText().toString().split(" - ");

        String y = date[2];
        String m = date[1];
        String d = date[0];

        fillGamesByDate(y+m+d);
    }

    protected void fillGamesByDate(String date) {

        Scoreboard scb = new Scoreboard();

        scoreboardList = scb.getScoreboardListByDate(date);

        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        RecyclerView mainRecycler = findViewById(R.id.rvScoreboard);

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

        // Instancia el TextView donde se mostrará la fecha.
        tvDay = findViewById(R.id.tvDay);

        // Escribe en la fecha el día, mes, y año.
        tvDay.setText(getString(R.string.mainDate,d,m,y));

        // Devuelve la fecha en formato YYYYMMDD
        return y + m + d;
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
                });
    }
}