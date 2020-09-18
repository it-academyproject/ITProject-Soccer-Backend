package com.itacademy.soccer.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="match_actions")
public class MatchActions{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@Id
	@OneToOne
	@JoinColumn(name="match_id")
	private Match match;
	
	@OneToOne
	@JoinColumn(name="kickoff_team_id")
	private Team kickOff;

	public MatchActions() {
		
	}
	
	public MatchActions(Match match, Team kickOff) {
		this.match = match;
		this.kickOff = kickOff;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Team getKickOff() {
		return kickOff;
	}

	public void setKickOff(Team kickOff) {
		this.kickOff = kickOff;
	}
	
	
}
