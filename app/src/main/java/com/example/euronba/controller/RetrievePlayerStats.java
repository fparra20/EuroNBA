package com.example.euronba.controller;

import com.example.euronba.model.PlayerStats;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class RetrievePlayerStats {

    public ArrayList<PlayerStats> getPlayerStatsFromID(String playerID) {

        ArrayList<PlayerStats> sList = new ArrayList<>();

        JSONObject jobj;

        try {

            jobj = new RetrieveInfo().execute("https://data.nba.net/data/10s/prod/v1/2020/players/" + playerID + "_profile.json").get();

            JSONArray data = jobj.getJSONObject("league").getJSONObject("standard").getJSONObject("stats").getJSONObject("regularSeason").getJSONArray("season");

            int i = 0;

            while (data.length() > i) {

                // El mismo jugador ha podido jugar en varios equipos la misma temproada, y con
                // este bucle recogemos sus estadísticas en cada uno de los equipos, y el total de la temporada
                for (int j = 0; j < data.getJSONObject(i).getJSONArray("teams").length(); j++) {
                    PlayerStats stats = new PlayerStats();

                    JSONObject jobjSeason = data.getJSONObject(i).getJSONArray("teams").getJSONObject(j);

                    stats.setSeasonYear(data.getJSONObject(i).getInt("seasonYear"));
                    stats.setGamesPlayed(jobjSeason.getString("gamesPlayed"));
                    stats.setTeamId(jobjSeason.getString("teamId"));
                    stats.setPpg(jobjSeason.getString("ppg"));
                    stats.setApg(jobjSeason.getString("apg"));
                    stats.setBpg(jobjSeason.getString("bpg"));
                    stats.setFgp(jobjSeason.getString("fgp"));
                    stats.setFtp(jobjSeason.getString("ftp"));
                    stats.setGamesStarted(jobjSeason.getString("gamesStarted"));
                    stats.setMpg(jobjSeason.getString("mpg"));
                    stats.setPlusMinus(jobjSeason.getString("plusMinus"));
                    stats.setRpg(jobjSeason.getString("rpg"));
                    stats.setSpg(jobjSeason.getString("spg"));
                    stats.setTopg(jobjSeason.getString("topg"));
                    stats.setTpp(jobjSeason.getString("tpp"));

                    sList.add(stats);
                }

                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sList;
    }
}