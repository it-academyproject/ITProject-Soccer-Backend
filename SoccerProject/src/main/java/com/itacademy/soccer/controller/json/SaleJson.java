package com.itacademy.soccer.controller.json;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.util.Verify;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleJson {

	private Long id;

	private Date limitDate;

	private Date firstLimitDate;

	private Float initialPrice;
	
	private Float lastBidPrice;
	
	private String teamName;

	private Player player;
	
	private List<BidJson> bids;

	public SaleJson() {

	}

	public Sale toSale() {

		Sale sale = new Sale();

		sale.setId(this.id);
		sale.setLimitDate(this.limitDate);
		if (this.firstLimitDate != null) {
			sale.setFirstLimitDate(this.firstLimitDate);
		}
		sale.setInitialPrice(this.initialPrice);
		sale.setPlayer(this.player);

		return sale;
	}

	public static SaleJson parseSaleToJson(Sale sale) {

		return new SaleJson() {
			{
				setId(sale.getId());
				setLimitDate(sale.getLimitDate());
				if (sale.getFirstLimitDate() != null) {
					setFirstLimitDate(sale.getFirstLimitDate());
				}
				setInitialPrice(sale.getInitialPrice());
				setLastBidPrice(Verify.isNullEmpty(sale.getBids()) 
						? 0 : sale.getBids().stream()
								.sorted(Comparator.comparing(Bid::getOperationDate).reversed())
								.findFirst().get().getBidPrice());
				setTeamName(sale.getPlayer().getTeam().getName());
				setPlayer(sale.getPlayer());
				setBids(
						Verify.isNullEmpty(sale.getBids())
						? new ArrayList<BidJson>()
						: BidJson.parseListBidToJson((sale.getBids())));
			}
		};
	}

	public static List<SaleJson> parseListSaleToJson(List<Sale> salesList) {

		return new ArrayList<SaleJson>() {
			private static final long serialVersionUID = 1135630209871998513L;

			{
				salesList.stream().forEach(sale -> this.add(parseSaleToJson(sale)));
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

	public Date getFirstLimitDate() {
		return firstLimitDate;
	}

	public void setFirstLimitDate(Date firstLimitDate) {
		this.firstLimitDate = firstLimitDate;
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

	public List<BidJson> getBids() {
		return bids;
	}

	public void setBids(List<BidJson> bids) {
		this.bids = bids;
	}

}
