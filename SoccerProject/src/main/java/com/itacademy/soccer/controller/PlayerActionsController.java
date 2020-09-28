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
@RequestMapping("/api/playeraction/{id}")
public class PlayerActionsController {

	@Autowired
	PlayerActionsServiceImpl playerActionsServiceImpl;

	DataForPlayerActions dataForPlayerActions = new DataForPlayerActions();

	//Get playerActions by player id and match id (TO DO)
	@GetMapping("/match/{id2}")
	HashMap<String,Object> getPlayerActionsByPlayerIdInMatch(@RequestParam String id, @RequestParam String id2 ){
		HashMap<String,Object> map = new HashMap<>();
		map = dataForPlayerActions.verifyIds(id, id2, map);

		if ( map.size() == 0) {
			PlayerActions playerActions = playerActionsServiceImpl.findByIdPlayerIdAndIdMatchId(Long.parseLong(id), Long.parseLong(id2));

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
	@GetMapping("/match/{id2}/{action}")
	HashMap<String,Object> getOnePlayerActionsInMatch(@RequestParam String id, @RequestParam String id2, @RequestParam String action){
		HashMap<String,Object> map = new HashMap<>();
		map = dataForPlayerActions.verifyIds(id, id2, map);
		PlayerActions playerActions = playerActionsServiceImpl.findByIdPlayerIdAndIdMatchId(Long.parseLong(id), Long.parseLong(id2));

		if (playerActions != null ) {
			int data = dataForPlayerActions.getOnePlayerActionsInOneMatch(playerActions, action);

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
	@GetMapping()
	HashMap<String,Object> getPlayerActionsByPlayerIdInAllMatches(@RequestParam String id ){
		HashMap<String,Object> map = new HashMap<>();
		map = dataForPlayerActions.verifyIds(id, null, map); // null para aprovechar el mismo método existente

		if ( map.size() == 0) {
			List<PlayerActions> playerActions = playerActionsServiceImpl.findByIdPlayerId(Long.parseLong(id));

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
	@PutMapping("/match/{id2}")
	HashMap<String,Object> putActionsByPlayerId(@RequestBody PlayerActions playerActions, @RequestParam Long playerId, @RequestParam Long matchId){
		HashMap<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "get all players");

		return map;
	}
//	@RequestMapping(
//			value = "/match/{id2}/lineup",
//
//			method = {RequestMethod.GET, RequestMethod.PUT})
	@PutMapping("/match/{id2}/lineup")
	HashMap<String,Object> postLineUpInMatch(@RequestBody PlayerActionsJson p){
		HashMap<String,Object> map = new HashMap<>();
		// condicional verifica entrada de LINEUP
		if ( p.getAction().equals("KEEPER") || p.getAction().equals("DEFENDER") || p.getAction().equals("MIDFIELDER") || p.getAction().equals("FORWARD")){
			Lineup lineup = Lineup.valueOf(p.getAction());
			map = dataForPlayerActions.verifyIds(p.getPlayerId(), p.getMatchId(), map);

			if (map.size() == 0) {
				try {
					PlayerActions playerActionsById = playerActionsServiceImpl.findByIdPlayerIdAndIdMatchId(Long.parseLong(p.getPlayerId()), Long.parseLong(p.getMatchId()));

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
			}
		}else{
			map.put("success", false);
			map.put("message", "lineUp not exist");
		}
		return map;
	}
}
