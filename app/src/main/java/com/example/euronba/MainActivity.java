package com.example.euronba;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euronba.controller.RetrieveScoreboard;
import com.example.euronba.model.Scoreboard;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Scoreboard> scoreboards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creamos un objeto recyclerView
        RecyclerView mainRecycler = (RecyclerView) findViewById(R.id.rvScoreboard);

        RetrieveScoreboard rs = new RetrieveScoreboard();

        scoreboards = rs.getScoreboardsOnDay("20210515");

        ScoreboardAdapter sbAdapter = new ScoreboardAdapter(scoreboards, this);

        mainRecycler.setAdapter(sbAdapter);

        mainRecycler.setLayoutManager(new LinearLayoutManager(this));

    }
}