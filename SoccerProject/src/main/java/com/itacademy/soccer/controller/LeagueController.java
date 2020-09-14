package com.itacademy.soccer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.itacademy.soccer.dto.League;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.User;
import com.itacademy.soccer.service.impl.LeagueServiceImpl;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api")

public class LeagueController {


    @Autowired
    LeagueServiceImpl leagueServiceImpl;



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

    @GetMapping("/league/{id}") // SHOW THAT LEAGUE TO ALL USERS     
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
    
	@PutMapping("/league/{id}") // MODIFY LEAGUE ONLY BY ADMIN
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
	
	
	@PostMapping("/league") // CREATE LEAGUE ONLY BY ADMIN
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
	
	@DeleteMapping("/league/{id}")  //DELETE LEAGUE ONLY BY ADMIN
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