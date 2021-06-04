package com.example.euronba.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.euronba.R;

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
        updateMyDataBase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDataBase(db, oldVersion, newVersion);

    }

    private void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*Control de versiones
         * si la BD no existe se crea (oldVersion<1)
         * posteriormente a su creación se actualiza (oldVersion<2)
         */
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE FAVORITES (ID TEXT PRIMARY KEY,"
                    + "TYPE TEXT, TEAMURL TEXT, PERSONNAME TEXT);");
            //El metodo auxiliar insertPet lo creamos para insertar varios filas
            insertFavorite(db,"1610612738", "team", "hawks", "Alan Walker");

        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE FAVORITES ADD COLUMN FAVORITE NUMERIC");
        }
    }

    /*Definicion del metodo auxiliar
     *db base de datos de trabajo
     * name Campo para el nombre de la mascota
     * rating Campo para el número de likes
     * resourceId campo identificador numerico de la imagen
     */

    public void insertFavorite(SQLiteDatabase db,
                            String id, String type, String teamUrl, String personName) {
        /*Objeto que nos va a permitir indicar que valores queremos
        insertar en la BD*/
        ContentValues teamValues = new ContentValues();
        /*
         * creamos cada uno de los campos de la fila a insertar
         */
        teamValues.put("ID", id);
        teamValues.put("TYPE", type);
        teamValues.put("TEAMURL", teamUrl);
        teamValues.put("PERSONNAME", personName);
        db.insert("FAVORITES", null, teamValues);
    }
}
