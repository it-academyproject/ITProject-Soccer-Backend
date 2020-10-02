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
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.IStatService;

@Service
public class StatServiceImpl implements IStatService {
	
	@Autowired
	BidServiceImpl bidServiceImpl;
	
	@Autowired
	TeamServiceImpl teamServiceImpl;
	

	@Override	
	public Date initDateInterval(Long id) {
		Date now = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(now);		
		c.add(Calendar.DATE, (int) -id);		

		return  c.getTime();
	
	}

	@Override
	public List<Sale> getSalesStatsKO(List<Sale> allSalesPeriod) {		
		List<Sale> saleNoBids= new ArrayList<>();	
		for (Sale sale : allSalesPeriod) {			
			List<Bid> bidsSale = bidServiceImpl.getBidsBySale(sale);				
			if (bidsSale.size()==0) {			
				saleNoBids.add(sale);					
			}
								
		}
		return saleNoBids;
	}

	
	@Override
	public HashMap<Object, Object> getSalesStatsOK(List<Sale> allSalesPeriod) {
		
		HashMap<Object,Object> salewithBids = new HashMap<>();
		for (Sale sale : allSalesPeriod) {
			
			List<Bid> bidsSale = bidServiceImpl.getBidsBySale(sale);	
				
			if (bidsSale.size()>0) {					
				salewithBids.put("Sale " + sale.getId() + " with player " + sale.getPlayer().getAka() + " for Initial Price : " + sale.getInitialPrice() + " Euros has the follows Bids " ,bidsSale);					
			}
								
		}
		return salewithBids;
	}

	
	@Override
	public HashMap<Object, Object> getAverageBidsperSales(List<Sale> allSales){
		
		HashMap<Object,Object> ratio = new HashMap<>();
		
		int iTotalBids = bidServiceImpl.getAllBids().size();
		double isales,iratio;					

		
		for (Sale sale : allSales) {
			
			List<Bid> bidsSale = bidServiceImpl.getBidsBySale(sale);	
			
			if(bidsSale.size()<0 && iTotalBids !=0) {
				
				ratio.put("Sale number " + sale.getId() + " from Player " + sale.getPlayer().getAka(), 0);			
				
			}else {
				
				isales =bidsSale.size();
				iratio=isales/iTotalBids;			
				ratio.put("Sale number " + sale.getId() + " from Player " + sale.getPlayer().getAka(), iratio);
			}			
		}
		
		return ratio;
	}

	@Override
	public HashMap<Object, Integer> getBidsperSales(List<Sale> allSales) {
		
		HashMap<Object,Integer> countBidsSales = new HashMap<>();
		
		for (Sale sale : allSales) {
			
			List<Bid> bidsSale = bidServiceImpl.getBidsBySale(sale);	
			
			if(bidsSale.size()<0) {
				
				countBidsSales.put(sale.getId() , 0);			
				
			}else {				
					
				countBidsSales.put(sale.getId(), bidsSale.size());
			}			
		}
		return countBidsSales;
	}

	@Override
	public Map<Object, Integer> sortMapbyValue(HashMap<Object, Integer> sortMap) {
		
		Map<Object, Integer> sortMapValue = sortMap.entrySet()
        .stream()
        .sorted((Map.Entry.<Object, Integer>comparingByValue().reversed()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return sortMapValue;
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
	public HashMap<Object, Integer> getMostBuyer(HashMap<Object, Integer> countBidsTeams) {
	
		HashMap<Object, Integer> mostTeamBids  = new HashMap<>();
		
		Map<Object, Integer> sortedByTeams= sortMapbyValue(countBidsTeams);		
		
	    Map.Entry<Object, Integer> entry = sortedByTeams.entrySet().iterator().next();
	    
	    //devuelve hash si quieres enseñar el total de bids
	    
	    mostTeamBids.put(entry.getKey(), entry.getValue());
	 
	    
	//	return (Team)  entry.getKey();
	    return mostTeamBids;
	}
	
}
