package com.itacademy.soccer.service;

import java.util.Date;
import java.util.List;

import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Player;

public interface IMatchService {
	
	public List<Match> showAllmatchesForTeamByID(Long teamId) throws Exception;
	
	public List<Match> showAllMatches();
	
	public Match createMatch( Long localTeamId, Long visitorTeamId, Date date, Long stadiumId);


}
