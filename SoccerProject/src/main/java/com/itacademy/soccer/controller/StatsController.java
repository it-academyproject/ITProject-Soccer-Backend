package com.itacademy.soccer.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itacademy.soccer.controller.json.BidJson;
import com.itacademy.soccer.dto.Bid;
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
	

	//B29 TODO List Sales stadistics
	
	
	@GetMapping("sales/bids/period/{id}/successful")
	public HashMap<String,Object> getSalesStats(@PathVariable Long id){	
		
		
		HashMap<String,Object> map = new HashMap<>();
		
		//This map stores the number of bids per active sales
		HashMap<Sale,Integer> salestats = new HashMap<>();
		
		
		try {

			Date today = new Date();			
			Date period;
			Long days = id;		
			
			// convert date to calendar to get period of time asked			
			period = statServiceImpl.initDateInterval(today,days);			
	
					
			List<Sale> allSalesPeriod = saleServiceImpl.saleListBetweenDates(period,today);

			// get the number of bid per sale in hashmap called salestats with sale as key and number of bids as value, if there are zero bids then the sale failed
			for (Sale tempsale : allSalesPeriod) {
				
				List<Bid> bidspersale = bidServiceImpl.getBidsBySale(tempsale);						
				salestats.put(tempsale,bidspersale.size());  // Total Hashmap Sale -- Bids 
				
			}

			// create a TreeMap to get the frequency a value ( number of bids ) appears in the salestats hashmap
			NavigableMap<Integer, Integer> bidscounts = new TreeMap<Integer, Integer>();
	
			for ( Integer b : salestats.values()){
				
				int bidnumber =  bidscounts.get(b) == null ? 0 : bidscounts.get(b);				
				bidscounts.put(b, bidnumber +1);  // Total Treehmap  Bid -- NumBids 
			}
	
			
			//get the total of bids (totalbids) my multiplying the key and value of the bidscounts TreeMap
			int totalbids = 0;
			for ( int d = 0; d <= bidscounts.lastKey(); d++) {
				
				if (bidscounts.get(d) != null) {
					
					totalbids = totalbids + ( d * bidscounts.get(d) );
					
				}
				
			}
	
			int totalsales = salestats.size();
		
			double averagebids = (double) totalbids / (double ) totalsales;	
			
			
			
			//B29 TODO they are not imlemented yet the following stadistics
			//- Most buyer Team
			//- Most seller Team
			// Also the stadistics should be repeated for the last month, maybe it should extracted the code from
			// the controller into a separate class to reuse the stadistics calculation source.

			if(allSalesPeriod.size() > 0) {
				
				map.put("total number of succesful sales, those WITH bids", totalsales);
			//	map.put("total number of succesful sales, those WITH bids", salestats.size()-bidscounts.get(0));
			//	map.put("total number of failed sales, those WITHOUT bids", bidscounts.get(0));
				map.put("average number of bids per sale", averagebids);
				map.put("maximum number of bids a particular sales has got", bidscounts.lastKey());
				map.put("success", true);
				map.put("message", "these are the stadistics for last "+ days +" days");


				map.put("all sales of last week", allSalesPeriod);
				map.put("message", "get all sales of last "+ days +" days");
			}else {
				map.put("success", false);
				map.put("message", "There were no sales in that period of time, sorry!");

				//throw new Exception();
			}
			
			
			
			
			

		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}

		return map;
	}


}
