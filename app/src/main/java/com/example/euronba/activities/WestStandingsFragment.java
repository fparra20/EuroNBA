package com.example.euronba.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.euronba.R;
import com.example.euronba.adapters.StandingsAdapter;
import com.example.euronba.model.Standings;

import java.util.ArrayList;

public class WestStandingsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerViewEast = (RecyclerView) inflater.inflate(R.layout.fragment_standings, container, false);

        ArrayList<Standings> stEast = new Standings().getStandingsEast();

        StandingsAdapter adapterEast = new StandingsAdapter(stEast, this.getActivity());

        LinearLayoutManager linearLayoutManagerEast = new LinearLayoutManager(this.getContext());

        recyclerViewEast.setLayoutManager(linearLayoutManagerEast);

        recyclerViewEast.setAdapter(adapterEast);

        return recyclerViewEast;
    }

}