package com.itacademy.soccer.service;

import java.util.*;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;

public interface IStatService {

	Date initDateInterval(Long id);

	List<Sale> getSalesStatsKO(List<Sale> allSalesPeriod);

	HashMap<Object, Object> getSalesStatsOK(List<Sale> allSalesPeriod);

	HashMap<Object, Object> getAverageBidsperSales(List<Sale> allSales);

	HashMap<Object, Integer> getBidsperSales(List<Sale> allSales);

//	Map<Object, Integer> sortMapbyBids(HashMap<Object, Integer> countBidsSales);

	HashMap<Object, Integer> getBidsperTeams(List<Bid> allBids);

	HashMap<Object, Integer> getMostBuyer(HashMap<Object, Integer> countBidsTeams);

	Map<Object, Integer> sortMapbyValue(HashMap<Object, Integer> sortMap);
	
	


}