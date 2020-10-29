package com.itacademy.soccer.service.impl;


import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dto.PlayerActions;
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
	@Autowired
	IPlayerActionsDAO iPlayerActionsDAO;
	
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

	@Override
	//get player with more GOALS on a certain match(id)
	public Optional<Player> getPlayerMoreGoals(Long id) {
		List<PlayerActions> playerActionsList = iPlayerActionsDAO.findByIdMatchId(id);

		Comparator<PlayerActions> compareByGoals = Comparator.comparing(PlayerActions::getGoals).reversed();
		Collections.sort(playerActionsList, compareByGoals);

		Long myPlayerId = (playerActionsList.get(0)).getId().getPlayerId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	@Override
	//get player with more FOULS on a certain match(id)
	public Optional<Player> getPlayerMoreFouls(Long id) {
		List<PlayerActions> playerActionsList = iPlayerActionsDAO.findByIdMatchId(id);

		Comparator<PlayerActions> compareByFouls = Comparator.comparing(PlayerActions::getFouls).reversed();
		Collections.sort(playerActionsList, compareByFouls);

		Long myPlayerId = (playerActionsList.get(0)).getId().getPlayerId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	@Override
	//get player with more ASSISTS on a certain match(id)
	public Optional<Player> getPlayerMoreAssists(Long id) {
		List<PlayerActions> playerActionsList = iPlayerActionsDAO.findByIdMatchId(id);

		Comparator<PlayerActions> compareByAssists = Comparator.comparing(PlayerActions::getAssists).reversed();
		Collections.sort(playerActionsList, compareByAssists);

		Long myPlayerId = (playerActionsList.get(0)).getId().getPlayerId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	@Override
	//get player with more RED CARDS on a certain match(id)
	public Optional<Player> getPlayerMoreRedCards(Long id) {
		List<PlayerActions> playerActionsList = iPlayerActionsDAO.findByIdMatchId(id);

		Comparator<PlayerActions> compareByRedCards = Comparator.comparing(PlayerActions::getRedCards).reversed();
		Collections.sort(playerActionsList, compareByRedCards);

		Long myPlayerId = (playerActionsList.get(0)).getId().getPlayerId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	@Override
	//get player with more YELLOW CARDS on a certain match(id)
	public Optional<Player> getPlayerMoreYellowCards(Long id) {
		List<PlayerActions> playerActionsList = iPlayerActionsDAO.findByIdMatchId(id);

		Comparator<PlayerActions> compareByYellowCards = Comparator.comparing(PlayerActions::getYellowCards).reversed();
		Collections.sort(playerActionsList, compareByYellowCards);

		Long myPlayerId = (playerActionsList.get(0)).getId().getPlayerId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	@Override
	//get player with more Saves on a certain match(id)
	public Optional<Player> getPlayerMoreSaves(Long id) {
		List<PlayerActions> playerActionsList = iPlayerActionsDAO.findByIdMatchId(id);

		Comparator<PlayerActions> compareBySaves = Comparator.comparing(PlayerActions::getSaves).reversed();
		Collections.sort(playerActionsList, compareBySaves);

		Long myPlayerId = (playerActionsList.get(0)).getId().getPlayerId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	//get player with more Goals in total
	@Override
	public Optional<Player> getPlayerMoreGoalsTotal() {
		List<Player> playerList = iPlayerDAO.findAll();

		Comparator<Player> compareByGoalsByPlayer = Comparator.comparing(Player::getNumberOfGoalsByPlayer).reversed();
		Collections.sort(playerList, compareByGoalsByPlayer);

		Long myPlayerId = (playerList.get(0)).getId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	//get player with more Fouls in total
	@Override
	public Optional<Player> getPlayerMoreFoulsTotal() {
		List<Player> playerList = iPlayerDAO.findAll();

		Comparator<Player> compareByFoulsByPlayer = Comparator.comparing(Player::getNumberOfFoulsByPlayer).reversed();
		Collections.sort(playerList, compareByFoulsByPlayer);

		Long myPlayerId = (playerList.get(0)).getId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	//get player with more Assists in total
	@Override
	public Optional<Player> getPlayerMoreAssistsTotal() {
		List<Player> playerList = iPlayerDAO.findAll();

		Comparator<Player> compareByAssistsByPlayer = Comparator.comparing(Player::getNumberOfAssistsByPlayer).reversed();
		Collections.sort(playerList, compareByAssistsByPlayer);

		Long myPlayerId = (playerList.get(0)).getId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	//get player with more Red Cards in total
	@Override
	public Optional<Player> getPlayerMoreRedCardsTotal() {
		List<Player> playerList = iPlayerDAO.findAll();

		Comparator<Player> compareByRedCardsByPlayer = Comparator.comparing(Player::getNumberOfRedCardsByPlayer).reversed();
		Collections.sort(playerList, compareByRedCardsByPlayer);

		Long myPlayerId = (playerList.get(0)).getId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	//get player with more Yellow Cards in total
	@Override
	public Optional<Player> getPlayerMoreYellowCardsTotal() {
		List<Player> playerList = iPlayerDAO.findAll();

		Comparator<Player> compareByYellowCardsByPlayer = Comparator.comparing(Player::getNumberOfYellowCardsByPlayer).reversed();
		Collections.sort(playerList, compareByYellowCardsByPlayer);

		Long myPlayerId = (playerList.get(0)).getId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
	}

	//get player with more Saves in total
	@Override
	public Optional<Player> getPlayerMoreSavesTotal() {
		List<Player> playerList = iPlayerDAO.findAll();

		Comparator<Player> compareBySavesByPlayer = Comparator.comparing(Player::getNumberOfSavesByPlayer).reversed();
		Collections.sort(playerList, compareBySavesByPlayer);

		Long myPlayerId = (playerList.get(0)).getId();

		Optional<Player> myPlayer = iPlayerDAO.findById(myPlayerId);

		return myPlayer;
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
