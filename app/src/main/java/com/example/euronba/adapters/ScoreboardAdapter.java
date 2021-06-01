package com.example.euronba.adapters;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.activities.GameActivity;
import com.example.euronba.model.Scoreboard;
import com.example.euronba.model.Team;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.ScoreViewHolder> {

    ArrayList<Scoreboard> scores;
    Activity activity;

    public ScoreboardAdapter(ArrayList<Scoreboard> scores, Activity activity) {
        this.scores = scores;
        this.activity = activity;
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivLocalLogo;
        private TextView tvLocalScore;
        private ImageView ivVisitorLogo;
        private TextView tvVisitorScore;
        private TextView tvLocalTeamName;
        private TextView tvVisitorTeamName;
        private TextView tvLocalTeamStandings;
        private TextView tvVisitorTeamStandings;
        private TextView tvScoreStatus;
        private TextView tvScoreClock;
        private MaterialCardView cvScoreboard;
        private TextView tvSummaryText;
        private TextView tvDate;


        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            // Iniciamos todos los item con los que vamos a trabajar
            ivLocalLogo = (ImageView) itemView.findViewById(R.id.ivLocalLogo);
            tvLocalScore = (TextView) itemView.findViewById(R.id.tvLocalScore);

            ivVisitorLogo = (ImageView) itemView.findViewById(R.id.ivVisitorLogo);
            tvVisitorScore = (TextView) itemView.findViewById(R.id.tvVisitorScore);

            tvLocalTeamName = (TextView) itemView.findViewById(R.id.tvLocalTeamName);
            tvVisitorTeamName = (TextView) itemView.findViewById(R.id.tvVisitorTeamName);

            tvLocalTeamStandings = (TextView) itemView.findViewById(R.id.tvLocalTeamStandings);
            tvVisitorTeamStandings = (TextView) itemView.findViewById(R.id.tvVisitorTeamStandings);

            tvScoreStatus = (TextView) itemView.findViewById(R.id.tvScoreStatus);
            tvScoreClock = (TextView) itemView.findViewById(R.id.tvScoreClock);

            cvScoreboard = (MaterialCardView) itemView.findViewById(R.id.scoreboard);
            tvSummaryText = (TextView) itemView.findViewById(R.id.tvSummaryText);

        }
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_scoreboard, parent, false);
        return new ScoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder scoreViewHolder, int position) {
        Scoreboard score = scores.get(position);

        Team tm = new Team();

        scoreViewHolder.ivLocalLogo.setImageResource(tm.getTeamById(score.getLocalTeam().getTeamId(), activity.getApplicationContext()).getLogo());
        scoreViewHolder.tvLocalScore.setText(score.getLocalTeam().getScore());

        scoreViewHolder.ivVisitorLogo.setImageResource(tm.getTeamById(score.getVisitorTeam().getTeamId(), activity.getApplicationContext()).getLogo());
        scoreViewHolder.tvVisitorScore.setText(score.getVisitorTeam().getScore());

        scoreViewHolder.tvLocalTeamName.setText(tm.getTeamById(score.getLocalTeam().getTeamId(), activity.getApplicationContext()).getNickname());
        scoreViewHolder.tvVisitorTeamName.setText(tm.getTeamById(score.getVisitorTeam().getTeamId(), activity.getApplicationContext()).getNickname());

        String localWL = score.getLocalTeam().getWin() + " - " + score.getLocalTeam().getLoss();
        String visitorWL = score.getVisitorTeam().getWin() + " - " + score.getVisitorTeam().getLoss();
        scoreViewHolder.tvLocalTeamStandings.setText(localWL);
        scoreViewHolder.tvVisitorTeamStandings.setText(visitorWL);

        String gameClock = "";

        // Si el partido está transcuyendo actualmente, el statusNum es 2

        if (score.statusNum == 2) {
            if (score.getCurrentPeriod() >= 1 && score.getCurrentPeriod() <= 4)
                gameClock = score.getClock() + " - " + score.getCurrentPeriod() + "th";

            if (score.getCurrentPeriod() == 5)
                gameClock = score.getClock() + " - OT";

            // Si hay más de 5 periodos, se resta el periodo a 4, entonces se obtiene el número
            // de la prórroga. Por ejemplo, si hay 7 períodos, quiere decir que hubo 3 prórrogas
            if (score.getCurrentPeriod() > 5)
                gameClock = score.getClock() + " - " + (score.getCurrentPeriod() - 4) + "OT";

            scoreViewHolder.tvScoreClock.setText(gameClock);
        }

        scoreViewHolder.tvScoreStatus.setText(score.getStartTimeUTC());

        if (score.statusNum == 3) {
            if (score.getCurrentPeriod() == 5)
                gameClock = "OT";

            if (score.getCurrentPeriod() > 5)
                gameClock = (score.getCurrentPeriod() - 4) + "OT";

            scoreViewHolder.tvScoreClock.setText(gameClock);
        }

        scoreViewHolder.tvSummaryText.setText(score.getSummaryText());

        // Guarda el resultado del reloj tras los if
        String finalGameClock = gameClock;

        TextView tv = activity.findViewById(R.id.tvDay);
        String[] dateSplit = tv.getText().toString().split(" - ");
        String date = dateSplit[0] + dateSplit[1] + dateSplit[2];

        System.out.println(date);

        scoreViewHolder.cvScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GameActivity.class);

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

                activity.startActivity(intent);

                Toast.makeText(activity, score.getSummaryText(), Toast.LENGTH_SHORT).show();
            }
        });


        /*
        // Que si clickamos el nombre, nos salga un toast con nombre completo
        scoreViewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,score.getArenaName(),Toast.LENGTH_SHORT).show();
            }
        });

        // Evento cuando se clicka el camaleon para puntuar
        scoreViewHolder.ibPuntuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Guardamos el valor actual de puntuación
                int currentValue=pet.getRating();

                // Sumamos 1
                int newValue = currentValue+1;

                // Cambiamos el rating de la mascota en cuestion
                pet.setRating(newValue);

                // Actualizamos también el TextView que lo muestra
                petViewHolder.tvRating.setText(String.valueOf(newValue));

                // Creamos el cursor para traer los datos de la BD
                SQLiteOpenHelper PetsDataBaseHelper = new PetsDataBaseHelper(v.getContext());

                // Extrae la base de datos para trabajar con ella
                SQLiteDatabase db = PetsDataBaseHelper.getReadableDatabase();

                ContentValues petValues = new ContentValues();
                /*
                 * creamos cada uno de los campos de la fila a insertar
                petValues.put("RATING",String.valueOf(newValue));

                // Ejecutamos el update del rating en esta mascota en concreto
                db.update("PET", petValues,"_id= ?",new String[]{String.valueOf(pet.getPet_id())});

            }
        });
         */
    }


    @Override
    public int getItemCount() {
        return scores.size();
    }
}
