package com.itacademy.soccer.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.lineup.Lineup;
import com.itacademy.soccer.dto.serializable.PlayerMatchId;
import com.itacademy.soccer.game.DataForPlayerActions;
import com.itacademy.soccer.service.impl.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.itacademy.soccer.dto.PlayerActions;
import com.itacademy.soccer.service.impl.PlayerActionsServiceImpl;

import static com.itacademy.soccer.dto.lineup.Lineup.*;

@RestController
@RequestMapping("/api/players/{id}")
public class PlayerActionsController {

	@Autowired
	PlayerActionsServiceImpl playerActionsServiceImpl;

	DataForPlayerActions dataForPlayerActions = new DataForPlayerActions();

	//Get playerActions by player id and match id (TO DO)
	@GetMapping("/matches/{id2}/playeractions")
	HashMap<String,Object> getPlayerActionsByPlayerIdInMatch(@RequestParam Long playerId, @RequestParam Long matchId){
		HashMap<String,Object> map = new HashMap<>();
		PlayerActions playerActions = playerActionsServiceImpl.findByIdPlayerIdAndIdMatchId(playerId, matchId);

		if (playerActions != null) {
			map.put("success", true);
			map.put("player actions: ", playerActions);
			map.put("message", "get all players");
		}else {
			map.put("success", false);
			map.put("message", "player with id " + playerId + " or match with id "+ matchId + " not found ");
		}
		return map;
	}
	@GetMapping("/matches/{id2}/onePlayeractions/{action}")
	HashMap<String,Object> getOnePlayerActionsInMatch(@RequestParam Long playerId, @RequestParam Long matchId, @RequestParam String action){
		HashMap<String,Object> map = new HashMap<>();
		PlayerActions playerActions = playerActionsServiceImpl.findByIdPlayerIdAndIdMatchId(playerId, matchId);

		if (playerActions != null ) {
			int data = dataForPlayerActions.getOnePlayerActionsInOneMatch(playerActions, action);

			if (data != 0) {
				map.put("success", true);
				map.put(action + " for this player ", data);
				map.put("message", "actions for one player in matchid: " + matchId);
			}else {
				map.put("success", false);
				map.put("message", "action not exist");
			}
		}else {
			map.put("success", false);
			map.put("message", "player with id " + playerId + " or match with id "+ matchId + " not found ");
		}
		return map;
	}
	@GetMapping("/playeractions")
	HashMap<String,Object> getPlayerActionsByPlayerIdInAllMatches(@RequestParam Long playerId){
		HashMap<String,Object> map = new HashMap<>();
		List<PlayerActions> playerActions = playerActionsServiceImpl.findByIdPlayerId(playerId);

		if (playerActions.size() > 0) {
			map.put("success", true);
			map.put("player actions: ", playerActions);
			map.put("message", "get all players");
		}else {
			map.put("success", false);
			map.put("message", "player with id " + playerId + " not found ");
		}
		return map;
	}
	//TODO no hace nada - Inacabado . put playerActions by playerId and matchId
	@PutMapping("/matches/{id2}/playeractions")
	HashMap<String,Object> putActionsByPlayerId(@RequestBody PlayerActions playerActions, @RequestParam Long playerId, @RequestParam Long matchId){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "get all players");

		return map;
	}
	@PostMapping("/matches/{id2}/lineUp")
	HashMap<String,Object> postLineUpInMatch(@RequestParam Long playerId, @RequestParam Long matchId, @RequestParam String entry){
		HashMap<String,Object> map = new HashMap<>();
		// condicional verifica entrada de LINEUP
		if ( entry.equals("KEEPER") || entry.equals("DEFENDER") || entry.equals("MIDFIELDER") || entry.equals("FORWARD")){
			Lineup lineup = Lineup.valueOf(entry);
			try {

				PlayerActions playerActionsById = playerActionsServiceImpl.findByIdPlayerIdAndIdMatchId(playerId, matchId);

				if (playerActionsById != null) {
					playerActionsById.setLineup(lineup);
					map.put("success", true);
					map.put("match_id ", playerActionsById.getId().getPlayerId());
					map.put("player_id ", playerActionsById.getId().getMatchId());
					map.put("lineUp ", playerActionsById.getLineup());
					map.put("message", "LineUp modified");
					playerActionsServiceImpl.save(playerActionsById);
				} else {
					map.put("success", false);
					map.put("message", "player with id " + playerId + " or match with id " + matchId + " not found ");
				}

			} catch (Exception e) {
				map.put("success", false);
				map.put("message", "something went wrong: " + e.getMessage());
			}
		}else{
			map.put("success", false);
			map.put("message", "lineUp not exist");
		}
		return map;
	}
}
