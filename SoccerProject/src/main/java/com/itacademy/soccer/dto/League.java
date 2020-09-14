package com.itacademy.soccer.dto;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table (name="league")
public class League {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name= "name")
	private String name;
	
	@Column(name = "begindate")
	private Date begindate;
	
	@Column (name= "endingdate")
	private Date endingdate;
	
	@Column (name= "numberrounds")
	private int numberrounds;
	
	@Column (name= "participants")
	private int participants;
	
	@Column (name= "division")
	private Long division;
	
	public League(Long id, String name, Date beginDate, Date endingDate, int numberRounds, int participants,
			Long division) {

		this.id = id;
		this.name = name;
		this.begindate = beginDate;
		this.endingdate = endingDate;
		this.numberrounds = numberRounds;
		this.participants = participants;
		this.division = division;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return begindate;
	}

	public void setBeginDate(Date beginDate) {
		this.begindate = beginDate;
	}

	public Date getEndingDate() {
		return endingdate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingdate = endingDate;
	}

	public int getNumberRounds() {
		return numberrounds;
	}

	public void setNumberRounds(int numberRounds) {
		this.numberrounds = numberRounds;
	}

	public int getParticipants() {
		return participants;
	}

	public void setParticipants(int participants) {
		this.participants = participants;
	}

	public Long getDivision() {
		return division;
	}

	public void setDivision(Long division) {
		this.division = division;
	}
	
	
	@Override
	public String toString() {
		return "League [id=" + id + ", name=" + name + ", beginDate=" + begindate + ", endingDate=" + endingdate
				+ ", numberRounds=" + numberrounds + ", participants=" + participants + ", division=" + division + "]";
	}

}
