package com.itacademy.soccer.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerActionsJson;
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
@RequestMapping("/api/playeractions/{id}")
public class PlayerActionsController {

	@Autowired
	PlayerActionsServiceImpl playerActionsServiceImpl;

	DataForPlayerActions dataForPlayerActions = new DataForPlayerActions();

	//Get playerActions by player id and match id (TO DO)
	@GetMapping("/matches/{id2}")
	HashMap<String,Object> getPlayerActionsByPlayerIdInMatch(@RequestBody PlayerActionsJson p){
		HashMap<String,Object> map = new HashMap<>();
		PlayerActions playerActions = playerActionsServiceImpl.findByIdPlayerIdAndIdMatchId(p.getPlayerId(), p.getMatchId());

		if (playerActions != null) {
			map.put("success", true);
			map.put("player actions: ", playerActions);
			map.put("message", "get all players");
		}else {
			map.put("success", false);
			map.put("message", "player with id " + p.getPlayerId() + " or match with id "+ p.getMatchId() + " not found ");
		}
		return map;
	}
	@GetMapping("/matches/{id2}/{action}")
	HashMap<String,Object> getOnePlayerActionsInMatch(@RequestBody PlayerActionsJson p){
		HashMap<String,Object> map = new HashMap<>();
		PlayerActions playerActions = playerActionsServiceImpl.findByIdPlayerIdAndIdMatchId(p.getPlayerId(), p.getMatchId());

		if (playerActions != null ) {
			int data = dataForPlayerActions.getOnePlayerActionsInOneMatch(playerActions, p.getAction());

			if (data != 0) {
				map.put("success", true);
				map.put(p.getAction() + " for this player ", data);
				map.put("message", "actions for one player in matchid: " + p.getMatchId());
			}else {
				map.put("success", false);
				map.put("message", "action not exist");
			}
		}else {
			map.put("success", false);
			map.put("message", "player with id " + p.getPlayerId() + " or match with id "+ p.getMatchId() + " not found ");
		}
		return map;
	}
	@GetMapping("/playeractions")
	HashMap<String,Object> getPlayerActionsByPlayerIdInAllMatches(@RequestBody PlayerActionsJson p){
		HashMap<String,Object> map = new HashMap<>();
		List<PlayerActions> playerActions = playerActionsServiceImpl.findByIdPlayerId(p.getPlayerId());

		if (playerActions.size() > 0) {
			map.put("success", true);
			map.put("player actions: ", playerActions);
			map.put("message", "get all players");
		}else {
			map.put("success", false);
			map.put("message", "player with id " + p.getPlayerId() + " not found ");
		}
		return map;
	}
	//TODO no hace nada - Inacabado . put playerActions by playerId and matchId
	@PutMapping("/matches/{id2}")
	HashMap<String,Object> putActionsByPlayerId(@RequestBody PlayerActions playerActions, @RequestParam Long playerId, @RequestParam Long matchId){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "get all players");

		return map;
	}
	@PostMapping("/matches/{id2}/lineUp")
	HashMap<String,Object> postLineUpInMatch(@RequestBody PlayerActionsJson p){
		HashMap<String,Object> map = new HashMap<>();
		// condicional verifica entrada de LINEUP
		if ( p.getAction().equals("KEEPER") || p.getAction().equals("DEFENDER") || p.getAction().equals("MIDFIELDER") || p.getAction().equals("FORWARD")){
			Lineup lineup = Lineup.valueOf(p.getAction());
			try {

				PlayerActions playerActionsById = playerActionsServiceImpl.findByIdPlayerIdAndIdMatchId(p.getPlayerId(), p.getMatchId());

				if (playerActionsById != null) {
					playerActionsById.setLineup(lineup);
					map.put("success", true);
					map.put("player_id ", playerActionsById.getId().getPlayerId());
					map.put("match_id", playerActionsById.getId().getMatchId());
					map.put("lineUp ", playerActionsById.getLineup());
					map.put("message", "LineUp modified");
					playerActionsServiceImpl.save(playerActionsById);
				} else {
					map.put("success", false);
					map.put("message", "player with id " + p.getPlayerId() + " or match with id " + p.getMatchId() + " not found ");
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
