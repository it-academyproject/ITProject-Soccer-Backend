package com.itacademy.soccer.gameEngine.interfaces;

import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.MatchActions;
import com.itacademy.soccer.dto.Team;

public interface IMatchOperations {

	//Returns the kickoff team
	public Team generateKickOff(Match match);
	
	//Save the kickoff team in database
	public MatchActions saveKickOff(Match match, Team team);
	
}
