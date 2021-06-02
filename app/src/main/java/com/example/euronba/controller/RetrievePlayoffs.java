package com.example.euronba.controller;


import com.example.euronba.model.PlayoffsBracket;
import com.example.euronba.model.TeamPlayoffs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RetrievePlayoffs {

    public ArrayList<PlayoffsBracket> getPlayOffsByYear(int year) {

        ArrayList<PlayoffsBracket> poList = new ArrayList<>();

        JSONObject jobj;

        try {
            jobj = new RetrieveInfo().execute("http://data.nba.net/v2015/json/mobile_teams/nba/" + year + "/scores/00_playoff_bracket.json").get();

            int i = 0;

            JSONArray rounds = jobj.getJSONObject("pb").getJSONArray("r");

            while (rounds.length() > i) {

                int j = 0;
                JSONArray conferences = jobj.getJSONObject("pb").getJSONArray("r").getJSONObject(i).getJSONArray("co");

                while (conferences.length() > j) {
                    JSONArray series = jobj.getJSONObject("pb").getJSONArray("r").getJSONObject(i).getJSONArray("co").getJSONObject(j).getJSONArray("ser");

                    int k = 0;

                    while (series.length() > k) {
                        PlayoffsBracket po = new PlayoffsBracket();

                        po.setRoundNum(String.valueOf(rounds.getJSONObject(i).getInt("id")));

                        po.setConfName(conferences.getJSONObject(j).getString("val"));

                        po.setGameNumber(series.getJSONObject(k).getInt("in"));

                        po.setSummaryStatusText(series.getJSONObject(k).getString("seri"));

                        TeamPlayoffs bottomRow = new TeamPlayoffs();

                        bottomRow.setSeedNum(series.getJSONObject(k).getString("t2s"));

                        bottomRow.setWins(String.valueOf(series.getJSONObject(k).getInt("t2w")));

                        bottomRow.setTeamId(series.getJSONObject(k).getString("tid2"));

                        TeamPlayoffs topRow = new TeamPlayoffs();

                        topRow.setSeedNum(series.getJSONObject(k).getString("t1s"));

                        topRow.setWins(String.valueOf(series.getJSONObject(k).getInt("t1w")));

                        topRow.setTeamId(series.getJSONObject(k).getString("tid1"));

                        po.setBottomRow(bottomRow);

                        po.setTopRow(topRow);

                        poList.add(po);

                        k++;
                    }

                    j++;
                }

                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return poList;
    }

    public ArrayList<PlayoffsBracket> getPlayOffsByYearConfRound(int year, String conf, String round){
        ArrayList<PlayoffsBracket> poList = getPlayOffsByYear(year);

        ArrayList<PlayoffsBracket> poFiltered = new ArrayList<>();

        for(int i = 0 ; i<poList.size() ; i++){

            if (poList.get(i).getConfName().equals(conf) && poList.get(i).getRoundNum().equals(round)) {
                poFiltered.add(poList.get(i));
            }
        }

        return poFiltered;
    }
}