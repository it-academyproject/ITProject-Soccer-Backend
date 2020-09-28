
package com.itacademy.soccer.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="team") // Tab Team


public class Team {
	/////////////// ATRIBUTES ///////////////
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")	
	private String name;
	
	@Column(name="foundation_date")	
	private Date foundation_date;
	
	@Column(name="badge")
	private String badge;
	
	@Column(name="budget")
	private Float budget;
	
	@Column(name="wins")
	private int wins;
	
	@Column(name="losses")
	private int losses;
	
	@Column(name="draws")
	private int draws;

	
	@OneToOne()
    @JoinColumn(name="league_id")  
	@JsonIgnore
	private League league;  //Team Relation One to One with a League



	// SOLUTION FOR GAME ENGINE - SCHEDULING MATCHES
	// - When trying to generate kickoff team and querying players from this team, the next exception 
	//   is triggered:
	// - org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: XXX, 
	//   could not initialize proxy - no Session Runnable
	// - property [fetch = FetchType.EAGER] inside @OneToMany annotation SOLVES THIS PROBLEM 

	
	@JsonIgnore
	@OneToMany(mappedBy="team")
	private List<Player> playersList ;

	@OneToMany
	private List<Match> matches;


	public Team() {
	}

	/////////////// GETTERS & SETTERS ///////////////
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the foundation_date
	 */
	public Date getFoundation_date() {
		return foundation_date;
	}

	/**
	 * @param foundation_date the foundation_date to set
	 */
	public void setFoundation_date(Date foundation_date) {
		this.foundation_date = foundation_date;
	}

	/**
	 * @return the badge
	 */
	public String getBadge() {
		return badge;
	}

	/**
	 * @param badge the badge to set
	 */
	public void setBadge(String badge) {
		this.badge = badge;
	}

	/**
	 * @return the budget
	 */
	public Float getBudget() {
		return budget;
	}

	/**
	 * @param budget the budget to set
	 */
	public void setBudget(Float budget) {
		this.budget = budget;
	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * @param wins the wins to set
	 */
	public void setWins(int wins) {
		this.wins = wins;
	}

	/**
	 * @return the losses
	 */
	public int getLosses() {
		return losses;
	}

	/**
	 * @param losses the losses to set
	 */
	public void setLosses(int losses) {
		this.losses = losses;
	}

	/**
	 * @return the draws
	 */
	public int getDraws() {
		return draws;
	}

	/**
	 * @param draws the draws to set
	 */
	public void setDraws(int draws) {
		this.draws = draws;
	}

	/**
	 * List of players in team
	 * @return list players
	 */
	public List<Player> getPlayersList() {
		return playersList;
	}
	public void setPlayersList(List<Player> playersList) {
		this.playersList = playersList;
	}
	
	@OneToMany
	@JsonIgnore
	public List<Match> getMatches() {
		return matches;
	}

	/**
	 * @param matches the matches to set
	 */
	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}
	
		
	/////////////// TOSTRING ///////////////
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", foundation_date=" + foundation_date + ", badge=" + badge
				+ ", budget=" + budget + ", wins=" + wins + ", losses=" + losses + ", draws=" + draws + ",league=" + league + "]";
	}

}
