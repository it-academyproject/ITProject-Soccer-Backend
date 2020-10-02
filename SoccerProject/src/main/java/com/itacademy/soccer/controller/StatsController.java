package com.itacademy.soccer.controller;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.service.impl.BidServiceImpl;
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
	
		
	@GetMapping("sales/bids/days/{id}/successful")
	public HashMap<String,Object> getSalesStatsOK(@PathVariable Long id){			
		
		HashMap<String,Object> map = new HashMap<>();		
		
		try {
			
			Date initialDate = statServiceImpl.initDateInterval(id);			
			List<Sale> allSalesPeriod = saleServiceImpl.saleListBetweenDates(initialDate);				
			HashMap<Object,Object> salewithBids =  statServiceImpl.getSalesStatsOK(allSalesPeriod); 		
			
			
			map.put("total of Sale with BIDS is : ", salewithBids.size());		
			map.put("Total of " +salewithBids.size()+ " Sales with Bids for last "+ id +" days", salewithBids);				

		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}
				
		return map;
	}
	
	@GetMapping("sales/bids/days/{id}/failed")	
	public HashMap<String,Object> getSalesStatsKO(@PathVariable Long id){	
		
		HashMap<String,Object> map = new HashMap<>();
		
		try {
			
			Date initialDate = statServiceImpl.initDateInterval(id);			
			List<Sale> allSalesPeriod = saleServiceImpl.saleListBetweenDates(initialDate);				
			List<Sale> saleNoBids= statServiceImpl.getSalesStatsKO(allSalesPeriod);
			
			map.put("total of Sale without BIDS is  ", saleNoBids.size());		
			map.put("There the follow Sales without Bids for last "+ id +" days", saleNoBids);				

		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");

		}
		
		return map;
	}
	

	@GetMapping("sales/bids/average")	
	public HashMap<String,Object> getAverageBidsperSales(){			
		
		HashMap<String,Object> map = new HashMap<>();		
		
		try {
			
			int iTotalBids = bidServiceImpl.getAllBids().size();			
			List<Sale> allSales = saleServiceImpl.listAllSales();			
			HashMap<Object,Object> ratio = statServiceImpl.getAverageBidsperSales(allSales);	
			
			map.put("total of BIDS is  ", iTotalBids);		
			map.put("Average Bids for Sales ", ratio);		
			
			
		} catch (Exception e) {
			
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");

		}
				
		return map;
	}
	
	
	@GetMapping("sales/bids/max")	
	public HashMap<String,Object> getMaxBidsperSales(){			
		
		
		HashMap<String,Object> map = new HashMap<>();		
		try {
			
			List<Sale> allSales = saleServiceImpl.listAllSales();			
			HashMap<Object,Integer> countBidsSales = statServiceImpl.getBidsperSales(allSales);	
			
			Map<Object, Integer> sortedByCount = countBidsSales.entrySet()
		                .stream()
		                .sorted((Map.Entry.<Object, Integer>comparingByValue().reversed()))
		                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	   
			map.put("maximum sales bids  ", sortedByCount);		
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");

		}
		return map;
	}

}
