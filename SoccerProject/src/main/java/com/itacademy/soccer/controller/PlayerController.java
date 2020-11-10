package com.itacademy.soccer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import com.itacademy.soccer.dao.IMatchDAO;
import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dto.PlayerActionResult;
import com.itacademy.soccer.dto.PlayerActions;
import com.itacademy.soccer.service.impl.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.service.impl.PlayerServiceImpl;

@RestController
@RequestMapping("/api/players")

public class PlayerController {

	@Autowired
	PlayerServiceImpl playerServiceImpl;
	
	@Autowired
	MatchServiceImpl matchServiceImpl;
	@Autowired
	IPlayerDAO iPlayerDAO;

	@Autowired
	IMatchDAO iMatchDAO;
	@Autowired
	IPlayerActionsDAO iPlayerActionsDAO;


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
	
	
	@PostMapping() // CREATE PLAYER
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
			map.put("message", "Player NOT Created! :" + e.getMessage());
		}
		return map;
	}
	
	@GetMapping("/teams/{id}") // GET PLAYERS BY TEAM
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
	
	@GetMapping("/{id}") // GET PLAYER BY ID
	HashMap<String,Object> getPlayerById(@PathVariable Long id){
		HashMap<String,Object> map = new HashMap<>();
		try {
			Optional<Player> player = iPlayerDAO.findById(id);
			map.put("success", true);
			map.put("player", player.get());
			map.put("message", "get one player by id");
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
		
		return map;
	}
	
	@GetMapping("/name/{name}") // GET PLAYER BY NAME
	HashMap<String,Object> getPlayersByName(@PathVariable String name){
		HashMap<String,Object> map = new HashMap<>();
		try {
			Player player = iPlayerDAO.findByName(name);
			if (player != null) {
				map.put("success", true);
				map.put("player", player);
				map.put("message", "get one player by name");
			}else {
				map.put("success", false);
				map.put("message", "Error getting player with name: " + name);
				
				//throw new Exception();
			}
			
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
		return map;
	}
	
	
	@PutMapping // MODIFY PLAYER
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
	@DeleteMapping("/{id}") // DELETE PLAYER
	public void deleteUser(@PathVariable long id){
		iPlayerDAO. deleteById(id);
	}


	//get the player with more goals, the player with more fouls, etc ... all the stats BY MATCH
	@GetMapping("/stats/matches/{id}")
	HashMap<String, Object> getStats(@PathVariable String id) {
		HashMap<String, Object> map = new HashMap<>();
		try {

			Optional<Player> playerMoreGoals = playerServiceImpl.getPlayerMoreGoals(Long.parseLong(id));
			PlayerActionResult playerMoreGoalsResult = new PlayerActionResult(playerMoreGoals.get().getId(), playerMoreGoals.get().getName(), playerMoreGoals.get().getNumberOfGoals());

			Optional<Player> playerMoreAssists = playerServiceImpl.getPlayerMoreAssists(Long.parseLong(id));
			PlayerActionResult playerMoreAssistsResult = new PlayerActionResult(playerMoreAssists.get().getId(), playerMoreAssists.get().getName(), playerMoreAssists.get().getNumberOfAssists());

			Optional<Player> playerMoreFouls = playerServiceImpl.getPlayerMoreFouls(Long.parseLong(id));
			PlayerActionResult playerMoreFoulsResult = new PlayerActionResult(playerMoreFouls.get().getId(), playerMoreFouls.get().getName(), playerMoreFouls.get().getNumberOfFouls());

			Optional<Player> playerMoreRedCards = playerServiceImpl.getPlayerMoreRedCards(Long.parseLong(id));
			PlayerActionResult playerMoreRedCardsResult = new PlayerActionResult(playerMoreRedCards.get().getId(), playerMoreRedCards.get().getName(), playerMoreRedCards.get().getNumberOfRedCards());

			Optional<Player> playerMoreYellowCards = playerServiceImpl.getPlayerMoreYellowCards(Long.parseLong(id));
			PlayerActionResult playerMoreYellowCardsResult = new PlayerActionResult(playerMoreYellowCards.get().getId(), playerMoreYellowCards.get().getName(), playerMoreYellowCards.get().getNumberOfYellowCards());

			Optional<Player> playerMoreSaves = playerServiceImpl.getPlayerMoreSaves(Long.parseLong(id));
			PlayerActionResult playerMoreSavesResult = new PlayerActionResult(playerMoreSaves.get().getId(), playerMoreSaves.get().getName(), playerMoreSaves.get().getNumberOfSaves());

			map.put("success", true);
			map.put("player with more goals", playerMoreGoalsResult);
			map.put("player with more assists", playerMoreAssistsResult);
			map.put("player with more fouls", playerMoreFoulsResult);
			map.put("player with more red cards", playerMoreRedCardsResult);
			map.put("player with more yellow cards", playerMoreYellowCardsResult);
			map.put("player with more saves", playerMoreSavesResult);
			map.put("message", "get stats");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}

		return map;

	}

	//get the player with more goals, the player with more fouls, etc ... GLOBAL/TOTAL STATS
	@GetMapping("/stats")
	HashMap<String, Object> getGlobalStats(	) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<Player> playerList = iPlayerDAO.findAll();
			List<PlayerActions> playerActionsListList = iPlayerActionsDAO.findAll();

			Optional<Player> playerMoreGoals = playerServiceImpl.getPlayerMoreGoalsTotal();
			PlayerActionResult playerMoreGoalsResult = new PlayerActionResult(playerMoreGoals.get().getId(), playerMoreGoals.get().getName(), playerMoreGoals.get().getNumberOfGoals());

			Optional<Player> playerMoreFouls = playerServiceImpl.getPlayerMoreFoulsTotal();
			PlayerActionResult playerMoreFoulsResult = new PlayerActionResult(playerMoreFouls.get().getId(), playerMoreFouls.get().getName(), playerMoreFouls.get().getNumberOfFouls());

			Optional<Player> playerMoreAssists = playerServiceImpl.getPlayerMoreAssistsTotal();
			PlayerActionResult playerMoreAssistsResult = new PlayerActionResult(playerMoreAssists.get().getId(), playerMoreAssists.get().getName(), playerMoreAssists.get().getNumberOfAssists());

			Optional<Player> playerMoreRedCards = playerServiceImpl.getPlayerMoreRedCardsTotal();
			PlayerActionResult playerMoreRedCardsResult = new PlayerActionResult(playerMoreRedCards.get().getId(), playerMoreRedCards.get().getName(), playerMoreRedCards.get().getNumberOfRedCards());

			Optional<Player> playerMoreYellowCards = playerServiceImpl.getPlayerMoreYellowCardsTotal();
			PlayerActionResult playerMoreYellowCardsResult = new PlayerActionResult(playerMoreYellowCards.get().getId(), playerMoreYellowCards.get().getName(), playerMoreYellowCards.get().getNumberOfYellowCards());

			Optional<Player> playerMoreSaves = playerServiceImpl.getPlayerMoreSavesTotal();
			PlayerActionResult playerMoreSavesResult = new PlayerActionResult(playerMoreSaves.get().getId(), playerMoreSaves.get().getName(), playerMoreSaves.get().getNumberOfSaves());


			map.put("success", true);
			map.put("player with more goals", playerMoreGoalsResult);
			map.put("player with more assists", playerMoreAssistsResult);
			map.put("player with more fouls", playerMoreFoulsResult);
			map.put("player with more red cards", playerMoreRedCardsResult);
			map.put("player with more yellow cards", playerMoreYellowCardsResult);
			map.put("player with more saves", playerMoreSavesResult);
			map.put("message", "get stats");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}

		return map;

	}

}
