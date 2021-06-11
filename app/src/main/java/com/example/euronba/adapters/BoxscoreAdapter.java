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
import com.example.euronba.model.Boxscore;
import com.example.euronba.model.Team;

import java.util.List;

public class BoxscoreAdapter extends RecyclerView.Adapter {
    List<Boxscore> playerBoxList;
    Activity activity;

    public BoxscoreAdapter(List<Boxscore> playerBoxList, Activity activity) {
        this.playerBoxList = playerBoxList;
        this.activity = activity;
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPlayerBoxName;
        private final TextView tvPlayerBoxMin;
        private final TextView tvPlayerBoxPoints;
        private final TextView tvPlayerBoxAssists;
        private final TextView tvPlayerBoxTotReb;
        private final TextView tvPlayerBoxBlocks;
        private final TextView tvPlayerBoxSteals;
        private final TextView tvPlayerBoxFgm;
        private final TextView tvPlayerBoxFga;
        private final TextView tvPlayerBoxFgp;
        private final TextView tvPlayerBoxTpm;
        private final TextView tvPlayerBoxTpa;
        private final TextView tvPlayerBoxTpp;
        private final TextView tvPlayerBoxFtm;
        private final TextView tvPlayerBoxFta;
        private final TextView tvPlayerBoxFtp;
        private final TextView tvPlayerBoxOffReb;
        private final TextView tvPlayerBoxDefReb;
        private final TextView tvPlayerBoxTurnovers;
        private final TextView tvPlayerBoxPFouls;
        private final TextView tvPlayerBoxPlusMinus;
        private final LinearLayout linearRow;


        public RowViewHolder(View itemView) {
            super(itemView);

            tvPlayerBoxName = itemView.findViewById(R.id.tvPlayerBoxName);
            tvPlayerBoxMin = itemView.findViewById(R.id.tvPlayerBoxMin);
            tvPlayerBoxPoints = itemView.findViewById(R.id.tvPlayerBoxPoints);
            tvPlayerBoxAssists = itemView.findViewById(R.id.tvPlayerBoxAssists);
            tvPlayerBoxTotReb = itemView.findViewById(R.id.tvPlayerBoxTotReb);

            tvPlayerBoxBlocks = itemView.findViewById(R.id.tvPlayerBoxBlocks);
            tvPlayerBoxSteals = itemView.findViewById(R.id.tvPlayerBoxSteals);
            tvPlayerBoxFgm = itemView.findViewById(R.id.tvPlayerBoxFgm);
            tvPlayerBoxFga = itemView.findViewById(R.id.tvPlayerBoxFga);
            tvPlayerBoxFgp = itemView.findViewById(R.id.tvPlayerBoxFgp);
            tvPlayerBoxTpm = itemView.findViewById(R.id.tvPlayerBoxTpm);
            tvPlayerBoxTpa = itemView.findViewById(R.id.tvPlayerBoxTpa);
            tvPlayerBoxTpp = itemView.findViewById(R.id.tvPlayerBoxTpp);
            tvPlayerBoxFtm = itemView.findViewById(R.id.tvPlayerBoxFtm);
            tvPlayerBoxFta = itemView.findViewById(R.id.tvPlayerBoxFta);
            tvPlayerBoxFtp = itemView.findViewById(R.id.tvPlayerBoxFtp);
            tvPlayerBoxOffReb = itemView.findViewById(R.id.tvPlayerBoxOffReb);
            tvPlayerBoxDefReb = itemView.findViewById(R.id.tvPlayerBoxDefReb);
            tvPlayerBoxTurnovers = itemView.findViewById(R.id.tvPlayerBoxTurnovers);
            tvPlayerBoxPFouls = itemView.findViewById(R.id.tvPlayerBoxPFouls);
            tvPlayerBoxPlusMinus = itemView.findViewById(R.id.tvPlayerBoxPlusMinus);
            linearRow = itemView.findViewById(R.id.tableRow);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_boxscore, parent, false);

        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            rowViewHolder.linearRow.setBackgroundResource(R.color.purple_light);

            TextView tv;
            for (int i = 0; i < rowViewHolder.linearRow.getChildCount(); i++) {
                View view = rowViewHolder.linearRow.getChildAt(i);
                if (view instanceof TextView) {
                    tv = (TextView) view;
                    tv.setTextColor(view.getContext().getResources().getColor(R.color.white));
                    tv.setTypeface(null, Typeface.BOLD);
                }
            }
        } else {
            Boxscore playerBox = playerBoxList.get(rowPos - 1);
            int rowColor = 0;
            if (rowPos % 2 == 0) {
                rowColor = R.color.eblue_mid;
            }

            if (rowPos % 2 != 0) {
                rowColor = R.color.eblue_light;
            }
            rowViewHolder.linearRow.setBackgroundResource(rowColor);

            // Guarda un string con el nombre completo del jugador
            String playerFullName = playerBox.getFullName();

            String playerNameWithPosition;

            // Si el jugador no ha sido titular, la posición aparecerá vacía.
            // Si lo ha sido, lo marcamos al final del nombre
            if (!playerBox.getPos().equals("")) {
                playerNameWithPosition = playerFullName + " - " + playerBox.getPos();

                rowViewHolder.tvPlayerBoxName.setText(playerNameWithPosition);
                // Controla que los titulares salgan en negrita
                rowViewHolder.tvPlayerBoxName.setTypeface(null, Typeface.BOLD);
            }

            rowViewHolder.tvPlayerBoxName.setText(playerFullName + " " + playerBox.getPos());
            // Comprueba que el jugador ha jugado el partido
            if (playerBox.dnp.isEmpty()) {
                rowViewHolder.tvPlayerBoxMin.setText(playerBox.getMin());
                rowViewHolder.tvPlayerBoxPoints.setText(playerBox.getPoints());
                rowViewHolder.tvPlayerBoxAssists.setText(playerBox.getAssists());
                rowViewHolder.tvPlayerBoxTotReb.setText(playerBox.getTotReb());
            } else {
                // Si el jugador no ha jugado el partido, aumentamos el tamaño del campo Min para que
                // quepa el texto DNP (que normalmente será mayor, algo así como DNP- Technical Decision)
                // Y el resto de campos se mostrarán en blanco.
                LinearLayout.LayoutParams params = new
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;

                // Borra 3 de los textvieww que igualmente no se iban a mostrar para que no ocupen
                // espacio innecesariamente
                rowViewHolder.tvPlayerBoxPoints.setVisibility(View.GONE);
                rowViewHolder.tvPlayerBoxAssists.setVisibility(View.GONE);
                rowViewHolder.tvPlayerBoxTotReb.setVisibility(View.GONE);
                // Asigna al textView Min las nuevas propiedades.
                rowViewHolder.tvPlayerBoxMin.setLayoutParams(params);

                // Muestra el texto DNP
                rowViewHolder.tvPlayerBoxMin.setText(playerBox.getDnp());
            }

            rowViewHolder.tvPlayerBoxBlocks.setText(playerBox.getBlocks());
            rowViewHolder.tvPlayerBoxSteals.setText(playerBox.getSteals());
            rowViewHolder.tvPlayerBoxFgm.setText(playerBox.getFgm());
            rowViewHolder.tvPlayerBoxFga.setText(playerBox.getFga());
            rowViewHolder.tvPlayerBoxFgp.setText(playerBox.getFgp());
            rowViewHolder.tvPlayerBoxTpm.setText(playerBox.getTpm());
            rowViewHolder.tvPlayerBoxTpa.setText(playerBox.getTpa());
            rowViewHolder.tvPlayerBoxTpp.setText(playerBox.getTpp());
            rowViewHolder.tvPlayerBoxFtm.setText(playerBox.getFtm());
            rowViewHolder.tvPlayerBoxFta.setText(playerBox.getFta());
            rowViewHolder.tvPlayerBoxFtp.setText(playerBox.getFtp());
            rowViewHolder.tvPlayerBoxOffReb.setText(playerBox.getOffReb());
            rowViewHolder.tvPlayerBoxDefReb.setText(playerBox.getDefReb());
            rowViewHolder.tvPlayerBoxTurnovers.setText(playerBox.getTurnovers());
            rowViewHolder.tvPlayerBoxPFouls.setText(playerBox.getpFouls());
            rowViewHolder.tvPlayerBoxPlusMinus.setText(playerBox.getPlusMinus());


            String tmUrl = new Team().getTeamById(playerBox.getTeamId(), activity).getUrlName();
            rowViewHolder.tvPlayerBoxName.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), PlayerActivity.class);

                intent.putExtra(PlayerActivity.EXTRA_PERSONID, playerBox.getPersonId());
                intent.putExtra(PlayerActivity.EXTRA_TEAMURL, tmUrl);

                System.out.println(tmUrl);

                activity.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return playerBoxList.size() + 1; // one more to add header row
    }

}
