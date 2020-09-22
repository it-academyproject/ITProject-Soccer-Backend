package com.itacademy.soccer.dto.serializable;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PlayerMatchId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//attributes
	private Long playerId;
	private Long matchId;
	
	//Constructor
	public PlayerMatchId() {
		
	}
	
	//Getters and Setters
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Long getMatchId() {
		return matchId;
	}
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	
	
}
