package com.example.euronba;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.adapters.ScoreboardAdapter;
import com.example.euronba.model.Scoreboard;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ArrayList<Scoreboard> scoreboardList;

    // Crea un objeto textView para mostrar la fecha a partir del objeto presente en el layout
    TextView tvDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recoge la fecha completa actual
        final Calendar c = Calendar.getInstance();

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

        // Crea un objeto ImageButton para el calendario a partir del objeto presente en el layout
        ImageButton ibCalendar = (ImageButton) findViewById(R.id.ibCalendar);

        tvDay=(TextView) findViewById(R.id.tvDay);

        // Llama al método que genera el recyclerview según la fecha
        showGamesByDate(y,m,d);

        // Añade un ClickListener al botón del calendario
        ibCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si se ha pulsado el botón con el calendario
                if (v == ibCalendar) {

                    // Instancia 3 variables de tipo int, para guardar día mes y año actual.
                    int mYear, mMonth, mDay;

                    String dateOnTv = tvDay.getText().toString();

                    String[] splitDateOnTv = dateOnTv.split(" - ");

                    mYear = Integer.parseInt(splitDateOnTv[0]);
                    mMonth = Integer.parseInt(splitDateOnTv[1])-1;
                    mDay = Integer.parseInt(splitDateOnTv[2]);

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

                                    showGamesByDate(y, m, d);
                                }
                            }, mYear, mMonth, mDay);

                    // Muestra el diálogo de selección de fecha
                    datePickerDialog.show();
                }
            }
        });
    }

    protected void showGamesByDate(String y, String m, String d){

        Scoreboard scb = new Scoreboard();

        scoreboardList = scb.getScoreboardListByDate(y+m+d);

        // Crea un objeto RecyclerView a partir del objeto presente en el layout
        RecyclerView mainRecycler = (RecyclerView) findViewById(R.id.rvScoreboard);

        // Crea un objeto ScoreboardAdapter a partir del arrayList de partidos
        ScoreboardAdapter sbAdapter = new ScoreboardAdapter(scoreboardList, this);

        // Enlaza el objeto recyclerview al adaptador
        mainRecycler.setAdapter(sbAdapter);

        // Crea un nuevo Layout para mostrar la lista de los RecyclerView
        mainRecycler.setLayoutManager(new LinearLayoutManager(this));

        // Pone el texto con la fecha al textView
        tvDay.setText(y + " - " + m + " - " + d);
    }
}