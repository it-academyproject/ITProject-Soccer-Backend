/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
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

import com.itacademy.soccer.dao.IMatchDAO;
import com.itacademy.soccer.dao.IMatchTournamentDetailDAO;
import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dao.ITournamentDAO;
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
		

	

//	public void createMatchAcionsForMatch(Long id){
//
//	}

	
	
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
}
