package com.example.euronba.controller;


import com.example.euronba.model.PlayoffsBracket;
import com.example.euronba.model.TeamPlayoffs;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class RetrievePlayoffs {

    public ArrayList<PlayoffsBracket> getPlayOffsByYear(int year) {

        ArrayList<PlayoffsBracket> poList = new ArrayList<>();

        JSONObject jobj;

        try {
            jobj = new RetrieveInfo().execute("http://data.nba.net/v2015/json/mobile_teams/nba/"+year+"/scores/00_playoff_bracket.json").get();

            JSONArray series = jobj.getJSONObject("pb").getJSONArray("r").getJSONObject(0).getJSONArray("co").getJSONObject(0).getJSONArray("ser");

            int i = 0;

            while (series.length() > i) {

                PlayoffsBracket po = new PlayoffsBracket();

                po.setRoundNum("1");

                po.setConfName("East");

                po.setGameNumber(series.getJSONObject(i).getInt("in"));

                po.setIsSeriesCompleted(true);

                po.setSummaryStatusText(series.getJSONObject(i).getString("seri"));

                TeamPlayoffs bottomRow = new TeamPlayoffs();

                bottomRow.setSeedNum(series.getJSONObject(i).getString("t2s"));

                bottomRow.setWins(String.valueOf(series.getJSONObject(i).getInt("t2w")));

                bottomRow.setTeamId(series.getJSONObject(i).getString("tid2"));

                TeamPlayoffs topRow = new TeamPlayoffs();

                topRow.setSeedNum(series.getJSONObject(i).getString("t1s"));

                topRow.setWins(String.valueOf(series.getJSONObject(i).getInt("t1w")));

                topRow.setTeamId(series.getJSONObject(i).getString("tid1"));

                po.setBottomRow(bottomRow);

                po.setTopRow(topRow);

                poList.add(po);

                i++;

            }
      /*
      i = 0;
      while (playin.length() > i) {
        PlayoffsBracket po = new PlayoffsBracket();
        po.setRoundNum(playin.getJSONObject(i).getString("roundNum"));
        TeamPlayoffs bottomRow = new TeamPlayoffs();
        bottomRow.setSeedNum(playin.getJSONObject(i).getJSONObject("bottomRow").getString("seedNum"));
        po.setBottomRow(bottomRow);
        poList.add(po);
        i++;
      }
       */
        } catch (Exception e) {
            e.printStackTrace();
        }

        return poList;
    }
}