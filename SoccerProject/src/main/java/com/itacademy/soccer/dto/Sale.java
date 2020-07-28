package com.itacademy.soccer.dto;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="sale")
public class Sale {
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	private Long id;
	
	@Column(name="limit_date")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date limitDate;
	
	@Column(name="initial_price")
	private float initialPrice;

//	@ManyToOne
//	@JoinColumn(name="player_id")
//	private Player player;
	
//	@OneToMany
//	@JoinColumn(name="sale_id")
//	private List<Bid> bids;
	public Sale() {
		
	}
	
	public Sale(Long id, Date limitDate, float initialPrice) {
		this.id = id;
		this.limitDate = limitDate;
		this.initialPrice = initialPrice;
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

	public float getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(float initialPrice) {
		this.initialPrice = initialPrice;
	}
	
	
	
}
