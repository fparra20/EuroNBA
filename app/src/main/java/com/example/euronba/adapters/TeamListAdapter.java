package com.example.euronba.adapters;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.activities.TeamActivity;
import com.example.euronba.model.Team;

import java.util.List;

// Adaptador que se usa en TeamListActivity y rellena la lista de los jugadores.
public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.TeamListViewHolder> {
    List<Team> teamList;
    Activity activity;

    // Constructor del adaptador
    public TeamListAdapter(List<Team> teamList, Activity activity) {
        this.teamList = teamList;
        this.activity = activity;
    }

    // Instancia todos los objetos que se quieren rellenar
    public static class TeamListViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTeamListName;
        private final ImageView ivTeamListTeamLogo;
        private final CardView cvTeamList;

        // Constructor que vincula los objetos a su vista en el layout
        public TeamListViewHolder(View itemView) {
            super(itemView);

            tvTeamListName = itemView.findViewById(R.id.tvTeamListName);
            ivTeamListTeamLogo = itemView.findViewById(R.id.ivTeamListTeamLogo);
            cvTeamList = itemView.findViewById(R.id.cvTeamList);
        }
    }

    // Crea una vista a partir del layout que queremos para rellenar
    @NonNull
    @Override
    public TeamListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_team, parent, false);

        return new TeamListViewHolder(itemView);
    }

    // Método que vincula la vista con los datos
    @Override
    public void onBindViewHolder(@NonNull TeamListAdapter.TeamListViewHolder tListViewHolder, int position) {

        // Obtiene el objeto Team correspondiente a la posición del adaptador
        Team tm = teamList.get(position);

        // Rellena los datos
        tListViewHolder.tvTeamListName.setText(tm.getFullName());
        tListViewHolder.ivTeamListTeamLogo.setImageResource(tm.getLogo());

        // Crea un Listener para la vista del equipo
        tListViewHolder.cvTeamList.setOnClickListener(v -> {

            // Instancia un objeto intent con la actividad TeamActivity
            Intent intent = new Intent(activity.getApplicationContext(), TeamActivity.class);

            // Añade datos al intent necesarios para crear la siguiente actividad
            intent.putExtra(TeamActivity.EXTRA_TEAMID, tm.getTeamId());

            // Empieza la siguiente actividad.
            activity.startActivity(intent);
        });
    }

    // Obtiene la posición del adaptador
    @Override
    public int getItemCount() {
        return teamList.size();
    }

}
