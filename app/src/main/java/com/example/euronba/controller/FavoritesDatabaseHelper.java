package com.example.euronba.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Clase auxiliar para el tratamiento de la base de datos
public class FavoritesDatabaseHelper extends SQLiteOpenHelper {

    //Constantes para definir los parametros básicos para crear la BD
    private static final String DB_NAME = "FAVORITESv6"; //Nombre de la BD
    private static final int DB_VERSION = 1; //Version de la BD

    //Constructor de la clase, llamamos al constructor de la superclase
    public FavoritesDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }
    /*Metodo que crea la base de datos
     *Añadimos la sentencia SQL necesaria para crear la BD mediante el uso de
     * la sentencia SQL
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*Metodo auxiliar para tener todo el tratamiento de  la BD
         *en la misma ubicacion
         */
        updateMyDataBase(db, 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDataBase(db, oldVersion);

    }

    private void updateMyDataBase(SQLiteDatabase db, int oldVersion) {
        /*Control de versiones
         * si la BD no existe se crea (oldVersion<1)
         * posteriormente a su creación se actualiza (oldVersion<2)
         */
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE FAVORITES (ID TEXT PRIMARY KEY,"
                    + "TYPE TEXT, TEAMURL TEXT, PERSONNAME TEXT);");
        }
    }
}
