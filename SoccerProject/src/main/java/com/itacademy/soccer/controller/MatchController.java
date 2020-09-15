/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itacademy.soccer.controller.json.MatchJson;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.gameEngine.GameEngine;
import com.itacademy.soccer.service.impl.MatchServiceImpl;

/**
 * @author KevHaes
 *
 */
@RestController
@RequestMapping(path = "/api")
public class MatchController {

	@Autowired
	GameEngine gameEngine;
	
	@Autowired
	MatchServiceImpl matchServiceImpl;

	// A list of all matches of 1 team by id
	@GetMapping(path = "/teams/{id}/matches")
	public HashMap<String, Object> showAllmatchesForTeamByID(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<Match> MatchesToShow = matchServiceImpl.showAllmatchesForTeamByID(id);
			map.put("success", true);
			map.put("message", "Matches found");
			map.put("matches", MatchesToShow);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Matches NOT Found ! :" + e.getMessage());
		}

		return map;

	}

	// A list of all matches of all teams
	@GetMapping(path = "/teams/matches")
	public HashMap<String, Object> showAllMatches() {
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<Match> MatchesToShow = matchServiceImpl.showAllMatches();
			map.put("success", true);
			map.put("message", "Matches found");
			map.put("matches", MatchesToShow);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Matches NOT Found ! :" + e.getMessage());
		}
		return map;

	}

	// Create a new match between 2 teams
	// Schedule generation of match created from GameEngine
	@PostMapping(path = "/matches")
	//public HashMap<String, Object> createMatch(@RequestBody Team local_team, Team visitor_team) {
	public HashMap<String, Object> createMatch(@RequestBody MatchJson jsonMatch) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", true);
				
		Long localTeamId = jsonMatch.getLocal_team();
		Long visitorTeamId = jsonMatch.getVisitor_team();
		Date date = jsonMatch.getDate();
		
		try {
			Match createdMatch = matchServiceImpl.createMatch(
					localTeamId, visitorTeamId,date);
			
			gameEngine.scheduleMatch(createdMatch.getId()); // SCHEDULE ENGINE
			
			map.put("success", true);
			map.put("message", "createdMatch");
			map.put("match", createdMatch);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Match NOT Created ! :" + e.getMessage());
		}
		return map;

	}


	// TO DO: Only allow acces to ADMIN users
	// In case match should have been generated but it is not
	
	// Play generation of match (matchId field from json request body)
	@PutMapping(path = "/matches/play/{id}")
	public HashMap<String, Object> playMatch(@PathVariable("id") Long matchId) {
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", true);
				
		try {
			
			gameEngine.playMatch(matchId); // PLAY ENGINE
			
			map.put("success", true);
			map.put("message", "played match");
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Match NOT Played ! :" + e.getMessage());
		}
		return map;

	}
	
}
