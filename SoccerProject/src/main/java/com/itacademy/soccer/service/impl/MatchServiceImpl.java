/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.Random;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.ILeagueDAO;
import com.itacademy.soccer.dao.IMatchDAO;
import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dto.League;
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
	
	@Autowired
	ILeagueDAO iLeagueDAO;
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

	//B-70 Created matches for a league
	@Override
	public List<Match> createLeague(Long leagueId) {
		
		//Obtain the league
		Optional<League> leagueDAO = iLeagueDAO.findById(leagueId);
		League leagueToCreate = new League();
		if(!(leagueDAO==null)) {	
			leagueToCreate.setId(leagueDAO.get().getId());
			leagueToCreate.setName(leagueDAO.get().getName());
			leagueToCreate.setBeginDate(leagueDAO.get().getBeginDate());
			leagueToCreate.setEndingDate(leagueDAO.get().getEndingDate());
			leagueToCreate.setDivision(leagueDAO.get().getDivision());
			leagueToCreate.setMaxParticipants(leagueDAO.get().getMaxParticipants());
			leagueToCreate.setNumberRounds(leagueDAO.get().getNumberRounds());	
		}
		
		//Control: Matches from the League are created?	
		
		//Obtain the teams
		List<Team> teamsList = iTeamDAO.findByleagueId(leagueId);
		int sizeTeams = teamsList.size();
		
		//Control: sizeTeams > LeagueToCreate.maxParticipants?
		
		
		//Obtain the rounds
		int maxRounds = leagueToCreate.getNumberRounds();
		
		//FIXTURE ALGORITHM
		int columns = sizeTeams/2;
		int rows = sizeTeams-1;
				
		//Class for each game played
		class Game{
			//value doesn't matter
			int local = -1;
			int visitor = -1;
		}
		
		//Array (of rounds per matches) according to the number of total teams
		Game[][] game = new Game[rows][columns];
		
		//Loop to place local teams
		for(int i=0, k=0; i< rows; i++) {
			for(int j=0; j< columns; j++) {
				game[i][j] = new Game(); //necessary so that it is not null
				game[i][j].local = k;
				k++;
				if(k == rows) {
					k = 0;
				}
			}
		}
		
		//Loop to place visiting teams
		for(int i=0; i< rows; i++) {
			if(i % 2 == 0) {
				game[i][0].visitor = sizeTeams-1;
			}
			else {
				game[i][0].visitor = game[i][0].local;
				game[i][0].local = sizeTeams-1;
			}
		}
		
		int highestTeam = sizeTeams-1;
		int highestOddTeam = highestTeam-1;
		
		//Loop to place the last missing visiting teams
		for(int i=0, k=highestOddTeam; i < rows; i++) {
			for(int j=1; j < columns; j++) {
				game[i][j].visitor = k;
				k--;
				if(k == -1) {
					k = highestOddTeam;
				}
			}
		}
				
		//The array has been created with the positions of the list of teams that interests us
		
		//Save Teams from the teamsList to the match table
		
		int round=0;
		
		for(int k=1; k < maxRounds+1; k++) {
			round=k;
			for (int i=0; i< game.length; i++) {
				//The matchday is created
				int matchday = i+1;
				
				for (int j=0; j< game[i].length; j++) {
					//Create the match
					Match match = new Match();
					//Get the Teams from the positions of teamsList of this match
					Team localTeam, visitorTeam;
					if(round%2==0) {
						localTeam = teamsList.get(game[i][j].visitor); 
						visitorTeam = teamsList.get(game[i][j].local); 
					}else {
						localTeam = teamsList.get(game[i][j].local); 
						visitorTeam = teamsList.get(game[i][j].visitor); 
					}
					//Includes the local team, visitor team, matchday and round to match
					match.setTeam_local(localTeam);
					match.setTeam_visitor(visitorTeam);
					match.setMatchday(matchday);
					match.setRound(round);
					//The match is stored to the match table
					iMatchDAO.save(match);
				}
			}
	
		}//FIXTURE ALGORITHM END
					
		return null;
	}
	
}
