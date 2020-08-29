package com.itacademy.soccer.service;

import java.util.List;

import com.itacademy.soccer.dto.Player;

public interface IPlayerService {
	
	public List<Player> playerList();
	
	public List<Player> playerListByTeam(Long teamId);
	
	public Player playerByName(String playerName);
	
	public Player playerById(Long playerId);
	
	public Player updatePlayer(Player player);
}
