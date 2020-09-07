package com.itacademy.soccer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dto.PlayerActions;
import com.itacademy.soccer.service.IPlayerActionsService;

@Service
public class PlayerActionsServiceImpl implements IPlayerActionsService{

	@Autowired
	IPlayerActionsDAO iPlayerActionsDAO;
	
	//find by playerMatchId
	@Override
	public PlayerActions playerActionsByPlayerMatchId(Long playerMatchId) {
		
		/*PlayerMatchId playerMatchId = iPlayerActionsDAO.findById(playerMatchId);
		
		return iPlayerActionsDAO.findById(playerMatchId);*/
		
		return null;
	}
	
	@Override
	public PlayerActions updatePlayerActions(PlayerActions playerActions) {
		return iPlayerActionsDAO.save(playerActions);
	}
}
