package com.itacademy.soccer.controller.json;

public class StatTeamJson {
	
	private int total_bids;	
	private TeamJson team;
	
	
	
	public StatTeamJson(TeamJson team,int total_bids) {
	
		this.total_bids = total_bids;
		this.team = team;
	}



	@Override
	public String toString() {
		return "StatTeamJson [total_bids=" + total_bids + ", team=" + team + "]";
	}



	public StatTeamJson() {
		
	}



	public int getTotal_bids() {
		return total_bids;
	}



	public void setTotal_bids(int total_bids) {
		this.total_bids = total_bids;
	}



	public TeamJson getTeam() {
		return team;
	}



	public void setTeam(TeamJson team) {
		this.team = team;
	}
	
	
	
	

}
