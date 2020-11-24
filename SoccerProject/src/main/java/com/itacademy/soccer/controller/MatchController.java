/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itacademy.soccer.controller.json.MatchJson;
import com.itacademy.soccer.dto.League;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.gameEngine.GameEngine;
import com.itacademy.soccer.service.impl.LeagueServiceImpl;
import com.itacademy.soccer.service.impl.MatchServiceImpl;

/**
 * @author KevHaes
 *
 */
@RestController
@PreAuthorize("authenticated")
@RequestMapping(path = "/api")
public class MatchController {

	@Autowired
	GameEngine gameEngine;
	
	@Autowired
	MatchServiceImpl matchServiceImpl;
	
	@Autowired
	LeagueServiceImpl leagueServiceImpl;

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
	// Important: field date from match inside requestBody must be annotated with this annotation: 
	//            @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Madrid")
	//            In other case matches cannot be scheduled in the correct timezone timestamp
	@PostMapping(path = "/matches")
	public HashMap<String, Object> createMatch(@RequestBody MatchJson jsonMatch) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", true);
		Long localTeamId = jsonMatch.getLocal_team();
		Long visitorTeamId = jsonMatch.getVisitor_team();
		Date scheduleDate = jsonMatch.getDate();
		
		try {
			Match createdMatch = matchServiceImpl.createMatch( localTeamId, visitorTeamId,scheduleDate);
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
	
	
	@PostMapping(path = "/matches/tournaments/{tournament_id}")
	public HashMap<String, Object> createInitialMatchesTournament(@PathVariable Long tournament_id) {
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			
			List<Match> tournamentMatches = matchServiceImpl.createInitialMatchesTournament(tournament_id);
		
			if(tournamentMatches != null) {

				map.put("success", true);
				map.put("message", "first matches of the tournament have been created");
				map.put("matches", tournamentMatches);
		
			}else {
				map.put("success", false);
				map.put("message", "Check if the tournament exist and has the exact number of teams it needs");
				
			}
		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "ERROR: " + e.getMessage());
		}
		return map;
	}
	
	
	
	// ENDPOINT JUST TO TRY THE BEHAVIOUR OF CREATING NEW ROUNDS OF A TOURNAMENT
	@PostMapping(path = "/matches/tournaments/{tournament_id}/{round_num}")
	public HashMap<String, Object> createInitialMatchesTournament(@PathVariable Long tournament_id, @PathVariable int round_num) {
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			
			List<Match> tournamentMatches = matchServiceImpl.createRoundMatchesTournament(tournament_id, round_num);
		
			if(tournamentMatches != null) {

				map.put("success", true);
				map.put("message", "round " +round_num+ " matches of the tournament have been created");
				map.put("matches", tournamentMatches);
		
			}else {
				map.put("success", false);
				map.put("message", "Check if the round is not valid");
				
			}
		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "ERROR: " + e.getMessage());
		}
		return map;
	}
	

	// TO DO: Only allow acces to ADMIN users
	// In case match should have been generated but it is not
	
	//B-70 Create matches to a league
	@PostMapping(path = "/matches/leagues/{league_id}")
	public HashMap<String, Object> createLeague(@PathVariable("league_id") Long leagueId){
		
		HashMap<String, Object> map = new HashMap<>();

		try {
			
			List<League> leagueList = leagueServiceImpl.showAllLeagues();
			int count=0;
			for(League leagueChecker: leagueList) {
				
				if(leagueChecker.getId() == leagueId) {
					
					matchServiceImpl.createLeague(leagueId);
					map.put("success", true);
					map.put("message", "created matches to league with id "+leagueId+".");
					
				}else {
					count++;
				}
				
				if(count == leagueList.size()) {
					map.put("success", false);
					map.put("message", "League id = " + leagueId + " not exist, create first a league with this id.");
				}

			}
			
		}catch (Exception e) {
			map.put("succes", false);
			map.put("message", "League (Exception: " + e.getMessage() + ").");
		}
		
		return map;
		
	}
	
	// Play generation of match 
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
