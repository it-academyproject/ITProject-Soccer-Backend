/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author KevHaes
 *
 */
@Entity

public class Team {
	/////////////// ATRIBUTES ///////////////
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Date foundation_date;
	private String badge;
	private Float budget;
	private int wins;
	private int losses;
	private int draws;


	// SOLUTION FOR GAME ENGINE - SCHEDULING MATCHES
	// - When trying to generate kickoff team and querying players from this team, the next exception 
	//   is triggered:
	// - org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: XXX, 
	//   could not initialize proxy - no Session Runnable
	// - property [fetch = FetchType.EAGER] inside @OneToMany annotation SOLVES THIS PROBLEM 
	
	@JsonIgnore
	@OneToMany(mappedBy="team", fetch = FetchType.EAGER ) // READ COMMENTS BEFORE !!!
	private List<Player> playersList ;

	// Kevin annotations
	// @OneToOne(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	// private User user;


	/////////////// CONSTRUCTORS ///////////////
	public Team(Long id, String name, Date foundation_date, String badge, Float budget, int wins, int losses,
			int draws) {
		this.id = id;

		this.foundation_date = foundation_date;
		this.name = name;
		this.badge = badge;
		this.budget = budget;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
	}

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
	/* @return the userId
	 
	public User getUser() {
		return user;
	}

    @param userId the userId to set
	
	public void setUser(User user) {
		this.user = user;
	}
	*/

	/////////////// TOSTRING ///////////////
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", foundation_date=" + foundation_date + ", badge=" + badge
				+ ", budget=" + budget + ", wins=" + wins + ", losses=" + losses + ", draws=" + draws + "]";
	}

}
