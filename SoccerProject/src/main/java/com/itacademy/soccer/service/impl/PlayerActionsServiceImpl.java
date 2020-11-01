package com.itacademy.soccer.service.impl;

import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.serializable.PlayerMatchId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dto.PlayerActions;
import com.itacademy.soccer.service.IPlayerActionsService;

import java.util.*;

import static com.itacademy.soccer.dto.lineup.Lineup.KEEPER;
import static com.itacademy.soccer.dto.lineup.Lineup.NOT_ALIGNED;

@Service
public class PlayerActionsServiceImpl implements IPlayerActionsService{


	@Autowired
	IPlayerActionsDAO iPlayerActionsDAO;


//	public PlayerActions findByIdPlayerIdAndIdMatchId(Long playerId, Long matchId) {
//
//		/*PlayerMatchId playerMatchId = iPlayerActionsDAO.findById(playerMatchId);
//
//		return iPlayerActionsDAO.findById(playerMatchId);*/
//
//		return iPlayerActionsDAO.findByIdPlayerIdAndIdMatchId(playerId, matchId);
//	}

	public List<PlayerActions> findByIdPlayerId(Long playerId) {
		return iPlayerActionsDAO.findByIdPlayerId(playerId);
	}

	public void createActionsForPLayers(Match match, List<Player> playersList){
		try {
			for (Player player : playersList) {
				initializeActionsInMatch(match, player);
				// guardar actions tambien en List de player.... inncesario
			}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println(" Error in createActionsForPlayer ....");
		}

	}
	public void initializeActionsInMatch(Match match, Player player){

		PlayerMatchId playerMatchId = new PlayerMatchId();
		playerMatchId.setPlayerId(player.getId());
		playerMatchId.setMatchId(match.getId());

		PlayerActions actions = new PlayerActions();
		actions.setId(playerMatchId);
		actions.setGoals(0);
		actions.setAssists(0);
		actions.setFouls(0);
		actions.setRedCards(0);
		actions.setYellowCards(0);
		actions.setSaves(0);
		actions.setLineup(NOT_ALIGNED);
		actions.setPlayer(player);
		actions.setMatch(match);

		try {
			iPlayerActionsDAO.save(actions);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public int getOnePlayerActionsInOneMatch(PlayerActions playerActions, String action){
		int data = 0;
		switch (action){
			case "goals":
				data = playerActions.getGoals();
				break;
			case "assists":
				data = playerActions.getAssists();
				break;
			case "fouls":
				data = playerActions.getFouls();
				break;
			case "red_cards":
				data = playerActions.getRedCards();
				break;
			case "yellow_cards":
				data = playerActions.getYellowCards();
				break;
			case "saves":
				data = playerActions.getSaves();
				break;
			default: return -1;
		}
		return data;
	}
	public HashMap<String,Object> verifyIds(String ids, String id2, HashMap<String, Object> map){
		Long id;
		try{
			id = Long.parseLong( ids );

			if ( id2 != null) id = Long.parseLong( id2 );
		}catch (Exception e){
			map.put("success", false);
			map.put("message", "Incorrect data Id  " );
		}
		return map;
	}
}
