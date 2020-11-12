package com.itacademy.soccer.dto;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
@Table (name="league")
public class League {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name= "name")
	private String name;
	
	@Column(name = "begin_date")
	@JsonProperty("begin_date")
	private Date beginDate;
	
	@Column (name= "ending_date")
	@JsonProperty("ending_date")
	private Date endingDate;
	
	@Column (name= "number_rounds")
	@JsonProperty ("number_rounds")
	private int numberRounds;
	
	@Column (name= "max_participants")
	@JsonProperty ("max_participants")
	private int maxParticipants;
	
	@Column (name= "division")
	private Long division;
	
	public League() {	}
	
	public League(Long id, String name, Date beginDate, Date endingDate, int numberRounds, int maxParticipants,
			Long division) {

		this.id = id;
		this.name = name;
		this.beginDate = beginDate;
		this.endingDate = endingDate;
		this.numberRounds = numberRounds;
		this.maxParticipants = maxParticipants;
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
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	public int getNumberRounds() {
		return numberRounds;
	}

	public void setNumberRounds(int numberRounds) {
		this.numberRounds = numberRounds;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(int participants) {
		this.maxParticipants = participants;
	}

	public Long getDivision() {
		return division;
	}

	public void setDivision(Long division) {
		this.division = division;
	}
	
	
	@Override
	public String toString() {
		return "League [id=" + id + ", name=" + name + ", beginDate=" + beginDate + ", endingDate=" + endingDate
				+ ", numberRounds=" + numberRounds + ", max_participants=" + maxParticipants + ", division=" + division + "]";
	}

}
