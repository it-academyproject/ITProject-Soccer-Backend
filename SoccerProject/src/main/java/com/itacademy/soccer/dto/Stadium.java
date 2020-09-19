package com.itacademy.soccer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="stadium")
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stadiumId;
    @Size(min = 1, max = 50)
    private String name;
    @Size(min = 1, max = 50)
    private String city;
    private int capacity;
    private double annual_income;

    @JsonIgnore
    @OneToMany(mappedBy="stadiumMany" )
    private List<Match> matchList;

    public Stadium(Long stadiumId, String name, String city, int capacity, double annual_income, List<Match> matchList) {
        this.stadiumId = stadiumId;
        this.name = name;
        this.city = city;
        this.capacity = capacity;
        this.annual_income = annual_income;
       // this.matchList = matchList;
        this.matchList = matchList;
    }

    public Stadium() {
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
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
