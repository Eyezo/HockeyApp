package com.example.eyezo.hockeyapp;

import java.util.Date;

public class MatchStatsClass {

    private String teamFor;
    private String teamAgainst;
    private String turnOver1stHalfTeam;
    private String turnOver2ndHalfTeam;
    private String turnOver1stHalfAgainst;
    private String turnOver2ndHalfAgainst;
    private String goal1stHalfTeam;
    private String goal2ndHalfTeam;
    private String goal1stHalfAgainst;
    private String goal2ndHalfAgainst;
    private String circlePen1st;
    private String circlePen2nd;
    private String penaltyCorners1st;
    private String penaltyConers2nd;
    private String shots1st;
    private String shots2nd;

    private String objectId;
    private Date updated;
    private Date created;

    public MatchStatsClass()
    {
        teamFor = null;
        teamAgainst = null;
        turnOver1stHalfTeam = null;
        turnOver2ndHalfTeam = null;
        turnOver1stHalfAgainst = null;
        turnOver2ndHalfAgainst = null;
        goal1stHalfTeam = null;
        goal2ndHalfTeam = null;
        goal1stHalfAgainst = null;
        goal2ndHalfAgainst = null;
        circlePen1st = null;
        circlePen2nd = null;
        penaltyCorners1st = null;
        penaltyConers2nd = null;
        shots1st = null;
        shots2nd = null;

    }

    public String getTeamFor() {
        return teamFor;
    }

    public void setTeamFor(String teamFor) {
        this.teamFor = teamFor;
    }

    public String getTeamAgainst() {
        return teamAgainst;
    }

    public void setTeamAgainst(String teamAgainst) {
        this.teamAgainst = teamAgainst;
    }

    public String getGoal1stHalfTeam() {
        return goal1stHalfTeam;
    }

    public void setGoal1stHalfTeam(String goal1stHalfTeam) {
        this.goal1stHalfTeam = goal1stHalfTeam;
    }

    public String getGoal2ndHalfTeam() {
        return goal2ndHalfTeam;
    }

    public void setGoal2ndHalfTeam(String goal2ndHalfTeam) {
        this.goal2ndHalfTeam = goal2ndHalfTeam;
    }

    public String getGoal1stHalfAgainst() {
        return goal1stHalfAgainst;
    }

    public void setGoal1stHalfAgainst(String goal1stHalfAgainst) {
        this.goal1stHalfAgainst = goal1stHalfAgainst;
    }

    public String getGoal2ndHalfAgainst() {
        return goal2ndHalfAgainst;
    }

    public void setGoal2ndHalfAgainst(String goal2ndHalfAgainst) {
        this.goal2ndHalfAgainst = goal2ndHalfAgainst;
    }

    public String getTurnOver1stHalfTeam() {
        return turnOver1stHalfTeam;
    }

    public void setTurnOver1stHalfTeam(String turnOver1stHalfTeam) {
        this.turnOver1stHalfTeam = turnOver1stHalfTeam;
    }

    public String getTurnOver2ndHalfTeam() {
        return turnOver2ndHalfTeam;
    }

    public void setTurnOver2ndHalfTeam(String turnOver2ndHalfTeam) {
        this.turnOver2ndHalfTeam = turnOver2ndHalfTeam;
    }

    public String getTurnOver1stHalfAgainst() {
        return turnOver1stHalfAgainst;
    }

    public void setTurnOver1stHalfAgainst(String turnOver1stHalfAgainst) {
        this.turnOver1stHalfAgainst = turnOver1stHalfAgainst;
    }

    public String getTurnOver2ndHalfAgainst() {
        return turnOver2ndHalfAgainst;
    }

    public void setTurnOver2ndHalfAgainst(String turnOver2ndHalfAgainst) {
        this.turnOver2ndHalfAgainst = turnOver2ndHalfAgainst;
    }

    public String getCirclePen1st() {
        return circlePen1st;
    }

    public void setCirclePen1st(String circlePen1st) {
        this.circlePen1st = circlePen1st;
    }

    public String getCirclePen2nd() {
        return circlePen2nd;
    }

    public void setCirclePen2nd(String circlePen2nd) {
        this.circlePen2nd = circlePen2nd;
    }

    public String getPenaltyCorners1st() {
        return penaltyCorners1st;
    }

    public void setPenaltyCorners1st(String penaltyCorners1st) {
        this.penaltyCorners1st = penaltyCorners1st;
    }

    public String getPenaltyConers2nd() {
        return penaltyConers2nd;
    }

    public void setPenaltyConers2nd(String penaltyConers2nd) {
        this.penaltyConers2nd = penaltyConers2nd;
    }

    public String getShots1st() {
        return shots1st;
    }

    public void setShots1st(String shots1st) {
        this.shots1st = shots1st;
    }

    public String getShots2nd() {
        return shots2nd;
    }

    public void setShots2nd(String shots2nd) {
        this.shots2nd = shots2nd;
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
