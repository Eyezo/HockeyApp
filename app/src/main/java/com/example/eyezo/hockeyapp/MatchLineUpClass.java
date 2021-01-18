package com.example.eyezo.hockeyapp;

import java.util.Date;

public class MatchLineUpClass {
    private String team;
    private String teamAgainst;
    private String coach;
    private int numberOfData;
    private String playerName;
    private String playerPosition;
    private String playerGoals;
    private String playerRating;

    private String objectId;
    private Date updated;
    private Date created;

    public MatchLineUpClass()
    {
        team = null;
        teamAgainst = null;
        coach = null;
        numberOfData = 0;
        playerName = null;
        playerPosition = null;
        playerGoals = "0";
        playerRating = null;
    }

    public int getNumberOfData() {
        return numberOfData;
    }

    public void setNumberOfData(int numberOfData) {
        this.numberOfData = numberOfData;
    }
    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeamAgainst() {
        return teamAgainst;
    }

    public void setTeamAgainst(String teamAgainst) {
        this.teamAgainst = teamAgainst;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public String getPlayerGoals() {
        return playerGoals;
    }

    public void setPlayerGoals(String playerGoals) {
        this.playerGoals = playerGoals;
    }

    public String getPlayerRating() {
        return playerRating;
    }

    public void setPlayerRating(String playerRating) {
        this.playerRating = playerRating;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}