package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dao.ITournamentDAO;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.Tournament;
import com.itacademy.soccer.service.ITournamentService;

@Service
public class TournamentServiceImpl implements ITournamentService {
	
	@Autowired
	private ITournamentDAO iTournamentDAO;
	@Autowired
	private ITeamDAO iTeamDAO;

	@Override
	public List<Tournament> showAllTournaments() {
		
		return iTournamentDAO.findAll();
	}
	
	@Override
	public Tournament getOneTournamentById(Long id) {		
		return iTournamentDAO.findById(id).get();
	}

	
	@Override
	public Tournament updateTournament(Tournament tournament) {
		
		Optional<Tournament> tournamentToUpdate =iTournamentDAO.findById(tournament.getId());
		
		try {
		
			if(tournamentToUpdate.isPresent() && tournamentNameAvailableUpdate(tournament.getId(), tournament.getName())==true) {
				
				if(validNumberRounds(tournamentToUpdate.get(), tournament.getNumberRounds()) == true) {
				
					if (tournament.getName() != null) tournamentToUpdate.get().setName(tournament.getName());
					if (tournament.getBeginDate() != null) tournamentToUpdate.get().setBeginDate(tournament.getBeginDate());
					if (tournament.getEndingDate() != null) tournamentToUpdate.get().setEndingDate(tournament.getEndingDate());
					if ((Integer)tournament.getNumberRounds() != null) tournamentToUpdate.get().setNumberRounds(tournament.getNumberRounds());
		
					return iTournamentDAO.save(tournamentToUpdate.get());
				
				}else {
					
					System.out.println("You're trying to update to a tournament with not enough number of rounds for the teams you have. Please put more rounds");
					return null;
				}
	
			}else {
				
				System.out.println("The name of the tournament alredy exists, change it");
				return null;
			}
		
		}catch (Exception e){
			e.printStackTrace();
		}	
		
		return null;
	}

	
	@Override
	public Tournament createTournament(Tournament tournament) {		
		
		if(tournamentNameAvailableCreate(tournament.getName()) == true) {

			return iTournamentDAO.save(tournament);			
		
		}else {
			
			System.out.println("The name of the tournament is repeated");
			return null;
		}
		
	}

	@Override
	public void deleteTournamentById(Long id) {
		iTournamentDAO.deleteById(id);
		
	}

	@Override
	public List<Team> showTeamsByTournament(Long id){
	
	  	List<Team> allTeams = iTeamDAO.findAll();   	
	  	List<Team> teamsByTournament = new ArrayList<>();
    	
    	for (Team team : allTeams) {    	
    		
    		if (team.getTournament() != null && team.getTournament().getId() == id) {
    			teamsByTournament.add(team);
    		}
		}
	   
    	return teamsByTournament;		
	}

	
	@Override
	public Team insertTeamIntoTournament(Long tournamentId, Team teamSelected) {
		
		Optional<Tournament> tournamentToInsert;

		try{
			tournamentToInsert=iTournamentDAO.findById(tournamentId);
			
			if( spaceInTournament(tournamentToInsert.get()) ) {
				
				if(teamSelected.getTournament() == null || teamSelected.getTournament().getId() != tournamentId) {			
					teamSelected.setTournament(tournamentToInsert.get());
					return iTeamDAO.save(teamSelected);
	
				}else {
					System.out.println("The team " +teamSelected.getName()+ " is already in the tournament " +teamSelected.getTournament().getName()+ " (id: " +teamSelected.getTournament().getId()+ ")");
					return null;
				}
				
			}else {		
				System.out.println("The tournament is full");
				return null;
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}	
		
		return null;
		
	}
	
	
	//UTILITIES METHODS
	
	// Validates name of a tournament already exists in order to create a new tournament
    public boolean tournamentNameAvailableCreate(String name) {
    	
    	boolean tournamentNameAvailable = true;
    		
    	Tournament tournamentToSearch = iTournamentDAO.findByName(name);

		if(tournamentToSearch != null) { tournamentNameAvailable= false; }

    	return tournamentNameAvailable;
    }
    
    
	// Validates name of a tournament already exists in order to update a new tournament
    public boolean tournamentNameAvailableUpdate(long id, String name) {
    	
    	boolean tournamentNameAvailable = true;
    
    	Tournament tournamentToSearch = iTournamentDAO.findByName(name);
		
		//if there is a tournament with that name but is not the one we want to update
		if(tournamentToSearch != null && tournamentToSearch.getId() != id) { tournamentNameAvailable= false; }

    	return tournamentNameAvailable;
    }
    
    
    // Validate if a new numberRounds is ok for a tournament
    public boolean validNumberRounds (Tournament tournament, int numberRounds) {
    	
    	boolean validNumberRounds = true;
    	    	
    	int teamsInTournament = iTeamDAO.findBytournamentId(tournament.getId()).size();
    	
    	if (Math.pow(2, numberRounds) < teamsInTournament) { validNumberRounds = false; }

    	return validNumberRounds;

    }
    
    // Validate if there is space in a tournament to insert a new team
    public boolean spaceInTournament (Tournament tournament) {
    	
    	boolean spaceInTournament = true;
    	    	
    	int teamsInTournament = iTeamDAO.findBytournamentId(tournament.getId()).size();
    	
    	if (tournament.getNeededParticipants() <= teamsInTournament) { spaceInTournament = false; }

    	return spaceInTournament;

    }
}
