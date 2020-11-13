package com.itacademy.soccer.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="bid")
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "team_id")
	private Long teamId;

	@Column(name = "bid_price")
	private float bidPrice;

	@Column(name = "operation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operationDate;

	@ManyToOne
	@JoinColumn(name = "sale_id")
	@JsonIgnore
	private Sale sale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id", insertable = false, updatable = false)
	@JsonIgnore
	private Team team;

	public Bid() {

	}

	
	public Bid(Long id, Long teamId, float bidPrice, Date operationDate, Sale sale, Team team) {
		this.id = id;
		this.teamId = teamId;
		this.bidPrice = bidPrice;
		this.operationDate = operationDate;
		this.sale = sale;
		this.team = team;
	}

	public Bid(Long id, float bidPrice, Date operationDate) {
		this.id = id;
		this.bidPrice = bidPrice;
		this.operationDate = operationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public float getBidPrice() {
		return bidPrice;
	}


	public void setBidPrice(float bidPrice) {
		this.bidPrice = bidPrice;
	}


	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	//@JsonIgnore
	//@ManyToOne(fetch = FetchType.LAZY) //TODO FALTA COMPROBAR
	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}


	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

}




