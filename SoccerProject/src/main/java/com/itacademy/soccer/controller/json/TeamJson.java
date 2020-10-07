package com.itacademy.soccer.controller.json;

import java.util.Date;


public class TeamJson {
	
	
	private Long id;	
	private String name;
	private Date foundation_date;		
	private String badge;	
	private Float budget;	
	private int wins;	
	private int losses;	
	private int draws;
	
	
	public TeamJson(Long id, String name, Date foundation_date, String badge, Float budget, int wins, int losses,
			int draws) {
		super();
		this.id = id;
		this.name = name;
		this.foundation_date = foundation_date;
		this.badge = badge;
		this.budget = budget;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
	}
	
	
	public TeamJson() {
	
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getFoundation_date() {
		return foundation_date;
	}
	public void setFoundation_date(Date foundation_date) {
		this.foundation_date = foundation_date;
	}
	public String getBadge() {
		return badge;
	}
	public void setBadge(String badge) {
		this.badge = badge;
	}
	public Float getBudget() {
		return budget;
	}
	public void setBudget(Float budget) {
		this.budget = budget;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}
	public int getDraws() {
		return draws;
	}
	public void setDraws(int draws) {
		this.draws = draws;
	}
	
	


}
