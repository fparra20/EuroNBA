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
import com.example.euronba.activities.TeamActivity;
import com.example.euronba.model.Favorite;
import com.example.euronba.model.Team;

import java.util.List;

// Adapaptador que se usa en FavoritesActivity y rellena la lista de los favoritos.
public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.FavoritesListViewHolder> {
    List<Favorite> favList;
    Activity activity;

    // Constructor del adaptador
    public FavoritesListAdapter(List<Favorite> favList, Activity activity) {
        this.favList = favList;
        this.activity = activity;
    }

    // Instancia todos los objetos que se quieren rellenar
    public static class FavoritesListViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFavoriteListName;
        private final ImageView ivFavoriteListTeamLogo;
        private final CardView cvFavoriteList;

        // Constructor que vincula los objetos a su vista en el layout
        public FavoritesListViewHolder(View itemView) {
            super(itemView);

            tvFavoriteListName = itemView.findViewById(R.id.tvPlayerListName);
            ivFavoriteListTeamLogo = itemView.findViewById(R.id.ivPlayerListTeamLogo);
            cvFavoriteList = itemView.findViewById(R.id.cvPlayerList);
        }
    }

    // Crea una vista a partir del layout que queremos para rellenar
    @NonNull
    @Override
    public FavoritesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_player, parent, false);

        return new FavoritesListViewHolder(itemView);
    }

    // Método que vincula la vista con los datos
    @Override
    public void onBindViewHolder(@NonNull FavoritesListAdapter.FavoritesListViewHolder pListViewHolder, int position) {

        // Obtiene el objeto favorito correspodiente a partir de la posición del adaptador
        Favorite fav = favList.get(position);

        // Controla que el objeto guardado es un equipo y rellena los datos correspondientes
        if (fav.getType().equals("team")) {

            Team tm = new Team().getTeamById(fav.getId(), activity);
            pListViewHolder.tvFavoriteListName.setText(tm.getFullName());
            pListViewHolder.ivFavoriteListTeamLogo.setImageResource(tm.getLogo());
        }

        // Controla que el objeto guardado es un jugador y rellena los datos correspondientes
        if (fav.getType().equals("player")) {

            pListViewHolder.tvFavoriteListName.setText(fav.getPersonName());
        }

        // Crea un listener para el objeto del jugador o equipo
        pListViewHolder.cvFavoriteList.setOnClickListener(v -> {

            // Instancia un objeto intent vacío
            Intent intent = null;

            // Si el objeto recibido es un jugador manda abre al actividad TeamActivity con los
            // datos extra correspondientes
            if (fav.getType().equals("team")) {

                // Instancia un objeto intent con la actividad PlayerActivity
                intent = new Intent(activity.getApplicationContext(), TeamActivity.class);

                // Añade datos al intent necesarios para crear la siguiente actividad
                intent.putExtra(TeamActivity.EXTRA_TEAMID, fav.getId());
            }

            // Si el objeto recibido es un jugador manda abre al actividad PlayerActivity con los
            // datos extra correspondientes
            if (fav.getType().equals("player")) {

                // Instancia un objeto intent con la actividad PlayerActivity
                intent = new Intent(activity.getApplicationContext(), PlayerActivity.class);

                // Añade datos al intent necesarios para crear la siguiente actividad
                intent.putExtra(PlayerActivity.EXTRA_PERSONID, fav.getId());
                intent.putExtra(PlayerActivity.EXTRA_TEAMURL, fav.getTeamUrl());
            }

            // Abre la nueva actividad
            activity.startActivity(intent);
        });
    }

    // Obtiene la posición del adaptador
    @Override
    public int getItemCount() {
        return favList.size();
    }

}
