package com.itacademy.soccer.controller.json;

import java.util.Date;

import com.itacademy.soccer.dto.Player;

public class SalesFilterJson {

	private Long id;

	private Date limitDate;

	private Float initialPrice;

	private Float lastBidPrice;

	private String teamName;

	private Player player;

	public SalesFilterJson() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	public Float getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(Float initialPrice) {
		this.initialPrice = initialPrice;
	}

	public Float getLastBidPrice() {
		return lastBidPrice;
	}

	public void setLastBidPrice(Float lastBidPrice) {
		this.lastBidPrice = lastBidPrice;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
