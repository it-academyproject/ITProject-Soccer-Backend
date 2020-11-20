package com.itacademy.soccer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.Tournament;
import com.itacademy.soccer.service.ITeamService;
import com.itacademy.soccer.service.impl.TournamentServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@PreAuthorize("authenticated")
@RequestMapping("/api")
public class TournamentController {

    @Autowired
    TournamentServiceImpl tournamentServiceImpl;
    @Autowired
    ITeamService iTeamService;

    @GetMapping("/tournaments") // SHOW ALL TOURNAMENTS TO ALL USERS
    public HashMap<String, Object> showAllUsers()    
    {
    	HashMap< String,Object> map = new HashMap<>();
    	try {
    		List<Tournament> allTournaments = tournamentServiceImpl.showAllTournaments();
			map.put("success", true);
			map.put("message", "Got all Tournaments");
			map.put("Tournaments", allTournaments);
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "no Tournaments to be shown! :" + e.getMessage());

		}
        return map;
    }

    @GetMapping("/tournaments/{id}") // SHOW THAT TOURNAMENT TO ALL USERS     
    public  HashMap<String, Object> showTournamentById (@PathVariable Long id) {
    	HashMap<String, Object> map = new HashMap<>();
    	
		try {
			Tournament tournament = tournamentServiceImpl.getOneTournamentById(id);
			map.put("success", true);
			map.put("message", "Got one Tournament");
			map.put("Tournament", tournament);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "no Tournament to be shown! :" + e.getMessage());
		}
		return map;
    }
    

	@GetMapping("/tournaments/{id}/teams") // SHOWS THE TEAMS BELONGS TO THE TOURNAMENT.	    
    public HashMap <String, Object>  showTeamsByTournament(@PathVariable Long id) {
	    	
	    	HashMap<String, Object> map = new HashMap<>();    	    
	     	List<Team> teamsTournament = new ArrayList<>();
	    	
	    	
	    	try {
	    		
	    		Tournament 	tournament = tournamentServiceImpl.getOneTournamentById(id);	
	    		teamsTournament =tournamentServiceImpl.showTeamsByTournament(id); //All teams with the same {id} tournament 
	    		
	    	 		    	 	
	    		if (teamsTournament !=null && teamsTournament.size() != 0) {
	    			
	    			map.put("The "+ id +" Tournament called : --- "  + tournament.getName() + " Has : ", teamsTournament);  	
	    		}else {
	    			
	    		 	map.put("message", "The "+ id +" Tournament called : --- " + tournament.getName() +  " --- has no teams");   
	    		}
	       	    
	    	}catch (Exception e) {
	    		
	            map.put("message", "something went wrong! :" + e.getMessage());
	    	   	map.put("message", "The "+ id  +  " --- doesn't exist");        	
	            
			}    	
	    	
	     	return  map;   	
	    }

	
    
	@PutMapping("/tournaments") // MODIFY TOURNAMENT ONLY BY ADMIN
	public HashMap<String,Object> modifyTournament(@RequestBody Tournament tournament){
	
		HashMap<String,Object> map = new HashMap<>();		
		Tournament updatedTournament;
		
		try {			
			
			updatedTournament = tournamentServiceImpl.updateTournament(tournament);
			
			if(updatedTournament != null) {
			
				map.put("success", true);
				map.put("New Tournament Values have been changed: ", tournament);

			}else {
				map.put("success", false);
				map.put("message ", "Check the name or the number of rounds of the tournament. The name has to be unique and number of rounds enough to have the teams that the tournament already has");
			}
			
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
				
		return map;
	}
	
	
	@PutMapping("/tournaments/teams") // INSERT ONE TEAM IN ONE TOURNAMENT ONLY BY ADMIN	
	public HashMap<String,Object> insertTeamIntoTournament(@RequestBody ObjectNode objectNode){
		
		HashMap<String,Object> map = new HashMap<>();
		Team teamToInsert;
		Tournament tournament;
		
		Long team_id = 0L;
		Long tournament_id = 0L;
		
		try {

			team_id = objectNode.get("team_id").asLong();
			tournament_id = objectNode.get("tournament_id").asLong();
			
			tournament = tournamentServiceImpl.getOneTournamentById(tournament_id);
			teamToInsert= tournamentServiceImpl.insertTeamIntoTournament(tournament_id, iTeamService.getOneTeamById(team_id)); 
		
			if(teamToInsert != null) {
				
				map.put("success", true);
				map.put("The Team called " + teamToInsert.getName() + " with id :" + teamToInsert.getId() + " has signed up for tournament ", tournament);
				
			}else{
				
				map.put("success", false);
				map.put("message", "Check if the team you want to insert is already in the tournament or the tournament is full");		
			}			
			
		} catch (Exception e) {
			map.put("success", false);
		  	map.put("message","Either the body json request is not valid or the team or tournament id doesn't exist");         

		}
		
		return map;
	}
		
	@PostMapping("/tournaments") // CREATE TOURNAMENT ONLY BY ADMIN	
	public HashMap<String,Object> createTournament(@RequestBody Tournament tournament){
	
		HashMap<String,Object> map = new HashMap<>();	
		Tournament newTournament;
		
		try {
			
			newTournament = tournamentServiceImpl.createTournament(tournament);
			
			if(newTournament != null) {
				
				map.put("success", true);
				map.put("New Tournament has been create: ", newTournament);
			
			}else {
				map.put("success", false);
				map.put("message", "Check the name of the tournament, it cannot be repeated ");	
			}
			
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
				
		return map;
	}
	
	
	@DeleteMapping("/tournaments/{id}")  //DELETE TOURNAMENT ONLY BY ADMIN
	public HashMap<String, Object> deleteTournamentById(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			tournamentServiceImpl.deleteTournamentById(id);
			map.put("success", true);
			map.put("message", "Tournament id:"+ id + " has been deleted");
		} catch (Exception e) {
			map.put("success", false);
			
			if(e.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {
				map.put("message", "No tournament deleted! : This tournament has associated teams!" );
			}
			
			else map.put("message", "No Tournament deleted! :" + e.getMessage());
			
		}
		return map;
	}
}