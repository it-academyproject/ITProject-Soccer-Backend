package com.itacademy.soccer.controller.json;


public class StatSaleJson {	

	private int total_bids;	
	private SaleJson sale;	
	
	public StatSaleJson() {			
	}
	
	public StatSaleJson(SaleJson sale,int total_bids) {
		this.sale = sale;
		this.total_bids = total_bids;	
	}
	

	public int getTotal_bids() {
		return total_bids;
	}

	public void setTotal_bids(int total_bids) {
		this.total_bids = total_bids;
	}

	public SaleJson getSale() {
		return sale;
	}

	public void setSale(SaleJson sale) {
		this.sale = sale;
	}
	
}
