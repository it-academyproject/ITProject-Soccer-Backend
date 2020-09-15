package com.itacademy.soccer.gameEngine;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IMatchDAO;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.gameEngine.schedule.MatchScheduler;
import com.itacademy.soccer.gameEngine.schedule.PlayMatchRunnable;

@Service
public class GameEngine implements IGameEngine{

	@Autowired
	IMatchDAO iMatchDAO;
	
	@Autowired
	MatchScheduler matchScheduler;
	
	@Override
	public void scheduleMatch(Long matchId) {
		
		System.out.println("scheduling Match "+matchId+" ...");

		Match match = iMatchDAO.findById(matchId).get();
		Date matchDate = match.getDate();//TO DO: ¿¿¿ WHY IS NOT UTC CORRECT ???
		
		matchDate = addHoursToDate(matchDate,-2); // UTC+2 HARDCODED SOLUTION. IN THIS WAY WORKS FINE...
		
		Runnable runnable = new PlayMatchRunnable(this, matchId);
		matchScheduler.schedule(runnable, matchDate);
		
		System.out.println("scheduled Match "+matchId+ " for date: " + matchDate + ".");
		
	}
	
	@Override
	public void playMatch(Long matchId) {
		System.out.println("played Match "+matchId);
	}
	
	
	//Method for testing schedules purpose
	private Date addHoursToDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }	
	
}
