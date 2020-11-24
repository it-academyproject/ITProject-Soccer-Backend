
package com.itacademy.soccer.dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
	private Date foundationDate;
	
	@Column(name="badge")
	private String badge;
	
	@Column(name="budget")
	private Float budget;
	
	@Column(name="wins")
	private Integer wins;
	
	@Column(name="losses")
	private Integer losses;
	
	@Column(name="draws")
	private Integer draws;

	
	@OneToOne()
    @JoinColumn(name="league_id")  
	@JsonIgnore
	private League league;  //Team Relation One to One with a League

	@OneToOne()
    @JoinColumn(name="tournament_id")  
	@JsonIgnore
	private Tournament tournament;  //Team Relation One to One with a Tournament

	// SOLUTION FOR GAME ENGINE - SCHEDULING MATCHES
	// - When trying to generate kickoff team and querying players from this team, the next exception 
	//   is triggered:
	// - org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: XXX, 
	//   could not initialize proxy - no Session Runnable
	// - property [fetch = FetchType.EAGER] inside @OneToMany annotation SOLVES THIS PROBLEM 

	
	@JsonIgnore
	@OneToMany(mappedBy="team", fetch = FetchType.EAGER)
	private List<Player> playersList ;

	@OneToMany
	private List<Match> matches;


	public Team() {
	}
	
	//For {@code BidJson} convert in {@code Bid} 
	public Team(Long id) {
		this.id = id;
	}

	public Team(Long id, String name, Date foundationDate, String badge, Float budget, Integer wins, Integer losses,
			Integer draws, League league, List<Player> playersList, List<Match> matches) {
		this.id = id;
		this.name = name;
		this.foundationDate = foundationDate;
		this.badge = badge;
		this.budget = budget;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
		this.league = league;
		this.playersList = playersList;
		this.matches = matches;
	}
	
	public Team(String name) {
		this.name = name;
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
	public Date getFoundationDate() {
		return foundationDate;
	}

	/**
	 * @param foundationDate the foundation_date to set
	 */
	public void setFoundationDate(Date foundationDate) {
		this.foundationDate = foundationDate;
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
	public Integer getWins() {
		return wins;
	}

	/**
	 * @param wins the wins to set
	 */
	public void setWins(Integer wins) {
		this.wins = wins;
	}

	/**
	 * @return the losses
	 */
	public Integer getLosses() {
		return losses;
	}

	/**
	 * @param losses the losses to set
	 */
	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	/**
	 * @return the draws
	 */
	public Integer getDraws() {
		return draws;
	}

	/**
	 * @param draws the draws to set
	 */
	public void setDraws(Integer draws) {
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
	
	
	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
		
	/////////////// TOSTRING ///////////////
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", foundation_date=" + foundationDate + ", badge=" + badge
				+ ", budget=" + budget + ", wins=" + wins + ", losses=" + losses + ", draws=" + draws + ",league=" + league + "]";
	}
	
	public Team clone() {
		return new Team(this.id, this.name, this.foundationDate, this.badge, this.budget, this.wins, this.losses, this.draws, this.league, this.playersList, this.matches);
	}

	@Override
	public int hashCode() {
		return Objects.hash(badge, budget, draws, foundationDate, id, losses, name, wins);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		return Objects.equals(badge, other.badge) && Objects.equals(budget, other.budget)
				&& Objects.equals(draws, other.draws) && Objects.equals(foundationDate, other.foundationDate)
				&& Objects.equals(id, other.id) && Objects.equals(losses, other.losses)
				&& Objects.equals(name, other.name) && Objects.equals(wins, other.wins);
	}
}
