package com.itacademy.soccer.controller;
import java.util.HashMap;
import java.util.List;

import com.itacademy.soccer.controller.json.PlayerActionsJson;
import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dto.lineup.Lineup;
import com.itacademy.soccer.service.IPlayerActionsService;
import com.itacademy.soccer.service.impl.PlayerActionsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.itacademy.soccer.dto.PlayerActions;

@RestController
@RequestMapping("/api/playeractions")
public class PlayerActionsController {


	@Qualifier("IPlayerActionsDAO")
	@Autowired
	IPlayerActionsDAO iPlayerActionsDAO;
	@Autowired
	PlayerActionsServiceImpl playerActionsService;

	//Get playerActions by player id and match id (TO DO)
	@GetMapping("/{id}/matches/{id2}")
	HashMap<String,Object> getPlayerActionsByPlayerIdInMatch(@PathVariable String id, @PathVariable String id2 ){
		HashMap<String,Object> map = new HashMap<>();
		map = playerActionsService.verifyIds(id, id2, map);

		if ( map.size() == 0) {
			PlayerActions playerActions = iPlayerActionsDAO.findByIdPlayerIdAndIdMatchId(Long.parseLong(id), Long.parseLong(id2));

			if (playerActions != null) {
				map.put("success", true);
				map.put("player actions: ", playerActions);
				map.put("message", "get all players");
			} else {
				map.put("success", false);
				map.put("message", "player with id " + id + " or match with id " + id2 + " not found ");
			}
		}
		return map;
	}
	@GetMapping("/{id}/matches/{id2}/{action}")
	HashMap<String,Object> getOnePlayerActionsInMatch(@PathVariable String id, @PathVariable String id2, @PathVariable String action){
		HashMap<String,Object> map = new HashMap<>();
		map = playerActionsService.verifyIds(id, id2, map);
		PlayerActions playerActions = iPlayerActionsDAO.findByIdPlayerIdAndIdMatchId(Long.parseLong(id), Long.parseLong(id2));

		if (playerActions != null ) {
			int data = playerActionsService.getOnePlayerActionsInOneMatch(playerActions, action);

			if (data != -1) {
				map.put("success", true);
				map.put( action + " for this player ", data);
				map.put("message", "actions for one player in matchid: " + id2);
			}else {
				map.put("success", false);
				map.put("message", "action not exist");
			}
		}else {
			map.put("success", false);
			map.put("message", "player with id " + id + " or match with id "+ id2 + " not found ");
		}
		return map;
	}
	@GetMapping("/{id}")
	HashMap<String,Object> getPlayerActionsByPlayerIdInAllMatches(@PathVariable String id ){
		HashMap<String,Object> map = new HashMap<>();
		map = playerActionsService.verifyIds(id, null, map); // null para aprovechar el mismo método existente

		if ( map.size() == 0) {
			List<PlayerActions> playerActions = iPlayerActionsDAO.findByIdPlayerId(Long.parseLong(id));

			if (playerActions.size() > 0) {
				map.put("success", true);
				map.put("player actions: ", playerActions);
				map.put("message", "get all players");
			} else {
				map.put("success", false);
				map.put("message", "player with id " + id + " not found ");
			}
		}
		return map;
	}
	//TODO no hace nada - Inacabado . put playerActions by playerId and matchId
	@PutMapping("/{id}/matches/{id2}")
	HashMap<String,Object> putActionsByPlayerId(@RequestBody PlayerActions playerActions, @PathVariable Long playerId, @PathVariable Long matchId){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "get all players");
		return map;
	}

	@PutMapping("/matches/lineup")
	HashMap<String,Object> postLineUpInMatch(@RequestBody PlayerActionsJson p){
		HashMap<String,Object> map = new HashMap<>();
		// condicional verifica entrada de LINEUP
		if ( p.getAction().equals("KEEPER") || p.getAction().equals("DEFENDER") || p.getAction().equals("MIDFIELDER") || p.getAction().equals("FORWARD") || p.getAction().equals("NOT_ALIGNED")){
			Lineup lineup = Lineup.valueOf(p.getAction());
			map = playerActionsService.verifyIds(p.getPlayerId(), p.getMatchId(), map);

			if (map.size() == 0) {
				try {
					PlayerActions playerActionsById = iPlayerActionsDAO.findByIdPlayerIdAndIdMatchId(Long.parseLong(p.getPlayerId()), Long.parseLong(p.getMatchId()));

					if (playerActionsById != null) {
						playerActionsById.setLineup(lineup);
						map.put("success", true);
						map.put("player_id ", playerActionsById.getId().getPlayerId());
						map.put("match_id", playerActionsById.getId().getMatchId());
						map.put("lineUp ", playerActionsById.getLineup());
						map.put("message", "LineUp modified");
						iPlayerActionsDAO.save(playerActionsById);
					} else {
						map.put("success", false);
						map.put("message", "player with id " + p.getPlayerId() + " or match with id " + p.getMatchId() + " not found ");
					}

				} catch (Exception e) {
					map.put("success", false);
					map.put("message", "something went wrong: " + e.getMessage());
				}
			}
		}else{
			map.put("success", false);
			map.put("message", "lineUp not exist");
		}
		return map;
	}
}
