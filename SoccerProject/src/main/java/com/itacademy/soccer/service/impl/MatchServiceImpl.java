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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.ILeagueDAO;
import com.itacademy.soccer.dao.IMatchDAO;
import com.itacademy.soccer.dao.IMatchTournamentDetailDAO;
import com.itacademy.soccer.dao.ITeamDAO;

import com.itacademy.soccer.dao.ITournamentDAO;
import com.itacademy.soccer.dto.League;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.MatchTournamentDetail;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.Tournament;
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
	ITournamentDAO iTournamentDAO;
	
	@Autowired
	IMatchTournamentDetailDAO iMatchTournamentDetailDAO;

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


	@Override
	public List<Match> createInitialMatchesTournament(Long tournamentId) {

		Optional<Tournament> tournament = iTournamentDAO.findById(tournamentId);
		List<Team> teams = iTeamDAO.findBytournamentId(tournamentId);
		Match match;
		MatchTournamentDetail matchTournamentDetail = new MatchTournamentDetail();
		List<Match> matchesCreated = new ArrayList<>();
		
		try {
			
			if(tournament.isPresent()) {  //if the tournament exists
				
				if(tournament.get().getNeededParticipants() == teams.size()){  //if the tournament has the exact number of teams depending on the rounds (it needs 2^numRounds teams)
										
					for(int i= 0; i< (Math.pow(2, tournament.get().getNumberRounds()))/2 ;i++) {  // we create (2^numRounds)/2 matches, picking teams randomly
						
						//we pick one random team from the teams list and remove it form the list
						int indexTeam1 = pickRandomTeam(teams);
						Team team1 = teams.get(indexTeam1);
						teams.remove(indexTeam1);
						
						//pick another one
						int indexTeam2 = pickRandomTeam(teams);
						Team team2 = teams.get(indexTeam2);
						teams.remove(indexTeam2);
						
						//create and add the match to the final list
						match= createMatch(team1.getId(), team2.getId(), tournament.get().getBeginDate());
						matchesCreated.add(match);
						
						
						//insert data into matchTournamentDetail table
						matchTournamentDetail.setMatchId(match.getId());
						matchTournamentDetail.setNumMatch(i+1);
						matchTournamentDetail.setRoundNumber(1);
						iMatchTournamentDetailDAO.save(matchTournamentDetail);	
					}
					
					return matchesCreated;
					
				}else {
					System.out.println("The tournament must have " +tournament.get().getNeededParticipants()+ " participants, and has " +teams.size());
				}
			}else {
				System.out.println("The tournament doesn't exist");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return null;
	}
	
	
	
	@Override
	public List<Match> createRoundMatchesTournament(Long tournamentId, int roundNumber) {

		Optional<Tournament> tournament = iTournamentDAO.findById(tournamentId);
		Match match;
		MatchTournamentDetail matchTournamentDetail = new MatchTournamentDetail();
		List<Match> matchesCreated = new ArrayList<>();
		
		List<MatchTournamentDetail> detailMatchesOfLastRoundPlayed = iMatchTournamentDetailDAO.findByRoundNumber(roundNumber-1);  //match details of the last round played
		List<Match> matchesPlayedLastRound =new ArrayList<>();
		
		try {
					
			if (roundNumber < tournament.get().getNumberRounds()) {   //we cannot create a round greater than tournament has
				
				//get the matches of the last round played
				for (int i=0; i<detailMatchesOfLastRoundPlayed.size(); i++) {
					
					matchesPlayedLastRound.add(iMatchDAO.findById(detailMatchesOfLastRoundPlayed.get(i).getMatchId()).get());
				}
				
				
				int numMatch= 1;
				
				// pick the winners of the matches in pairs and create the new match (we want to create a tree shape)
				for(int i=0; i<matchesPlayedLastRound.size(); i+=2) {
					
					Team teamWinner1 = getWinnerOfMatch(matchesPlayedLastRound.get(i));
					Team teamWinner2 = getWinnerOfMatch(matchesPlayedLastRound.get(i+1));
									
					match= createMatch(teamWinner1.getId(), teamWinner2.getId(), tournament.get().getBeginDate());
					matchesCreated.add(match);
		
					//insert data into matchTournamentDetail table
					matchTournamentDetail.setMatchId(match.getId());
					matchTournamentDetail.setNumMatch(numMatch);
					matchTournamentDetail.setRoundNumber(roundNumber);
					iMatchTournamentDetailDAO.save(matchTournamentDetail);	
					
					numMatch++;				
				}
				
				return matchesCreated;
			
			}else {

				System.out.println("The tournament has " +tournament.get().getNumberRounds()+ " and you are trying to create the round number " +roundNumber);
				return null;
				
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return null;
	}
		


	
	
	//Return the index of a random team inside of a list	
	public int pickRandomTeam (List<Team> teams) {
	    	
		return (int) (Math.random() * teams.size());
		 
	}
	
	
	//Return the winner of a match
	public Team getWinnerOfMatch (Match match) {
		
		if(match.getLocal_goals() > match.getVisitor_goals()) {
			
			return match.getTeam_local();
		
		}else {
			
			return match.getTeam_visitor();
		}
		
	}

	//B-70 Created matches for a league
	@Override
	//public List<Match> createLeague(Long leagueId) {
	public String createLeague(Long leagueId) {
		
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
			leagueToCreate.setCreated(leagueDAO.get().isCreated());
		}
		
		//TO-DO: Matches from the League are created?	
		/*
		if(leagueToCreate.isCreated()) {
			String message = "League matches have already been created!";
			return message;
		}else {
		*/
			//Obtain the teams
			List<Team> teamsList = iTeamDAO.findByleagueId(leagueId);
			int sizeTeams = teamsList.size();
			
			//TO-DO: sizeTeams > LeagueToCreate.maxParticipants?
			
			
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
						
			return "created matches to league with id "+leagueId+".";
		
		//} TO-DO: Matches from the League are created?	 -Update boolean League.created to true
	}
	
}
