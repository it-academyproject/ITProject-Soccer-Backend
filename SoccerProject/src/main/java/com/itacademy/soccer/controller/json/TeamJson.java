package com.itacademy.soccer.controller.json;

import java.util.Date;

import com.itacademy.soccer.dto.Team;


public class TeamJson {
	
	
	private Long id;	
	private String name;
	private Date foundation_date;		
	private String badge;	
	private Float budget;	
	private Integer wins;	
	private Integer losses;	
	private Integer draws;
	
	
	public TeamJson(Long id, String name, Date foundation_date, String badge, Float budget, Integer wins, Integer losses,
			Integer draws) {
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
	public Integer getWins() {
		return wins;
	}
	public void setWins(Integer wins) {
		this.wins = wins;
	}
	public Integer getLosses() {
		return losses;
	}
	public void setLosses(Integer losses) {
		this.losses = losses;
	}
	public Integer getDraws() {
		return draws;
	}
	public void setDraws(Integer draws) {
		this.draws = draws;
	}

	//B-44 -> B-66
	public Team toTeam() {
		
		Team team = new Team();
		
		team.setId(this.id);
		team.setName(this.name);
		team.setFoundation_date(this.foundation_date);
		team.setBadge(this.badge);
		team.setBudget(this.budget);
		team.setWins(this.wins);
		team.setLosses(this.losses);
		team.setDraws(this.draws);
		
		return team;
	}
	
	public static Team parseJsonToTeam(TeamJson teamJson) {
		return new Team() {
			{
				setId(teamJson.getId());
				setName(teamJson.getName());
				setFoundation_date(teamJson.getFoundation_date());
				setBadge(teamJson.getBadge());
				setBudget(teamJson.getBudget());
				setWins(teamJson.getWins());
				setLosses(teamJson.getLosses());
				setDraws(teamJson.getDraws());
			}
		};
	}
	
	public static TeamJson parseTeamToJson(Team team) {
		return new TeamJson() {
			{
				setId(team.getId());
				setName(team.getName());
				setFoundation_date(team.getFoundation_date());
				setBadge(team.getBadge());
				setBudget(team.getBudget());
				setWins(team.getWins());
				setLosses(team.getLosses());
				setDraws(team.getDraws());
			}
		};
	}


}
