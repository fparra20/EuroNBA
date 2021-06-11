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

// Clase que mostrará una tabla de clasificación de los equipos en la Conferencia Oeste
public class WestStandingsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Instancia un objeto RecyclerView adjuntando el layout Fragment_Standings
        RecyclerView recyclerViewEast = (RecyclerView) inflater.inflate(R.layout.fragment_standings, container, false);

        // Almacena un ArrayList de clasificaciones a partir de una llamada a la clase Standings
        ArrayList<Standings> stEast = new Standings().getStandingsEast();

        // Instancia el adaptador de Standings a partir de la lista almacenada anteriormente.
        StandingsAdapter adapterEast = new StandingsAdapter(stEast, this.getActivity());

        // Instancia un objeto LinearLayoutManager a partir del contexto del fragment
        LinearLayoutManager linearLayoutManagerEast = new LinearLayoutManager(this.getContext());

        // Asocia el Layout al RecyclerView
        recyclerViewEast.setLayoutManager(linearLayoutManagerEast);

        // Asocia el adaptador al RecyclerView
        recyclerViewEast.setAdapter(adapterEast);

        // Devuelve el recyclerView para poder ser mostrado cuando se llame.
        return recyclerViewEast;
    }

}