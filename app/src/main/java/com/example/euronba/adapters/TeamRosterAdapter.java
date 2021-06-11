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
import com.example.euronba.activities.PlayerActivity;
import com.example.euronba.model.Player;
import com.example.euronba.model.Team;

import java.util.List;

// Adaptador que se usa en Team y rellena la lista de los jugadores.
public class TeamRosterAdapter extends RecyclerView.Adapter<TeamRosterAdapter.TeamRosterViewHolder> {
    List<Player> teamRoster;
    Activity activity;

    // Constructor del adaptador
    public TeamRosterAdapter(List<Player> teamRoster, Activity activity) {
        this.teamRoster = teamRoster;
        this.activity = activity;
    }

    // Instancia todos los objetos que se quieren rellenar
    public static class TeamRosterViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTeamRosterPlayerName;
        private final TextView tvTeamRosterPosition;
        private final TextView tvTeamRosterAge;
        private final TextView tvTeamRosterCollege;
        private final LinearLayout tableRowTeamRoster;

        // Constructor que vincula los objetos a su vista en el layout
        public TeamRosterViewHolder(View itemView) {
            super(itemView);

            tvTeamRosterPlayerName = itemView.findViewById(R.id.tvTeamRosterPlayerName);
            tvTeamRosterPosition = itemView.findViewById(R.id.tvTeamRosterPosition);
            tvTeamRosterAge = itemView.findViewById(R.id.tvTeamRosterPro);
            tvTeamRosterCollege = itemView.findViewById(R.id.tvTeamRosterCollege);
            tableRowTeamRoster = itemView.findViewById(R.id.tableRowTeamRoster);
        }
    }

    // Crea una vista a partir del layout que queremos para rellenar
    @NonNull
    @Override
    public TeamRosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_team_roster, parent, false);

        return new TeamRosterViewHolder(itemView);
    }

    // Método que vincula la vista con los datos
    @Override
    public void onBindViewHolder(@NonNull TeamRosterAdapter.TeamRosterViewHolder teamRostervh, int position) {

        // Obtiene la posición del adaptador
        int rowPos = teamRostervh.getAdapterPosition();

        // Si es la primera posición, genera formatea los datos a modo de cabecera de tabla
        if (rowPos == 0) {

            // Pone el fondo de color distinto al resto de filas
            teamRostervh.tableRowTeamRoster.setBackgroundResource(R.color.purple_light);

            // Instancia un objeto TextView
            TextView tv;

            // Permite acceder al LinearLayout y obtener todas las vistas hijas
            for (int i = 0; i < teamRostervh.tableRowTeamRoster.getChildCount(); i++) {

                // Obtiene la vista del LinearLayout
                View view = teamRostervh.tableRowTeamRoster.getChildAt(i);

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
            // Almacena el objeto Player, que se obtiene desde el Arraylist a partir de
            // la posición del adaptador, restando 1 porque la primera fila es una cabecera
            Player player = teamRoster.get(rowPos - 1);

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
            teamRostervh.tableRowTeamRoster.setBackgroundResource(rowColor);

            // Obtiene un equipo a partir de la id
            Team tm = new Team().getTeamById(player.getTeamId(), activity);

            // Rellena el resto de datos
            teamRostervh.tvTeamRosterPlayerName.setText(player.getFullName());
            teamRostervh.tvTeamRosterPosition.setText(player.getPos());
            teamRostervh.tvTeamRosterAge.setText(player.getYearsPro());
            teamRostervh.tvTeamRosterCollege.setText(player.getCollegeName());

            // Crea un listener para el nombre del jugador
            teamRostervh.tvTeamRosterPlayerName.setOnClickListener(v -> {

                // Instancia un objeto intent con la actividad PlayerActivity
                Intent intent = new Intent(v.getContext(), PlayerActivity.class);

                // Añade datos al intent necesarios para crear la siguiente actividad
                intent.putExtra(PlayerActivity.EXTRA_PERSONID, player.getPersonId());
                intent.putExtra(PlayerActivity.EXTRA_TEAMURL, tm.getUrlName());

                // Abre la nueva actividad
                activity.startActivity(intent);
            });
        }
    }

    // Obtiene la posición del adaptador
    @Override
    public int getItemCount() {
        return teamRoster.size() + 1;
    }

}
