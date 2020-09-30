/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.dto;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author KevHaes
 *
 */
@Entity
@Table(name="`match`")
public class Match {
	/////////////// ATRIBUTES ///////////////
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Madrid")
	private Date date;
	
	@Column(name="local_goals")
	private int local_goals;
	
	@Column(name="visitor_goals")
	private int visitor_goals;

	@ManyToOne
	@JoinColumn(name="team_local_id")
	private Team team_local;

	@ManyToOne
	@JoinColumn(name="team_visitor_id")
	private Team team_visitor;

	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY,  cascade=CascadeType.ALL)
	@JoinColumn(name="stadium_id")
	private Stadium stadiumMany;

	@JsonIgnore
	@OneToOne(mappedBy="match", fetch = FetchType.LAZY) //fetch = FetchType.LAZY resolves error
														//Hibernate: More than one row with the given identifier was found error
	private MatchActions match_actions;

//	@OneToMany
//	@JoinColumn(name="match_id")
//	private List<PlayerActions> playeractions;

	/////////////// CONSTRUCTORS ///////////////
	public Match(Long id, Date date, int local_goals, int visitor_goals, Stadium stadiumMany) {
		this.id = id;
		this.date = date;
		this.local_goals = local_goals;
		this.visitor_goals = visitor_goals;
		this.stadiumMany = stadiumMany;
	}

	public Match() {
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
	 * @return the timestamp
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the local_goals
	 */
	public int getLocal_goals() {
		return local_goals;
	}

	/**
	 * @param local_goals the local_goals to set
	 */
	public void setLocal_goals(int local_goals) {
		this.local_goals = local_goals;
	}

	/**
	 * @return the visitors_goals
	 */
//	public int getVisitors_goals() {
//		return visitor_goals;
//	}
//
//	/**
//	 * @param visitors_goals the visitors_goals to set
//	 */
//	public void setVisitors_goals(int visitors_goals) {
//		this.visitor_goals = visitors_goals;
//	}

	

	public Team getTeam_local() {
		return team_local;
	}

	public void setTeam_local(Team team_local) {
		this.team_local = team_local;
	}

	public Team getTeam_visitor() {
		return team_visitor;
	}

	public void setTeam_visitor(Team team_visitor) {
		this.team_visitor = team_visitor;
	}

	/**
	 * @return the visitor_goals
	 */
	public int getVisitor_goals() {
		return visitor_goals;
	}

	/**
	 * @param visitor_goals the visitor_goals to set
	 */
	public void setVisitor_goals(int visitor_goals) {
		this.visitor_goals = visitor_goals;
	}

	public MatchActions getMatch_actions() {
		return match_actions;
	}

	public void setMatch_actions(MatchActions match_actions) {
		this.match_actions = match_actions;
	}

	/**
	 * @return the playeractions
	 */
//	@OneToMany
//	@JsonIgnore
//	public List<PlayerActions> getPlayeractions() {
//		return playeractions;
//	}
//
//	/**
//	 * @param playeractions the playeractions to set
//	 */
//	public void setPlayeractions(List<PlayerActions> playeractions) {
//		this.playeractions = playeractions;
//	}

	/////////////// TOSTRING ///////////////
	@Override
	public String toString() {
		return "Match [id=" + id + ", timestamp=" + date + ", local_goals=" + local_goals + ", visitors_goals="
				+ visitor_goals + ", team_local_id=" + team_local.getId() + ", team_visitors_id=" + team_visitor.getId() + "]";
	}
}
