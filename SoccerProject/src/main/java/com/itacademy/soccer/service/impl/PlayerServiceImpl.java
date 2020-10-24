package com.itacademy.soccer.service.impl;

import java.util.List;
import java.util.Optional;

import com.itacademy.soccer.controller.json.PlayerJson;
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
	public Optional<Player> findById(Long playerId) { // Created to find players by id at UserController.java to assign players when creating manager
	 return iPlayerDAO.findById(playerId);	
	}
	
	@Override 
	public void changeTeam (Player player, Team team) { // Created to change team_id when player  signs for a team
		player.setTeam_id(team.getId()); // Update team id in Player
		iPlayerDAO.save(player); // Update player
	}
	
}
