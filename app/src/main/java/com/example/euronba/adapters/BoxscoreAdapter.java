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

    public class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPlayerBoxName;
        private TextView tvPlayerBoxMin;
        private TextView tvPlayerBoxPoints;
        private TextView tvPlayerBoxAssists;
        private TextView tvPlayerBoxTotReb;
        private LinearLayout linearRow;


        public RowViewHolder(View itemView) {
            super(itemView);

            tvPlayerBoxName = itemView.findViewById(R.id.tvPlayerBoxName);
            tvPlayerBoxMin = itemView.findViewById(R.id.tvPlayerBoxMin);
            tvPlayerBoxPoints = itemView.findViewById(R.id.tvPlayerBoxPoints);
            tvPlayerBoxAssists = itemView.findViewById(R.id.tvPlayerBoxAssists);
            tvPlayerBoxTotReb = itemView.findViewById(R.id.tvPlayerBoxTotReb);
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

            rowViewHolder.tvPlayerBoxName.setText("Player");
            rowViewHolder.tvPlayerBoxMin.setText("Min");
            rowViewHolder.tvPlayerBoxPoints.setText("Pts");
            rowViewHolder.tvPlayerBoxAssists.setText("Ast");
            rowViewHolder.tvPlayerBoxTotReb.setText("Reb");
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
            String playerFullName = playerBox.getFirstName() + " " + playerBox.getLastName();

            String playerNameWithPosition = "";

            // Si el jugador no ha sido titular, la posición aparecerá vacía.
            // Si lo ha sido, lo marcamos al final del nombre
            if (!playerBox.getPos().equals("")) {
                playerNameWithPosition = playerFullName + " - " + playerBox.getPos();

                rowViewHolder.tvPlayerBoxName.setText(playerNameWithPosition);
                // Controla que los titulares salgan en negrita
                rowViewHolder.tvPlayerBoxName.setTypeface(null, Typeface.BOLD);
            }

            rowViewHolder.tvPlayerBoxName.setText(playerFullName);
            rowViewHolder.tvPlayerBoxMin.setText(playerBox.getMin());
            rowViewHolder.tvPlayerBoxPoints.setText(playerBox.getPoints());
            rowViewHolder.tvPlayerBoxAssists.setText(playerBox.getTotReb());
            rowViewHolder.tvPlayerBoxTotReb.setText(playerBox.getAssists());

            String tmUrl = new Team().getTeamById(playerBox.getTeamId(),activity).getUrlName();
            rowViewHolder.tvPlayerBoxName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), PlayerActivity.class);

                    intent.putExtra(PlayerActivity.EXTRA_PERSONID, playerBox.getPersonId());
                    intent.putExtra(PlayerActivity.EXTRA_TEAMURL, tmUrl);

                    System.out.println(tmUrl);

                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return playerBoxList.size() + 1; // one more to add header row
    }

}
