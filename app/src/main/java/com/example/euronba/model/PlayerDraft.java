package com.example.euronba.model;

public class PlayerDraft extends Player {

    public String pickNum;
    public String roundNum;
    public String seasonYear;

    public String getPickNum() {
        return pickNum;
    }

    public void setPickNum(String pickNum) {
        this.pickNum = pickNum;
    }

    public String getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(String roundNum) {
        this.roundNum = roundNum;
    }

    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

}
