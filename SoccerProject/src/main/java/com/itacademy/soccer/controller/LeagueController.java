package com.itacademy.soccer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itacademy.soccer.dto.League;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.ITeamService;
import com.itacademy.soccer.service.impl.LeagueServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api")

public class LeagueController {

    @Autowired
    LeagueServiceImpl leagueServiceImpl;
    @Autowired
    ITeamService iTeamService;

    @GetMapping("/leagues") // SHOW ALL LEAGUES TO ALL USERS
    public HashMap<String, Object> showAllUsers()    
    {
    	HashMap< String,Object> map = new HashMap<>();
    	try {
    		List<League> allLeagues = leagueServiceImpl.showAllLeagues();
			map.put("success", true);
			map.put("message", "Got all Leagues");
			map.put("Leagues", allLeagues);
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "no Leagues to be shown! :" + e.getMessage());

		}
        return map;
    }

    @GetMapping("/leagues/{id}") // SHOW THAT LEAGUE TO ALL USERS     
    public  HashMap<String, Object> showLeagueById (@PathVariable Long id) {
    	HashMap<String, Object> map = new HashMap<>();
    	
		try {
			League league = leagueServiceImpl.getOneLeagueById(id);
			map.put("success", true);
			map.put("message", "Got one League");
			map.put("League", league);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "no League to be shown! :" + e.getMessage());
		}
		return map;
    }
    

	@GetMapping("/leagues/{id}/teams") // SHOWS THE TEAMS BELONGS TO THE LEAGUE.	    
    public HashMap <String, Object>  showTeamsByLeague(@PathVariable Long id) {
	    	
	    	HashMap<String, Object> map = new HashMap<>();    	    
	     	List<Team> teamsLeague = new ArrayList<>();
	    	
	    	
	    	try {
	    		
	    		League 	league = leagueServiceImpl.getOneLeagueById(id);	
	    		teamsLeague =leagueServiceImpl.showTeamsByLeague(id); //All teams with the same {id} league 
	    		
	    	 		    	 	
	    		if (teamsLeague !=null && teamsLeague.size() != 0) {
	    			
	    			map.put("The "+ id +" League called : --- "  + league.getName() + " Has : ", teamsLeague);  	
	    		}else {
	    			
	    		 	map.put("message", "The "+ id +" League called : --- " + league.getName() +  " --- has no teams");   
	    		}
	       	    
	    	}catch (Exception e) {
	    		
	            map.put("message", "something went wrong! :" + e.getMessage());
	    	   	map.put("message", "The "+ id  +  " --- doesn't exist");        	
	            
			}    	
	    	
	     	return  map;   	
	    }

	
    
	@PutMapping("/leagues") // MODIFY LEAGUE ONLY BY ADMIN
	public HashMap<String,Object> modifyLeague(@RequestBody League league){
	
		HashMap<String,Object> map = new HashMap<>();		
		League updatedLeague;
		
		try {			
			
			updatedLeague = leagueServiceImpl.updateLeague(league);
			
			if(updatedLeague != null) {
			
				map.put("success", true);
				map.put("New League Values have been changed: ", league);

			}else {
				map.put("success", false);
				map.put("message ", "Check the name or max_particpants of the league. The name has to be unique and max_participants greater than the teams that the league already has");
			}
			
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
				
		return map;
	}
	
	
	@PutMapping("/leagues/teams") // INSERT ONE TEAM IN ONE LEAGUE ONLY BY ADMIN	
	public HashMap<String,Object> insertTeamintoLeague(@RequestBody ObjectNode objectNode){
		
		HashMap<String,Object> map = new HashMap<>();
		Team teamToInsert;
		League league;
		
		Long team_id = 0L;
		Long league_id = 0L;
		
		try {

			team_id = objectNode.get("team_id").asLong();
			league_id = objectNode.get("league_id").asLong();
			
			league = leagueServiceImpl.getOneLeagueById(league_id);
			teamToInsert= leagueServiceImpl.insertTeamintoLeague(league_id, iTeamService.getOneTeamById(team_id)); 
		
			if(teamToInsert != null) {
				
				map.put("success", true);
				map.put("The Team called " + teamToInsert.getName() + " with id :" + teamToInsert.getId() + " has signed up for league ", league);
				
			}else{
				
				map.put("success", false);
				map.put("message", "Check if the team you want to insert is already in the league or the league is full");		
			}			
			
		} catch (Exception e) {
			map.put("success", false);
		  	map.put("message","Either the body json request is not valid or the team or league id doesn't exist");         

		}
		
		return map;
	}
		
	@PostMapping("/leagues") // CREATE LEAGUE ONLY BY ADMIN	
	public HashMap<String,Object> createLeague(@RequestBody League league){
	
		HashMap<String,Object> map = new HashMap<>();	
		League newLeague;
		
		try {
			
			newLeague = leagueServiceImpl.createLeague(league);
			
			if(newLeague != null) {
				
				map.put("success", true);
				map.put("New League has been create: ", newLeague);
			
			}else {
				map.put("success", false);
				map.put("message", "Check the name of the league, it cannot be repeated ");	
			}
			
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
				
		return map;
	}
	
	@DeleteMapping("/leagues/{id}")  //DELETE LEAGUE ONLY BY ADMIN
	public HashMap<String, Object> deleteLeagueById(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			leagueServiceImpl.deleteLeagueById(id);
			map.put("success", true);
			map.put("message", "This league "+ id + " has been deleted");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "No League deleted! :" + e.getMessage());
		}
		return map;
	}
}