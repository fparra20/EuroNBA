package com.example.euronba.model;

import com.example.euronba.controller.RetrievePlayer;
import com.example.euronba.controller.RetrievePlayerCareer;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


public class Player {

    public String firstName;
    public String lastName;
    public String personId;
    public String teamId;
    public String jersey;
    public boolean isActive;
    public String pos;
    public String heightMeters;
    public String weightKilograms;
    public String dateOfBirthUTC;
    public String age;
    public List<PlayerTeams> teams;
    public PlayerDraft draft;
    public String nbaDebutYear;
    public String yearsPro;
    public String collegeName;
    public String lastAffiliation;
    public String country;

    public Player() {

    }

    public String getAge() {

        // Formateamos la fecha de nacimiento a LocalDate
        LocalDate birth = LocalDate.parse(getDateOfBirthUTC());

        // Recogemos la fecha actual
        LocalDate current = LocalDate.now();

        // Guardamos los años, usando la clase Period que calcula el tiempo que ha pasado
        int years = Period.between(birth, current).getYears();

        // Guardamos los días, usando la clase Period que calcula el tiempo que ha pasado
        int days = Period.between(birth, current).getDays();

        // Devolvemos un String con el dato formateado
        return String.valueOf(years + "y, " + days + "d");
    }

    public ArrayList<PlayerStats> getPlayerCareerFromId (String playerId){

        RetrievePlayerCareer rpc = new RetrievePlayerCareer();

        return rpc.getPlayerStatsFromID(playerId);
    }

    public Player getPlayerProfileFromId (String playerId){

        RetrievePlayer rpc = new RetrievePlayer();

        return rpc.getPlayerInfoById(playerId);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getJersey() {
        return jersey;
    }

    public void setJersey(String jersey) {
        this.jersey = jersey;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getHeightMeters() {
        return heightMeters;
    }

    public void setHeightMeters(String heightMeters) {
        this.heightMeters = heightMeters;
    }

    public String getWeightKilograms() {
        return weightKilograms;
    }

    public void setWeightKilograms(String weightKilograms) {
        this.weightKilograms = weightKilograms;
    }

    public String getDateOfBirthUTC() {
        return dateOfBirthUTC;
    }

    public void setDateOfBirthUTC(String dateOfBirthUTC) {
        this.dateOfBirthUTC = dateOfBirthUTC;
    }

    public List<PlayerTeams> getTeams() {
        return teams;
    }

    public void setTeams(List<PlayerTeams> teams) {
        this.teams = teams;
    }

    public PlayerDraft getDraft() {
        return draft;
    }

    public void setDraft(PlayerDraft draft) {
        this.draft = draft;
    }

    public String getNbaDebutYear() {
        return nbaDebutYear;
    }

    public void setNbaDebutYear(String nbaDebutYear) {
        this.nbaDebutYear = nbaDebutYear;
    }

    public String getYearsPro() {
        return yearsPro;
    }

    public void setYearsPro(String yearsPro) {
        this.yearsPro = yearsPro;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getLastAffiliation() {
        return lastAffiliation;
    }

    public void setLastAffiliation(String lastAffiliation) {
        this.lastAffiliation = lastAffiliation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
