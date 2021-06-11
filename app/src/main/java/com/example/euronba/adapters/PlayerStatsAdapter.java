package com.example.euronba.adapters;


import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.model.PlayerStats;
import com.example.euronba.model.Team;

import java.util.List;

// Adaptador que se usa en PlayerListActivity y rellena la lista de los jugadores.
public class PlayerStatsAdapter extends RecyclerView.Adapter<PlayerStatsAdapter.PlayerStatsViewHolder> {
    List<PlayerStats> playerStatsList;
    Activity activity;

    // Constructor del adaptador
    public PlayerStatsAdapter(List<PlayerStats> playerStatsList, Activity activity) {
        this.playerStatsList = playerStatsList;
        this.activity = activity;
    }

    // Instancia todos los objetos que se quieren rellenar
    public static class PlayerStatsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPlayerStatsSeasonYear;
        private final TextView tvPlayerStatsTeam;
        private final TextView tvPlayerStatsGp;
        private final TextView tvPlayerStatsMpg;

        private final TextView tvPlayerStatsPpg;
        private final TextView tvPlayerStatsRpg;
        private final TextView tvPlayerStatsApg;
        private final TextView tvPlayerStatsBpg;
        private final TextView tvPlayerStatsFgp;
        private final TextView tvPlayerStatsFtp;
        private final TextView tvPlayerStatsTpp;
        private final TextView tvPlayerStatsTopg;
        private final TextView tvPlayerStatsPlusMinus;

        private final LinearLayout tableRowPlayer;

        // Constructor que vincula los objetos a su vista en el layout
        public PlayerStatsViewHolder(View itemView) {
            super(itemView);

            tvPlayerStatsSeasonYear = itemView.findViewById(R.id.tvPlayerStatsSeasonYear);
            tvPlayerStatsTeam = itemView.findViewById(R.id.tvPlayerStatsTeam);
            tvPlayerStatsGp = itemView.findViewById(R.id.tvPlayerStatsGp);
            tvPlayerStatsMpg = itemView.findViewById(R.id.tvPlayerStatsMpg);
            tvPlayerStatsPpg = itemView.findViewById(R.id.tvPlayerStatsPpg);
            tvPlayerStatsRpg = itemView.findViewById(R.id.tvPlayerStatsRpg);
            tvPlayerStatsApg = itemView.findViewById(R.id.tvPlayerStatsApg);
            tvPlayerStatsBpg = itemView.findViewById(R.id.tvPlayerStatsBpg);
            tvPlayerStatsFgp = itemView.findViewById(R.id.tvPlayerStatsFgp);
            tvPlayerStatsFtp = itemView.findViewById(R.id.tvPlayerStatsFtp);
            tvPlayerStatsTpp = itemView.findViewById(R.id.tvPlayerStatsTpp);
            tvPlayerStatsTopg = itemView.findViewById(R.id.tvPlayerStatsTopg);
            tvPlayerStatsPlusMinus = itemView.findViewById(R.id.tvPlayerStatsPlusMinus);

            tableRowPlayer = itemView.findViewById(R.id.tableRowPlayer);

        }
    }

    // Crea una vista a partir del layout que queremos para rellenar
    @NonNull
    @Override
    public PlayerStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_player_seasons, parent, false);

        return new PlayerStatsViewHolder(itemView);
    }

    // Método que vincula la vista con los datos
    @Override
    public void onBindViewHolder(@NonNull PlayerStatsAdapter.PlayerStatsViewHolder psHolder, int position) {

        // Obtiene la posición del adaptador
        int rowPos = psHolder.getAdapterPosition();

        // Si es la primera posición, genera formatea los datos a modo de cabecera de tabla
        if (rowPos == 0) {

            // Pone el fondo de color distinto al resto de filas
            psHolder.tableRowPlayer.setBackgroundResource(R.color.purple_light);

            // Instancia un objeto TextView
            TextView tv;

            // Permite acceder al LinearLayout y obtener todas las vistas hijas
            for (int i = 0; i < psHolder.tableRowPlayer.getChildCount(); i++) {

                // Obtiene la vista del LinearLayout
                View view = psHolder.tableRowPlayer.getChildAt(i);

                // Si la vista encontrada es un textview, le cambia el color a blanco y lo pone
                // en negrita, para que los TextView de la cabecera se vea distinta a las demás.
                if (view instanceof TextView) {
                    tv = (TextView) view;
                    tv.setTextColor(view.getContext().getResources().getColor(R.color.white));
                    tv.setTypeface(null, Typeface.BOLD);
                }
            }
        }

        // Para todas las demás posiciones
        else {

            // Almacena el objeto Playerstats, que se obtiene desde el Arraylist a partir de
            // la posición del adaptador, restando 1 porque la primera fila es una cabecera
            PlayerStats playerStats = playerStatsList.get(rowPos - 1);

            // Inicializa un color
            int rowColor = 0;

            // Indica el color de las filas pares
            if (rowPos % 2 == 0) {
                rowColor = R.color.eblue_mid;
            }

            // Indica el color de las filas impares
            if (rowPos % 2 != 0) {
                rowColor = R.color.eblue_light;
            }

            // Pone como fondo de la fila el color correpondiente
            psHolder.tableRowPlayer.setBackgroundResource(rowColor);

            // Obtiene un equipo a partir de la id
            Team tm = new Team().getTeamById(playerStats.getTeamId(), activity);


            // En ocasiones un jugador ha jugado en más de un equipo en una misma temporada
            // en estos se devuelve un equipo vacío, en el que se suman sus estadísticas totales, TOT
            if (tm.getTricode() == null)
                psHolder.tvPlayerStatsTeam.setText(R.string.total);
            else
                psHolder.tvPlayerStatsTeam.setText(tm.getTricode());

            // Rellena el resto de datos
            psHolder.tvPlayerStatsSeasonYear.setText(String.valueOf(playerStats.getSeasonYear()));
            psHolder.tvPlayerStatsGp.setText(playerStats.getGamesPlayed());
            psHolder.tvPlayerStatsMpg.setText(playerStats.getMpg());
            psHolder.tvPlayerStatsPpg.setText(playerStats.getPpg());
            psHolder.tvPlayerStatsRpg.setText(playerStats.getRpg());
            psHolder.tvPlayerStatsApg.setText(playerStats.getApg());
            psHolder.tvPlayerStatsBpg.setText(playerStats.getBpg());
            psHolder.tvPlayerStatsFgp.setText(playerStats.getFgp());
            psHolder.tvPlayerStatsTpp.setText(playerStats.getTpp());
            psHolder.tvPlayerStatsFtp.setText(playerStats.getFtp());
            psHolder.tvPlayerStatsTopg.setText(playerStats.getTopg());
            psHolder.tvPlayerStatsPlusMinus.setText(playerStats.getPlusMinus());
        }
    }

    // Obtiene la posición del adaptador
    @Override
    public int getItemCount() {
        return playerStatsList.size() + 1;
    }

}
