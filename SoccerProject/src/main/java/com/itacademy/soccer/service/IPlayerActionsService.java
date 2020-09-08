package com.itacademy.soccer.service;

import com.itacademy.soccer.dto.PlayerActions;
import org.springframework.stereotype.Service;

@Service
public interface IPlayerActionsService {

	public PlayerActions playerActionsByPlayerMatchId(Long playerMatchId);
	
	public PlayerActions updatePlayerActions(PlayerActions playerActionss);
}
