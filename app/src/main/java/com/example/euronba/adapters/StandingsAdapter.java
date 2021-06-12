package com.example.euronba.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.activities.TeamActivity;
import com.example.euronba.model.Standings;
import com.example.euronba.model.Team;

import java.util.List;

// Adapaptador que se usa en StandingsActivity y rellena la tabla de clasificaciones.
public class StandingsAdapter extends RecyclerView.Adapter<StandingsAdapter.StandingsViewHolder> {
    List<Standings> standings;
    Activity activity;

    // Constructor del adaptador
    public StandingsAdapter(List<Standings> standings, Activity activity) {
        this.standings = standings;
        this.activity = activity;
    }

    // Instancia todos los objetos que se quieren rellenar
    public static class StandingsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvStandingsName;
        private final TextView tvStandingsWin;
        private final TextView tvStandingsLosses;
        private final TextView tvStandingsPct;
        private final TextView tvStandingsStreak;
        private final TextView tvStandingsLastTen;
        private final LinearLayout tableRowStandings;

        // Constructor que vincula los objetos a su vista en el layout
        public StandingsViewHolder(View itemView) {
            super(itemView);

            // Iniciamos todos los item con los que vamos a trabajar
            tvStandingsName = itemView.findViewById(R.id.tvStandingsName);
            tvStandingsWin = itemView.findViewById(R.id.tvStandingsWin);
            tvStandingsLosses = itemView.findViewById(R.id.tvStandingsLosses);
            tvStandingsPct = itemView.findViewById(R.id.tvStandingsPct);
            tvStandingsStreak = itemView.findViewById(R.id.tvStandingsStreak);
            tvStandingsLastTen = itemView.findViewById(R.id.tvStandingsLastTen);
            tableRowStandings = itemView.findViewById(R.id.tableRowStandings);
        }
    }

    // Crea una vista a partir del layout que queremos para rellenar
    @NonNull
    @Override
    public StandingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_standings, parent, false);

        return new StandingsViewHolder(itemView);
    }

    // Método que vincula la vista con los datos
    @Override
    public void onBindViewHolder(@NonNull StandingsAdapter.StandingsViewHolder standingsViewHolder, int position) {

        // Obtiene la posición del adaptador
        int rowPos = standingsViewHolder.getAdapterPosition();

        // Si es la primera posición, genera formatea los datos a modo de cabecera de tabla
        if (rowPos == 0) {

            // Pone el fondo de color distinto al resto de filas
            standingsViewHolder.tableRowStandings.setBackgroundResource(R.color.purple_light);

            // Instancia un objeto TextView
            TextView tv;

            // Permite acceder al LinearLayout y obtener todas las vistas hijas
            for (int i = 0; i < standingsViewHolder.tableRowStandings.getChildCount(); i++) {

                // Obtiene la vista del LinearLayout
                View view = standingsViewHolder.tableRowStandings.getChildAt(i);

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

            // Almacena el objeto Standings, que se obtiene desde el Arraylist a partir de
            // la posición del adaptador, restando 1 porque la primera fila es una cabecera
            Standings st = standings.get(rowPos - 1);

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
            standingsViewHolder.tableRowStandings.setBackgroundResource(rowColor);

            // Obtiene un equipo a partir de la id
            Team tm = new Team().getTeamById(st.getTeamId(), activity);

            // Rellena el resto de datos
            String rankAndTeam = st.getConfRank()+"- " + tm.getNickname();
            standingsViewHolder.tvStandingsName.setText(rankAndTeam);
            standingsViewHolder.tvStandingsWin.setText(st.getWin());
            standingsViewHolder.tvStandingsLosses.setText(st.getLoss());
            standingsViewHolder.tvStandingsPct.setText(st.getWinPct());
            standingsViewHolder.tvStandingsStreak.setText(st.getStreak());
            String lastTen = st.getLastTenWin() + "-" + st.getLastTenLoss();
            standingsViewHolder.tvStandingsLastTen.setText(lastTen);

            // Crea un listener para el nombre del equipo
            standingsViewHolder.tvStandingsName.setOnClickListener(v -> {

                // Crea un intent que manda la aplicación a TeamActivity
                Intent intent = new Intent(v.getContext(), TeamActivity.class);

                // Añade datos al intent necesarios para crear la siguiente actividad
                intent.putExtra(TeamActivity.EXTRA_TEAMID, st.getTeamId());

                // Empieza la siguiente actividad.
                activity.startActivity(intent);
            });
        }
    }

    // Obtiene la posición del adaptador
    @Override
    public int getItemCount() {
        return standings.size() + 1;
    }

}
