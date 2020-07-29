/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.impl.MatchServiceImpl;

/**
 * @author KevHaes
 *
 */
@RestController
@RequestMapping(path = "/api")
public class MatchController {

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
	@PostMapping(path = "/matches")
	public HashMap<String, Object> createMatch(@RequestBody Team local_team, Team visitor_team) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", true);
		try {
			Match createdMatch = matchServiceImpl.createMatch(local_team, visitor_team);
			map.put("success", true);
			map.put("message", "createdMatch");
			map.put("match", createdMatch);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Match NOT Created ! :" + e.getMessage());
		}
		return map;

	}

}
