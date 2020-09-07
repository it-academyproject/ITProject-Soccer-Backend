/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IMatchDAO;
import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.IMatchService;

/**
 * @author KevHaes
 *
 */
@Service
public class MatchServiceImpl implements IMatchService {
/////////////// ATRIBUTES ///////////////
	@Autowired
	IMatchDAO iMatchDAO;
	
	@Autowired
	ITeamDAO iTeamDAO;
/////////////// CONSTRUCTORS ///////////////

	@Override
	public List<Match> showAllMatches() {
		
		List<Match> matchesList = iMatchDAO.findAll();
	 
	 return matchesList;
		
	}

	@Override
	public List<Match> showAllmatchesForTeamByID(Long id) throws Exception{
		
		List<Match> matchesToShow = new ArrayList<>();
	
		for (Match match : showAllMatches()) {
			
			Team localTeam = match.getTeam_local();
			Team visitorTeam = match.getTeam_visitors();
			
			if (localTeam.getId().equals(id) || visitorTeam.getId().equals(id)) {
				matchesToShow.add(match);
			}
		}
		 
		return matchesToShow;
	}

	@Override
	public Match createMatch(Team p_local_team, Team p_visitor_team) {
		
		Match matchToSave = new Match();
		
		Team localTeam = iTeamDAO.findById(p_local_team.getId()).get();
		Team visitorTeam = iTeamDAO.findById(p_visitor_team.getId()).get();
		
		matchToSave.setTeam_local(localTeam);
		matchToSave.setTeam_visitors(visitorTeam);
		
		matchToSave.setLocal_goals(0);
		matchToSave.setVisitor_goals(0);
		
		return iMatchDAO.save(matchToSave);
	}

}
