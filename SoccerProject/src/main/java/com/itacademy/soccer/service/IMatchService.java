package com.itacademy.soccer.service;

import java.util.Date;
import java.util.List;

import com.itacademy.soccer.dto.Match;

public interface IMatchService {
	
	public List<Match> showAllmatchesForTeamByID(Long teamId) throws Exception;
	
	public List<Match> showAllMatches();
	
	public Match createMatch( Long localTeamId, Long visitorTeamId, Date date);
	
	public List<Match> createInitialMatchesTournament(Long tournamentId);

	public List<Match> createRoundMatchesTournament(Long tournamentId, int roundNumber);


}
