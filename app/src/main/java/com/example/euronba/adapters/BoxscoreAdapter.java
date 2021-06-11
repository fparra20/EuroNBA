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

// Adaptador que se usa en GameActtivity y rellena las tablas de estadísticas de los partidos.
public class BoxscoreAdapter extends RecyclerView.Adapter<BoxscoreAdapter.BoxscoreViewHolder> {
    List<Boxscore> playerBoxList;
    Activity activity;

    // Constructor del adaptador
    public BoxscoreAdapter(List<Boxscore> playerBoxList, Activity activity) {
        this.playerBoxList = playerBoxList;
        this.activity = activity;
    }

    // Instancia todos los objetos que se quieren rellenar
    public static class BoxscoreViewHolder extends RecyclerView.ViewHolder {
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

        // Constructor que vincula los objetos a su vista en el layout
        public BoxscoreViewHolder(View itemView) {
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

    // Crea una vista a partir del layout que queremos para rellenar
    @NonNull
    @Override
    public BoxscoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_boxscore, parent, false);

        return new BoxscoreViewHolder(itemView);
    }

    // Método que vincula la vista con los datos
    // Da al RecyclerView un formato de tabla
    @Override
    public void onBindViewHolder(@NonNull BoxscoreAdapter.BoxscoreViewHolder bsHolder, int position) {

        // Obteiene la posición del adaptador
        int rowPos = bsHolder.getAdapterPosition();

        // Si es la primera posición, genera formatea los datos a modo de cabecera de tabla
        if (rowPos == 0) {

            // Pone el fondo de color distinto al resto de filas
            bsHolder.linearRow.setBackgroundResource(R.color.purple_light);

            // Instancia un objeto TextView
            TextView tv;

            // Permite acceder al LinearLayout y obtener todas las vistas hijas
            for (int i = 0; i < bsHolder.linearRow.getChildCount(); i++) {

                // Obtiene la vista del LinearLayout
                View view = bsHolder.linearRow.getChildAt(i);

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

            // Almacena el objeto Boxscore, que se obtiene desde el Arraylist a partir de
            // la posición del adaptador, restando 1 porque la primera fila es una cabecera
            Boxscore playerBox = playerBoxList.get(rowPos - 1);

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
            bsHolder.linearRow.setBackgroundResource(rowColor);

            // Guarda un string con el nombre completo del jugador
            String playerFullName = playerBox.getFullName();

            // Si el jugador no ha sido titular, la posición aparecerá vacía.
            // Si lo ha sido, lo marcamos al final del nombre
            if (!playerBox.getPos().equals("")) {

                // Controla que los jugadores titulares salgan en negrita
                bsHolder.tvPlayerBoxName.setTypeface(null, Typeface.BOLD);
            }

            // Muestra el jugador y su posición. Si es titular, saldrá su posición, si no, saldrá en blanco
            bsHolder.tvPlayerBoxName.setText(playerFullName + " " + playerBox.getPos());

            // Comprueba que el jugador ha jugado el partido
            if (playerBox.dnp.isEmpty()) {
                bsHolder.tvPlayerBoxMin.setText(playerBox.getMin());
                bsHolder.tvPlayerBoxPoints.setText(playerBox.getPoints());
                bsHolder.tvPlayerBoxAssists.setText(playerBox.getAssists());
                bsHolder.tvPlayerBoxTotReb.setText(playerBox.getTotReb());
            } else {
                // Si el jugador no ha jugado el partido, aumentamos el tamaño del campo Min para que
                // quepa el texto DNP (que normalmente será mayor, algo así como DNP- Technical Decision)
                // Y el resto de campos se mostrarán en blanco.
                LinearLayout.LayoutParams params = new
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;

                // Borra 3 de los textvieww que igualmente no se iban a mostrar para que no ocupen
                // espacio innecesariamente
                bsHolder.tvPlayerBoxPoints.setVisibility(View.GONE);
                bsHolder.tvPlayerBoxAssists.setVisibility(View.GONE);
                bsHolder.tvPlayerBoxTotReb.setVisibility(View.GONE);
                // Asigna al textView Min las nuevas propiedades.
                bsHolder.tvPlayerBoxMin.setLayoutParams(params);

                // Muestra el texto DNP
                bsHolder.tvPlayerBoxMin.setText(playerBox.getDnp());
            }

            // Rellena el resto de datos
            bsHolder.tvPlayerBoxBlocks.setText(playerBox.getBlocks());
            bsHolder.tvPlayerBoxSteals.setText(playerBox.getSteals());
            bsHolder.tvPlayerBoxFgm.setText(playerBox.getFgm());
            bsHolder.tvPlayerBoxFga.setText(playerBox.getFga());
            bsHolder.tvPlayerBoxFgp.setText(playerBox.getFgp());
            bsHolder.tvPlayerBoxTpm.setText(playerBox.getTpm());
            bsHolder.tvPlayerBoxTpa.setText(playerBox.getTpa());
            bsHolder.tvPlayerBoxTpp.setText(playerBox.getTpp());
            bsHolder.tvPlayerBoxFtm.setText(playerBox.getFtm());
            bsHolder.tvPlayerBoxFta.setText(playerBox.getFta());
            bsHolder.tvPlayerBoxFtp.setText(playerBox.getFtp());
            bsHolder.tvPlayerBoxOffReb.setText(playerBox.getOffReb());
            bsHolder.tvPlayerBoxDefReb.setText(playerBox.getDefReb());
            bsHolder.tvPlayerBoxTurnovers.setText(playerBox.getTurnovers());
            bsHolder.tvPlayerBoxPFouls.setText(playerBox.getpFouls());
            bsHolder.tvPlayerBoxPlusMinus.setText(playerBox.getPlusMinus());


            // Obtiene el url del equipo
            String tmUrl = new Team().getTeamById(playerBox.getTeamId(), activity).getUrlName();

            // Crea un listener para el nombre del jugador
            bsHolder.tvPlayerBoxName.setOnClickListener(v -> {

                // Crea un intent que manda la aplicación a PlayerActivity
                Intent intent = new Intent(v.getContext(), PlayerActivity.class);

                // Añade datos al intent necesarios para crear la siguiente actividad
                intent.putExtra(PlayerActivity.EXTRA_PERSONID, playerBox.getPersonId());
                intent.putExtra(PlayerActivity.EXTRA_TEAMURL, tmUrl);

                // Empieza la siguiente actividad.
                activity.startActivity(intent);
            });
        }
    }

    // Obtiene la posición del adaptador
    @Override
    public int getItemCount() {
        return playerBoxList.size() + 1;
    }

}
