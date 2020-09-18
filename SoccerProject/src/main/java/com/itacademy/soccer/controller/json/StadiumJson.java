package com.itacademy.soccer.controller.json;

import javax.validation.constraints.Pattern;

public class StadiumJson {

    private String id;
    private String id2;
    private String name;
    private String city;
    private String capacity;
    private String annual_income;

    public StadiumJson() {
    }
    public StadiumJson(String id) {
        this.id = id;
    }

    public StadiumJson(String id, String id2) {
        this.id = id;
        this.id2 = id2;
    }

    public StadiumJson(String name, String city, String capacity, String annual_income) {

        this.name = name;
        this.city = city;
        this.capacity = capacity;
        this.annual_income = annual_income;
    }


    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getAnnual_income() {
        return annual_income;
    }

    public void setAnnual_income(String annual_income) {
        this.annual_income = annual_income;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
