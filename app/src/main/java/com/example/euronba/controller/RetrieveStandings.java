package com.example.euronba.controller;

import com.example.euronba.model.Standings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RetrieveStandings {

    public ArrayList<Standings> getStandingsWest() {

        ArrayList<Standings> pList = new ArrayList<>();

        JSONObject jobj;

        try {

            jobj = new RetrieveInfo().execute("https://data.nba.net/data/10s/prod/v1/current/standings_conference.json").get();

            JSONArray west = jobj.getJSONObject("league").getJSONObject("standard").getJSONObject("conference").getJSONArray("west");
            int i = 0;


            while (west.length() > i) {

                Standings standings = new Standings();

                standings.setTeamId(west.getJSONObject(i).getString("teamId"));

                standings.setWin(west.getJSONObject(i).getString("win"));
                standings.setLoss(west.getJSONObject(i).getString("loss"));
                standings.setWinPct(west.getJSONObject(i).getString("winPct"));

                standings.setLastTenWin(west.getJSONObject(i).getString("lastTenWin"));
                standings.setLastTenLoss(west.getJSONObject(i).getString("lastTenLoss"));

                standings.setGamesBehind(west.getJSONObject(i).getString("gamesBehind"));
                standings.setStreak(west.getJSONObject(i).getJSONObject("teamSitesOnly").getString("streakText"));

                pList.add(standings);

                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pList;
    }

    public ArrayList<Standings> getStandingsEast() {

        ArrayList<Standings> pList = new ArrayList<>();

        JSONObject jobj;

        try {

            jobj = new RetrieveInfo().execute("https://data.nba.net/data/10s/prod/v1/current/standings_conference.json").get();

            JSONArray east = jobj.getJSONObject("league").getJSONObject("standard").getJSONObject("conference").getJSONArray("east");

            int i = 0;

            while (east.length() > i) {

                Standings standings = new Standings();

                standings.setTeamId(east.getJSONObject(i).getString("teamId"));

                standings.setWin(east.getJSONObject(i).getString("win"));
                standings.setLoss(east.getJSONObject(i).getString("loss"));
                standings.setWinPct(east.getJSONObject(i).getString("winPct"));

                standings.setLastTenWin(east.getJSONObject(i).getString("lastTenWin"));
                standings.setLastTenLoss(east.getJSONObject(i).getString("lastTenLoss"));

                standings.setGamesBehind(east.getJSONObject(i).getString("gamesBehind"));
                standings.setStreak(east.getJSONObject(i).getJSONObject("teamSitesOnly").getString("streakText"));

                pList.add(standings);

                i++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pList;
    }

}
