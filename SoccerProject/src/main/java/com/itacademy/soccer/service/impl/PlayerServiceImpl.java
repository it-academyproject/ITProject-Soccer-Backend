package com.itacademy.soccer.service.impl;

import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dto.Player;
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
	public Player updatePlayer(PlayerJson player) {

		try {
			Optional<Player> playerLocalized = iPlayerDAO.findById(Long.parseLong(player.getIdPlayer()));
			if (playerLocalized.isPresent()) {
				if (player.getAka().length() == 0) playerLocalized.get().setAka(null);
				else playerLocalized.get().setAka(player.getAka());
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
}
