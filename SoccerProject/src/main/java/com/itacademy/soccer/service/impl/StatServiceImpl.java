package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.ISaleService;
import com.itacademy.soccer.service.IStatService;

@Service
public class StatServiceImpl implements IStatService {
	
	@Autowired
	BidServiceImpl bidServiceImpl;
	
	@Autowired
	TeamServiceImpl teamServiceImpl;
	
	@Autowired
	PlayerServiceImpl playerServiceImpl;
	
	@Autowired
	SaleServiceImpl saleServiceImpl;

	@Override	
	public Date initDateInterval(Long id) {
		Date now = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(now);		
		c.add(Calendar.DATE, (int) -id);		

		return  c.getTime();
	
	}

	
	@Override
	public List<Sale> getSalesStats(Long id, boolean state) {			
		
		Date initial_date = initDateInterval(id);			
		List<Sale> all_sales_period = saleServiceImpl.saleListBetweenDates(initial_date);		
		
		List<Sale> sale_no_bids= new ArrayList<>();	
		List<Sale> sale_yes_bids= new ArrayList<>();		
		List<Sale> result_sale= new ArrayList<>();	
		List<Bid> bids_sale = new ArrayList<>();	
		
		result_sale=null;
		
		for (Sale sale : all_sales_period) {			
			
			bids_sale = bidServiceImpl.getBidsBySale(sale);		
			
			if (bids_sale.size()>0) {					
				sale_yes_bids.add(sale);				
			}else {				
				sale_no_bids.add(sale);
			}
			
		}
				
		
		if (state== true) {			
			result_sale = sale_yes_bids;				
		}else {			
			result_sale = sale_no_bids;		
		}
				
		
		return result_sale;
	}
	
	
	@Override
	public HashMap<Object, Integer> sortMapbyValue(HashMap<Object, Integer> sortMap) {
		
		HashMap<Object, Integer> sortMapValue = sortMap.entrySet()
        .stream()
        .sorted((Map.Entry.<Object, Integer>comparingByValue().reversed()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return sortMapValue;
	}

		
	@Override
	public HashMap<Sale, Object> getAverageBidsperSales(List<Sale> allSales){
		
		HashMap<Sale,Object> ratio = new HashMap<>();
		
		int iTotalBids = bidServiceImpl.getAllBids().size();
		double isales,iratio;					

		
		for (Sale sale : allSales) {
			
			List<Bid> bidsSale = bidServiceImpl.getBidsBySale(sale);	
			
			if(bidsSale.size()<0 && iTotalBids !=0) {
				ratio.put(sale , 0);
				
			}else {
				
				isales =bidsSale.size();
				iratio=isales/iTotalBids;			
				ratio.put(sale, iratio);
			}			
		}
		
		return ratio;
	}

	@Override
	public HashMap<Object, Integer> getBidsperSales(List<Sale> allSales) {
		
		HashMap<Object, Integer> countBidsSales = new HashMap<>();		
		
		for (Sale sale : allSales) {	
			
			List<Bid> bidsSale = bidServiceImpl.getBidsBySale(sale);	
			
			if(bidsSale.size()<0) {		
				
				countBidsSales.put(sale , 0);		
				
			}else {			
				
				countBidsSales.put(sale, bidsSale.size());
			}			
		}
		return countBidsSales;
	}

	

	@Override
	public HashMap<Object, Integer> getBidsperTeams(List<Bid> allBids) {		
		HashMap<Object,Integer> countBidsTeams = new HashMap<>();		
		List<Team> allTeams = teamServiceImpl.getAllTeams();		
		for (Team team : allTeams) {			
			List<Bid> bidsTeam = bidServiceImpl.getBidsByTeams(team);			
			if(bidsTeam.size()<0) {				
				countBidsTeams.put(team , 0);						
			}else {								
				countBidsTeams.put(team, bidsTeam.size());
			}		
		}			
		return countBidsTeams;
	}
	
	@Override
	public HashMap<Object, Integer> getBidsperPlayers(List<Player> allPlayers) {
		
		HashMap<Object, Integer> bidsPlayer=  new HashMap<>();
		
		List<Bid> allBids = bidServiceImpl.getAllBids();		
		List<Sale> allSales = saleServiceImpl.listAllSales();
	
		HashMap<Object, Integer> countBidsSales = getBidsperSales(allSales);   //has con venta y numBids.
		
		System.out.println(countBidsSales);
			
		for (Map.Entry<Object, Integer> entry : countBidsSales.entrySet()) {
		    
		    for (Sale sale : allSales) {
		    	
		    	if (sale.equals(entry.getKey())) { //si el id de la lista sales = al id del mapa es que es un registro con bbids
		    		
		    		bidsPlayer.put(sale.getPlayer(), entry.getValue());
		    	}
			}		  
		}
	
		return bidsPlayer;	
	}
	

	@Override
	public HashMap<Object, Integer> getMostBuyer(HashMap<Object, Integer> countBidsTeams) {
	
		LinkedHashMap<Object, Integer> mostTeamBids  = new LinkedHashMap<Object, Integer>();		
		HashMap<Object, Integer> sortedByBids= sortMapbyValue(countBidsTeams);  
		Integer bigvalue = (Integer) sortedByBids.entrySet().stream().max((a,b) -> a.getValue().compareTo(b.getValue())).get().getValue();
		
		for (Map.Entry<Object, Integer> entry : sortedByBids.entrySet()) {
		    
		    
		    if(entry.getValue().equals(bigvalue)) {
			    mostTeamBids.put(entry.getKey(), entry.getValue());				
		    }
		}
		
	    return mostTeamBids;
	}


	@Override
	public HashMap<Object, Integer> getMostSeller(HashMap<Object, Integer> bidsPlayer) {
	
		LinkedHashMap<Object, Integer> mostPlayerBids  = new LinkedHashMap<Object, Integer>();		
		HashMap<Object, Integer> sortedByBids = sortMapbyValue(bidsPlayer);
	
		Integer bigvalue = (Integer) sortedByBids.entrySet().stream().max((a,b) -> a.getValue().compareTo(b.getValue())).get().getValue();
		
		System.out.println(bidsPlayer);
		
		for (Map.Entry<Object, Integer> entry : bidsPlayer.entrySet()) {
		    
		    if(entry.getValue().equals(bigvalue)) {
		    	mostPlayerBids.put(entry.getKey(), entry.getValue());				
		    }
		}
		
	    return mostPlayerBids;
	}
	
}
