package com.example.euronba.controller;

import com.example.euronba.model.Player;
import com.example.euronba.model.PlayerDraft;
import com.example.euronba.model.PlayerTeams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class RetrievePlayer {

    public ArrayList<Player> getPlayers() {

        ArrayList<Player> pList = new ArrayList<>();

        JSONObject jobj;

        try {

            jobj = new RetrieveInfo().execute("https://data.nba.net/data/10s/prod/v1/2020/players.json").get();

            JSONArray data = jobj.getJSONObject("league").getJSONArray("standard");

            int i = 0;

            while (data.length() > i) {

                Player player = new Player();

                player.setPersonId(data.getJSONObject(i).getString("personId"));

                player.setCollegeName(data.getJSONObject(i).getString("collegeName"));

                player.setCountry(data.getJSONObject(i).getString("country"));

                player.setDateOfBirthUTC(data.getJSONObject(i).getString("dateOfBirthUTC"));

                player.setFirstName(data.getJSONObject(i).getString("firstName"));

                PlayerDraft pD = new PlayerDraft();

                pD.setSeasonYear(data.getJSONObject(i).getJSONObject("draft").getString("seasonYear"));

                pD.setPickNum(data.getJSONObject(i).getJSONObject("draft").getString("pickNum"));

                pD.setRoundNum(data.getJSONObject(i).getJSONObject("draft").getString("roundNum"));

                pD.setDraftedTeamId(data.getJSONObject(i).getJSONObject("draft").getString("teamId"));

                player.setDraft(pD);

                player.setHeightMeters(data.getJSONObject(i).getString("heightMeters"));

                player.setIsActive(data.getJSONObject(i).getBoolean("isActive"));

                player.setJersey(data.getJSONObject(i).getString("jersey"));

                player.setLastAffiliation(data.getJSONObject(i).getString("lastAffiliation"));

                player.setLastName(data.getJSONObject(i).getString("lastName"));

                player.setNbaDebutYear(data.getJSONObject(i).getString("nbaDebutYear"));

                player.setPersonId(data.getJSONObject(i).getString("personId"));

                player.setPos(data.getJSONObject(i).getString("pos"));

                player.setTeamId(data.getJSONObject(i).getString("teamId"));

                ArrayList<PlayerTeams> tmList = new ArrayList<>();

                JSONArray tmArray = data.getJSONObject(i).getJSONArray("teams");

                for (int j = 0; j < tmArray.length(); j++) {
                    PlayerTeams tm = new PlayerTeams();

                    tm.setTeamId(tmArray.getJSONObject(j).getString("teamId"));
                    tm.setSeasonStart(tmArray.getJSONObject(j).getString("seasonStart"));
                    tm.setSeasonEnd(tmArray.getJSONObject(j).getString("seasonEnd"));

                    tmList.add(tm);
                }

                player.setTeams(tmList);

                player.setWeightKilograms(data.getJSONObject(i).getString("weightKilograms"));

                player.setYearsPro(data.getJSONObject(i).getString("yearsPro"));

                pList.add(player);

                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pList;
    }

    public Player getPlayerInfoById(String id) {

        ArrayList<Player> allPlayers = getPlayers();

        Player p = new Player();

        for (int i = 0; i < allPlayers.size(); i++) {
            if (allPlayers.get(i).getPersonId().equals(id)) {

                p = allPlayers.get(i);
            }
        }
        return p;
    }
}