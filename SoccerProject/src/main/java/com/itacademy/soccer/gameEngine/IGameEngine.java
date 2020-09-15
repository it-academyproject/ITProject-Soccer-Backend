package com.itacademy.soccer.gameEngine;

import java.util.Date;

public interface IGameEngine {

	public void scheduleMatch(Long matchId);
	
	public void playMatch(Long matchId);
	
}
