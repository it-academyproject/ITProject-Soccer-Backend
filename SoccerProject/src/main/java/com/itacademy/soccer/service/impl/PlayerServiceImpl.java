package com.itacademy.soccer.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dto.PlayerActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dto.Player;
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


}
