package com.example.euronba.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.adapters.StandingsAdapter;
import com.example.euronba.model.Standings;

import java.util.ArrayList;

public class EastStandingsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // OESTE
        RecyclerView recyclerViewWest = (RecyclerView) inflater.inflate(R.layout.fragment_standings, container, false);

        ArrayList<Standings> stWest = new Standings().getStandingsWest();

        StandingsAdapter adapterWest = new StandingsAdapter(stWest, this.getActivity());

        LinearLayoutManager linearLayoutManagerWest = new LinearLayoutManager(this.getContext());

        recyclerViewWest.setLayoutManager(linearLayoutManagerWest);

        recyclerViewWest.setAdapter(adapterWest);

        return recyclerViewWest;
    }

}