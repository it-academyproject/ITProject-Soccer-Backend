package com.itacademy.soccer.service;

import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Team;

public interface IPlayerService {
	
	public List<Player> playerList();
	
	public List<Player> playerListByTeam(Long teamId);
	
	public Optional<Player> findById(Long playerId);

	public Player updatePlayer(PlayerJson player);

	Player assignInitialValues(Player player );
		
	public void changeTeam (Player player, Team team ); // Change team_id when player signs for a team
	
	public List<Player> getPlayersFromJson(String playersJson); // Get list of players from userJson players list 
	
	public List<Player> signFreePlayers(List<Player> playersList, Team team); // Sign free players from list given

}
