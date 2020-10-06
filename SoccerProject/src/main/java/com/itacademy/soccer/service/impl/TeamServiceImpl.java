/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	ITeamDAO iTeamsDao;

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

	public LinkedHashMap<String, String> parseTeamToMap(Team t, LinkedHashMap<String,String> map){
		map.put("id", t.getId().toString());
		map.put("name", t.getName());
		map.put("wins", Integer.toString(t.getWins()));
		map.put("losses", Integer.toString(t.getLosses()));
		map.put("draws", Integer.toString(t.getDraws()));

		return map;
	}

}
