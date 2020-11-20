package com.itacademy.soccer.service;

import java.util.List;

import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.Tournament;

public interface ITournamentService {

	public List<Tournament> showAllTournaments();
	
	public Tournament getOneTournamentById(Long id);
	
	public Tournament updateTournament(Tournament league);
	
	public Tournament createTournament(Tournament league);
	
	public void deleteTournamentById(Long id);
	
	public List<Team> showTeamsByTournament(Long id);
	
	public Team insertTeamIntoTournament(Long tournamentId, Team teamSelected);
	
	
}
