package com.example.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.euronba.R;

//Clase auxiliar para el tratamiento de la base de datos
public class TeamsDatabaseHelper extends SQLiteOpenHelper {

    //Constantes para definir los parametros básicos para crear la BD
    private static final String DB_NAME = "TEAMS"; //Nombre de la BD
    private static final int DB_VERSION = 1; //Version de la BD

    //Constructor de la clase, llamamos al constructor de la superclase
    public TeamsDatabaseHelper(Context context) {
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
            db.execSQL("CREATE TABLE PET (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "IMAGE_RESOURCE_ID INTEGER, "
                    + "NAME TEXT, "
                    + "RATING INTEGER);");
            //El metodo auxiliar insertPet lo creamos para insertar varios filas
            /*
            insertPet(db, R.drawable.camaleon, "Chamaleon", 0);
            insertPet(db, R.drawable.mountain_horned_dragon, "Mountain Horned Dragon", 0);
            insertPet(db, R.drawable.russian_turtle, "Russian turtle", 0);
            insertPet(db, R.drawable.fire_skink, "Fire Skink", 0);
            insertPet(db, R.drawable.red_eared_slider_turtle, "Red Eared Turtle", 0);
            insertPet(db, R.drawable.baby_bearded_dragon, "Baby Bearded Dragon", 0);
            insertPet(db, R.drawable.gecko, "Common Gecko", 0);
            insertPet(db, R.drawable.ball_phyton, "Ball Phyton", 0);
            insertPet(db, R.drawable.anolis_carolinensis, "Anolis Carolinensis", 0);
            insertPet(db, R.drawable.leopard_gecko, "Leopard Gecko", 0);
             */
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE PET ADD COLUMN FAVORITE NUMERIC");
        }
    }

    /*Definicion del metodo auxiliar
     *db base de datos de trabajo
     * name Campo para el nombre de la mascota
     * rating Campo para el número de likes
     * resourceId campo identificador numerico de la imagen
     */
    private void insertPet(SQLiteDatabase db,
                           int resourceId,
                           String name,
                           int rating) {
        /*Objeto que nos va a permitir indicar que valores queremos
        insertar en la BD*/
        ContentValues petValues = new ContentValues();
        /*
         * creamos cada uno de los campos de la fila a insertar
         */
        petValues.put("IMAGE_RESOURCE_ID", resourceId);
        petValues.put("NAME", name);
        petValues.put("RATING", rating);
        db.insert("PET", null, petValues);
    }
}
