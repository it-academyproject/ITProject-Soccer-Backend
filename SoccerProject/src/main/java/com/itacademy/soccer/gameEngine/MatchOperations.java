package com.itacademy.soccer.gameEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IMatchActionsDAO;
import com.itacademy.soccer.dao.IMatchDAO;
import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.MatchActions;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.PlayerActions;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.serializable.PlayerMatchId;
import com.itacademy.soccer.gameEngine.interfaces.IMatchOperations;

@Service
public class MatchOperations implements IMatchOperations {

	@Autowired
	IMatchDAO iMatchDAO;

	@Qualifier("IPlayerActionsDAO")
	@Autowired
	IPlayerActionsDAO iPlayerActionsDAO;
	
	@Autowired
	IMatchActionsDAO iMatchActionsDAO;
	
	public Team generateKickOff(Match match) {
		
		System.out.println("************************");
		System.out.println("*  GENERATING KICKOFF  *");
		System.out.println("************************");
		
		List<PlayerActions> localPlayersAct = getMatchPlayersByTeam(match,match.getTeam_local());
		List<PlayerActions> visitorPlayersAct = getMatchPlayersByTeam(match,match.getTeam_visitor());

		printListMatchPlayers(localPlayersAct, visitorPlayersAct);
		
		double LP = sumTeamPass(localPlayersAct);
		double VP = sumTeamPass(visitorPlayersAct);
		
		double localPossibility=0;
		double visitorPossibility=0;
		
		if (LP!=0 && VP!=0) { 
			localPossibility = LP/(LP+VP);
			visitorPossibility = VP/(LP+VP);
		}
		
		System.out.println(">>> LOCAL POSSIBILITY = [" + localPossibility +"] <<<");
		System.out.println(">>> VISITOR POSSIBILITY = [" + visitorPossibility +"] <<<");

		Team kickOffTeam = getRandomKickOff(match, localPossibility, visitorPossibility);
		
		return kickOffTeam;
	}
		
	private Team getRandomKickOff(Match match, double localPossibility, double visitorPossibility) {
		double randomNumber = Math.random();
		
		if (visitorPossibility==0) return match.getTeam_local();
		if (localPossibility==0) return match.getTeam_visitor();
		
		if (randomNumber <= localPossibility) {
			return match.getTeam_local();
		}else {
			return match.getTeam_visitor();
		}
	}
	
	public MatchActions saveKickOff(Match match, Team team) {
		
		MatchActions updatedMatchActions = null;
		
		try {
			
			MatchActions matchActions = match.getMatch_actions();
			matchActions.setKickOff(team);
			
			updatedMatchActions = iMatchActionsDAO.save(matchActions);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return updatedMatchActions;
	}
	
	// Returns the subset of team players playing the match passed by parameter
	// Returns a list of PlayerActions. Each PlayerActions has a relationship with one Player
	// and one Match
	private List<PlayerActions> getMatchPlayersByTeam(Match match, Team team){
		
		List<Player> allTeamPlayers = team.getPlayersList();
		List<PlayerActions> listMatchPlayerActionsByTeam = new ArrayList<>();
		
		for( Player player : allTeamPlayers ) {
			
			PlayerMatchId playerMatchId = new PlayerMatchId();
			playerMatchId.setMatchId(match.getId());
			playerMatchId.setPlayerId(player.getId());
			
//			System.out.println("Match="+match.getId());
//			System.out.println("Player="+player.getId());
			
			Optional<PlayerActions> optionalPlayerAction = iPlayerActionsDAO.findById(playerMatchId);
			
			//if (optionalPlayerAction.isEmpty()==false) { //Available method isEmpty from Java 11
			if (optionalPlayerAction.isPresent()) { //Java 8
				PlayerActions playerAction = optionalPlayerAction.get();
				listMatchPlayerActionsByTeam.add(playerAction);
			}

		}
				
		return listMatchPlayerActionsByTeam;
	}
	
	private int sumTeamPass(List<PlayerActions> listPlayerActions) {
		
		int total = 0;
		
		for ( PlayerActions pa : listPlayerActions ) {
			total = total + pa.getPlayer().getPass();
		}
		
		return total;
	}
	
	private void printListMatchPlayers(List<PlayerActions> localPA, List<PlayerActions> visitorPA) {
		
		System.out.println("-------------");
		System.out.println("LOCAL_PLAYERS");
		System.out.println("-------------");
		
		printListPlayerActions(localPA);
	
		System.out.println("---------------");
		System.out.println("VISITOR_PLAYERS");
		System.out.println("---------------");
		
		printListPlayerActions(visitorPA);
	}
		
	//Method for testing and print in console
	private void printListPlayerActions(List<PlayerActions> listPlayerActions) {
		
		for(PlayerActions pa : listPlayerActions ) {
			
			PlayerMatchId playerMatchId = pa.getId();
			Player player = pa.getPlayer();
			
			System.out.println("Player [id="+player.getId()+", name="+player.getName()+
							   ", pass="+player.getPass()+"]");
			
		}
	}
}
