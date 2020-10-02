/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.ITeamService;

/**
 * @author KevHaes
 *
 */
@Service
public class TeamServiceImpl implements ITeamService {

	@Autowired
	ITeamDAO iTeamsDao;
	@Override
	public void createTeamInitial(TeamServiceImpl teamService){
		List<Team> teamsToShow = teamService.getAllTeams();
		if ( teamsToShow.size() == 0){
			Team team1 = new Team();

			team1.setName("Free market");
			team1.setFoundation_date(new Date());
			team1.setBadge("x");
			team1.setBudget(0F);
			team1.setWins(0);
			team1.setLosses(0);
			team1.setDraws(0);

			teamService.createTeam(team1);
		}
	}
	@Override
	public Team createTeam(Team team) {
		return iTeamsDao.save(team);
	}

	@Override
	public List<Team> getAllTeams() {
		return iTeamsDao.findAll();
	}

	@Override
	public Team getOneTeamById(Long id) {
		return iTeamsDao.findById(id).get();
	}

	@Override
	public Team modifyOneTeamById(Long id, Team team) {
		return iTeamsDao.save(team);
	}

	@Override
	public Team getOneTeamByIdResults(Long id) {
		return iTeamsDao.findById(id).get();
	}

	@Override
	public void deleteOneTeamById(Long id) {
		iTeamsDao.deleteById(id);
	}

}
