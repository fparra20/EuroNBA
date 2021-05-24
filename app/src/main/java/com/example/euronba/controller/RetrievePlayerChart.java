package com.example.euronba.controller;

import com.example.euronba.model.PlayerChart;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Usuario
 */

public class RetrievePlayerChart {

    public ArrayList<PlayerChart> getPlayersChart(String date, String gameId) {

        ArrayList<PlayerChart> pcList = new ArrayList<>();

        JSONObject jobj;

        try {

            // Obtenemos la lista de jugadores
            jobj = new RetrieveInfo().execute("http://data.nba.net/data/10s/prod/v1/" + date + "/" + gameId + "_boxscore.json").get();

            JSONArray data = jobj.getJSONObject("stats").getJSONArray("activePlayers");

            int i = 0;

            while (data.length() > i) {

                // El mismo jugador ha podido jugar en varios equipos la misma temproada, y con
                // este bucle recogemos sus estadísticas en cada uno de los equipos, y el total de la temporada
                PlayerChart sb = new PlayerChart();

                JSONObject jobjScore = data.getJSONObject(i);

                sb.setPersonId(jobjScore.getString("personId"));
                sb.setFirstName(jobjScore.getString("firstName"));
                sb.setLastName(jobjScore.getString("lastName"));
                sb.setJersey(jobjScore.getString("jersey"));
                sb.setTeamId(jobjScore.getString("teamId"));

                sb.setAssists(jobjScore.getString("assists"));
                sb.setBlocks(jobjScore.getString("blocks"));
                sb.setDefReb(jobjScore.getString("defReb"));
                sb.setDnp(jobjScore.getString("dnp"));
                sb.setFga(jobjScore.getString("fga"));
                sb.setFgm(jobjScore.getString("fgm"));
                sb.setFgp(jobjScore.getString("fgp"));
                sb.setFta(jobjScore.getString("fta"));
                sb.setFtm(jobjScore.getString("ftm"));
                sb.setFtp(jobjScore.getString("ftp"));
                sb.setIsOnCourt(jobjScore.getBoolean("isOnCourt"));
                sb.setMin(jobjScore.getString("min"));
                sb.setOffReb(jobjScore.getString("offReb"));
                sb.setPlusMinus(jobjScore.getString("plusMinus"));
                sb.setPoints(jobjScore.getString("points"));
                sb.setPos(jobjScore.getString("pos"));
                sb.setPosition_full(jobjScore.getString("position_full"));
                sb.setSteals(jobjScore.getString("steals"));
                sb.setTotReb(jobjScore.getString("totReb"));
                sb.setTpa(jobjScore.getString("tpa"));
                sb.setTpm(jobjScore.getString("tpm"));
                sb.setTpp(jobjScore.getString("tpp"));
                sb.setTurnovers(jobjScore.getString("turnovers"));
                sb.setpFouls(jobjScore.getString("pFouls"));

                pcList.add(sb);

                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pcList;
    }
}
