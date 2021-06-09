package com.example.euronba.controller;

import com.example.euronba.model.Boxscore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Usuario
 */

public class RetrieveBoxscore {

    public ArrayList<Boxscore> getBoxscore(String date, String gameId) {

        ArrayList<Boxscore> pcList = new ArrayList<>();

        JSONObject jobj;

        try {

            // Obtenemos la lista de jugadores
            jobj = new RetrieveInfo().execute("https://data.nba.net/data/10s/prod/v1/" + date + "/" + gameId + "_boxscore.json").get();

            JSONArray data = jobj.getJSONObject("stats").getJSONArray("activePlayers");

            int i = 0;

            while (data.length() > i) {

                // El mismo jugador ha podido jugar en varios equipos la misma temproada, y con
                // este bucle recogemos sus estadísticas en cada uno de los equipos, y el total de la temporada
                Boxscore sb = new Boxscore();

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
                sb.setMin(jobjScore.getString("min"));
                sb.setOffReb(jobjScore.getString("offReb"));
                sb.setPlusMinus(jobjScore.getString("plusMinus"));
                sb.setPoints(jobjScore.getString("points"));
                sb.setPos(jobjScore.getString("pos"));
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
