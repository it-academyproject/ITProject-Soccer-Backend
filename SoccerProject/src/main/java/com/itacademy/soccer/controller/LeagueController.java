package com.itacademy.soccer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.itacademy.soccer.dto.League;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.User;
import com.itacademy.soccer.service.ILeagueService;
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
    
	@PutMapping("/leagues/{id}") // MODIFY LEAGUE ONLY BY ADMIN
	HashMap<String,Object> modifyLeague(@PathVariable Long id, @RequestBody League league){
	
		HashMap<String,Object> map = new HashMap<>();		
		League leagueSelected = new League();	
		
		try {
			leagueSelected = leagueServiceImpl.getOneLeagueById(id);		
			
			if (leagueSelected != null) {				
			
				leagueSelected.setName(league.getName());
				leagueSelected.setBeginDate(league.getBeginDate());
				leagueSelected.setEndingDate(league.getEndingDate());
				leagueSelected.setNumberRounds(league.getNumberRounds());
				leagueSelected.setParticipants(league.getParticipants());
				leagueSelected.setDivision(league.getDivision());
				
				
				leagueServiceImpl.updateLeague(leagueSelected);
				
				map.put("success", true);
				map.put("New League Values have been changed: ", leagueSelected);
				}else {
				map.put("success", false);
			}
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
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

	
	@PutMapping("/leagues/teams/{id}") // INSERT ONE TEAM IN ONE LEAGUE ONLY BY ADMIN
	
	HashMap<String,Object> insertTeamintoLeague(@PathVariable Long id, @RequestBody Long league_id){
		
		Team teamSelected =new Team();
		League leagueSelected = new League();		
		
		HashMap<String,Object> map = new HashMap<>();	
		
		try {
			
			teamSelected= iTeamService.getOneTeamById(id);			
			leagueSelected = leagueServiceImpl.getOneLeagueById(league_id);
			
			if (teamSelected != null && leagueSelected != null) {				
				
				if (teamSelected.getLeague().getId() != leagueSelected.getId()) {
					teamSelected.setLeague(leagueSelected);
					leagueServiceImpl.insertTeamintoLeague(teamSelected);
					map.put("success", true);
					map.put("The Team called " + teamSelected.getName() + " with id :" + id + " has signed up for league ", leagueSelected);
				}else {
					map.put("success", true);
					map.put("The Team called " + teamSelected.getName() + " is already in the league ", league_id);
			
				}
				
			}else {
					
				map.put("success", false);
			}
			
			
		} catch (Exception e) {
			
			map.put("success", false);
		  	map.put("message","Make sure The Team "+ id + " exists or the league "+ league_id +" exists");        
		}
		
		return map;
	}
		
	@PostMapping("/leagues") // CREATE LEAGUE ONLY BY ADMIN
	HashMap<String,Object> createLeague(@RequestBody League league){
	
		HashMap<String,Object> map = new HashMap<>();	
		League newLeague = new League();
		
		try {
			newLeague = leagueServiceImpl.createLeague(league);
			map.put("success", true);
			map.put("New League has been create: ", newLeague);
	
		
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