package com.itacademy.soccer.service;

import java.util.List;

import com.itacademy.soccer.dto.League;
import com.itacademy.soccer.dto.Team;

public interface ILeagueService {

	public List<League> showAllLeagues();
	
	public League getOneLeagueById(Long id);
	
	public League updateLeague(League league);
	
	public League createLeague(League league);
	
	public void deleteLeagueById(Long id);
	
	public List<Team> showTeamsByLeague(Long id);
	
	public Team insertTeamintoLeague(Team teamSelected);
}
