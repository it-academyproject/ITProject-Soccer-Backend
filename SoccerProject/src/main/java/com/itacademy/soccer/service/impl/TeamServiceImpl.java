/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dto.Player;
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
	@Autowired
	IPlayerDAO iPlayerDao;

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

	public List<Player> getBestPlayersByTeam(Long id) {
		List<Player>getAllPlayer = iPlayerDao.findAll();//Traigo todos los jugadores a lista Player
		List<Player>getPlayersByTeams = new ArrayList<>(); 
		for (Player player : getAllPlayer) {
			if(player.getTeam().getId()==id) {
				getPlayersByTeams.add(player);
			}
			
		}
		return getPlayersByTeams;
	}


}
