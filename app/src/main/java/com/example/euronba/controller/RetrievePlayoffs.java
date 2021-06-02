package com.example.euronba.controller;


import com.example.euronba.model.PlayoffsBracket;
import com.example.euronba.model.TeamPlayoffs;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class RetrievePlayoffs {

    public ArrayList<PlayoffsBracket> getPlayOffsByYear(int year) {

        ArrayList<PlayoffsBracket> poList = new ArrayList<>();

        JSONObject jobj;

        try {
            jobj = new RetrieveInfo().execute("https://data.nba.net/data/10s/prod/v1/" + year + "/playoffsBracket.json").get();

            JSONArray series = jobj.getJSONArray("series");

            try {
                JSONArray playin = jobj.getJSONArray("playin");
            } catch (Exception e) {

            }

            int i = 0;

            while (series.length() > i) {

                PlayoffsBracket po = new PlayoffsBracket();

                po.setRoundNum(series.getJSONObject(i).getString("roundNum"));

                po.setConfName(series.getJSONObject(i).getString("confName"));

                po.setGameNumber(series.getJSONObject(i).getInt("gameNumber"));

                po.setIsSeriesCompleted(series.getJSONObject(i).getBoolean("isSeriesCompleted"));

                po.setSeriesId(series.getJSONObject(i).getString("seriesId"));

                po.setSummaryStatusText(series.getJSONObject(i).getString("summaryStatusText"));

                TeamPlayoffs bottomRow = new TeamPlayoffs();

                bottomRow.setSeedNum(series.getJSONObject(i).getJSONObject("bottomRow").getString("seedNum"));

                bottomRow.setIsSeriesWinner(series.getJSONObject(i).getJSONObject("bottomRow").getBoolean("isSeriesWinner"));

                bottomRow.setWins(series.getJSONObject(i).getJSONObject("bottomRow").getString("wins"));

                bottomRow.setTeamId(series.getJSONObject(i).getJSONObject("bottomRow").getString("teamId"));

                TeamPlayoffs topRow = new TeamPlayoffs();

                topRow.setSeedNum(series.getJSONObject(i).getJSONObject("topRow").getString("seedNum"));

                topRow.setIsSeriesWinner(series.getJSONObject(i).getJSONObject("topRow").getBoolean("isSeriesWinner"));

                topRow.setWins(series.getJSONObject(i).getJSONObject("topRow").getString("wins"));

                topRow.setTeamId(series.getJSONObject(i).getJSONObject("topRow").getString("teamId"));

                po.setRoundNum(series.getJSONObject(i).getString("roundNum"));

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