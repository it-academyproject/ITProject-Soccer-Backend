package com.itacademy.soccer.controller.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Team;

public class BidJson {

	private Long id;

	private Float bidPrice;

	private Date operationDate;

	private Long teamId;

	private Long saleId;

	public BidJson() {

	}

	public BidJson(Long id, float bidPrice, Date operationDate, Long teamId, Long salesId) {
		this.id = id;
		this.bidPrice = bidPrice;
		this.operationDate = operationDate;
		this.teamId = teamId;
		this.saleId = salesId;
	}

	public Bid toBid() {

		Bid bid = new Bid();

		bid.setId(this.id);
		bid.setOperationDate(this.operationDate);
		bid.setBidPrice(this.bidPrice);

		Team team = new Team();
		team.setId(this.teamId);
		bid.setTeam(team);

		return bid;
	}

	public static BidJson parseBidToJson(Bid bid) {

		return new BidJson() {
			{
				setId(bid.getId());
				setBidPrice(bid.getBidPrice());
				setOperationDate(bid.getOperationDate());
				setTeamId(bid.getTeam().getId());
				setSaleId(bid.getSale().getId());
			}
		};
	}

	public static List<BidJson> parseListBidToJson(List<Bid> bidsList) {

		return new ArrayList<BidJson>() {
			private static final long serialVersionUID = 1306107459405064139L;

			{
				bidsList.stream().forEach(bid -> this.add(parseBidToJson(bid)));
			}
		};
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Float bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

}
