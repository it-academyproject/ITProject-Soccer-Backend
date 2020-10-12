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
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="bid")
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	private Long team_id;

	@Column(name = "bid_price")
	private float bid_price;

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

	
	public Bid(Long id, Long team_id, float bid_price, Date operationDate, Sale sale, Team team) {
		this.id = id;
		this.team_id = team_id;
		this.bid_price = bid_price;
		this.operationDate = operationDate;
		this.sale = sale;
		this.team = team;
	}

	public Bid(Long id, float bidPrice, Date operationDate) {
		this.id = id;
		this.bid_price = bid_price;
		this.operationDate = operationDate;
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


	public Long getTeam_id() {
		return team_id;
	}

	public void setTeam_id(Long team_id) {
		this.team_id = team_id;
	}

}




