package com.example.eyezo.hockeyapp;

import java.util.Date;

public class AddPlayer {

    private String name;
    private String surname;
    private String teamName;
    private String coachName;
    private String medAidName;
    private String medAidPlan;
    private String medAidNumber;
    private String allergies;
    private String firstParentNum;
    private String secondParentNum;

    private String objectId ;
    private Date created ;
    private Date updated ;

    public AddPlayer()
    {
        name = null;
        surname = null;
        teamName = null;
        coachName = null;
        medAidName = null;
        medAidNumber = null;
        medAidPlan = null;
        allergies = null;
        firstParentNum = null;
        secondParentNum = null;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getMedAidName() {
        return medAidName;
    }

    public void setMedAidName(String medAidName) {
        this.medAidName = medAidName;
    }

    public String getMedAidPlan() {
        return medAidPlan;
    }

    public void setMedAidPlan(String medAidPlan) {
        this.medAidPlan = medAidPlan;
    }

    public String getMedAidNumber() {
        return medAidNumber;
    }

    public void setMedAidNumber(String medAidNumber) {
        this.medAidNumber = medAidNumber;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getFirstParentNum() {
        return firstParentNum;
    }

    public void setFirstParentNum(String firstParentNum) {
        this.firstParentNum = firstParentNum;
    }

    public String getSecondParentNum() {
        return secondParentNum;
    }

    public void setSecondParentNum(String secondParentNum) {
        this.secondParentNum = secondParentNum;
    }
}
