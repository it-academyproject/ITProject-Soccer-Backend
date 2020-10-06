package com.itacademy.soccer.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Player;


public interface IPlayerDAO extends JpaRepository<Player, Long> {
	
	Player findByName(String name);

	List<Player> findByteamId(Long teamId);
	
	Player findTopByTeamIdOrderByKeeperDescIdAsc (Long teamId);

	List<Player> findByTeamIdAndKeeper(Long teamId, int keeperTop);

	Player findTopByTeamIdOrderByDefenseDescIdAsc (Long teamId);

	List<Player> findByTeamIdAndDefense(Long teamId, int defenseTop);

	Player findTopByTeamIdOrderByPassDescIdAsc (Long teamId);

	List<Player> findByTeamIdAndPass(Long teamId, int passTop);

	Player findTopByTeamIdOrderByAttackDescIdAsc (Long teamId);

	List<Player> findByTeamIdAndAttack(Long teamId, int attackTop);

	/*
	default Optional<Player> findById(Long playerId) {
		return findById(playerId);
	}*/


	//Player save (Player player);

	
}
