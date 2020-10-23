package com.itacademy.soccer.service;

import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import com.itacademy.soccer.dto.Player;

public interface IPlayerService {
	
	public List<Player> playerList();
	
	public List<Player> playerListByTeam(Long teamId);

	public Player updatePlayer(PlayerJson player);

	Player assignInitialValues(Player player );
	
	public Optional<Player> findById(Long playerId); // Created to find players by id at UserController.java to assign players when creating manager
}
