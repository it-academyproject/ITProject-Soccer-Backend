package com.itacademy.soccer.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itacademy.soccer.dto.PlayerActions;
import com.itacademy.soccer.service.impl.PlayerActionsServiceImpl;

@RestController
@RequestMapping("/api")
public class PlayerActionsController {
	
	@Autowired
	PlayerActionsServiceImpl playerActionsServiceImpl;
	
	//Get playerActions by player id and match id (TO DO)
	@GetMapping("/players/{id}/matches/{id2}/playeractions")
	HashMap<String,Object> getPlayerActionsByPlayerId(@RequestParam Long playerId, @RequestParam Long matchId){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "get all players");
		
		return map;
	}
	//put playerActions by playerId and matchId (TO DO)
	@PutMapping("/players/{id}/matches/{id2}/playeractions")
	HashMap<String,Object> putActionsByPlayerId(@RequestBody PlayerActions playerActions, @RequestParam Long playerId, @RequestParam Long matchId){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "get all players");
		
		return map;
	}

}
