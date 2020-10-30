package com.itacademy.soccer.controller.json;

import java.util.Date;

import com.itacademy.soccer.dto.Player;

public class SalesFilterJson {
	
	private Long id;
	
	private Date limit_date;
	
	private float initial_price;
	
	private float last_bid_price;
	
	private String team_name;
	
	private Player player;
	
	
	public SalesFilterJson(){}

//	public SalesFilterJson(Long id, Date limit_date, float initial_price, Player player) {
//	this.id = id;
//	this.limit_date = limit_date;
//	this.initial_price = initial_price;
//	this.player = player;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLimit_date() {
		return limit_date;
	}

	public void setLimit_date(Date limit_date) {
		this.limit_date = limit_date;
	}

	public float getInitial_price() {
		return initial_price;
	}

	public void setInitial_price(float initial_price) {
		this.initial_price = initial_price;
	}

	public float getlast_bid_price() {
		return last_bid_price;
	}

	public void setlast_bid_price(float last_bid_price) {
		this.last_bid_price = last_bid_price;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	
}
