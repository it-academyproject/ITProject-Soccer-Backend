package com.itacademy.soccer.dto;


import javax.persistence.*;


@Entity
@Table(name="`match_tournament_detail`")
public class MatchTournamentDetail {
	/////////////// ATRIBUTES ///////////////

	
	@Id
	@Column (name= "match_id")
	private long matchId;
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//private Long id;
	
	
	//Is the number of the round inside of the tournament
	@Column (name= "round_number")
	//@JsonProperty ("round_number")
	private int roundNumber;
	
	
	//Is the match number inside of the round. This number will help to define the matches of the next round
	@Column (name= "num_match")
	//@JsonProperty ("num_match")
	private int numMatch;
	
	
	//Join with match table
	@OneToOne
	@JoinColumn(name="match_id")
	private Match match;
	


	/////////////// CONSTRUCTORS ///////////////
	public MatchTournamentDetail(long matchId,int roundNumber, int numMatch) {
		this.matchId = matchId;
		this.roundNumber = roundNumber;
		this.numMatch = numMatch;
	}

	public MatchTournamentDetail() {
	}

	/////////////// GETTERS & SETTERS ///////////////

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
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

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	/////////////// TOSTRING ///////////////

	@Override
	public String toString() {
		return "MatchTournamentDetail [match=" + match + ", roundNumber=" + roundNumber + ", numMatch=" + numMatch
				+ "]";
	}
}
