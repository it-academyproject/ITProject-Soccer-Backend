package com.itacademy.soccer.controller.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;

public class BidJson {

	private Long id;
		
	private float bid_price;
	
	private Date operation_date;
		
	private Long team_id;
	
	private Long sale_id;

	
	public BidJson(){
		
	}
	
	public BidJson(Long id, float bid_price, Date operation_date, Long team_id, Long sales_id) {
		this.id = id;
		this.bid_price = bid_price;
		this.operation_date = operation_date;
		this.team_id = team_id;
		this.sale_id = sales_id;
	}

	public Bid setJsonToObject() {
		
		Bid bid = new Bid();
		
		bid.setId(this.id);
		bid.setOperationDate(this.operation_date);
		bid.setBid_price(this.bid_price);
		
		Team team = new Team();
		team.setId(this.team_id);
		bid.setTeam(team);
		
		return bid;
	}

	public static BidJson parseObjectToJson(Bid bid) {
		
		BidJson json = new BidJson();
		
		json.setId(bid.getId());
		json.setBid_price(bid.getBid_price());
		json.setOperation_date(bid.getOperationDate());
		json.setTeam_id(bid.getTeam().getId());
		json.setSale_id(bid.getSale().getId());
		
		return json;
	}
	
	public static List<BidJson> parseListToJson(List<Bid> bidsList) {
		
		List<BidJson> jsonList = new ArrayList<>();
		
		for (Bid b : bidsList) {
			BidJson json = parseObjectToJson(b);
			jsonList.add(json);
		}
		
		return jsonList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getBid_price() {
		return bid_price;
	}

	public void setBid_price(float bid_price) {
		this.bid_price = bid_price;
	}

	public Date getOperation_date() {
		return operation_date;
	}

	public void setOperation_date(Date operation_date) {
		this.operation_date = operation_date;
	}

	public Long getTeam_id() {
		return team_id;
	}

	public void setTeam_id(Long team_id) {
		this.team_id = team_id;
	}

	public Long getSale_id() {
		return sale_id;
	}

	public void setSale_id(Long sale_id) {
		this.sale_id = sale_id;
	}

	
	
	
}
