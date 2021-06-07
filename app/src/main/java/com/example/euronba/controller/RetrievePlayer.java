package com.example.euronba.controller;

import com.example.euronba.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
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

                player.setFirstName(data.getJSONObject(i).getString("firstName"));

                player.setLastName(data.getJSONObject(i).getString("lastName"));

                player.setJersey(data.getJSONObject(i).getString("jersey"));

                player.setPos(data.getJSONObject(i).getString("pos"));

                player.setTeamId(data.getJSONObject(i).getString("teamId"));

                pList.add(player);

                i++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return pList;
    }

    public Player getPlayerInfoById(String id, String teamUrl) {


        Player player = new Player();

        JSONObject jobj;

        try {

            jobj = new RetrieveInfo().execute("http://data.nba.net/v2015/json/mobile_teams/nba/2020/teams/" + teamUrl + "_roster.json").get();

            JSONObject dataTeam = jobj.getJSONObject("t");

            JSONArray dataPlayer = jobj.getJSONObject("t").getJSONArray("pl");

            int i = 0;

            while (dataPlayer.length() > i) {

                if (dataPlayer.getJSONObject(i).getString("pid").equals(id)) {

                    player.setPersonId(dataPlayer.getJSONObject(i).getString("pid"));

                    player.setCollegeName(dataPlayer.getJSONObject(i).getString("hcc"));

                    player.setCountry(dataPlayer.getJSONObject(i).getString("hcc"));

                    player.setDateOfBirthUTC(dataPlayer.getJSONObject(i).getString("dob"));

                    player.setFirstName(dataPlayer.getJSONObject(i).getString("fn"));

                    player.setHeightFt(dataPlayer.getJSONObject(i).getString("ht"));

                    player.setJersey(dataPlayer.getJSONObject(i).getString("num"));

                    player.setLastName(dataPlayer.getJSONObject(i).getString("ln"));

                    player.setPos(dataPlayer.getJSONObject(i).getString("pos"));

                    player.setTeamId(dataTeam.getString("tid"));

                    player.setWeightLbs(dataPlayer.getJSONObject(i).getString("wt"));

                    player.setYearsPro(dataPlayer.getJSONObject(i).getString("y"));
                }
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return player;
    }


    public ArrayList<Player> getPlayersByTeamUrl(String teamUrl) {

        ArrayList<Player> pList = new ArrayList<>();

        JSONObject jobj;

        try {

            jobj = new RetrieveInfo().execute("http://data.nba.net/v2015/json/mobile_teams/nba/2020/teams/" + teamUrl + "_roster.json").get();

            JSONObject dataTeam = jobj.getJSONObject("t");

            JSONArray dataPlayer = jobj.getJSONObject("t").getJSONArray("pl");

            int i = 0;

            while (dataPlayer.length() > i) {

                Player player = new Player();

                player.setPersonId(dataPlayer.getJSONObject(i).getString("pid"));

                player.setCollegeName(dataPlayer.getJSONObject(i).getString("hcc"));

                player.setCountry(dataPlayer.getJSONObject(i).getString("hcc"));

                player.setDateOfBirthUTC(dataPlayer.getJSONObject(i).getString("dob"));

                player.setFirstName(dataPlayer.getJSONObject(i).getString("fn"));

                player.setHeightFt(dataPlayer.getJSONObject(i).getString("ht"));

                player.setJersey(dataPlayer.getJSONObject(i).getString("num"));

                player.setLastName(dataPlayer.getJSONObject(i).getString("ln"));

                player.setPos(dataPlayer.getJSONObject(i).getString("pos"));

                player.setTeamId(dataTeam.getString("tid"));

                player.setWeightLbs(dataPlayer.getJSONObject(i).getString("wt"));

                player.setYearsPro(dataPlayer.getJSONObject(i).getString("y"));

                pList.add(player);

                i++;

                System.out.println(player.getFirstName());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pList;
    }
}