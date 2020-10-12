package com.itacademy.soccer.controller;



import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.itacademy.soccer.controller.json.StatSaleJson;
import com.itacademy.soccer.controller.json.StatTeamJson;


import com.itacademy.soccer.dto.Sale;

import com.itacademy.soccer.service.impl.BidServiceImpl;
import com.itacademy.soccer.service.impl.PlayerServiceImpl;
import com.itacademy.soccer.service.impl.SaleServiceImpl;
import com.itacademy.soccer.service.impl.StatServiceImpl;
import com.itacademy.soccer.service.impl.TeamServiceImpl;


@RequestMapping("/api/stats")
@RestController
public class StatsController {
	
	@Autowired
	BidServiceImpl bidServiceImpl;
	@Autowired
	SaleServiceImpl saleServiceImpl;
	@Autowired
	TeamServiceImpl teamServiceImpl;	
	@Autowired
	StatServiceImpl statServiceImpl;	
	@Autowired
	PlayerServiceImpl playerServiceImpl;
	
	
	@GetMapping("sales/bids/days/{n_day}/successful")
	public LinkedHashMap<String,Object> getSalesStatsOK(@PathVariable Long n_day){			
		LinkedHashMap<String,Object> map = new LinkedHashMap<>();		
		
		try {			
			List<Sale>  sale_Bids =  statServiceImpl.getSalesStats(n_day, true); 		
			
			map.put("success", true);			
			map.put("Total_sales_closed", sale_Bids.size());		
			map.put("From_date", statServiceImpl.initDateInterval(n_day) );				
			map.put("Sale" , sale_Bids);

		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}
				
		return map;
	}
	
	
	
	@GetMapping("sales/bids/days/{n_day}/failed")	
	public LinkedHashMap<String,Object> getSalesStatsKO(@PathVariable Long n_day){			
		LinkedHashMap<String,Object> map = new LinkedHashMap<>();
		
		try {						
			List<Sale>  sale_Bids =  statServiceImpl.getSalesStats(n_day, false); 		
			
			map.put("success", true);			
			map.put("Total_sales_closed", sale_Bids.size());		
			map.put("From_date", statServiceImpl.initDateInterval(n_day) );		
			map.put("Sale" , sale_Bids);
			
		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}		
		return map;
	}
	

	@GetMapping("sales/bids/average")	
	public LinkedHashMap<Object,Object> getAverageBidsperSales(){				
		LinkedHashMap<Object,Object> map = new LinkedHashMap<>();	
		
		try {
			
			List<StatSaleJson>list_average_stats =  statServiceImpl.getBidsperSalesJson();	
			double i_total_bids = bidServiceImpl.getAllBidsClosed().size();	
			double i_total_sales = saleServiceImpl.listAllSalesClosed().size();	
			
			double i_average= statServiceImpl.average(i_total_bids,i_total_sales);	
			double i_total_sales_bids=statServiceImpl.getTotalSalesBid(list_average_stats) ;			
			
			map.put("Total_bids_closed", (int) i_total_bids);	
			map.put("Total_sales_closed", (int) i_total_sales);
			map.put("Total_sales_bids", (int) i_total_sales_bids);			
			map.put("Average", i_average);
			map.put("Sales",list_average_stats );	
	      	map.put("success", true);	   			
		} catch (Exception e) {			
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}	
		return map;
	}
	
	
	
	@GetMapping("sales/bids/max")	
	public LinkedHashMap<Object,Object> getMaxBidsperSales(){			
		LinkedHashMap<Object,Object> map = new LinkedHashMap<>();	
		
		try {			
			List<StatSaleJson> list_count_bids_sale_stats =  statServiceImpl.getBidsperSalesJson();				
			List<StatSaleJson>  list_stat_max_bids = statServiceImpl.maximumSaleBids();		
			
			double i_total_bids = bidServiceImpl.getAllBidsClosed().size();				
			double i_total_sales = saleServiceImpl.listAllSalesClosed().size();			
			double i_max_bids=list_count_bids_sale_stats.get(0).getTotal_bids();			
			
			map.put("Total_bids_closed", (int) i_total_bids);	
			map.put("Total_sales_closed", (int) i_total_sales);			
			map.put("Maximum_bid", (int) i_max_bids);			
			map.put("Maximum_sale", list_stat_max_bids);			
			map.put("Sales",list_count_bids_sale_stats );	
	      	map.put("success", true);			
		} catch (Exception e) {			
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}	
		return map;
	}
	
	
	@GetMapping("sales/bids/buyer/most")	
	public LinkedHashMap<Object,Object> getMostBuyer(){				
		LinkedHashMap<Object,Object> map = new LinkedHashMap<>();		
		
		try {
			
				List<StatTeamJson> list_count_bids_team_stats = statServiceImpl.getBidsperTeamsJson();
				List<StatTeamJson>  list_stat_max_bids_price = statServiceImpl.getMostBuyer();			
				double i_total_sales = saleServiceImpl.listAllSalesClosed().size();			
				
				double i_total_bids = bidServiceImpl.getAllBidsClosed().size();					
				Collections.sort(list_count_bids_team_stats, Comparator.comparing(StatTeamJson::getMax_price).reversed());		
				double i_max_bids=list_count_bids_team_stats.get(0).getMax_price();			
			
				map.put("Total_bids_closed", (int) i_total_bids);	
				map.put("Total_sales_closed", (int) i_total_sales);					
				map.put("Maximum_price_bids", i_max_bids);			
				map.put("Most_buyer", list_stat_max_bids_price);			
				map.put("Bids",list_count_bids_team_stats );		      
			
				map.put("success", true);	
				
			} catch (Exception e) {
				
				map.put("success", false);
				map.put("message", "There were no sales in that period of time, sorry!");
			}
		
		return map;
	}
	
	@GetMapping("sales/bids/buyer/most/bidder")	
	public LinkedHashMap<Object,Object> getMostBuyerBidder(){				
		LinkedHashMap<Object,Object> map = new LinkedHashMap<>();		
		
		try {		
			List<StatTeamJson> list_count_bids_team_stats = statServiceImpl.getBidsperTeamsJson();
			List<StatTeamJson>  list_stat_max_bids = statServiceImpl.getMostBuyerBids();			
			
			double i_total_bids = bidServiceImpl.getAllBidsClosed().size();					
			Collections.sort(list_count_bids_team_stats, Comparator.comparing(StatTeamJson::getTotal_bids).reversed());		
			double i_max_bids=list_count_bids_team_stats.get(0).getTotal_bids();			
			
			map.put("Total_bids", (int) i_total_bids);	
			map.put("Maximum_bids", (int) i_max_bids);			
			map.put("Most_bidder", list_stat_max_bids);			
			map.put("Bids",list_count_bids_team_stats );		      
		    map.put("success", true);		
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}
		return map;
	}
	
	@GetMapping("sales/bids/seller/most")	
	public LinkedHashMap<Object,Object> getMostSeller(){
		LinkedHashMap<Object,Object> map = new LinkedHashMap<>();		
		
		try {				      
		    map.put("success", true);		
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}
	
		return map;
	}
	
}
