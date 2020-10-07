package com.itacademy.soccer.service;

import java.util.*;


import com.itacademy.soccer.controller.json.StatSaleJson;
import com.itacademy.soccer.controller.json.StatTeamJson;
import com.itacademy.soccer.dto.Bid;

import com.itacademy.soccer.dto.Sale;


public interface IStatService {

	// AUXILIARS METHODS
	
	Date initDateInterval(Long id);
	HashMap<Object, Integer> sortMapbyValue(HashMap<Object, Integer> sortMap);

	// AVERAGE BIDS - SALES
	double average(double i_total_bids, double i_total_sales);
	// MAX BID
	double getTotalSalesBid(List<StatSaleJson> list_average_stats);	

	
	// SUCCESS OR FAIL SALES
	List<Sale> getSalesStats(Long id, boolean state);
	
	// MAXIMUM BIDS - SALES
	List<StatSaleJson> getBidsperSalesJson();
	List<StatSaleJson> maximumSaleBids();
	
	// MOST BUYER - TEAMS
	List<StatTeamJson> getBidsperTeamsJson();		
	List<StatTeamJson> getMostBuyer();
	
}
