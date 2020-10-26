package com.itacademy.soccer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dto.PlayerActions;
import org.dom4j.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;

import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.service.impl.PlayerServiceImpl;

@RestController
@RequestMapping("/api/players")

public class PlayerController {

	@Autowired
	PlayerServiceImpl playerServiceImpl;
	@Autowired
	IPlayerDAO iPlayerDAO;


	//get all players
	@GetMapping()
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
	@PostMapping()
	public HashMap<String, Object> createPlayer(@RequestBody Player player) {
		HashMap<String, Object> map = new HashMap<>();
		player = playerServiceImpl.assignInitialValues(player);
		try {
			Player NewlyCreatedPlayer = iPlayerDAO.save(player);
			map.put("success", true);
			map.put("message", "Player Created");
			map.put("player", NewlyCreatedPlayer);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Player NOT Created ! :" + e.getMessage());
		}
		return map;
	}
	
	@GetMapping("/teams/{id}")
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
	
	@GetMapping("/{id}")
	HashMap<String,Object> getPlayerById(@PathVariable Long id){
		HashMap<String,Object> map = new HashMap<>();
		try {
			Optional<Player> player = iPlayerDAO.findById(id);
			map.put("success", true);
			map.put("player", player.get());
			map.put("message", "get one players by id");
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
		
		return map;
	}
	
	@GetMapping("/name/{name}")
	HashMap<String,Object> getPlayersByName(@PathVariable String name){
		HashMap<String,Object> map = new HashMap<>();
		try {
			Player player = iPlayerDAO.findByName(name);
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
	
	//edit attributes in player
	@PutMapping
	HashMap<String,Object> putPlayer(@RequestBody PlayerJson player){
		HashMap<String,Object> map = new HashMap<>();
		System.out.println("update.....................................   " +  player.getName());
		try {
			Player updatedPlayer = playerServiceImpl.updatePlayer(player);

			map.put("success", true);
			map.put("player with new attributes", iPlayerDAO.findById(updatedPlayer.getId()));
			map.put("message", "attributes modified");
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: It seems Player with Id "+player.getId() +" does not exist");
		}
				
		return map;
	}
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable long id){
		iPlayerDAO. deleteById(id);
	}


	//get the player with more goals, the player with more fouls, etc ... all the stats BY MATCH
	@GetMapping("/stats/matches/{id}")
	HashMap<String, Object> getStats(@PathVariable String id) {
		HashMap<String, Object> map = new HashMap<>();
		try {

			Optional<Player> playerMoreGoals = playerServiceImpl.getPlayerMoreGoals(Long.parseLong(id));
			Optional<Player> playerMoreAssists = playerServiceImpl.getPlayerMoreAssists(Long.parseLong(id));
			Optional<Player> playerMoreFouls = playerServiceImpl.getPlayerMoreFouls(Long.parseLong(id));
			Optional<Player> playerMoreRedCards = playerServiceImpl.getPlayerMoreRedCards(Long.parseLong(id));
			Optional<Player> playerMoreYellowCards = playerServiceImpl.getPlayerMoreYellowCards(Long.parseLong(id));
			Optional<Player> playerMoreSaves = playerServiceImpl.getPlayerMoreSaves(Long.parseLong(id));

			map.put("success", true);
			map.put("player with more goals", playerMoreGoals);
			map.put("player with more assists", playerMoreAssists);
			map.put("player with more fouls", playerMoreFouls);
			map.put("player with more red cards", playerMoreRedCards);
			map.put("player with more yellow cards", playerMoreYellowCards);
			map.put("player with more saves", playerMoreSaves);
			map.put("message", "get stats");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}

		return map;

	}

}
