package com.example.euronba.model;

/**
 *
 * @author Usuario
 */
public class Scoreboard {

    public String gameId;
    public int seasonStageId;
    public String seasonYear;
    public String arenaName;
    public String arenaCity;
    public int statusNum;
    public String startTimeUTC;
    public String clock;
    public String gameDuration;
    public int currentPeriod;
    public TeamScore visitorTeam;
    public TeamScore localTeam;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getSeasonStageId() {
        return seasonStageId;
    }

    public void setSeasonStageId(int seasonStageId) {
        this.seasonStageId = seasonStageId;
    }

    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    public String getArenaCity() {
        return arenaCity;
    }

    public void setArenaCity(String arenaCity) {
        this.arenaCity = arenaCity;
    }

    public int getStatusNum() {
        return statusNum;
    }

    public void setStatusNum(int statusNum) {
        this.statusNum = statusNum;
    }

    public String getStartTimeUTC() {
        return startTimeUTC;
    }

    public void setStartTimeUTC(String startTimeUTC) {
        this.startTimeUTC = startTimeUTC;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(String gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(int currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public TeamScore getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(TeamScore visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public TeamScore getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(TeamScore localTeam) {
        this.localTeam = localTeam;
    }

}
