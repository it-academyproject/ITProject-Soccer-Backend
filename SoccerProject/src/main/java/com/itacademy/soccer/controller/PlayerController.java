package com.itacademy.soccer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dao.ITeamDAO;

import org.dom4j.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;

import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.impl.PlayerServiceImpl;
import com.itacademy.soccer.service.impl.TeamServiceImpl;

@RestController
@RequestMapping("/api/players")

public class PlayerController {

	@Autowired
	PlayerServiceImpl playerServiceImpl;
	@Autowired
	IPlayerDAO iPlayerDAO;

	@Autowired
	TeamServiceImpl teamServiceImpl;
	@Autowired
	ITeamDAO iTeamDAO;
	

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

	@GetMapping("/player")
	HashMap<String,Object> getTeamByPlayerName(@RequestParam(name="name") String name){
		HashMap<String,Object> map = new HashMap<>();
		String teamName;
		try {
			Player playerSelected = iPlayerDAO.findByName(name);
			Long id = playerSelected.getTeam_id();
			Team teamSelected = teamServiceImpl.getOneTeamById(id);
			teamName = teamSelected.getName();
			map.put("success", true);
			map.put("message", "Get team name");
			map.put("team", teamName);
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "no teams to be shown!: " + e.getMessage());
		}
		
		return map;
	}
	
}
