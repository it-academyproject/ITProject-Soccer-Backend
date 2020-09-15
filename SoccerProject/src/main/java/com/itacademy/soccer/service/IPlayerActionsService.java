package com.itacademy.soccer.service;

import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dto.PlayerActions;
import com.itacademy.soccer.dto.serializable.PlayerMatchId;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IPlayerActionsService extends IPlayerActionsDAO {


	public PlayerActions findByIdPlayerIdAndIdMatchId(Long playerId, Long matchId);

	List<PlayerActions> findByIdPlayerId(Long playerId);

//	public PlayerActions updatePlayerActions(PlayerActions playerActionss);

	//PlayerActions findByPlayerIdAndMatchId(Long playerId, Long matchId);



	public PlayerActions save(PlayerActions playerActions);

}
