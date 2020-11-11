package com.itacademy.soccer.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.itacademy.soccer.controller.json.TeamJson;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.impl.TeamServiceImpl;

@RestController
@RequestMapping(path = "/api/teams")
public class TeamController {

	@Autowired
	TeamServiceImpl teamServiceImpl;
 
	@PostMapping // CREATE TEAM
	public HashMap<String, Object> createTeam(@RequestBody Team team) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			Team newTeam = teamServiceImpl.createTeamInitial(team.getName()); // Creates new team with given name
			teamServiceImpl.createTeam(newTeam); // Saves team in DB
			
			map.put("success", true);
			map.put("message", "Team Created");
			map.put("team", newTeam);
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Team NOT Created! :" + e.getMessage());
		}
		return map;
	}

	@GetMapping // GET ALL TEAMS
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

	@GetMapping(path = "/{id}") // GET TEAM BY ID
	public HashMap<String, Object> getOneTeamById(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			Team toShowTeam = teamServiceImpl.getOneTeamById(id);
			map.put("success", true);
			map.put("message", "Got " + toShowTeam.getName() + " team");
			map.put("team", toShowTeam);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "no team to be shown! :" + e.getMessage());
		}
		return map;
	}

	//B-44 -> B-66
	@PutMapping // MODIFY TEAM
	public HashMap<String, Object> modifyOneTeamById(@RequestBody TeamJson teamJson) {
		
		Team teamToUpdate = teamJson.toTeam();
		
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			
			Team toShowTeam = teamServiceImpl.modifyOneTeamById(teamToUpdate);
			
			if (toShowTeam == null) {throw new Exception();}
			
			map.put("success", true);
			map.put("message", "One team modified");
			map.put("team", toShowTeam);
			
		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "no team to be shown! :" + e.getMessage());
		}
		
		return map;
	}

	@GetMapping(path = "/{id}/stats") // GET TEAM STATS
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

	//B-50
	@DeleteMapping // DELETE TEAM 
	public HashMap<String, Object> deleteOneTeamById(@RequestBody TeamJson team)  {
				
		HashMap<String, Object> map = new HashMap<>();
		try {
			teamServiceImpl.deleteOneTeamById(team.getId());
			map.put("success", true);
			map.put("message", "One team deleted");
		} catch (Exception e) {
			map.put("success", false);
			
			if(e.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {
				map.put("message", "No team deleted! : This Team has associated players!" );
			}
			
			else map.put("message", "No team deleted! : This Team does not exist!");
		} 
		return map;
	}


	@GetMapping(path = "/best") // GET BEST TEAMS
	public LinkedHashMap<String, Object> getTeamsByMaxWLD(){

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		try{
			map.put("success", true);
			map.put("message", "Max W/L/D");
			map.put("wins", teamServiceImpl.getMaxWinsTeam());
			map.put("losses", teamServiceImpl.getMaxLossesTeam());
			map.put("draws", teamServiceImpl.getMaxDrawsTeam());

		}catch (Exception e){
			map.put("success", false);
			map.put("message", "error message :" + e.getMessage());
		}
		return map;
	}

	@GetMapping("/{id}/best") // GET BEST PLAYER IN TEAM
	public LinkedHashMap<String,Object> bestPlayersInTeam(@PathVariable Long id){
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		try{
			teamServiceImpl.getOneTeamById(id);
		} catch (Exception e){
			map.put("success", false);
			map.put("message", "team not found! :" + e.getMessage());
		}

		try{
			List<String> bestKeeperTop = teamServiceImpl.getNameBestKeeperInTeam(id);
			List<String> bestDefenderTop = teamServiceImpl.getNameBestDefenderInTeam(id);
			List<String> bestPasserTop = teamServiceImpl.getNameBestPasserInTeam(id);
			List<String> bestShooterTop = teamServiceImpl.getNameBestShooterInTeam(id);
			map.put("success", true);
			map.put("keeper", bestKeeperTop);
			map.put("defender", bestDefenderTop);
			map.put("passer", bestPasserTop);
			map.put("shooter", bestShooterTop);
		} catch (Exception e) {
		map.put("success", false);
		map.put("message", "no result to be shown! :" + e.getMessage());
	}
		return map;
	}

}
