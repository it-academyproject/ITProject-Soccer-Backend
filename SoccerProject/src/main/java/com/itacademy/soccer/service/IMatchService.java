package com.itacademy.soccer.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.dto.Match;

public interface IMatchService {
	
	public List<Match> showAllmatchesForTeamByID(Long teamId) throws Exception;
	
	public List<Match> showAllMatches();
	
	public Match createMatch( Long localTeamId, Long visitorTeamId, Date date);

	Optional<Match> findById(Long id);
}
