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
	//public HashMap<String,Object> modifyLeague(@PathVariable Long id, @RequestBody League league){
	public HashMap<String,Object> modifyLeague(@RequestBody League league){
	
		HashMap<String,Object> map = new HashMap<>();		
		League leagueSelected = new League();	
		int teamsInLeague;
		
		try {
			leagueSelected = leagueServiceImpl.getOneLeagueById(league.getId());
			teamsInLeague = leagueServiceImpl.showTeamsByLeague(league.getId()).size();

			//El !=null de este if, si se cumple, arrojan un error 400 BAD REQUEST y no hace entrar en el else que había. Por eso la
			// única manera de entrar en el else, será que quieras updatear la liga con menos max_participantes de los que ya hay
			// por eso he puesto el mensaje de error en ese else
			if (leagueSelected != null && league.getMaxParticipants() >= teamsInLeague) {				
			
				leagueSelected.setName(league.getName());
				leagueSelected.setBeginDate(league.getBeginDate());
				leagueSelected.setEndingDate(league.getEndingDate());
				leagueSelected.setNumberRounds(league.getNumberRounds());
				leagueSelected.setMaxParticipants(league.getMaxParticipants());
				leagueSelected.setDivision(league.getDivision());				
				
				leagueServiceImpl.updateLeague(leagueSelected);
				
				map.put("success", true);
				map.put("New League Values have been changed: ", leagueSelected);
				}else {
				map.put("success", false);
				map.put("message", "The updated league has max_participants:" +league.getMaxParticipants()+ " and number of teams:" +teamsInLeague+ " The max_participants should be equal or greater than the number of teams");

			}
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
				
		return map;
	}
	
	
	@PutMapping("/leagues/teams/{id}") // INSERT ONE TEAM IN ONE LEAGUE ONLY BY ADMIN	
	public HashMap<String,Object> insertTeamintoLeague(@PathVariable Long id, @RequestBody League league){
		
		Team teamSelected = new Team();
		League leagueSelected = new League();		
		HashMap<String,Object> map = new HashMap<>();	
		int teamsInLeague;
		
		try {
			teamSelected= iTeamService.getOneTeamById(id);	
			leagueSelected = leagueServiceImpl.getOneLeagueById(league.getId());
			teamsInLeague = leagueServiceImpl.showTeamsByLeague(league.getId()).size();
			
			
			//Los !=null de este if, si se cumplen, arrojan un error 400 BAD REQUEST y no hacen entrar en el else que había. Por eso la
			// única manera de entrar en el else, será que la liga esté llena y no puedas sobrepasar el max_participantes
			// por eso he puesto el mensaje de "la liga está llena" en el esle
			if (teamSelected != null && leagueSelected != null && teamsInLeague < leagueSelected.getMaxParticipants()) { 		
				
				if (teamSelected.getLeague() == null || teamSelected.getLeague().getId() != leagueSelected.getId() ) { 
						teamSelected.setLeague(leagueSelected);						
						leagueServiceImpl.insertTeamintoLeague(teamSelected); 
						map.put("success", true);
						map.put("The Team called " + teamSelected.getName() + " with id :" + teamSelected.getId() + " has signed up for league ", leagueSelected);						
					}else {  
						map.put("success", false);
						map.put("The Team called " + teamSelected.getName() + " is already in the league ", teamSelected.getLeague().getId());		
					}	
				
			}else {	 				
				map.put("success", false);
			  	map.put("message","The League:" +leagueSelected.getId()+ " is full");        

			}			
			
		} catch (Exception e) {
			map.put("success", false);
		  	map.put("message","Make sure The Team "+ id + " exists or the league "+ league.getId() +" exists");        
		}
		
		return map;
	}
		
	@PostMapping("/leagues") // CREATE LEAGUE ONLY BY ADMIN	
	public HashMap<String,Object> createLeague(@RequestBody League league){
	
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