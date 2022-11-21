package com.example.covid_19tracker;

public class CaseSample {
    // private instance variables
    private String date;
    private String country;
    private String cumulativeTotal;
    private String dailyNew;
    private String active;
    private String cumulativeTotalDeaths;
    private String dailyNewDeaths;

    // constructor
    public CaseSample(String date, String country, String cumulativeTotal, String dailyNew, String active, String cumulativeTotalDeaths, String dailyNewDeaths) {
        this.date = date;
        this.country = country;
        this.cumulativeTotal = cumulativeTotal;
        this.dailyNew = dailyNew;
        this.active = active;
        this.cumulativeTotalDeaths = cumulativeTotalDeaths;
        this.dailyNewDeaths = dailyNewDeaths;
    }

    // getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCumulativeTotal() {
        return cumulativeTotal;
    }

    public void setCumulativeTotal(String cumulativeTotal) {
        this.cumulativeTotal = cumulativeTotal;
    }

    public String getDailyNew() {
        return dailyNew;
    }

    public void setDailyNew(String dailyNew) {
        this.dailyNew = dailyNew;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCumulativeTotalDeaths() {
        return cumulativeTotalDeaths;
    }

    public void setCumulativeTotalDeaths(String cumulativeTotalDeaths) {
        this.cumulativeTotalDeaths = cumulativeTotalDeaths;
    }

    public String getDailyNewDeaths() {
        return dailyNewDeaths;
    }

    public void setDailyNewDeaths(String dailyNewDeaths) {
        this.dailyNewDeaths = dailyNewDeaths;
    }
}
