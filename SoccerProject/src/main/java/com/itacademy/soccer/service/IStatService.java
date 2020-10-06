package com.itacademy.soccer.service;

import java.util.*;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;

public interface IStatService {

	Date initDateInterval(Long id);
	HashMap<Object, Integer> sortMapbyValue(HashMap<Object, Integer> sortMap);
	
	HashMap<Object, Integer> getBidsperSales(List<Sale> allSales);
	HashMap<Object, Integer> getBidsperTeams(List<Bid> allBids);	
	HashMap<Object, Integer> getBidsperPlayers(List<Player> allPlayers);
	
	
	
	HashMap<Sale, Object> getAverageBidsperSales(List<Sale> allSales);	
	

	HashMap<Object, Integer> getMostBuyer(HashMap<Object, Integer> countBidsTeams);
	
	HashMap<Object, Integer> getMostSeller(HashMap<Object, Integer> countBidsPlayers);	
	

	List<Sale> getSalesStats(Long id, boolean state);

	
	


}
