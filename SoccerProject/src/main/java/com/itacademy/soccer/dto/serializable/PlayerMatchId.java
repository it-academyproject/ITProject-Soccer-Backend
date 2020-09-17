package com.itacademy.soccer.dto.serializable;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class PlayerMatchId implements Serializable{
	
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlayerMatchId that = (PlayerMatchId) o;
		return playerId.equals(that.playerId) &&
				matchId.equals(that.matchId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerId, matchId);
	}
}
