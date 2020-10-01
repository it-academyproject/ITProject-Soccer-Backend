package com.itacademy.soccer.dto;

import javax.persistence.*;

import com.itacademy.soccer.dto.lineup.Lineup;
import com.itacademy.soccer.dto.serializable.PlayerMatchId;

@Entity
@Table(name="player_actions")
public class PlayerActions {
	
	//Atributes
	@EmbeddedId
	private PlayerMatchId id;
	
	@MapsId("playerId")
	@ManyToOne
	private Player player;
	
	@MapsId("matchId")
	@ManyToOne
	private Match match;
	
	
	
	private int goals;
	private int assists;
	private int fouls;
	@Column(name="red_cards")
	private int redCards;
	@Column(name="yellow_cards")
	private int yellowCards;
	private int saves;

	@Enumerated(EnumType.STRING)
	private Lineup lineup;
	
	//Constructor
	public PlayerActions() {

	}

	public PlayerActions(PlayerMatchId id, Player player, int goals, int assists, int fouls, int redCards, int yellowCards, int saves, Lineup lineup) {
		this.id = id;
		this.player = player;
		this.goals = goals;
		this.assists = assists;
		this.fouls = fouls;
		this.redCards = redCards;
		this.yellowCards = yellowCards;
		this.saves = saves;
		this.lineup = lineup;
	}
//Getters and Setters


	public int getFouls() {
		return fouls;
	}

	public void setFouls(int fouls) {
		this.fouls = fouls;
	}

	public PlayerMatchId getId() {
		return id;
	}

	public void setId(PlayerMatchId playerMatchId) {
		this.id = playerMatchId;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/*public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}*/

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public int getRedCards() {
		return redCards;
	}

	public void setRedCards(int redCards) {
		this.redCards = redCards;
	}

	public int getYellowCards() {
		return yellowCards;
	}

	public void setYellowCards(int yellowCards) {
		this.yellowCards = yellowCards;
	}

	public int getSaves() {
		return saves;
	}

	public void setSaves(int saves) {
		this.saves = saves;
	}

	public Lineup getLineup() {
		return lineup;
	}

	public void setLineup(Lineup lineup) {
		this.lineup = lineup;
	}
	
	
}
