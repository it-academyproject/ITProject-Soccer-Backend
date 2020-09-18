package com.itacademy.soccer.gameEngine;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IMatchActionsDAO;
import com.itacademy.soccer.dao.IMatchDAO;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.MatchActions;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.gameEngine.interfaces.IGameEngine;
import com.itacademy.soccer.gameEngine.schedule.MatchScheduler;
import com.itacademy.soccer.gameEngine.schedule.PlayMatchRunnable;

@Service
public class GameEngine implements IGameEngine{

	@Autowired
	IMatchDAO iMatchDAO;
	
	@Autowired
	MatchScheduler matchScheduler;
	
	@Autowired
	MatchOperations matchOperations;
	
	@Autowired
	IMatchActionsDAO iMatchActionsDAO;
	
	@Override
	public void scheduleMatch(Long matchId) {
		
		System.out.println("scheduling Match "+matchId+" ...");

		Match match = iMatchDAO.findById(matchId).get();
		Date matchDate = match.getDate();
		
		Runnable runnable = new PlayMatchRunnable(this, matchId);
		matchScheduler.schedule(runnable, matchDate);
		
//	    System.out.println("Test UTC TimeZone : "+new Date()); //It should be the current time 
		System.out.println("scheduled Match "+matchId+ " for date: " + matchDate + ".");
		
	}
	
	@Override
	public void playMatch(Long matchId) {
		
		System.out.println("Playing Match "+matchId+" ...");
		
		
		Match match = iMatchDAO.findById(matchId).get();
		
		//(1) CREATE INSTANCE OF MATCH_ACTIONS FOR THE CURRENT MATCH
		//----------------------------------------------------------
		//TO DO!!! - MATCH_ACTIONS IDENTIFIER(PRIMARY KEY) SHOULD BE MATCH_ID
		//TO DO!!! - IF MATCH_ACTIONS FOR THE CURRENT MATCH DOESN'T EXISTS CREATE A NEW MATCHACTIONS
		//           ELSE USE THE ONE ALREADY CREATED FOR THIS MATCH
		if(match.getMatch_actions()==null) {
			MatchActions matchActions = new MatchActions();
			matchActions.setMatch(match);
			iMatchActionsDAO.save(matchActions);
			
			match.setMatch_actions(matchActions);
			iMatchDAO.save(match);
		}
		
		//(2) KICKOFF
		//-----------
		//generate KICKOFF
		Team kickOffTeam = matchOperations.generateKickOff(match); 
		// save KICKOFF in database
		MatchActions updatedMatchActions = matchOperations.saveKickOff(match, kickOffTeam); 
		
		//pring kickoff team
		System.out.println("KICKOFF TEAM: [id="+kickOffTeam.getId()+", name="+kickOffTeam.getName()+"]");
		
		//(3) SCORE GENERATION (TO DO)
		//----------------------------
		match.setVisitor_goals(5);
		match.setLocal_goals(5);
		
		iMatchDAO.save(match);
		
		System.out.println("Played Match " +matchId+".");
		
	}
	
	
	//Help Method for testing schedules purpose
	private Date addHoursToDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }	
	
}
