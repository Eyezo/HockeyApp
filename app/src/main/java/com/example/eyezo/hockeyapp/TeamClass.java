package com.example.eyezo.hockeyapp;

import java.util.Date;

public class TeamClass {

    private String teamOrOpp;
    private String ageGroup;
    private String coachName;
    private String teamName;

    private String objectId ;
    private Date created ;
    private Date updated ;

    public TeamClass()
    {
        teamOrOpp = null;
        ageGroup = null;
        coachName = null;
        teamName = null;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getTeamOrOpp() {
        return teamOrOpp;
    }

    public void setTeamOrOpp(String teamOrOpp) {
        this.teamOrOpp = teamOrOpp;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
