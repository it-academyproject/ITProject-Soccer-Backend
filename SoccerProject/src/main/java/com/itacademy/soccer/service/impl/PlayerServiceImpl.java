package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.IPlayerService;

@Service
public class PlayerServiceImpl implements IPlayerService{
	@Autowired
	IPlayerDAO iPlayerDAO;
	
	@Override
	public List<Player> playerList(){
		return iPlayerDAO.findAll();
	}
	
	@Override
	public List<Player> playerListByTeam(Long teamId){
		return iPlayerDAO.findByteamId(teamId);
	}

	@Override
	public Optional<Player> findById(Long playerId) { 
	 return iPlayerDAO.findById(playerId);	
	}
		
	@Override
	public Player updatePlayer(PlayerJson player) {

		try {
			Optional<Player> playerLocalized = iPlayerDAO.findById(Long.parseLong(player.getId()));
			if (playerLocalized.isPresent()) {
				if (player.getAka() != null) playerLocalized.get().setAka(player.getAka());
				if (player.getName() != null) playerLocalized.get().setName(player.getName());;
				if (player.getAge() != null) playerLocalized.get().setAge(Integer.parseInt(player.getAge()));
				if (player.getKeeper() != null) playerLocalized.get().setKeeper(Integer.parseInt(player.getKeeper()));
				if (player.getDefense() != null) playerLocalized.get().setDefense(Integer.parseInt(player.getDefense()));
				if (player.getPass() != null) playerLocalized.get().setPass(Integer.parseInt(player.getPass()));
				if (player.getAttack() != null) playerLocalized.get().setAttack(Integer.parseInt(player.getAttack()));
				if (player.getTeam_id() != null) playerLocalized.get().setTeam_id(Long.parseLong(player.getTeam_id()));


				return iPlayerDAO.save(playerLocalized.get());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public Player assignInitialValues(Player player ){

		if (player.getTeam_id() == null) player.setTeam_id(1L);
		return player;
	}
	
		
	// Change player team_id when player signs for a team
	@Override 
	public void changeTeam (Player player, Team team) { 
		player.setTeam_id(team.getId()); // Update team id in Player
		iPlayerDAO.save(player); // Update player
	}
	
	// Get list of players from userJson players list 
	@Override
	public List<Player> getPlayersFromJson(String playersJson){ 
		String[] playersStringList = playersJson.split(","); // Split Json String with list of players												
		Long[] playersIds = new Long[playersStringList.length]; // List to store player ids
		List<Player> playersList = new ArrayList<Player>(); // List to store players

		for (int i = 0; i < playersStringList.length; i++) { // Get and add players to the list
			playersStringList[i] = playersStringList[i].replaceAll("\\D+", ""); // Use regex to delete non-digits																							
			playersIds[i] = Long.parseLong(playersStringList[i]); 
			
			Optional<Player> playerOptional = findById(playersIds[i]); // Find player by id
			if (playerOptional.isPresent()) { // Player by id found
				Player player = playerOptional.get();
				playersList.add(player); // Add player to list

			} else { // Player by id not found
				System.out.println( "player with id="+ playersIds[i] +" not found!");
			}
		}
		return playersList;
	}   
	
	// Sign free players from list given
	@Override
	public List<Player> signFreePlayers(List<Player> playersList, Team team) {
		List<Player> teamPlayers = new ArrayList<Player>(); // List to store players

		for (Player player : playersList) {
			if (player.getTeam_id() == null) { // Check if player is free -- team_id is null
				changeTeam(player, team); // Change player team
				teamPlayers.add(player); // Add player to list
				System.out.println(player.getName() + " with id=" + player.getId() + " has signed with " + team.getName()); // Info sign player

			} else { // Player belongs to a team
				System.out.println(player.getName() + " belongs to team " + player.getTeam().getName());
			}
		}
		return teamPlayers;
	}
	
}
