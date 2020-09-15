package com.itacademy.soccer.gameEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IMatchDAO;

@Service
public class GameEngine implements IGameEngine{

	@Autowired
	IMatchDAO iMatchDAO;
	
	public void scheduleMatch(Long matchId) {
		System.out.println("scheduled Match "+matchId);
	}
	
	public void playMatch(Long matchId) {
		System.out.println("played Match "+matchId);
	}
	
}
