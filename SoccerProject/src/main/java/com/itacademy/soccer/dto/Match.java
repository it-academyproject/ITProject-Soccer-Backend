/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private Team team_visitors;

//	@OneToMany
//	private List<PlayerActions> playeractions;
	// waiting B-19

	/////////////// CONSTRUCTORS ///////////////
	public Match(Long id, Date date, int local_goals, int visitor_goals) {
		this.id = id;
		this.date = date;
		this.local_goals = local_goals;
		this.visitor_goals = visitor_goals;
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
	public int getVisitors_goals() {
		return visitor_goals;
	}

	/**
	 * @param visitors_goals the visitors_goals to set
	 */
	public void setVisitors_goals(int visitors_goals) {
		this.visitor_goals = visitors_goals;
	}

	

	public Team getTeam_local() {
		return team_local;
	}

	public void setTeam_local(Team team_local) {
		this.team_local = team_local;
	}

	public Team getTeam_visitors() {
		return team_visitors;
	}

	public void setTeam_visitors(Team team_visitors) {
		this.team_visitors = team_visitors;
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
				+ visitor_goals + ", team_local_id=" + team_local.getId() + ", team_visitors_id=" + team_visitors.getId() + "]";
	}
}
