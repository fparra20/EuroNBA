package com.example.euronba.model;

import com.example.euronba.R;

import java.util.ArrayList;

public class Team {

    public int logo;
    public String teamId;
    public String city;
    public String nickname;
    public String fullName;
    public String tricode;
    public String confName;
    public String divName;
    public String urlName;

    public Team() {

    }

    public Team(int logo, String teamId, String city, String nickname, String fullName, String tricode, String confName, String divName, String urlName) {
        this.logo = logo;
        this.teamId = teamId;
        this.city = city;
        this.nickname = nickname;
        this.fullName = fullName;
        this.tricode = tricode;
        this.confName = confName;
        this.divName = divName;
        this.urlName = urlName;
    }


    public ArrayList<Team> teamsList() {


        ArrayList<Team> teams = new ArrayList();

        teams.add(new Team(R.drawable.bos, "1610612738", "Boston", "Celtics", "Boston Celtics", "BOS", "East", "Atlantic", "celtics"));
        teams.add(new Team(R.drawable.brk, "1610612751", "Brooklyin", "Nets", "Brooklyin Nets", "BKN", "East", "Atlantic", "nets"));
        teams.add(new Team(R.drawable.phi, "1610612755", "Philadelphia", "76ers", "Philadelphia 76ers", "PHI", "East", "Atlantic", "sixers"));
        teams.add(new Team(R.drawable.nyk, "1610612752", "New York", "Knicks", "New York Knicks", "NYK", "East", "Atlantic", "knicks"));
        teams.add(new Team(R.drawable.tor, "1610612761", "Toronto", "Raptors", "Toronto Raptors", "TOR", "East", "Atlantic", "raptors"));

        teams.add(new Team(R.drawable.atl, "1610612737", "Atlanta", "Hawks", "Atlanta Hawks", "ATL", "East", "Southeast", "hawks"));
        teams.add(new Team(R.drawable.cho, "1610612766", "Charlotte", "Hornets", "Charlotte Hornets", "CHA", "East", "Southeast", "hornets"));
        teams.add(new Team(R.drawable.orl, "1610612753", "Orlando", "Magic", "Orlando Magic", "ORL", "East", "Southeast", "magic"));
        teams.add(new Team(R.drawable.mia, "1610612748", "Miami", "Heat", "Miami Heat", "MIA", "East", "Southeast", "heat"));
        teams.add(new Team(R.drawable.was, "1610612764", "Washington", "Wizards", "Washington Wizards", "WAS", "East", "Southeast", "wizards"));

        teams.add(new Team(R.drawable.chi, "1610612741", "Chicago", "Bulls", "Chicago Bulls", "CHI", "East", "Central", "bulls"));
        teams.add(new Team(R.drawable.cle, "1610612739", "Cleveland", "Cavaliers", "Cleveland Cavaliers", "CLE", "East", "Central", "cavaliers"));
        teams.add(new Team(R.drawable.det, "1610612765", "Detroit", "Pistons", "Detroit Pistons", "DET", "East", "Central", "pistons"));
        teams.add(new Team(R.drawable.ind, "1610612754", "Indiana", "Pacers", "Indiana Pacers", "IND", "East", "Central", "pacers"));
        teams.add(new Team(R.drawable.mil, "1610612749", "Milwaukee", "Bucks", "Indiana Pacers", "IND", "East", "Central", "bucks"));

        teams.add(new Team(R.drawable.dal, "1610612742", "Dallas", "Mavericks", "Dallas Mavericks", "DAL", "West", "Southwest", "mavericks"));
        teams.add(new Team(R.drawable.hou, "1610612745", "Houston", "Rockets", "Houston Rockets", "HOU", "West", "Southwest", "rockets"));
        teams.add(new Team(R.drawable.mem, "1610612763", "Memphis", "Grizzlies", "Memphis Grizzlies", "MEM", "West", "Southwest", "grizzlies"));
        teams.add(new Team(R.drawable.sas, "1610612759", "San Antonio", "Spurs", "San Antonio Spurs", "SAS", "West", "Southwest", "spurs"));
        teams.add(new Team(R.drawable.nop, "1610612740", "New Orleans", "Pelicans", "New Orleans Pelicans", "NOP", "West", "Southwest", "pelicans"));

        teams.add(new Team(R.drawable.den, "1610612743", "Denver", "Nuggets", "Denver Nuggets", "DEN", "West", "Northwest", "nuggets"));
        teams.add(new Team(R.drawable.min, "1610612750", "Minnesota", "Timberwolves", "Minnesota Timberwolves", "MIN", "West", "Northwest", "timberwolves"));
        teams.add(new Team(R.drawable.okc, "1610612760", "Oklahoma City", "Thunder", "Oklahoma City Thunder", "OKC", "West", "Northwest", "thunder"));
        teams.add(new Team(R.drawable.por, "1610612757", "Portland", "Trail Blazers", "Portland Trail Blazers", "POR", "West", "Northwest", "blazers"));
        teams.add(new Team(R.drawable.uta, "1610612762", "Utah", "Jazz", "Utah Jazz", "UTA", "West", "Northwest", "jazz"));

        teams.add(new Team(R.drawable.gsw, "1610612744", "Golden State", "Warriors", "Golden State Warriors", "GSW", "West", "Pacific", "warriors"));
        teams.add(new Team(R.drawable.lac, "1610612746", "LA", "Clippers", "Los Angeles Clippers", "LAC", "West", "Pacific", "clippers"));
        teams.add(new Team(R.drawable.lal, "1610612747", "LA", "Lakers", "Los Angeles Lakers", "LAL", "West", "Pacific", "lakers"));
        teams.add(new Team(R.drawable.pho, "1610612756", "Phoenix", "Suns", "Phoenix Suns", "PHX", "West", "Pacific", "suns"));
        teams.add(new Team(R.drawable.sac, "1610612758", "Sacramento", "Kings", "Sacramento Kings", "SAC", "West", "Pacific", "kings"));

        return teams;

    }

    public Team getTeamById(String id) {

        Team tm = new Team();

        for (int i = 0; i < teamsList().size(); i++) {
            if (teamsList().get(i).getTeamId().equals(id)) {
                tm = teamsList().get(i);
            }
        }

        return tm;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }


    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTricode() {
        return tricode;
    }

    public void setTricode(String tricode) {
        this.tricode = tricode;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public String getDivName() {
        return divName;
    }

    public void setDivName(String divName) {
        this.divName = divName;
    }
}
