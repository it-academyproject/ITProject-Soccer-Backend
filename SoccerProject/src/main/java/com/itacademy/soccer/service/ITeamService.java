/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.itacademy.soccer.dto.Team;

/**
 * @author KevHaes
 *
 */
public interface ITeamService {

	public Team createTeam(Team team);

	public List<Team> getAllTeams();

	public Team getOneTeamById(Long id);

	public Team modifyOneTeamById(Long id, Team team);

	public Team getOneTeamByIdResults(Long id);

	public void deleteOneTeamById(Long id);	

	public List<LinkedHashMap<String, String>> getMaxWinsTeam();

	public List<LinkedHashMap<String, String>> getMaxLossesTeam();

	public List<LinkedHashMap<String, String>> getMaxDrawsTeam();

}
