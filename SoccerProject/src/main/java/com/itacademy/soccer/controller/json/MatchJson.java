package com.itacademy.soccer.controller.json;

import java.util.Date;

public class MatchJson {
	
//	private Long match_id;
	
	private Long local_team;
	
	private Long visitor_team;
	
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
//
//	public Long getMatch_id() {
//		return match_id;
//	}
//
//	public void setMatch_id(Long match_id) {
//		this.match_id = match_id;
//	}

	
	
}
