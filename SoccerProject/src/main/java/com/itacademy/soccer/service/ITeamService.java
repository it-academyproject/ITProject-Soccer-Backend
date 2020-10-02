/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service;

import java.util.List;

import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.impl.TeamServiceImpl;

/**
 * @author KevHaes
 *
 */
public interface ITeamService {

	void createTeamInitial(TeamServiceImpl teamService);
	public Team createTeam(Team team);

	public List<Team> getAllTeams();

	public Team getOneTeamById(Long id);

	public Team modifyOneTeamById(Long id, Team team);

	public Team getOneTeamByIdResults(Long id);

	public void deleteOneTeamById(Long id);	


}
