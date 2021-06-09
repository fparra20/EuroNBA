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

// Clase que mostrará una tabla de clasificación de los equipos en la Conferencia Este
public class EastStandingsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Instancia un objeto RecyclerView adjuntando el layout Fragment_Standings
        RecyclerView recyclerViewWest = (RecyclerView) inflater.inflate(R.layout.fragment_standings, container, false);

        // Almacena un ArrayList de clasificaciones a partir de una llamada a la clase Standings
        ArrayList<Standings> stWest = new Standings().getStandingsWest();

        // Instancia el adaptador de Standings a partir de la lista almacenada anteriormente.
        StandingsAdapter adapterWest = new StandingsAdapter(stWest, this.getActivity());

        // Instancia un objeto LinearLayoutManager a partir del contexto del fragment
        LinearLayoutManager linearLayoutManagerWest = new LinearLayoutManager(this.getContext());

        // Asocia el Layout al RecyclerView
        recyclerViewWest.setLayoutManager(linearLayoutManagerWest);

        // Asocia el adaptador al RecyclerView
        recyclerViewWest.setAdapter(adapterWest);

        // Devuelve el recyclerView para poder ser mostrado cuando se llame.
        return recyclerViewWest;
    }

}