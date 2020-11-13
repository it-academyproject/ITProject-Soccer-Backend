package com.itacademy.soccer.controller.json;

import java.util.Comparator;
import java.util.Date;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Sale;

public class SalesFilterJson {

	private Long id;

	private Date limitDate;

	private Float initialPrice;

	private Float lastBidPrice;

	private String teamName;

	private Player player;

	public SalesFilterJson() {
	}
	
	public static SalesFilterJson parseSaleToJson(Sale sale) {
		return new SalesFilterJson() {
			{
				setId(sale.getId());
				setLimitDate(sale.getLimitDate());
				setInitialPrice(sale.getInitialPrice());
				setLastBidPrice((sale.getBids().isEmpty()) 
						? sale.getInitialPrice()
						: sale.getBids().stream()
								.sorted(Comparator.comparing(Bid::getOperationDate).reversed())
								.findFirst().get().getBidPrice());
				setTeamName(sale.getPlayer().getTeam().getName());
				setPlayer(sale.getPlayer());
			}
		};
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
