package com.itacademy.soccer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerActionsResultJson;
import com.itacademy.soccer.controller.json.PlayerJson;
import com.itacademy.soccer.dao.IMatchDAO;
import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.service.impl.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.service.impl.PlayerServiceImpl;

@RestController
@PreAuthorize("hasRole('MANAGER')")
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
	
	@PreAuthorize("hasRole('ADMIN')")
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
	public HashMap<String, Object> getStats(@PathVariable Long id) {

		HashMap<String, Object> map = new HashMap<>();

		try {

			map.put("success", true);

			playersMore(id).entrySet().stream()
					.forEach(x -> {
						if (x.getKey().contains("assists")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "assists"));
						} else if (x.getKey().contains("fouls")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "fouls"));
						} else if (x.getKey().contains("goals")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "goals"));
						} else if (x.getKey().contains("saves")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "saves"));
						} else if (x.getKey().contains("red")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "red"));
						} else if (x.getKey().contains("yellow")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "yellow"));
						}
					});

			map.put("message", "get stats");

		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}

		return map;

	}

	//get the player with more goals, the player with more fouls, etc ... GLOBAL/TOTAL STATS
	@GetMapping("/stats")
	public HashMap<String, Object> getGlobalStats() {

		HashMap<String, Object> map = new HashMap<>();

		try {
			map.put("success", true);

			playersMore(null).entrySet().stream()
					.forEach(x -> {
						if (x.getKey().contains("assists")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "assists"));
						} else if (x.getKey().contains("fouls")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "fouls"));
						} else if (x.getKey().contains("goals")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "goals"));
						} else if (x.getKey().contains("saves")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "saves"));
						} else if (x.getKey().contains("red")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "red"));
						} else if (x.getKey().contains("yellow")) {
							map.put(x.getKey(), PlayerActionsResultJson.parsePlayerToJson(x.getValue(), "yellow"));
						}
					});

			map.put("message", "get stats");

		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}

		return map;

	}

	@SuppressWarnings("serial")
	private HashMap<String, Player> playersMore(Long id) {
		return new HashMap<String, Player>() {
			{
				put("assists", (id == null) ? playerServiceImpl.getPlayerMoreAssistsTotal().get()
						: playerServiceImpl.getPlayerMoreAssists(id).get());

				put("fouls", (id == null) ? playerServiceImpl.getPlayerMoreFoulsTotal().get()
						: playerServiceImpl.getPlayerMoreFouls(id).get());

				put("goals", (id == null) ? playerServiceImpl.getPlayerMoreGoalsTotal().get()
						: playerServiceImpl.getPlayerMoreGoals(id).get());

				put("saves", (id == null) ? playerServiceImpl.getPlayerMoreSavesTotal().get()
						: playerServiceImpl.getPlayerMoreSaves(id).get());

				put("red_cards", (id == null) ? playerServiceImpl.getPlayerMoreRedCardsTotal().get()
						: playerServiceImpl.getPlayerMoreRedCards(id).get());

				put("yellow_cards", (id == null) ? playerServiceImpl.getPlayerMoreYellowCardsTotal().get()
						: playerServiceImpl.getPlayerMoreYellowCards(id).get());

			}
		};
	}
}
