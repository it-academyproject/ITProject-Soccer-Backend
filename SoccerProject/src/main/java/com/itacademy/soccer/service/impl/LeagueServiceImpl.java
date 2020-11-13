package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.ILeagueDAO;
import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dto.League;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.ILeagueService;

@Service
public class LeagueServiceImpl implements ILeagueService {
	
	@Autowired
	private ILeagueDAO iLeagueDAO;
	@Autowired
	private ITeamDAO iTeamsDao;

	@Override
	public List<League> showAllLeagues() {
		
		return iLeagueDAO.findAll();
	}
	
	@Override
	public League getOneLeagueById(Long id) {		
		return iLeagueDAO.findById(id).get();
	}

	
	@Override
	public League updateLeague(League league) {
		
		Optional<League> leagueToUpdate =iLeagueDAO.findById(league.getId());
		
		try {
		
			if(leagueToUpdate.isPresent() && leagueNameAvailable(league.getId(), league.getName())==true) {
				
				if(validMaxParticipants(leagueToUpdate.get(), league.getMaxParticipants()) == true) {
				
					if (league.getName() != null) leagueToUpdate.get().setName(league.getName());
					if (league.getBeginDate() != null) leagueToUpdate.get().setBeginDate(league.getBeginDate());
					if (league.getEndingDate() != null) leagueToUpdate.get().setEndingDate(league.getEndingDate());
					if ((Integer)league.getMaxParticipants() != null) leagueToUpdate.get().setMaxParticipants(league.getMaxParticipants());
					if ((Integer)league.getNumberRounds() != null) leagueToUpdate.get().setNumberRounds(league.getNumberRounds());
					if (league.getDivision() != null) leagueToUpdate.get().setDivision(league.getDivision());
		
					return iLeagueDAO.save(leagueToUpdate.get());
				
				}else {
					
					System.out.println("The max_participants should be greater than the teams that the league already has");
					return null;
				}
	
			}else {
				
				System.out.println("The name of the league alredy exists, change it");
				return null;
			}
		
		}catch (Exception e){
			e.printStackTrace();
		}	
		
		return null;
	}

	
	@Override
	public League createLeague(League league) {		
		return iLeagueDAO.save(league);
	}

	@Override
	public void deleteLeagueById(Long id) {
		iLeagueDAO.deleteById(id);
		
	}

	@Override
	public List<Team> showTeamsByLeague(Long id){
	
	  	List<Team> allTeams = iTeamsDao.findAll();   	
	  	List<Team> teamsByLeague = new ArrayList<>();
    	
    	for (Team team : allTeams) {    	
    		
    		if (team.getLeague() != null && team.getLeague().getId() == id) {
    			teamsByLeague.add(team);
    		}
		}
	   
    	return teamsByLeague;		
	}

	
	@Override
	public Team insertTeamintoLeague(Long leagueId, Team teamSelected) {
		
		Optional<League> leagueToInsert;

		try{
			leagueToInsert=iLeagueDAO.findById(leagueId);
			
			if( spaceInLeague(leagueToInsert.get()) ) {
				
				if(teamSelected.getLeague() == null || teamSelected.getLeague().getId() != leagueId) {			
					teamSelected.setLeague(leagueToInsert.get());
					return iTeamsDao.save(teamSelected);
	
				}else {
					System.out.println("The team " +teamSelected.getName()+ " is already in the league " +teamSelected.getLeague().getName()+ " (id: " +teamSelected.getLeague().getId()+ ")");
					return null;
				}
				
			}else {		
				System.out.println("The league is full");
				return null;
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}	
		
		return null;
		
	}
	
	
	//UTILITIES METHODS
	
    // Validates name of a league already exists 
    public boolean leagueNameAvailable(long id, String name) {
    	
    	boolean leagueNameAvailable = true;
    
		League leagueToSeach = iLeagueDAO.findByName(name);
		
		//if there is a league with that name but is not the one we want to update
		if(leagueToSeach != null && leagueToSeach.getId() != id) { leagueNameAvailable= false; }

    	return leagueNameAvailable;
    }
    
    
    // Validate if a new max_participants is ok for a league
    public boolean validMaxParticipants (League league, int maxParticipants) {
    	
    	boolean validMaxParticpants = true;
    	    	
    	int teamsInLeague = iTeamsDao.findByleagueId(league.getId()).size();
    	
    	if (maxParticipants < teamsInLeague) { validMaxParticpants = false; }

    	return validMaxParticpants;

    }
    
    // Validate if there is space in a league to insert a new team
    public boolean spaceInLeague (League league) {
    	
    	boolean spaceInLeague = true;
    	    	
    	int teamsInLeague = iTeamsDao.findByleagueId(league.getId()).size();
    	
    	if (league.getMaxParticipants() <= teamsInLeague) { spaceInLeague = false; }

    	return spaceInLeague;

    }
}
