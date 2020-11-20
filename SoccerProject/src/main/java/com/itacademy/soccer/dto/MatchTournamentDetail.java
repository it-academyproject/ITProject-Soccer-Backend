package com.itacademy.soccer.dto;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="`match_tournament_detail`")
public class MatchTournamentDetail {
	/////////////// ATRIBUTES ///////////////


	//@OneToOne
    //@JoinColumn(name="match_id")
	@JsonProperty ("match_id")
	private Match match; 
	
	@Column (name= "round_number")
	@JsonProperty ("round_number")
	private int roundNumber;
	
	@Column (name= "num_match")
	@JsonProperty ("num_match")
	private int numMatch;
	


	/////////////// CONSTRUCTORS ///////////////
	public MatchTournamentDetail(Match match,int roundNumber, int numMatch) {
		this.match = match;
		this.roundNumber = roundNumber;
		this.numMatch = numMatch;
	}

	public MatchTournamentDetail() {
	}

	/////////////// GETTERS & SETTERS ///////////////

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public int getNumMatch() {
		return numMatch;
	}

	public void setNumMatch(int numMatch) {
		this.numMatch = numMatch;
	}


	/////////////// TOSTRING ///////////////

	@Override
	public String toString() {
		return "MatchTournamentDetail [match=" + match + ", roundNumber=" + roundNumber + ", numMatch=" + numMatch
				+ "]";
	}
}
