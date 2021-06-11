package com.example.euronba.adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.model.PlayoffsBracket;
import com.example.euronba.model.Team;

import java.util.ArrayList;

// Adapaptador que se usa en PlayoffsActivity y rellena la vista de los PlayOffs.
public class PlayoffsAdapter extends RecyclerView.Adapter<PlayoffsAdapter.PlayoffsViewHolder> {
    ArrayList<PlayoffsBracket> poBracket;
    Activity activity;

    // Constructor del adaptador
    public PlayoffsAdapter(ArrayList<PlayoffsBracket> poBracket, Activity activity) {
        this.poBracket = poBracket;
        this.activity = activity;
    }

    // Instancia todos los objetos que se quieren rellenar
    public static class PlayoffsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPlayOffsTopSeed;
        private final TextView tvPlayOffsTopTricode;
        private final TextView tvPlayOffsTopWins;
        private final ImageView ivPlayOffsTopLogo;

        private final TextView tvPlayOffsBottomSeed;
        private final TextView tvPlayOffsBottomTricode;
        private final TextView tvPlayOffsBottomWins;
        private final ImageView ivPlayOffsBottomLogo;

        // Constructor que vincula los objetos a su vista en el layout
        public PlayoffsViewHolder(View itemView) {
            super(itemView);

            tvPlayOffsTopSeed = itemView.findViewById(R.id.tvPlayOffsTopSeed);
            tvPlayOffsTopTricode = itemView.findViewById(R.id.tvPlayOffsTopTricode);
            tvPlayOffsTopWins = itemView.findViewById(R.id.tvPlayOffsTopWins);
            ivPlayOffsTopLogo = itemView.findViewById(R.id.ivPlayOffsTopLogo);

            tvPlayOffsBottomSeed = itemView.findViewById(R.id.tvPlayOffsBottomSeed);
            tvPlayOffsBottomTricode = itemView.findViewById(R.id.tvPlayOffsBottomTricode);
            tvPlayOffsBottomWins = itemView.findViewById(R.id.tvPlayOffsBottomWins);
            ivPlayOffsBottomLogo = itemView.findViewById(R.id.ivPlayOffsBottomLogo);
        }
    }

    // Crea una vista a partir del layout que queremos para rellenar
    @NonNull
    @Override
    public PlayoffsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_playoff_series, parent, false);

        return new PlayoffsViewHolder(itemView);
    }

    // Método que vincula la vista con los datos
    @Override
    public void onBindViewHolder(@NonNull PlayoffsAdapter.PlayoffsViewHolder paHolder, int position) {

        // Obtiene el objeto PlayoffsBracket correspondiente a la posición del adaptador
        PlayoffsBracket poSeries = poBracket.get(position);

        // Obtiene un objeto de equipo para cada uno de los dos que hay, uno arriba y otro abajo
        Team tmBottom = new Team().getTeamById(poSeries.getBottomRow().getTeamId(), activity);
        Team tmTop = new Team().getTeamById(poSeries.getTopRow().getTeamId(), activity);

        // Rellena el resto de datos para cada uno de los equipos
        paHolder.tvPlayOffsTopTricode.setText(tmTop.getTricode());
        paHolder.tvPlayOffsTopSeed.setText(poSeries.getTopRow().getSeedNum());
        paHolder.tvPlayOffsTopWins.setText(poSeries.getTopRow().getWins());
        paHolder.ivPlayOffsTopLogo.setImageResource(tmTop.getLogo());

        paHolder.tvPlayOffsBottomTricode.setText(tmBottom.getTricode());
        paHolder.tvPlayOffsBottomSeed.setText(poSeries.getBottomRow().getSeedNum());
        paHolder.tvPlayOffsBottomWins.setText(poSeries.getBottomRow().getWins());
        paHolder.ivPlayOffsBottomLogo.setImageResource(tmBottom.getLogo());
    }

    // Obtiene la posición del adaptador
    @Override
    public int getItemCount() {
        return poBracket.size();
    }

}
