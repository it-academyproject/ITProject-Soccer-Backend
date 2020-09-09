package com.itacademy.soccer.controller;

import java.util.HashMap;
import java.util.List;

import com.itacademy.soccer.game.VerifyDataPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.service.impl.PlayerServiceImpl;

@RestController
@RequestMapping("/api")

public class PlayerController {

	@Autowired
	PlayerServiceImpl playerServiceImpl;
	VerifyDataPlayer verifyDataPlayer = new VerifyDataPlayer();

	//get all players
	@GetMapping("/players")
	HashMap<String,Object> getAllPlayers(){
		HashMap<String,Object> map = new HashMap<>();
		try {
			List<Player> allPlayers = playerServiceImpl.playerList();
			
			if(allPlayers.size() > 0) {
				map.put("success", true);
				map.put("all players", allPlayers);
				map.put("message", "get all players");
			}else {
				map.put("success", false);
				map.put("message", "Error getting all players");
				
				//throw new Exception();
			}
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
		
		return map;
	}
	@PostMapping("/insertPlayers")
	public HashMap<String, Object> createPlayer(@RequestBody Player player) {

		HashMap<String, Object> map = new HashMap<>();
		player = verifyDataPlayer.assignInitialValues(player);
		try {
			Player NewlyCreatedPlayer = playerServiceImpl.save(player);
			map.put("success", true);
			map.put("message", "Player Created");
			map.put("player", NewlyCreatedPlayer);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Player NOT Created ! :" + e.getMessage());
		}
		return map;
	}
	
	@GetMapping("/teams/{id}/players")
	HashMap<String,Object> getPlayersByTeamId(@PathVariable Long id){
		HashMap<String,Object> map = new HashMap<>();
		try {
			List<Player> allPlayersByTeamId = playerServiceImpl.playerListByTeam(id);
			
			if(allPlayersByTeamId.size() > 0) {
				map.put("success", true);
				map.put("all players by team id", allPlayersByTeamId);
				map.put("message", "get all players by team id");
			}else {
				map.put("success", false);
				map.put("message", "Error getting all players");
				
				//throw new Exception();
			}
			
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
		
		return map;
	}
	
	@GetMapping("/players/id/{id}")
	HashMap<String,Object> getPlayerById(@PathVariable Long id){
		HashMap<String,Object> map = new HashMap<>();
		try {
			Player player = playerServiceImpl.playerById(id);
			map.put("success", true);
			map.put("player", player);
			map.put("message", "get one players by id");
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
		
		return map;
	}
	
	@GetMapping("/players/name/{name}")
	HashMap<String,Object> getPlayersByName(@PathVariable String name){
		HashMap<String,Object> map = new HashMap<>();
		try {
			Player player = playerServiceImpl.playerByName(name);
			if (player != null) {
				map.put("success", true);
				map.put("player", player);
				map.put("message", "get one players by name");
			}else {
				map.put("success", false);
				map.put("message", "Error getting players with name: " + name);
				
				//throw new Exception();
			}
			
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
		
		return map;
	}
	
	//edit Aka in player
	@PutMapping("/players/{id}")
	HashMap<String,Object> putPlayersAka(@RequestBody Player player, @PathVariable Long id){
		HashMap<String,Object> map = new HashMap<>();
		try {
			Player playerLocalized = playerServiceImpl.playerById(id);
			
			if (playerLocalized != null) {
				playerLocalized.setAka(player.getAka());
				map.put("success", true);
				map.put("player with new Aka", playerLocalized);
				map.put("message", "AKA modified");
			}else {
				map.put("success", false);
				map.put("message", "player with id " + player.getId() + " not found and aka not changed");
				
				//throw new Exception();
			}
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
				
		return map;
	}
	@DeleteMapping("/deletePlayer/{id}")
	public void deleteUser(@PathVariable long id){
		playerServiceImpl.deletePlayerById(id);
	}

}
