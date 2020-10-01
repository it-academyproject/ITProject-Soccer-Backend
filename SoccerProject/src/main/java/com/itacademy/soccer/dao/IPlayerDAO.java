package com.itacademy.soccer.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Player;


public interface IPlayerDAO extends JpaRepository<Player, Long> {
	
	Player findByName(String name);

	List<Player> findByteamId(Long teamId);

	default Optional<Player> findById(Long playerId) {
		return findById(playerId);
	}

	//Player save (Player player);

	
}
