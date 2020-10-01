package com.itacademy.soccer.service;

import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import com.itacademy.soccer.dto.Player;

public interface IPlayerService {
	
	public List<Player> playerList();
	
	public List<Player> playerListByTeam(Long teamId);
	
	public Player playerByName(String playerName);
	
	public Player playerById(Long playerId);

	public Player updatePlayer(PlayerJson player);

	Optional<Player> findById(Long playerId);

	Player save(Player player);

	void deletePlayerById(Long id);
}
