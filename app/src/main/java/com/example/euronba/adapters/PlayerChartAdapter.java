package com.example.euronba.adapters;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.euronba.R;
import com.example.euronba.model.PlayerChart;

import java.util.List;

public class PlayerChartAdapter extends RecyclerView.Adapter {
    List<PlayerChart> movieList;

    public PlayerChartAdapter(List<PlayerChart> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_item, parent, false);

        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            rowViewHolder.txtRank.setBackgroundResource(R.color.orange_black);
            rowViewHolder.txtMovieName.setBackgroundResource(R.color.orange_black);
            rowViewHolder.txtYear.setBackgroundResource(R.color.orange_black);
            rowViewHolder.txtCost.setBackgroundResource(R.color.orange_black);

            rowViewHolder.txtRank.setText("Player");
            rowViewHolder.txtMovieName.setText("Pts");
            rowViewHolder.txtYear.setText("Ast");
            rowViewHolder.txtCost.setText("Reb");
        } else {
            PlayerChart modal = movieList.get(rowPos-1);

            int rowColor = 0;
            if (rowPos%2==0) {
                rowColor = R.color.orange_mid;
            }

            if(rowPos%2!=0){
                rowColor = R.color.orange_light;
            }
            rowViewHolder.txtRank.setBackgroundResource(rowColor);
            rowViewHolder.txtMovieName.setBackgroundResource(rowColor);
            rowViewHolder.txtYear.setBackgroundResource(rowColor);
            rowViewHolder.txtCost.setBackgroundResource(rowColor);

            rowViewHolder.txtRank.setText(modal.getFirstName()+ " "+modal.getLastName());
            rowViewHolder.txtMovieName.setText(modal.getPoints());
            rowViewHolder.txtYear.setText(modal.getTotReb());
            rowViewHolder.txtCost.setText(modal.getAssists());
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size()+1; // one more to add header row
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtRank;
        protected TextView txtMovieName;
        protected TextView txtYear;
        protected TextView txtCost;

        public RowViewHolder(View itemView) {
            super(itemView);

            txtRank = itemView.findViewById(R.id.txtRank);
            txtMovieName = itemView.findViewById(R.id.txtMovieName);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtCost = itemView.findViewById(R.id.txtCost);
        }
    }
}
