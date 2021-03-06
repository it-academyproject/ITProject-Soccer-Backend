package com.itacademy.soccer.controller.json;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MatchJson {
		
	private Long local_team;
	
	private Long visitor_team;
	
	//Required to receive date and time in correct timezone
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Madrid")
	private Date date;

	public Long getLocal_team() {
		return local_team;
	}

	public void setLocal_team(Long local_team) {
		this.local_team = local_team;
	}

	public Long getVisitor_team() {
		return visitor_team;
	}

	public void setVisitor_team(Long visitor_team) {
		this.visitor_team = visitor_team;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	
	
}
