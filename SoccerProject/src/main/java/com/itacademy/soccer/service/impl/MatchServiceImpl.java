/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Qualifier("IPlayerActionsDAO")
	@Autowired
	IPlayerActionsDAO iPlayerActionsDAO;
	@Autowired
	ITeamDAO iTeamDAO;
	@Autowired
	IPlayerDAO iPlayerDAO;
	@Autowired
	PlayerActionsServiceImpl playerActionsService;
	Logger logger = Logger.getLogger(MatchServiceImpl.class.getName());
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
			Team visitorTeam = match.getTeam_visitor();

			if (localTeam.getId().equals(id) || visitorTeam.getId().equals(id)) {
				matchesToShow.add(match);
			}
		}

		return matchesToShow;
	}

	@Override
	public Match createMatch(Long localTeamId, Long visitorTeamId, Date date) {
		Match matchToSave = new Match();
		try {
			Team localTeam = iTeamDAO.findById(localTeamId).get();
			Team visitorTeam = iTeamDAO.findById(visitorTeamId).get();

			matchToSave.setTeam_local(localTeam);
			matchToSave.setTeam_visitor(visitorTeam);
			matchToSave.setLocal_goals(0);
			matchToSave.setVisitor_goals(0);

			matchToSave.setDate(date);

			iMatchDAO.save(matchToSave);
			playerActionsService.createActionsForPLayers(matchToSave, localTeam.getPlayersList());
			playerActionsService.createActionsForPLayers(matchToSave, visitorTeam.getPlayersList());


			// Call info method

			// ****createMatchAcionsForMatch(matchToSave.getId());
			logger.info("message: CREATED ACTIONS ...........................................................");
		}catch (Exception e){
			logger.log( Level.SEVERE, " message: Error in  createMatch ........................");
			e.printStackTrace();
		}
		return matchToSave;
	}

//	public void createMatchAcionsForMatch(Long id){
//
//	}

}
