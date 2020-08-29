package com.itacademy.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.PlayerActions;
import com.itacademy.soccer.dto.serializable.PlayerMatchId;

public interface IPlayerActionsDAO extends JpaRepository<PlayerActions, PlayerMatchId>{


}
