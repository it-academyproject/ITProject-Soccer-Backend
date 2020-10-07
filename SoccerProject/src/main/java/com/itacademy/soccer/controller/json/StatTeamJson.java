package com.itacademy.soccer.controller.json;

public class StatTeamJson {
	
	private int total_bids;	
	private TeamJson team;
	
	public StatTeamJson() {		
	}
	
	public StatTeamJson(TeamJson team,int total_bids) {	
		this.total_bids = total_bids;
		this.team = team;
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
