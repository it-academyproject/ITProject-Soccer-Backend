package com.itacademy.soccer.controller;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;
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
	public HashMap<String,Object> getSalesStatsOK(@PathVariable Long n_day){			
		HashMap<String,Object> map = new HashMap<>();		
		
		try {			
			List<Sale>  sale_Bids =  statServiceImpl.getSalesStats(n_day, true); 		
			
			map.put("Sales : ", sale_Bids.size());		
			map.put("Total of " + sale_Bids.size()+ " Sales with Bids for last "+ n_day +" days", sale_Bids);				

		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}
				
		return map;
	}
	
	
	
	@GetMapping("sales/bids/days/{n_day}/failed")	
	public HashMap<String,Object> getSalesStatsKO(@PathVariable Long n_day){			
		HashMap<String,Object> map = new HashMap<>();
		
		try {						
			List<Sale>  sale_Bids =  statServiceImpl.getSalesStats(n_day, false); 		
			
			map.put("Sales  ", sale_Bids.size());		
			map.put("There the follow Sales without Bids for last "+ n_day +" days", sale_Bids);				

		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");

		}
		
		return map;
	}
	

	@GetMapping("sales/bids/average")	
	public HashMap<Object,Object> getAverageBidsperSales(){			
		
		HashMap<Object,Object> map = new HashMap<>();		
		
		try {
			
			int iTotalBids = bidServiceImpl.getAllBids().size();			
			List<Sale> allSales = saleServiceImpl.listAllSales();			
			HashMap<Sale,Object> ratio = statServiceImpl.getAverageBidsperSales(allSales);	
			
			map.put("total of BIDS is  ", iTotalBids);		
			
			Set keys = ratio.keySet();
		  	for (Iterator i = keys.iterator(); i.hasNext(); ) {		
		  		
		          Sale key = (Sale) i.next();		          
		          map.put(key.getId(), ratio.get(key));
		  	}
			
		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");

		}
				
		return map;
	}
	
	
	@GetMapping("sales/bids/max")	
	public HashMap<Object,Object> getMaxBidsperSales(){					
		
		HashMap<Object,Object> map = new HashMap<>();	
		
		try {
			
			List<Sale> allSales = saleServiceImpl.listAllSales();			
			HashMap<Object,Integer> countBidsSales = statServiceImpl.getBidsperSales(allSales);					
			HashMap<Object, Integer> sortedByCount = statServiceImpl.sortMapbyValue(countBidsSales);	
			
			Set keys = sortedByCount.keySet();
		  	for (Iterator i = keys.iterator(); i.hasNext(); ) {		
		  		
		          Sale key = (Sale) i.next();		          
		          map.put(key.getId(), sortedByCount.get(key));
		  	}
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");

		}
		return map;
	}

	@GetMapping("sales/bids/buyer/most")	
	public HashMap<Object,Object> getMostBuyer(){	
		
		HashMap<Object,Object> map = new HashMap<>();				
		try {
			List<Bid> allBids = bidServiceImpl.getAllBids();
			HashMap<Object, Integer> countBidsTeams = statServiceImpl.getBidsperTeams(allBids);		
			
			HashMap<Object, Integer>  mostBuyer = statServiceImpl.getMostBuyer(countBidsTeams);		
		  
			map.put( "The Most Teams Buyer " , " with numbers of BIDS" );						
			
			for (Map.Entry<Object, Integer> buyer : mostBuyer.entrySet()) {
			    
				Team team = (Team) buyer.getKey();
			    Integer value = buyer.getValue();			
			    map.put( team.getId(), value );		
			}
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}
		return map;
	}
	
	
	@GetMapping("sales/bids/players/seller/most")	
	public HashMap<Object,Object> getMostSeller(){
		
		HashMap<Object,Object> map = new HashMap<>();		
		try {
			
			List<Player> allPlayers = playerServiceImpl.playerList();
			
			HashMap<Object, Integer> countBidsSeller = statServiceImpl.getBidsperPlayers(allPlayers);
			
			HashMap<Object, Integer>  mostSeller = statServiceImpl.getMostSeller(countBidsSeller);		
			
			System.out.println("hahmap seller"+ mostSeller);
					
			map.put( "The Most Player Seller " , " with numbers of BIDS" );				
			
			for (Map.Entry<Object, Integer> seller : mostSeller.entrySet()) {
			    
				Player player = (Player) seller.getKey();
			    Integer value = seller.getValue();			
			    map.put( player.getAka(), value );		
			}			
	
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");

		}
		return map;
	}
	
}
