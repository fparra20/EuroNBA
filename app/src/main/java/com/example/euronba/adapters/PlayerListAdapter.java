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
import com.example.euronba.activities.PlayerActivity;
import com.example.euronba.model.Player;
import com.example.euronba.model.Team;

import java.util.List;

// Adaptador que se usa en PlayerListActivity y rellena la lista de los jugadores.
public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerListViewHolder> {
    List<Player> playerList;
    Activity activity;

    // Constructor del adaptador
    public PlayerListAdapter(List<Player> playerList, Activity activity) {
        this.playerList = playerList;
        this.activity = activity;
    }

    // Instancia todos los objetos que se quieren rellenar
    public static class PlayerListViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPlayerListName;
        private final TextView tvPlayerListPositionJersey;
        private final ImageView ivPlayerListTeamLogo;
        private final CardView cvPlayerList;

        // Constructor que vincula los objetos a su vista en el layout
        public PlayerListViewHolder(View itemView) {
            super(itemView);

            tvPlayerListName = itemView.findViewById(R.id.tvPlayerListName);
            tvPlayerListPositionJersey = itemView.findViewById(R.id.tvPlayerListPositionJersey);
            ivPlayerListTeamLogo = itemView.findViewById(R.id.ivPlayerListTeamLogo);
            cvPlayerList = itemView.findViewById(R.id.cvPlayerList);
        }
    }

    // Crea una vista a partir del layout que queremos para rellenar
    @NonNull
    @Override
    public PlayerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_player, parent, false);

        return new PlayerListViewHolder(itemView);
    }

    // Método que vincula la vista con los datos
    @Override
    public void onBindViewHolder(@NonNull PlayerListAdapter.PlayerListViewHolder pListViewHolder, int position) {

        // Obtiene el objeto Player correspondiente a la posición del adaptador
        Player player = playerList.get(position);

        // Obtiene el equipo a partir del Id de equipo del jugador
        Team tm = new Team().getTeamById(player.getTeamId(), activity);

        // Rellena los datos
        pListViewHolder.tvPlayerListName.setText(player.getFullName());
        pListViewHolder.tvPlayerListPositionJersey.setText(activity.getString(R.string.teamListJerseyPos,player.getPos(),player.getJersey()));
        pListViewHolder.ivPlayerListTeamLogo.setImageResource(tm.getLogo());

        // Crea un Listener para la vista del jugador
        pListViewHolder.cvPlayerList.setOnClickListener(v -> {

            // Instancia un objeto intent con la actividad PlayerActivity
            Intent intent = new Intent(activity.getApplicationContext(), PlayerActivity.class);

            // Añade datos al intent necesarios para crear la siguiente actividad
            intent.putExtra(PlayerActivity.EXTRA_PERSONID, player.getPersonId());
            intent.putExtra(PlayerActivity.EXTRA_TEAMURL, tm.getUrlName());

            // Empieza la siguiente actividad.
            activity.startActivity(intent);
        });
    }

    // Obtiene la posición del adaptador
    @Override
    public int getItemCount() {
        return playerList.size();
    }

}
