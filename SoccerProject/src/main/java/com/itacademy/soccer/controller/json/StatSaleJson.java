package com.itacademy.soccer.controller.json;

public class StatSaleJson {

	private Integer totalBids;
	private SaleJson sale;

	public StatSaleJson() {
	}

	public StatSaleJson(SaleJson sale, Integer totalBids) {
		this.sale = sale;
		this.totalBids = totalBids;
	}

	public Integer getTotalBids() {
		return totalBids;
	}

	public void setTotalBids(Integer totalBids) {
		this.totalBids = totalBids;
	}

	public SaleJson getSale() {
		return sale;
	}

	public void setSale(SaleJson sale) {
		this.sale = sale;
	}

}
