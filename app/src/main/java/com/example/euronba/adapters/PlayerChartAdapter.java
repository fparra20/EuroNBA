package com.example.euronba.adapters;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

        System.out.println(rowPos + " - " + "aaa");

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            rowViewHolder.linearRow.setBackgroundResource(R.color.purple_light);

            TextView tv;
            for (int i=0; i<rowViewHolder.linearRow.getChildCount();i++)
            {
                View view = rowViewHolder.linearRow.getChildAt(i);
                if (view instanceof TextView){
                    tv = (TextView) view;
                    tv.setTextColor(view.getContext().getResources().getColor(R.color.white));
                    tv.setTypeface(null, Typeface.BOLD);
                }
            }

            rowViewHolder.txtRank.setText("Player");
            rowViewHolder.txtMovieName.setText("Pts");
            rowViewHolder.txtYear.setText("Ast");
            rowViewHolder.txtCost.setText("Reb");
        } else {
            PlayerChart modal = movieList.get(rowPos - 1);
                int rowColor = 0;
                if (rowPos % 2 == 0) {
                    rowColor = R.color.eblue_mid;
                }

                if (rowPos % 2 != 0) {
                    rowColor = R.color.eblue_light;
                }
            rowViewHolder.linearRow.setBackgroundResource(rowColor);

                rowViewHolder.txtRank.setText(modal.getFirstName() + " " + modal.getLastName());
                rowViewHolder.txtMovieName.setText(modal.getPoints());
                rowViewHolder.txtYear.setText(modal.getTotReb());
                rowViewHolder.txtCost.setText(modal.getAssists());
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size() + 1; // one more to add header row
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtRank;
        TextView txtMovieName;
        TextView txtYear;
        TextView txtCost;
        LinearLayout linearRow;


        public RowViewHolder(View itemView) {
            super(itemView);

            txtRank = itemView.findViewById(R.id.txtRank);
            txtMovieName = itemView.findViewById(R.id.txtMovieName);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtCost = itemView.findViewById(R.id.txtCost);
            linearRow = itemView.findViewById(R.id.tableRow);
        }
    }
}
