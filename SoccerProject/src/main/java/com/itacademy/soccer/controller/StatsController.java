package com.itacademy.soccer.controller;

import java.util.ArrayList;
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
import com.itacademy.soccer.service.ISaleService;
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
	

}
