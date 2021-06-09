package com.example.euronba.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.adapters.BoxscoreAdapter;
import com.example.euronba.model.Boxscore;
import com.example.euronba.model.Team;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    // Declaramos todos los datos que van a llegar desde la actividad anterior
    public static final String EXTRA_GAMEID = "gameId";
    public static final String EXTRA_SEASONSTAGEID = "seasonStageId";
    public static final String EXTRA_SEASONYEAR = "seasonYear";
    public static final String EXTRA_ARENANAME = "arenaName";
    public static final String EXTRA_ARENACITY = "arenaCity";
    public static final String EXTRA_STATUSNUM = "statusNum";
    public static final String EXTRA_STARTTIME = "startTime";
    public static final String EXTRA_CLOCK = "clock";
    public static final String EXTRA_GAMEDURATION = "gameDuration";
    public static final String EXTRA_CURRENTPERIOD = "currentPeriod";
    public static final String EXTRA_VISITORTEAMID = "visitorTeamId";
    public static final String EXTRA_VISITORTEAMWL = "visitorTeamWL";
    public static final String EXTRA_VISITORTEAMSCORE = "visitorTeamScore";
    public static final String EXTRA_LOCALTEAMID = "localTeamId";
    public static final String EXTRA_LOCALTEAMWL = "localTeamWL";
    public static final String EXTRA_LOCALTEAMSCORE = "localTeamScore";
    public static final String EXTRA_SUMMARYTEXT = "summaryText";
    public static final String EXTRA_DATE = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Almacena la barra de herramientas a partir de su ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Establece la barra de herramientas.
        setSupportActionBar(toolbar);

        // Permite que el botón para volver atrás aparezca.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Instancia un objeto Bundle con todos los datos pasados de la actividad anterior
        Bundle data = getIntent().getExtras();

        // Rellena la información detallada del partido
        fillGameInfo(data);

        // Rellena las tablas de puntuación
        fillGamebox(data);
    }

    // Rellena la información del partido con los datos recogidos de la actividad anterior.
    protected void fillGameInfo(Bundle data) {

        // Instancia un objeto TextView para cada uno de los elementos
        TextView tvSummaryText = findViewById(R.id.tvGameSummaryText);
        TextView tvLocalTeamName = findViewById(R.id.tvGameLocalTeamName);
        TextView tvLocalTeamWL = findViewById(R.id.tvGameLocalTeamStandings);
        TextView tvVisitorTeamName = findViewById(R.id.tvGameVisitorTeamName);
        TextView tvVisitorTeamWL = findViewById(R.id.tvGameVisitorTeamStandings);
        TextView tvLocalScore = findViewById(R.id.tvGameLocalScore);
        TextView tvVisitorScore = findViewById(R.id.tvGameVisitorScore);
        TextView tvArena = findViewById(R.id.tvGameArena);
        TextView tvClock = findViewById(R.id.tvGameScoreClock);
        TextView tvDuration = findViewById(R.id.tvGameDuration);
        TextView tvGameStart = findViewById(R.id.tvGameStartTime);
        ImageView ivLocalTeamLogo = findViewById(R.id.ivGameLocalLogo);
        ImageView ivVisitorTeamLogo = findViewById(R.id.ivGameVisitorLogo);

        // Obtenemos el ID de ambos equipos
        String localTeamId = data.getString("localTeamId");
        String visitorTeamId = data.getString("visitorTeamId");

        // A partir del Id, obtenemos el Objeto Team con los datos completos de ambos
        Team localTm = new Team().getTeamById(localTeamId, GameActivity.this);
        Team visitorTm = new Team().getTeamById(visitorTeamId, GameActivity.this);

        // Rellenamos todos los TextView a partir de los datos del bundle
        tvLocalTeamName.setText(localTm.getFullName());
        tvVisitorTeamName.setText(visitorTm.getFullName());

        ivLocalTeamLogo.setImageResource(localTm.getLogo());
        ivVisitorTeamLogo.setImageResource(visitorTm.getLogo());

        tvSummaryText.setText(data.getString("summaryText"));

        tvLocalTeamWL.setText(data.getString("localTeamWL"));
        tvVisitorTeamWL.setText(data.getString("visitorTeamWL"));

        tvLocalScore.setText(data.getString("localTeamScore"));
        tvVisitorScore.setText(data.getString("visitorTeamScore"));

        tvGameStart.setText(getString(R.string.gameStartTime, data.getString("startTime")));
        tvArena.setText(getString(R.string.gameArena, data.getString("arenaName"), data.getString("arenaCity")));

        tvDuration.setText(getString(R.string.gameDuration, data.getString("gameDuration")));

        // Comprueba que el partido ha empezado. Si no ha empezado, la duración se muestra como ":"
        if (data.getString("gameDuration").equals(":")) {

            // Se muestra un mensaje para indicar que no ha empezado
            tvDuration.setText(R.string.gameNotStart);
        }

        tvClock.setText(data.getString("currentPeriod"));

    }

    protected void fillGamebox(Bundle data) {

        // Almacena los objetos recyclerview presentes en la actividad
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlayerBoxList);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerViewPlayerBoxList2);

        // Instancia un objeto de la clase boxscore
        Boxscore box = new Boxscore();

        // Rellena una lista de objetos Boxscore a partir de la fecha e Id del partido
        // Esta lista contiene las estadisticas de los jugadores de los dos equipos, entremezclada
        ArrayList<Boxscore> bothBoxscore = box.getBoxscoreByDateAndGame(data.getString("date"), data.getString("gameId"));

        // Controla que la lista no esté vacía
        // Se pretende que a partir de esta lista, se creen dos según el equipo donde jueguen.
        if (bothBoxscore.size() != 0) {

            // Crea una lista de Objetos Boxscore para cada equipo
            ArrayList<Boxscore> rpcLocal = new ArrayList<>();
            ArrayList<Boxscore> rpcVisitor = new ArrayList<>();

            // Recorre la lista en que se encuentran jugadores de los dos equipos
            for (int i = 0; i < bothBoxscore.size(); i++) {

                // Añade a los jugadores del equipo Local a una lista
                if (bothBoxscore.get(i).getTeamId().equals(data.getString("localTeamId"))) {
                    rpcLocal.add(bothBoxscore.get(i));
                }

                // Añade a los jugadores del equipo visitante a otra lista
                if (bothBoxscore.get(i).getTeamId().equals(data.getString("visitorTeamId"))) {
                    rpcVisitor.add(bothBoxscore.get(i));
                }
            }

            // Instancia el adaptador para boxscore con la lista de jugadores locales
            BoxscoreAdapter adapter = new BoxscoreAdapter(rpcLocal, GameActivity.this);

            // Crea un nuevo Layout para mostrar la lista de los RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(GameActivity.this));

            // Enlaza el objeto recyclerview al adaptador
            recyclerView.setAdapter(adapter);

            // Instancia el adaptador para boxscore con la lista de jugadores visitantes
            BoxscoreAdapter adapter2 = new BoxscoreAdapter(rpcVisitor, GameActivity.this);

            // Crea un nuevo Layout para mostrar la lista de los RecyclerView
            recyclerView2.setLayoutManager(new LinearLayoutManager(GameActivity.this));

            // Enlaza el objeto recyclerview al adaptador
            recyclerView2.setAdapter(adapter2);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Controla lo que pasa cuando se pulsa el botón de atrás.
        if (item.getItemId() == android.R.id.home) {

            // Cierra la actividad y vuelve a la anterior.
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}