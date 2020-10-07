package com.itacademy.soccer.controller.json;

public class StatTeamJson {
	
	private int total_bids;	
	private float max_price;
	private TeamJson team;
	
	public StatTeamJson() {		
	}
	
	public StatTeamJson(TeamJson team,int total_bids, float max_price) {	
		this.total_bids = total_bids;
		this.team = team;
		this.max_price=max_price;
	}

	public int getTotal_bids() {
		return total_bids;
	}

	public void setTotal_bids(int total_bids) {
		this.total_bids = total_bids;
	}

	public float getMax_price() {
		return max_price;
	}

	public void setMax_price(float max_price) {
		this.max_price = max_price;
	}
	
	public TeamJson getTeam() {
		return team;
	}

	public void setTeam(TeamJson team) {
		this.team = team;
	}	

}
