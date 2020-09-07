package com.itacademy.soccer.service;

import java.util.List;

import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Team;

public interface IMatchService {
	
	public List<Match> showAllmatchesForTeamByID(Long teamId);
	
	public List<Match> showAllMatches();
	
	public Match createMatch( Team local_team, Team visitor_team );

}
