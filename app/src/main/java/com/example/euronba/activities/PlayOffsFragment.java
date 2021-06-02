package com.example.euronba.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.R;
import com.example.euronba.adapters.PlayoffsAdapter;
import com.example.euronba.adapters.StandingsAdapter;
import com.example.euronba.model.PlayoffsBracket;
import com.example.euronba.model.Standings;

import java.util.ArrayList;

public class PlayOffsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerViewPlayoffs = (RecyclerView) inflater.inflate(R.layout.fragment_standings, container, false);

        ArrayList<PlayoffsBracket> poBracket = new PlayoffsBracket().getPlayOffsBracketByYear(2020);

        PlayoffsAdapter adapterPo = new PlayoffsAdapter(poBracket, this.getActivity());

        LinearLayoutManager linearLayoutManagerPo = new LinearLayoutManager(this.getContext());

        recyclerViewPlayoffs.setLayoutManager(linearLayoutManagerPo);

        recyclerViewPlayoffs.setAdapter(adapterPo);

        return recyclerViewPlayoffs;
    }

}