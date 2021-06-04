package com.example.euronba.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.euronba.controller.FavoritesDatabaseHelper;
import com.example.euronba.controller.TeamsDatabaseHelper;

import java.util.ArrayList;

public class Favorite {

    public String id;
    public String type;
    public String teamUrl;
    public String personName;

    public Favorite(){

    }
    Favorite(String id, String type, String teamUrl, String personName){
        this.id = id;
        this.type = type;
        this.teamUrl = teamUrl;
        this.personName = personName;
    }

    public ArrayList<Favorite> getFavorites (Context ctx){
        ArrayList<Favorite> alFav;

        // Creamos el cursor para traer los datos de la BD
        SQLiteOpenHelper FavoritesDatabaseHelper = new FavoritesDatabaseHelper(ctx);

        // Extrae la base de datos para trabajar con ella
        SQLiteDatabase db = FavoritesDatabaseHelper.getReadableDatabase();

        Cursor cursor = db.query("FAVORITES",
                new String[]{"ID", "TYPE", "TEAMURL", "PERSONNAME"}, null,
                null, null, null, "TYPE");

        alFav = new ArrayList<Favorite>();

        if (cursor.moveToFirst()) {

            // Este código se repite mientras cursor siga teniendo datos
            do {
                // Para cada dato del cursor introducimos un nuevo valor en el ArrayList
                alFav.add(new Favorite(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));

            } while (cursor.moveToNext());

            // Si no tiene datos o no le quedan, cierra el cursor y las llamadas a la base de datos.
        } else {
            cursor.close();
            db.close();
        }

        return alFav;
    }

    public void insertFav(Context ctx){
        // Creamos el cursor para traer los datos de la BD
        SQLiteOpenHelper FavoritesDatabaseHelper = new FavoritesDatabaseHelper(ctx);

        System.out.println("yeeee"+id);
        // Extrae la base de datos para trabajar con ella
        SQLiteDatabase db = FavoritesDatabaseHelper.getReadableDatabase();

        if(type.equals("team")){
            ContentValues values = new ContentValues();

            values.put("ID", id);
            values.put("TYPE", type);

            db.insert("FAVORITES", null, values);
        }

        if(type.equals("player")){
            ContentValues values = new ContentValues();

            values.put("ID", id);
            values.put("TYPE", type);
            values.put("TEAMURL", teamUrl);
            values.put("PERSONNAME", personName);

            db.insert("FAVORITES", null, values);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeamUrl() {
        return teamUrl;
    }

    public void setTeamUrl(String teamUrl) {
        this.teamUrl = teamUrl;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
