package com.itacademy.soccer.service;

import java.util.*;


import com.itacademy.soccer.controller.json.StatSaleJson;
import com.itacademy.soccer.controller.json.StatTeamJson;

import com.itacademy.soccer.dto.Sale;


public interface IStatService {

	// AUXILIARS METHODS
	

	public Date initDateInterval(Long id);

	public HashMap<Object, Integer> sortMapbyValue(HashMap<Object, Integer> sortMap);

	// AVERAGE BIDS - SALES

	public double average(double i_total_bids, double i_total_sales);
	// MAX BID

	public double getTotalSalesBid(List<StatSaleJson> list_average_stats);	

	
	// SUCCESS OR FAIL SALES

	public List<Sale> getSalesStats(Long id, boolean state);
	
	// MAXIMUM BIDS - SALES

	public List<StatSaleJson> getBidsperSalesJson();

	public List<StatSaleJson> maximumSaleBids();
	
	// MOST BUYER - TEAMS

	public List<StatTeamJson> getBidsperTeamsJson();		

	public List<StatTeamJson> getMostBuyer();

	public List<StatTeamJson> getMostBuyerBids();
	
}
