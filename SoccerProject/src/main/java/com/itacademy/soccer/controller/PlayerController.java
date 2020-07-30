package com.itacademy.soccer.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PlayerController {

	@GetMapping("/players")
	HashMap<String,Object> getAllPlayers(){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "get all players");
		
		return map;
	}
	
	@GetMapping("/teams/{id}/players")
	HashMap<String,Object> getPlayersByTeamId(){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "get all players by team id");
		
		return map;
	}
	
	@GetMapping("/players/id/{id}")
	HashMap<String,Object> getPlayerById(){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "get one players by id");
		
		return map;
	}
	
	@GetMapping("/players/name/{name}")
	HashMap<String,Object> getPlayersByName(){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "get one players by name");
		
		return map;
	}
	
	@PutMapping("/players/{id}")
	HashMap<String,Object> putPlayersAka(){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "put AKA in players by id");
		
		return map;
	}
}
