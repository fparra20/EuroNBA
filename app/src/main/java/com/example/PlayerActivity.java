package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.controller.TeamsDatabaseHelper;
import com.example.euronba.R;
import com.example.euronba.model.Team;

import java.time.Clock;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    ArrayList<Team> teamsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // Creamos el cursor para traer los datos de la BD
        SQLiteOpenHelper TeamsDatabaseHelper = new TeamsDatabaseHelper(getApplicationContext());

        // Extrae la base de datos para trabajar con ella
        SQLiteDatabase db = TeamsDatabaseHelper.getReadableDatabase();

        // Crea una consulta en la tabla TEAMS con todas sus columnas
        // Además, las ordena por nombre de ciudad alfabéticamente
        Cursor cursor = db.query("TEAMS",
                new String[]{"IMAGE_LOGO", "TEAM_ID", "CITY", "NICKNAME", "FULLNAME", "TRICODE", "CONFNAME", "DIVNAME", "URLNAME"}, null,
                null, null, null, "CITY");

        //Creamos un arraylist de tipo Pet vacío
        teamsList = new ArrayList<Team>();

        // Comprobamos que el cursor no está vacío
        if (cursor.moveToFirst()) {

            // Este código se repite mientras cursor siga teniendo datos
            do {
                // Para cada dato del cursor introducimos un nuevo valor en el ArrayList
                teamsList.add(new Team(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)));

            } while (cursor.moveToNext());

            // Si no tiene datos o no le quedan, cierra el cursor y las llamadas a la base de datos.
        } else {
            cursor.close();
            db.close();
        }

        ImageView iv = findViewById(R.id.iv);

        iv.setImageResource(teamsList.get(29).getLogo());
        System.out.println("Logo: "+teamsList.get(0).getLogo());
        System.out.println("ID: "+teamsList.get(0).getTeamId());
        System.out.println("Full Name: "+teamsList.get(0).getFullName());
        System.out.println("Conf: "+teamsList.get(0).getConfName());
        System.out.println("Div: "+teamsList.get(0).getDivName());
        System.out.println("URL: "+teamsList.get(0).getUrlName());
    }
}