package com.itacademy.soccer.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
@Table (name="tournament")
public class Tournament {
	
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

	
	public Tournament() {	}
	
	public Tournament(Long id, String name, Date beginDate, Date endingDate, int numberRounds) {

		this.id = id;
		this.name = name;
		this.beginDate = beginDate;
		this.endingDate = endingDate;
		this.numberRounds = numberRounds;
	}

	
	
	//The tournament should have 2^numberRounds participants to be played.
	@JsonProperty ("needed_participants")
	public int getNeededParticipants() {
		
		return (int) Math.pow(2, numberRounds);
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

		
	@Override
	public String toString() {
		return "League [id=" + id + ", name=" + name + ", beginDate=" + beginDate + ", endingDate=" + endingDate
				+ ", numberRounds=" +numberRounds+ "]";
	}

}