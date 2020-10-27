/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service.impl;

import java.util.Date;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.itacademy.soccer.controller.json.TeamJson;
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
	public Team createTeamInitial(String name) { // Creates initial team with name provided
		Team team = new Team(); 
		team.setName(name);
		team.setFoundation_date(new Date()); // Set foundation date as creation in the system
		team.setBadge(null);
		team.setBudget(300000F); // Set initial budget as 300.000
		team.setWins(0);
		team.setLosses(0);
		team.setDraws(0);
		return team;
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
	public Team modifyOneTeamById(Team team) {
		//B-44
		Optional<Team> teamLocalized = iTeamsDao.findById(team.getId());
		try {
			if(teamLocalized != null) {
				teamLocalized.get().setName(team.getName());
				teamLocalized.get().setFoundation_date(team.getFoundation_date());
				teamLocalized.get().setBadge(team.getBadge());
				teamLocalized.get().setWins(team.getWins());
				teamLocalized.get().setLosses(team.getLosses());
				teamLocalized.get().setDraws(team.getDraws());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return iTeamsDao.save(teamLocalized.get());
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
	public List<LinkedHashMap<String, String>> getMaxWinsTeam() {
		List<Team> maxWins = iTeamsDao.findAll().stream()
				.filter(team -> team.getWins()>=0)
				.collect(Collectors.groupingBy(
						Team::getWins,
						TreeMap::new,
						Collectors.toList()))
				.lastEntry()
				.getValue();

		List<LinkedHashMap<String, String>> maxWinsList = new ArrayList<>();

		for(Team t : maxWins){
			LinkedHashMap<String, String> winsMap = new LinkedHashMap<>();
			parseTeamToMap(t, winsMap);
			maxWinsList.add(winsMap);
		}

		return maxWinsList;
	}

	@Override
	public List<LinkedHashMap<String, String>> getMaxLossesTeam() {
		List<Team> maxLosses = iTeamsDao.findAll().stream()
				.filter(team -> team.getLosses()>=0)
				.collect(Collectors.groupingBy(
						Team::getLosses,
						TreeMap::new,
						Collectors.toList()))
				.lastEntry()
				.getValue();

		List<LinkedHashMap<String, String>> maxLossesList = new ArrayList<>();

		for(Team t : maxLosses){
			LinkedHashMap<String, String> lossesMap = new LinkedHashMap<>();
			parseTeamToMap(t, lossesMap);
			maxLossesList.add(lossesMap);
		}

		return maxLossesList;

	}

	@Override
	public List<LinkedHashMap<String, String>> getMaxDrawsTeam() {
		List<Team> maxDraws = iTeamsDao.findAll().stream()
				.filter(team -> team.getDraws()>=0)
				.collect(Collectors.groupingBy(
						Team::getDraws,
						TreeMap::new,
						Collectors.toList()))
				.lastEntry()
				.getValue();

		List<LinkedHashMap<String, String>> maxDrawsList = new ArrayList<>();

		for(Team t : maxDraws){
			LinkedHashMap<String, String> drawsMap = new LinkedHashMap<>();
			parseTeamToMap(t, drawsMap);
			maxDrawsList.add(drawsMap);
		}

		return maxDrawsList;
	}

	public LinkedHashMap<String, String> parseTeamToMap(Team t, LinkedHashMap<String,String> map) {
		map.put("id", t.getId().toString());
		map.put("name", t.getName());
		map.put("wins", Integer.toString(t.getWins()));
		map.put("losses", Integer.toString(t.getLosses()));
		map.put("draws", Integer.toString(t.getDraws()));

		return map;
	}

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
