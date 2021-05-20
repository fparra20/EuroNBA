package com.example.euronba.controller;

import com.example.euronba.model.Scoreboard;
import com.example.euronba.model.TeamScore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RetrieveScoreboard {

    public ArrayList<Scoreboard> getScoreboardsOnDay(String date) {

        ArrayList<Scoreboard> sList = new ArrayList<>();

        JSONObject jobj;

        try {
            jobj = new RetrieveInfo().execute("http://data.nba.net/data/10s/prod/v1/" + date + "/scoreboard.json").get();

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

                /*
                String outputDate
                        = Instant.parse(jobjScore.getString("startTimeUTC"))
                        .atZone(ZoneId.of("Europe/Paris"))
                        .format(
                                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                        .withLocale(Locale.UK)
                        );

                sb.setStartTimeUTC(outputDate);
                 */
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
