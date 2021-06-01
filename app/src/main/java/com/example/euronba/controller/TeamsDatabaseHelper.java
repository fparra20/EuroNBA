package com.example.euronba.controller;

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
            db.execSQL("CREATE TABLE TEAMS (IMAGE_LOGO INTEGER,"
                    + "TEAM_ID TEXT PRIMARY KEY,"
                    + "CITY TEXT,"
                    + "NICKNAME TEXT,"
                    + "FULLNAME TEXT,"
                    + "TRICODE TEXT,"
                    + "CONFNAME TEXT,"
                    + "DIVNAME TEXT,"
                    + "URLNAME TEXT);");
            //El metodo auxiliar insertPet lo creamos para insertar varios filas
            insertTeam(db, R.drawable.bos, "1610612738", "Boston", "Celtics", "Boston Celtics", "BOS", "East", "Atlantic", "celtics");
            insertTeam(db, R.drawable.brk, "1610612751", "Brooklyin", "Nets", "Brooklyin Nets", "BKN", "East", "Atlantic", "nets");
            insertTeam(db, R.drawable.phi, "1610612755", "Philadelphia", "76ers", "Philadelphia 76ers", "PHI", "East", "Atlantic", "sixers");
            insertTeam(db, R.drawable.nyk, "1610612752", "New York", "Knicks", "New York Knicks", "NYK", "East", "Atlantic", "knicks");
            insertTeam(db, R.drawable.tor, "1610612761", "Toronto", "Raptors", "Toronto Raptors", "TOR", "East", "Atlantic", "raptors");

            insertTeam(db, R.drawable.atl, "1610612737", "Atlanta", "Hawks", "Atlanta Hawks", "ATL", "East", "Southeast", "hawks");
            insertTeam(db, R.drawable.cho, "1610612766", "Charlotte", "Hornets", "Charlotte Hornets", "CHA", "East", "Southeast", "hornets");
            insertTeam(db, R.drawable.orl, "1610612753", "Orlando", "Magic", "Orlando Magic", "ORL", "East", "Southeast", "magic");
            insertTeam(db, R.drawable.mia, "1610612748", "Miami", "Heat", "Miami Heat", "MIA", "East", "Southeast", "heat");
            insertTeam(db, R.drawable.was, "1610612764", "Washington", "Wizards", "Washington Wizards", "WAS", "East", "Southeast", "wizards");

            insertTeam(db, R.drawable.chi, "1610612741", "Chicago", "Bulls", "Chicago Bulls", "CHI", "East", "Central", "bulls");
            insertTeam(db, R.drawable.cle, "1610612739", "Cleveland", "Cavaliers", "Cleveland Cavaliers", "CLE", "East", "Central", "cavaliers");
            insertTeam(db, R.drawable.det, "1610612765", "Detroit", "Pistons", "Detroit Pistons", "DET", "East", "Central", "pistons");
            insertTeam(db, R.drawable.ind, "1610612754", "Indiana", "Pacers", "Indiana Pacers", "IND", "East", "Central", "pacers");
            insertTeam(db, R.drawable.mil, "1610612749", "Milwaukee", "Bucks", "Milwaukee Bucks", "MIL", "East", "Central", "bucks");

            insertTeam(db, R.drawable.dal, "1610612742", "Dallas", "Mavericks", "Dallas Mavericks", "DAL", "West", "Southwest", "mavericks");
            insertTeam(db, R.drawable.hou, "1610612745", "Houston", "Rockets", "Houston Rockets", "HOU", "West", "Southwest", "rockets");
            insertTeam(db, R.drawable.mem, "1610612763", "Memphis", "Grizzlies", "Memphis Grizzlies", "MEM", "West", "Southwest", "grizzlies");
            insertTeam(db, R.drawable.sas, "1610612759", "San Antonio", "Spurs", "San Antonio Spurs", "SAS", "West", "Southwest", "spurs");
            insertTeam(db, R.drawable.nop, "1610612740", "New Orleans", "Pelicans", "New Orleans Pelicans", "NOP", "West", "Southwest", "pelicans");

            insertTeam(db, R.drawable.den, "1610612743", "Denver", "Nuggets", "Denver Nuggets", "DEN", "West", "Northwest", "nuggets");
            insertTeam(db, R.drawable.min, "1610612750", "Minnesota", "Timberwolves", "Minnesota Timberwolves", "MIN", "West", "Northwest", "timberwolves");
            insertTeam(db, R.drawable.okc, "1610612760", "Oklahoma City", "Thunder", "Oklahoma City Thunder", "OKC", "West", "Northwest", "thunder");
            insertTeam(db, R.drawable.por, "1610612757", "Portland", "Trail Blazers", "Portland Trail Blazers", "POR", "West", "Northwest", "blazers");
            insertTeam(db, R.drawable.uta, "1610612762", "Utah", "Jazz", "Utah Jazz", "UTA", "West", "Northwest", "jazz");

            insertTeam(db, R.drawable.gsw, "1610612744", "Golden State", "Warriors", "Golden State Warriors", "GSW", "West", "Pacific", "warriors");
            insertTeam(db, R.drawable.lac, "1610612746", "LA", "Clippers", "Los Angeles Clippers", "LAC", "West", "Pacific", "clippers");
            insertTeam(db, R.drawable.lal, "1610612747", "LA", "Lakers", "Los Angeles Lakers", "LAL", "West", "Pacific", "lakers");
            insertTeam(db, R.drawable.pho, "1610612756", "Phoenix", "Suns", "Phoenix Suns", "PHX", "West", "Pacific", "suns");
            insertTeam(db, R.drawable.sac, "1610612758", "Sacramento", "Kings", "Sacramento Kings", "SAC", "West", "Pacific", "kings");

        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE TEAMS ADD COLUMN FAVORITE NUMERIC");
        }
    }

    /*Definicion del metodo auxiliar
     *db base de datos de trabajo
     * name Campo para el nombre de la mascota
     * rating Campo para el número de likes
     * resourceId campo identificador numerico de la imagen
     */

    private void insertTeam(SQLiteDatabase db,
                            int image_logo,
                            String teamId,
                            String city,
                            String nickname,
                            String fullname,
                            String tricode,
                            String confname,
                            String divname,
                            String urlname) {
        /*Objeto que nos va a permitir indicar que valores queremos
        insertar en la BD*/
        ContentValues teamValues = new ContentValues();
        /*
         * creamos cada uno de los campos de la fila a insertar
         */
        teamValues.put("IMAGE_LOGO", image_logo);
        teamValues.put("TEAM_ID", teamId);
        teamValues.put("CITY", city);
        teamValues.put("NICKNAME", nickname);
        teamValues.put("FULLNAME", fullname);
        teamValues.put("TRICODE", tricode);
        teamValues.put("CONFNAME", confname);
        teamValues.put("DIVNAME", divname);
        teamValues.put("URLNAME", urlname);
        db.insert("TEAMS", null, teamValues);
    }
}
