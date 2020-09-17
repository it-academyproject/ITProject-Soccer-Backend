package com.itacademy.soccer.gameEngine.schedule;

import com.itacademy.soccer.gameEngine.GameEngine;

public class PlayMatchRunnable implements Runnable{
	
	private Long matchId;
	
	private GameEngine gameEngine;
	
	public PlayMatchRunnable(GameEngine gameEngine, Long matchId) {
		this.gameEngine = gameEngine;
		this.matchId=matchId;
	}
	
	@Override
	public void run() {
		gameEngine.playMatch(this.matchId);
	   
	}
	
}
