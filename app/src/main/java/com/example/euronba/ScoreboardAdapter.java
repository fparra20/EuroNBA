package com.example.euronba;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.model.Scoreboard;
import com.example.euronba.model.Team;

import java.util.ArrayList;

    public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.ScoreViewHolder> {

    ArrayList<Scoreboard> scores;
    Activity activity;

    public ScoreboardAdapter(ArrayList<Scoreboard> scores, Activity activity) {
        this.scores = scores;
        this.activity = activity;
    }
    public static class ScoreViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivLocalLogo;
        private TextView tvLocalScore;
        private ImageView ivVisitorLogo;
        private TextView tvVisitorScore;
        private TextView tvLocalTeamName;
        private TextView tvVisitorTeamName;
        private TextView tvLocalTeamStandings;
        private TextView tvVisitorTeamStandings;
        private TextView tvScoreStatus;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            // Iniciamos todos los item con los que vamos a trabajar
            ivLocalLogo = (ImageView)itemView.findViewById(R.id.ivLocalLogo);
            tvLocalScore = (TextView)itemView.findViewById(R.id.tvLocalScore);

            ivVisitorLogo = (ImageView)itemView.findViewById(R.id.ivVisitorLogo);
            tvVisitorScore = (TextView)itemView.findViewById(R.id.tvVisitorScore);

            tvLocalTeamName = (TextView)itemView.findViewById(R.id.tvLocalTeamName);
            tvVisitorTeamName = (TextView)itemView.findViewById(R.id.tvVisitorTeamName);

            tvLocalTeamStandings = (TextView)itemView.findViewById(R.id.tvLocalTeamStandings);
            tvVisitorTeamStandings = (TextView)itemView.findViewById(R.id.tvVisitorTeamStandings);

            tvScoreStatus = (TextView)itemView.findViewById(R.id.tvScoreStatus);
        }
    }
    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_scoreboard,parent,false);
        return new ScoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder scoreViewHolder, int position) {
        Scoreboard score = scores.get(position);

        Team tm = new Team();

        scoreViewHolder.ivLocalLogo.setImageResource(tm.getTeamById(score.getLocalTeam().getTeamId()).getLogo());
        scoreViewHolder.tvLocalScore.setText(score.getLocalTeam().getScore());

        scoreViewHolder.ivVisitorLogo.setImageResource(tm.getTeamById(score.getVisitorTeam().getTeamId()).getLogo());
        scoreViewHolder.tvVisitorScore.setText(score.getVisitorTeam().getScore());

        scoreViewHolder.tvLocalTeamName.setText(tm.getTeamById(score.getLocalTeam().getTeamId()).getNickname());
        scoreViewHolder.tvVisitorTeamName.setText(tm.getTeamById(score.getVisitorTeam().getTeamId()).getNickname());

        scoreViewHolder.tvLocalTeamStandings.setText(score.getLocalTeam().getWin()+" - "+score.getLocalTeam().getLoss());
        scoreViewHolder.tvVisitorTeamStandings.setText(score.getVisitorTeam().getWin()+" - "+score.getVisitorTeam().getLoss());

        switch(score.getStatusNum()){
            case 1: scoreViewHolder.tvScoreStatus.setText("Yet to start");
            break;
            case 2: scoreViewHolder.tvScoreStatus.setText("Playing");
            break;
            case 3: scoreViewHolder.tvScoreStatus.setText("Finished");
            break;
        }

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
