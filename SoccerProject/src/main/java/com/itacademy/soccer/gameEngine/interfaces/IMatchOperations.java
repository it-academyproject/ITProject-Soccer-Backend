package com.itacademy.soccer.gameEngine.interfaces;

import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Team;

public interface IMatchOperations {

	//Returns the team
	public Team generateKickOff(Match match);
	
}
