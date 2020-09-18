package com.itacademy.soccer.dto;

import com.itacademy.soccer.controller.json.StadiumJson;

import javax.persistence.*;

@Entity
@Table(name="stadium")
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stadiumId;
    private String name;
    private String city;
    private int capacity;
    private double annual_income;

//    @OneToMany(mappedBy="stadium" , fetch = FetchType.LAZY,  cascade=CascadeType.ALL)
//    private List<Match> matchList;

    public Stadium(Long stadiumId, String name, String city, int capacity, double annual_income) {
        this.stadiumId = stadiumId;
        this.name = name;
        this.city = city;
        this.capacity = capacity;
        this.annual_income = annual_income;
       // this.matchList = matchList;
    }

    public Stadium() {

    }

    public Long getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(Long stadiumId) {
        this.stadiumId = stadiumId;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getAnnual_income() {
        return annual_income;
    }

    public void setAnnual_income(double annual_income) {
        this.annual_income = annual_income;
    }
}
