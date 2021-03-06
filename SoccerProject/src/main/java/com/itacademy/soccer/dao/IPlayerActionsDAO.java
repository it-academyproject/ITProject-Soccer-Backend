package com.itacademy.soccer.dao;

import com.itacademy.soccer.dto.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.PlayerActions;
import com.itacademy.soccer.dto.serializable.PlayerMatchId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlayerActionsDAO extends JpaRepository<PlayerActions, PlayerMatchId> {



    PlayerActions findByIdPlayerIdAndIdMatchId(Long playerId, Long matchId);

    List<PlayerActions> findByIdPlayerId (Long playerId);

    List<PlayerActions> findByIdMatchId (Long matchId);

}
