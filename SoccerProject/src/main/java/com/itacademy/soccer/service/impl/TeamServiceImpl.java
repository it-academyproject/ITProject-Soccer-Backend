/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service.impl;

import java.util.Date;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.ITeamService;

/**
 * @author KevHaes
 *
 */
@Service
public class TeamServiceImpl implements ITeamService {

	@Autowired
	IPlayerDAO iPlayerDAO;
	@Autowired
	ITeamDAO iTeamsDao;
	@Override
	public void createTeamInitial(TeamServiceImpl teamService){
		List<Team> teamsToShow = teamService.getAllTeams();
		if ( teamsToShow.size() == 0){
			Team team1 = new Team();

			team1.setName("Free market");
			team1.setFoundation_date(new Date());
			team1.setBadge("x");
			team1.setBudget(0F);
			team1.setWins(0);
			team1.setLosses(0);
			team1.setDraws(0);

			teamService.createTeam(team1);
		}
	}


	@Override
	public Team createTeam(Team team) {
		return iTeamsDao.save(team);
	}

	@Override
	public List<Team> getAllTeams() {
		return iTeamsDao.findAll();
	}

	@Override
	public Team getOneTeamById(Long id) {
		return iTeamsDao.findById(id).get();
	}

	@Override
	public Team modifyOneTeamById(Long id, Team team) {
		return iTeamsDao.save(team);
	}

	@Override
	public Team getOneTeamByIdResults(Long id) {
		return iTeamsDao.findById(id).get();
	}

	@Override
	public void deleteOneTeamById(Long id) {
		iTeamsDao.deleteById(id);
	}

	@Override
	public List<String> getNameBestKeeperInTeam(Long id) {

		int keeperTop =iPlayerDAO.findTopByTeamIdOrderByKeeperDescIdAsc(id).getKeeper();

		//list with names for best keepers according to filters 1.max(keeper) and 2.max(totalSkills)
		List<String> bestKeeperPlayers =	iPlayerDAO.findByTeamIdAndKeeper(id, keeperTop)
				.stream()
				.filter(Objects::nonNull) //should not be needed, just in case player initialization changes
				.collect(Collectors.groupingBy(
						Player::getTotalSkills,//group by total skills
						TreeMap::new, //insert data in a tree map
						Collectors.toList()
				))
				//returns last entry in the map, according to natural ordering the max value
				.lastEntry()
				//return the object with max(totalSkills)
				.getValue()
				.stream()
				.map(Player::getName)
				.collect(Collectors.toList());

		return bestKeeperPlayers;
	}

	@Override
	public List<String> getNameBestDefenderInTeam(Long id) {

		int defenseTop =iPlayerDAO.findTopByTeamIdOrderByDefenseDescIdAsc(id).getDefense();

		//list with names for best defenses according to filters 1.max(defense) and 2.max(totalSkills)
		List<String> bestDefenderPlayers =	iPlayerDAO.findByTeamIdAndDefense(id, defenseTop)
				.stream()
				.filter(Objects::nonNull)//should not be needed, just in case player initialization changes
				.collect(Collectors.groupingBy(
						Player::getTotalSkills,//group by total skills
						TreeMap::new, //insert data in a tree map
						Collectors.toList()
				))
				//returns last entry in the map, according to natural ordering the max value
				.lastEntry()
				//return the object with max(totalSkills)
				.getValue()
				.stream()
				.map(Player::getName)
				.collect(Collectors.toList());

		return bestDefenderPlayers;
	}

	@Override
	public List<String> getNameBestPasserInTeam(Long id) {

		int passTop =iPlayerDAO.findTopByTeamIdOrderByPassDescIdAsc(id).getPass();

		//list with names for best passers according to filters 1.max(pass) and 2.max(totalSkills)
		List<String> bestPasserPlayers = iPlayerDAO.findByTeamIdAndPass(id, passTop)
				.stream()
				.filter(Objects::nonNull)//should not be needed, just in case player initialization changes
				.collect(Collectors.groupingBy(
						Player::getTotalSkills,//group by total skills
						TreeMap::new, //insert data in a tree map
						Collectors.toList()
				))
				//returns last entry in the map, according to natural ordering the max value
				.lastEntry()
				//returns the object with max(totalSkills)
				.getValue()
				.stream()
				.map(Player::getName)
				.collect(Collectors.toList());

		return bestPasserPlayers;
	}

	@Override
	public List<String> getNameBestShooterInTeam(Long id) {

		int attackTop =iPlayerDAO.findTopByTeamIdOrderByAttackDescIdAsc(id).getAttack();

		//list with names for best shooters according to filters 1.max(attack) and 2.max(totalSkills)
		List<String> bestShooterPlayers = iPlayerDAO.findByTeamIdAndAttack(id, attackTop)
				.stream()
				.filter(Objects::nonNull)//should not be needed, just in case player initialization changes
				.collect(Collectors.groupingBy(
						Player::getTotalSkills,//group by total skills
						TreeMap::new, //insert data in a tree map
						Collectors.toList()
				))
				//returns last entry in the map, according to natural ordering the max value
				.lastEntry()
				//return the object with max(totalSkills)
				.getValue()
				.stream()
				.map(Player::getName)
				.collect(Collectors.toList());

		return bestShooterPlayers;
	}

}
