package com.example.euronba.controller;

import com.example.euronba.model.Scoreboard;
import com.example.euronba.model.TeamScore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class RetrieveScoreboard {

    public ArrayList<Scoreboard> getScoreboardsOnDay(String date) {

        ArrayList<Scoreboard> sList = new ArrayList<>();

        JSONObject jobj;

        try {
            jobj = new RetrieveInfo().execute("https://data.nba.net/data/10s/prod/v1/" + date + "/scoreboard.json").get();

            ArrayList<String> pList = new ArrayList<>();

            JSONArray data = jobj.getJSONArray("games");

            int i = 0;

            while (data.length() > i) {

                Scoreboard sb = new Scoreboard();

                JSONObject jobjScore = data.getJSONObject(i);

                sb.setArenaCity(jobjScore.getJSONObject("arena").getString("city"));
                sb.setArenaName(jobjScore.getJSONObject("arena").getString("name"));
                sb.setClock(jobjScore.getString("clock"));
                sb.setCurrentPeriod(jobjScore.getJSONObject("period").getInt("current"));
                sb.setGameDuration(jobjScore.getJSONObject("gameDuration").getString("hours") + ":" + jobjScore.getJSONObject("gameDuration").getString("minutes"));
                sb.setGameId(jobjScore.getString("gameId"));
                sb.setSeasonStageId(jobjScore.getInt("seasonStageId"));
                sb.setSeasonYear(jobjScore.getString("seasonYear"));

                // Comprobamos que el partido es de playoffs
                if(jobjScore.getInt("seasonStageId")>=4){

                    // Si lo es, añadimos
                    sb.setSummaryText(jobjScore.getJSONObject("playoffs").getString("seriesSummaryText"));
                }

                // Indica el formato en el que viene dada la fecha de comienzo en UTC
                DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                // Indica que la TimeZone en la que viene dado el dato es UTC
                utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                // Convierte el objeto obtenido en otro de tipo Date
                Date gameUTC = utcFormat.parse(jobjScore.getString("startTimeUTC"));

                // Indica el formato en el que queremos que salga la fecha
                DateFormat pstFormat = new SimpleDateFormat("HH:mm");

                // Indica la timeZone en la que queremos que salga
                // Con TimeZone.getDefault se obtiene la zona horaria del sistema en ese momento
                pstFormat.setTimeZone(TimeZone.getDefault());

                // Almacenamos la hora ya formateada
                sb.setStartTimeUTC(pstFormat.format(gameUTC));

                sb.setStatusNum(jobjScore.getInt("statusNum"));

                TeamScore localT = new TeamScore();

                localT.setTeamId(jobjScore.getJSONObject("hTeam").getString("teamId"));
                localT.setTricode(jobjScore.getJSONObject("hTeam").getString("triCode"));
                localT.setWin(jobjScore.getJSONObject("hTeam").getString("win"));
                localT.setLoss(jobjScore.getJSONObject("hTeam").getString("loss"));
                localT.setSeriesWin(jobjScore.getJSONObject("hTeam").getString("seriesWin"));
                localT.setSeriesLoss(jobjScore.getJSONObject("hTeam").getString("seriesLoss"));
                localT.setScore(jobjScore.getJSONObject("hTeam").getString("score"));

                TeamScore visitorT = new TeamScore();

                visitorT.setTeamId(jobjScore.getJSONObject("vTeam").getString("teamId"));
                visitorT.setTricode(jobjScore.getJSONObject("vTeam").getString("triCode"));
                visitorT.setWin(jobjScore.getJSONObject("vTeam").getString("win"));
                visitorT.setLoss(jobjScore.getJSONObject("vTeam").getString("loss"));
                visitorT.setSeriesWin(jobjScore.getJSONObject("vTeam").getString("seriesWin"));
                visitorT.setSeriesLoss(jobjScore.getJSONObject("vTeam").getString("seriesLoss"));
                visitorT.setScore(jobjScore.getJSONObject("vTeam").getString("score"));

                sb.setLocalTeam(localT);
                sb.setVisitorTeam(visitorT);

                sList.add(sb);

                i++;


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sList;
    }
}
