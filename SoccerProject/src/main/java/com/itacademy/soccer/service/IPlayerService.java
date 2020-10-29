package com.itacademy.soccer.service;

import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.PlayerActions;

public interface IPlayerService {
	
	public List<Player> playerList();
	
	public List<Player> playerListByTeam(Long teamId);

	public Player updatePlayer(PlayerJson player);

	Player assignInitialValues(Player player );

	Optional<Player> getPlayerMoreGoals(Long id);

	Optional<Player> getPlayerMoreFouls(Long id);

	Optional<Player> getPlayerMoreAssists(Long id);

	Optional<Player> getPlayerMoreRedCards(Long id);

	Optional<Player> getPlayerMoreYellowCards(Long id);

	Optional<Player> getPlayerMoreSaves(Long id);

    Optional<Player> getPlayerMoreGoalsTotal();

	//get player with more Fouls in total
	Optional<Player> getPlayerMoreFoulsTotal();

	//get player with more Assists in total
	Optional<Player> getPlayerMoreAssistsTotal();

	//get player with more Red Cards in total
	Optional<Player> getPlayerMoreRedCardsTotal();

	//get player with more Yellow Cards in total
	Optional<Player> getPlayerMoreYellowCardsTotal();

	//get player with more Saves in total
	Optional<Player> getPlayerMoreSavesTotal();
}
