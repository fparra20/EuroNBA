package com.example.euronba.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.euronba.controller.TeamsDatabaseHelper;

import java.util.ArrayList;

public class Team {

    public int logo;
    public String teamId;
    public String city;
    public String nickname;
    public String fullName;
    public String tricode;
    public String confName;
    public String divName;
    public String urlName;

    public Team() {

    }

    public Team(int logo, String teamId, String city, String nickname, String fullName, String tricode, String confName, String divName, String urlName) {
        this.logo = logo;
        this.teamId = teamId;
        this.city = city;
        this.nickname = nickname;
        this.fullName = fullName;
        this.tricode = tricode;
        this.confName = confName;
        this.divName = divName;
        this.urlName = urlName;
    }

    public ArrayList<Team> teamsList(Context ctx) {

        ArrayList<Team> teamsList;
        Team tm;
        // Creamos el cursor para traer los datos de la BD
        SQLiteOpenHelper TeamsDatabaseHelper = new TeamsDatabaseHelper(ctx);

        // Extrae la base de datos para trabajar con ella
        SQLiteDatabase db = TeamsDatabaseHelper.getReadableDatabase();

        // Crea una consulta en la tabla TEAMS con todas sus columnas
        // Además, las ordena por nombre de ciudad alfabéticamente
        Cursor cursor = db.query("TEAMSv1",
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

        return teamsList;
    }

    public Team getTeamById(String id, Context ctx) {

        Team tm = new Team();
        // Creamos el cursor para traer los datos de la BD
        SQLiteOpenHelper TeamsDatabaseHelper = new TeamsDatabaseHelper(ctx);

        // Extrae la base de datos para trabajar con ella
        SQLiteDatabase db = TeamsDatabaseHelper.getReadableDatabase();

        // Crea una consulta para obtener el equipo concreto en la tabla TEAMS con todas sus columnas

        Cursor c = db.rawQuery("SELECT IMAGE_LOGO, TEAM_ID, CITY, NICKNAME, FULLNAME, TRICODE, CONFNAME, DIVNAME, URLNAME FROM TEAMS WHERE TEAM_ID= '" + id + "' ", null);

        if (c.moveToFirst()) {
            do {
                tm = new Team(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8));
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return tm;
    }

    public ArrayList<Team> getAllTeams(Context ctx){

        Team tm = new Team();

        ArrayList<Team> tmList = new ArrayList<>();
        // Creamos el cursor para traer los datos de la BD
        SQLiteOpenHelper TeamsDatabaseHelper = new TeamsDatabaseHelper(ctx);

        // Extrae la base de datos para trabajar con ella
        SQLiteDatabase db = TeamsDatabaseHelper.getReadableDatabase();

        // Crea una consulta para obtener el equipo concreto en la tabla TEAMS con todas sus columnas

        Cursor c = db.rawQuery("SELECT IMAGE_LOGO, TEAM_ID, CITY, NICKNAME, FULLNAME, TRICODE, CONFNAME, DIVNAME, URLNAME FROM TEAMS", null);

        if (c.moveToFirst()) {
            do {
                tm = new Team(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8));
                tmList.add(tm);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return tmList;
    }
    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }


    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTricode() {
        return tricode;
    }

    public void setTricode(String tricode) {
        this.tricode = tricode;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public String getDivName() {
        return divName;
    }

    public void setDivName(String divName) {
        this.divName = divName;
    }
}
