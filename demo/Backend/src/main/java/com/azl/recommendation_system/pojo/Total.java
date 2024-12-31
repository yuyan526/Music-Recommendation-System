package com.azl.recommendation_system.pojo;

import org.springframework.stereotype.Component;

@Component
public class Total {
    private String totalsongs;
    private String totalusers;
    private String totalsheets;
    private String totalsingers;
    private String totaladmins;

    public Total() {
    }

    public String gettotalsongs() {
        return totalsongs;
    }

    public Total settotalsongs(String totalsongs) {
        this.totalsongs= totalsongs;
        return this;
    }

    public String gettotalusers() {
        return totalusers;
    }

    public Total settotalusers(String totalusers) {
        this.totalusers= totalusers;
        return this;
    }

    public String gettotalsheets() {
        return totalsheets;
    }

    public Total settotalsheets(String totalsheets) {
        this.totalsheets= totalsheets;
        return this;
    }

    public String gettotalsingers() {
        return totalsingers;
    }

    public Total settotalsingers(String totalsingers) {
        this.totalsingers= totalsingers;
        return this;
    }

    public String gettotaladmins() {
        return totaladmins;
    }

    public Total settotaladmins(String totaladmins) {
        this.totaladmins= totaladmins;
        return this;
    }

    @Override
    public String toString() {
        return "Total{" +
                "totalsongs='" + totalsongs + '\'' +
                ", totalusers='" + totalusers + '\'' +
                ", totalsheets='" + totalsheets + '\'' +
                ", totalsingers='" + totalsingers + '\'' +
                ", totaladmins='" + totaladmins + '\'' +
                '}';
    }
}
