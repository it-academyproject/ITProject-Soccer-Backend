/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author KevHaes
 *
 */
@Entity
public class Match {
	/////////////// ATRIBUTES ///////////////
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date timestamp;
	private int local_goals;
	private int visitor_goals;
	private Long team_local_id;
	private Long team_visitors_id;

	/////////////// CONSTRUCTORS ///////////////
	public Match(Long id, Date timestamp, int local_goals, int visitor_goals, Long team_local_id,
			Long team_visitors_id) {
		this.id = id;
		this.timestamp = timestamp;
		this.local_goals = local_goals;
		this.visitor_goals = visitor_goals;
		this.team_local_id = team_local_id;
		this.team_visitors_id = team_visitors_id;
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
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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

	/**
	 * @return the team_local_id
	 */
	public Long getTeam_local_id() {
		return team_local_id;
	}

	/**
	 * @param team_local_id the team_local_id to set
	 */
	public void setTeam_local_id(Long team_local_id) {
		this.team_local_id = team_local_id;
	}

	/**
	 * @return the team_visitors_id
	 */
	public Long getTeam_visitors_id() {
		return team_visitors_id;
	}

	/**
	 * @param team_visitors_id the team_visitors_id to set
	 */
	public void setTeam_visitors_id(Long team_visitors_id) {
		this.team_visitors_id = team_visitors_id;
	}

	/////////////// TOSTRING ///////////////
	@Override
	public String toString() {
		return "Match [id=" + id + ", timestamp=" + timestamp + ", local_goals=" + local_goals + ", visitors_goals="
				+ visitor_goals + ", team_local_id=" + team_local_id + ", team_visitors_id=" + team_visitors_id + "]";
	}
}
