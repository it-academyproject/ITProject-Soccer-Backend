/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.impl.TeamServiceImpl;

/**
 * @author KevHaes
 *
 */
@RestController
@RequestMapping(path = "/api/teams")
public class TeamController {

	@Autowired
	TeamServiceImpl teamServiceImpl;

	@PostMapping
	public HashMap<String, Object> createTeam(@RequestBody Team team) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			Team NewlyCreatedTeam = teamServiceImpl.createTeam(team);
			map.put("success", true);
			map.put("message", "Team Created");
			map.put("team", NewlyCreatedTeam);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Team NOT Created ! :" + e.getMessage());
		}
		return map;
	}

	@GetMapping
	public HashMap<String, Object> getAllTeams() {
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<Team> teamsToShow = teamServiceImpl.getAllTeams();
			map.put("success", true);
			map.put("message", "Got all Teams");
			map.put("teams", teamsToShow);

		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "no teams to be shown! :" + e.getMessage());
		}
		return map;
	}

	@GetMapping(path = "/{id}")
	public HashMap<String, Object> getOneTeamById(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			Team toShowTeam = teamServiceImpl.getOneTeamById(id);
			map.put("success", true);
			map.put("message", "Got one Teams");
			map.put("team", toShowTeam);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "no team to be shown! :" + e.getMessage());
		}
		return map;
	}

	@PutMapping(path = "/{id}")
	public HashMap<String, Object> modifyOneTeamById(@PathVariable Long id, @RequestBody Team team) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			Team toShowTeam = teamServiceImpl.modifyOneTeamById(id, team);
			map.put("success", true);
			map.put("message", "One team modified");
			map.put("team", toShowTeam);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "no team to be shown! :" + e.getMessage());
		}
		return map;
	}

	@GetMapping(path = "/{id}/result")
	public HashMap<String, Object> getOneTeamByIdResults(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			Team toShowTeam = teamServiceImpl.getOneTeamByIdResults(id);
			map.put("success", true);
			map.put("message", "Got results of team");
			// map.put("id", toShowTeam.getId()); --> ?
			map.put("wins", toShowTeam.getWins());
			map.put("losses", toShowTeam.getLosses());
			map.put("draws", toShowTeam.getDraws());
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "no result to be shown! :" + e.getMessage());
		}
		return map;
	}

	@DeleteMapping(path = "/{id}")
	public HashMap<String, Object> deleteOneTeamById(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			teamServiceImpl.deleteOneTeamById(id);
			map.put("success", true);
			map.put("message", "One team deleted");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "No team deleted! :" + e.getMessage());
		}
		return map;
	}

}
