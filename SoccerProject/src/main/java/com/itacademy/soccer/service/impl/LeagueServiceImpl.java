package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.ILeagueDAO;
import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dto.League;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.ILeagueService;

@Service
public class LeagueServiceImpl implements ILeagueService {
	
	@Autowired
	private ILeagueDAO iLeagueDAO;
	@Autowired
	private ITeamDAO iTeamsDao;

	@Override
	public List<League> showAllLeagues() {
		
		return iLeagueDAO.findAll();
	}
	
	@Override
	public League getOneLeagueById(Long id) {		
		return iLeagueDAO.findById(id).get();
	}

	
	@Override
	public League updateLeague(League league) {
		return iLeagueDAO.save(league);	
		
	}

	
	@Override
	public League createLeague(League league) {		
		return iLeagueDAO.save(league);
	}

	@Override
	public void deleteLeagueById(Long id) {
		iLeagueDAO.deleteById(id);
		
	}

	@Override
	public List<Team> showTeamsByLeague(Long id){
	
	  	List<Team> allTeams = iTeamsDao.findAll();   	
	  	List<Team> teamsByLeague = new ArrayList<>();
    	
    	for (Team team : allTeams) {    	
    		
    		if (team.getLeague() != null && team.getLeague().getId() == id) {
    			teamsByLeague.add(team);
    		}
		}
	   
    	return teamsByLeague;		
	}

	@Override
	public Team insertTeamintoLeague(Team teamSelected) {
		return iTeamsDao.save(teamSelected);
		 
	}

}
