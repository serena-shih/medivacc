package com.example.covid_19tracker;

public class VaccinationSample {
    // private instance variables
    private String country;
    private String isoCode;
    private String date;
    private String totalVac;
    private String peopleVac;
    private String peopleVacFull;
    private String dailyVacRaw;
    private String dailyVac;
    private String totalVacPerHund;
    private String peopleVacPerHund;
    private String fullVacPerHund;
    private String dailyVacPerMil;
    private String vaccine;
    private String srcName;
    private String srcWebsite;

    // constructor
    public VaccinationSample(String country, String isoCode, String date, String totalVac, String peopleVac, String peopleVacFull, String dailyVacRaw, String dailyVac, String totalVacPerHund, String peopleVacPerHund, String fullVacPerHund, String dailyVacPerMil, String vaccine, String srcName, String srcWebsite) {
        this.country = country;
        this.isoCode = isoCode;
        this.date = date;
        this.totalVac = totalVac;
        this.peopleVac = peopleVac;
        this.peopleVacFull = peopleVacFull;
        this.dailyVacRaw = dailyVacRaw;
        this.dailyVac = dailyVac;
        this.totalVacPerHund = totalVacPerHund;
        this.peopleVacPerHund = peopleVacPerHund;
        this.fullVacPerHund = fullVacPerHund;
        this.dailyVacPerMil = dailyVacPerMil;
        this.vaccine = vaccine;
        this.srcName = srcName;
        this.srcWebsite = srcWebsite;
    }

    // getters and setters

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalVac() {
        return totalVac;
    }

    public void setTotalVac(String totalVac) {
        this.totalVac = totalVac;
    }

    public String getPeopleVac() {
        return peopleVac;
    }

    public void setPeopleVac(String peopleVac) {
        this.peopleVac = peopleVac;
    }

    public String getPeopleVacFull() {
        return peopleVacFull;
    }

    public void setPeopleVacFull(String peopleVacFull) {
        this.peopleVacFull = peopleVacFull;
    }

    public String getDailyVacRaw() {
        return dailyVacRaw;
    }

    public void setDailyVacRaw(String dailyVacRaw) {
        this.dailyVacRaw = dailyVacRaw;
    }

    public String getDailyVac() {
        return dailyVac;
    }

    public void setDailyVac(String dailyVac) {
        this.dailyVac = dailyVac;
    }

    public String getTotalVacPerHund() {
        return totalVacPerHund;
    }

    public void setTotalVacPerHund(String totalVacPerHund) {
        this.totalVacPerHund = totalVacPerHund;
    }

    public String getPeopleVacPerHund() {
        return peopleVacPerHund;
    }

    public void setPeopleVacPerHund(String peopleVacPerHund) {
        this.peopleVacPerHund = peopleVacPerHund;
    }

    public String getFullVacPerHund() {
        return fullVacPerHund;
    }

    public void setFullVacPerHund(String fullVacPerHund) {
        this.fullVacPerHund = fullVacPerHund;
    }

    public String getDailyVacPerMil() {
        return dailyVacPerMil;
    }

    public void setDailyVacPerMil(String dailyVacPerMil) {
        this.dailyVacPerMil = dailyVacPerMil;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getSrcWebsite() {
        return srcWebsite;
    }

    public void setSrcWebsite(String srcWebsite) {
        this.srcWebsite = srcWebsite;
    }
}
