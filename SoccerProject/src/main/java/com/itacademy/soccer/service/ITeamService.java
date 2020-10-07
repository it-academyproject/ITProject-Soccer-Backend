/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service;

import java.util.List;

import com.itacademy.soccer.dto.Player;
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

	public List<String> getNameBestKeeperInTeam(Long id);

	public List<String> getNameBestDefenderInTeam(Long id);

	public List<String> getNameBestPasserInTeam(Long id);

	public List<String> getNameBestShooterInTeam(Long id);

}
