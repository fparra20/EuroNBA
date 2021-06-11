package com.example.euronba.adapters;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.activities.GameActivity;
import com.example.euronba.model.Scoreboard;
import com.example.euronba.model.Team;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

// Adapaptador que se usa en MainActivity y rellena la vista de los Partidos.
public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.ScoreViewHolder> {

    ArrayList<Scoreboard> scores;
    Activity activity;

    // Constructor del adaptador
    public ScoreboardAdapter(ArrayList<Scoreboard> scores, Activity activity) {
        this.scores = scores;
        this.activity = activity;
    }

    // Instancia todos los objetos que se quieren rellenar
    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivLocalLogo;
        private final TextView tvLocalScore;
        private final ImageView ivVisitorLogo;
        private final TextView tvVisitorScore;
        private final TextView tvLocalTeamName;
        private final TextView tvVisitorTeamName;
        private final TextView tvLocalTeamStandings;
        private final TextView tvVisitorTeamStandings;
        private final TextView tvScoreStatus;
        private final TextView tvScoreClock;
        private final MaterialCardView cvScoreboard;
        private final TextView tvSummaryText;

        // Constructor que vincula los objetos a su vista en el layout
        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);

            // Iniciamos todos los item con los que vamos a trabajar
            ivLocalLogo = itemView.findViewById(R.id.ivLocalLogo);
            tvLocalScore = itemView.findViewById(R.id.tvLocalScore);

            ivVisitorLogo = itemView.findViewById(R.id.ivVisitorLogo);
            tvVisitorScore = itemView.findViewById(R.id.tvVisitorScore);

            tvLocalTeamName = itemView.findViewById(R.id.tvLocalTeamName);
            tvVisitorTeamName = itemView.findViewById(R.id.tvVisitorTeamName);

            tvLocalTeamStandings = itemView.findViewById(R.id.tvLocalTeamStandings);
            tvVisitorTeamStandings = itemView.findViewById(R.id.tvVisitorTeamStandings);

            tvScoreStatus = itemView.findViewById(R.id.tvScoreStatus);
            tvScoreClock = itemView.findViewById(R.id.tvScoreClock);

            cvScoreboard = itemView.findViewById(R.id.scoreboard);
            tvSummaryText = itemView.findViewById(R.id.tvSummaryText);

        }
    }

    // Crea una vista a partir del layout que queremos para rellenar
    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_scoreboard, parent, false);
        return new ScoreViewHolder(v);
    }

    // Método que vincula la vista con los datos
    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder scoreViewHolder, int position) {

        // Obtiene el objeto Scoreboard correspondiente a la posición del adaptador
        Scoreboard score = scores.get(position);

        // Almacena un objeto de equipo para equipo local y para equipo visitante
        Team tmLocal = new Team().getTeamById(score.getLocalTeam().getTeamId(), activity);
        Team tmVisitor = new Team().getTeamById(score.getVisitorTeam().getTeamId(), activity);

        // Rellena el resto de los datos
        scoreViewHolder.ivLocalLogo.setImageResource(tmLocal.getLogo());
        scoreViewHolder.tvLocalScore.setText(score.getLocalTeam().getScore());

        scoreViewHolder.ivVisitorLogo.setImageResource(tmVisitor.getLogo());
        scoreViewHolder.tvVisitorScore.setText(score.getVisitorTeam().getScore());

        scoreViewHolder.tvLocalTeamName.setText(tmLocal.getNickname());
        scoreViewHolder.tvVisitorTeamName.setText(tmVisitor.getNickname());

        String localWL = score.getLocalTeam().getWin() + " - " + score.getLocalTeam().getLoss();
        String visitorWL = score.getVisitorTeam().getWin() + " - " + score.getVisitorTeam().getLoss();
        scoreViewHolder.tvLocalTeamStandings.setText(localWL);
        scoreViewHolder.tvVisitorTeamStandings.setText(visitorWL);

        scoreViewHolder.tvScoreStatus.setText(score.getStartTimeUTC());
        scoreViewHolder.tvSummaryText.setText(score.getSummaryText());

        // Instancia un objeto String vacío
        String gameClock = "";

        // Si el partido está transcuyendo actualmente, el statusNum es 2
        if (score.statusNum == 2) {

            // Controla si el partido está entre el 1er y 4o periodo.
            if (score.getCurrentPeriod() >= 1 && score.getCurrentPeriod() <= 4)
                gameClock = score.getClock() + " - " + score.getCurrentPeriod() + "th";

            // Controla si el partido está entre en el quinto periodo, prórroga.
            if (score.getCurrentPeriod() == 5)
                gameClock = score.getClock() + " - OT";

            // Si hay más de 5 periodos, se resta el periodo a 4, entonces se obtiene el número
            // de la prórroga. Por ejemplo, si hay 7 períodos, quiere decir que hubo 3 prórrogas
            if (score.getCurrentPeriod() > 5)
                gameClock = score.getClock() + " - " + (score.getCurrentPeriod() - 4) + "OT";

            // Rellena el reloj
            scoreViewHolder.tvScoreClock.setText(gameClock);
        }


        // Controla si el partido ha acabado, o sea, statusNum 3
        if (score.statusNum == 3) {

            // Controla que hubo una prórroga en el partido
            if (score.getCurrentPeriod() == 5)
                gameClock = "OT";

            // Controla que hubo más de una prórroga en el partido
            if (score.getCurrentPeriod() > 5)
                gameClock = (score.getCurrentPeriod() - 4) + "OT";

            // Rellena el reloj
            scoreViewHolder.tvScoreClock.setText(gameClock);
        }


        // Almacena el resultado del reloj tras los if, para pasarlo a la actividad siguiente
        String finalGameClock = gameClock;

        // Obtiene la vista TextView del día de partido
        TextView tv = activity.findViewById(R.id.tvDay);

        // Almacena el valor que hay en la vista y lo separa en un array de 3 valores
        String[] dateSplit = tv.getText().toString().split(" - ");

        // Almacena la fecha formateada como YYYYMMDD
        String date = dateSplit[2] + dateSplit[1] + dateSplit[0];

        // Añade un listener para el objeto del resultado
        scoreViewHolder.cvScoreboard.setOnClickListener(v -> {

            // Instancia un objeto intent con la actividad GameActivity
            Intent intent = new Intent(v.getContext(), GameActivity.class);

            // Añade datos al intent necesarios para crear la siguiente actividad
            intent.putExtra(GameActivity.EXTRA_GAMEID, score.getGameId());
            intent.putExtra(GameActivity.EXTRA_ARENANAME, score.getArenaName());
            intent.putExtra(GameActivity.EXTRA_ARENACITY, score.getArenaCity());
            intent.putExtra(GameActivity.EXTRA_CLOCK, score.getClock());
            intent.putExtra(GameActivity.EXTRA_CURRENTPERIOD, finalGameClock);
            intent.putExtra(GameActivity.EXTRA_GAMEDURATION, score.getGameDuration());
            intent.putExtra(GameActivity.EXTRA_LOCALTEAMID, score.getLocalTeam().getTeamId());
            intent.putExtra(GameActivity.EXTRA_LOCALTEAMWL, localWL);
            intent.putExtra(GameActivity.EXTRA_LOCALTEAMSCORE, score.getLocalTeam().getScore());
            intent.putExtra(GameActivity.EXTRA_SEASONSTAGEID, score.getSeasonStageId());
            intent.putExtra(GameActivity.EXTRA_STARTTIME, score.getStartTimeUTC());
            intent.putExtra(GameActivity.EXTRA_SEASONYEAR, score.getSeasonYear());
            intent.putExtra(GameActivity.EXTRA_SUMMARYTEXT, score.getSummaryText());
            intent.putExtra(GameActivity.EXTRA_STATUSNUM, score.getStatusNum());
            intent.putExtra(GameActivity.EXTRA_VISITORTEAMID, score.getVisitorTeam().getTeamId());
            intent.putExtra(GameActivity.EXTRA_VISITORTEAMWL, visitorWL);
            intent.putExtra(GameActivity.EXTRA_VISITORTEAMSCORE, score.getVisitorTeam().getScore());
            intent.putExtra(GameActivity.EXTRA_DATE, date);

            // Empieza la siguiente actividad.
            activity.startActivity(intent);
        });
    }

    // Obtiene la posición del adaptador
    @Override
    public int getItemCount() {
        return scores.size();
    }
}
