package com.itacademy.soccer.controller.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Sale;

public class SaleJson {

	private Long id;

	private Date limitDate;

	private Float initialPrice;

	private Long playerId;

	public SaleJson() {

	}

	public SaleJson(Long id, Date limitDate, Float initialPrice, Long playerId) {
		this.id = id;
		this.limitDate = limitDate;
		this.initialPrice = initialPrice;
		this.playerId = playerId;
	}

	public Sale toSale() {

		Sale sale = new Sale();

		sale.setId(this.id);
		sale.setLimitDate(this.limitDate);
		sale.setInitialPrice(this.initialPrice);

		Player player = new Player();
		player.setId(this.playerId);
		sale.setPlayer(player);

		return sale;
	}

	public static SaleJson parseSaleToJson(Sale sale) {

		return new SaleJson() {
			{
				setId(sale.getId());
				setInitialPrice(sale.getInitialPrice());
				setLimitDate(sale.getLimitDate());
				setPlayerId(sale.getPlayer().getId());
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

	public Float getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(Float initialPrice) {
		this.initialPrice = initialPrice;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

}
