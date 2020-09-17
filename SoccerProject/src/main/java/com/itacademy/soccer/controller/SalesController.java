package com.itacademy.soccer.controller;

import java.util.*;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.service.impl.BidServiceImpl;
import com.itacademy.soccer.service.impl.PlayerServiceImpl;
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

import com.itacademy.soccer.controller.json.SaleJson;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.service.impl.SaleServiceImpl;

@RestController
@RequestMapping("/api")
public class SalesController {

	@Autowired
	SaleServiceImpl saleServiceImpl;

	//B26
	@Autowired
	PlayerServiceImpl playerServiceImpl;

	//B29
	@Autowired
	BidServiceImpl bidServiceImpl;
	
	@GetMapping("/sales")
	public HashMap<String,Object> listAllSales(){
		
		List<Sale> salesList = saleServiceImpl.listAllSales();
		List<SaleJson> jsonList = SaleJson.parseListToJson(salesList);
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("sales", jsonList);
		map.put("success", true);
		map.put("message", "All working perfectly");
		
		return map;
	}
	
	@GetMapping("/sales/{id}")
	public HashMap<String,Object> getSalesById(@PathVariable(name="id") Long salesId){

		HashMap<String,Object> map = new HashMap<>();
		
		try {
			Sale sale = saleServiceImpl.getSaleById(salesId);
			SaleJson json = SaleJson.parseObjectToJson(sale);

			map.put("sale", json);
			map.put("success", true);
			map.put("message", "All working perfectly");
			
		} catch( NoSuchElementException e) {
			map.put("sale", null);
			map.put("success", false);
			map.put("message", "Sale with id "+salesId+" not found.");
		}
		
		
		return map;
	}
	
	@GetMapping("/sales/players/{id}")
	//B26
	HashMap<String,Object> getSalesByPlayer(@PathVariable Long id){
		HashMap<String,Object> map = new HashMap<>();
		try {
			Player myplayer = playerServiceImpl.playerById(id);
			List<Sale> allSalesByPlayer = saleServiceImpl.saleListByPlayer(myplayer);

			if(allSalesByPlayer.size() > 0) {
				map.put("success", true);
				map.put("all sales by player id", allSalesByPlayer);
				map.put("message", "get all sales by player id");
			}else {
				map.put("success", false);
				map.put("message", "Error getting all sales");

				//throw new Exception();
			}

		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}

		return map;
	}


	//public HashMap<String,Object> getSalesByPlayer(@PathVariable(name="id") Long playerId){
	//
	//	List<Sale> listSales = saleServiceImpl.getSalesByPlayer(playerId);
	//	List<SaleJson> jsonList = SaleJson.parseListToJson(listSales);
		
	//	HashMap<String,Object> map = new HashMap<>();
	//	map.put("playerId", playerId);
	//	map.put("sales", jsonList);
	//	map.put("success", true);
	//	map.put("message", "Pendiente de implementar por evitar conflicto con codigo Player");
		
	//	return map;
	//}
	
	@PostMapping("/sales") //@RequestBody Sale p_sale
	public HashMap<String,Object> createSale(@RequestBody SaleJson saleJson){
		
		Sale p_sale = saleJson.setJsonToObject();
		
		Sale sale = saleServiceImpl.createSale(p_sale);
		SaleJson json = SaleJson.parseObjectToJson(sale);
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("sale", json);
		map.put("success", true);
		map.put("message", "Sale created perfectly");
		
		return map;
	}
	
	@PutMapping("/sales/{id}") 
	public HashMap<String,Object> updateSale(@PathVariable(name="id") Long saleId, 
											 @RequestBody SaleJson saleJson){
		
		Sale p_sale = saleJson.setJsonToObject();
		
		Sale updatedSale = null;
		HashMap<String,Object> map = new HashMap<>();
		map.put("saleId", saleId);

		try {
			updatedSale = saleServiceImpl.updateSale(saleId, p_sale);
			SaleJson json = SaleJson.parseObjectToJson(updatedSale);
			
			map.put("sale", json);
			map.put("success", true);
			map.put("message", "Sale updated perfectly");
		}catch(NoSuchElementException e) {
			map.put("sale", null);
			map.put("success", false);
			map.put("message", "Sale with id "+saleId+" doesn't exist.");
		}
		
		return map;
	}
	
	@DeleteMapping("/sales/{id}") 
	public HashMap<String,Object> deleteSale(@PathVariable(name="id") Long saleId){
		
		HashMap<String,Object> map = new HashMap<>();

		try {
			saleServiceImpl.deleteSale(saleId);
			
			map.put("saleId", saleId);
			map.put("success", true);
			map.put("message", "Sale deleted perfectly");
		}catch(EmptyResultDataAccessException e){
			map.put("saleId", saleId);
			map.put("success", false);
			map.put("message", "Sale with id "+saleId+" doesn't exist.");
		}
		
		return map;
	}

	//B29 TODO List Sales stadistics
	@GetMapping("/sales/bids/stats")
	HashMap<String,Object> getSalesStats(){
		HashMap<String,Object> map = new HashMap<>();
		//This map stores the number of bids per active sales
		HashMap<Sale,Integer> salestats = new HashMap<>();
		try {

			// convert date to calendar to get last week date
			Date today = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(today);
			System.out.println(today);

			// manipulate date to substract seven days
			c.add(Calendar.DATE, -7);
			Date lastweek = c.getTime();
			System.out.println(lastweek);
			List<Sale> allSalesLastWeek = saleServiceImpl.saleListBetweenDates(lastweek,today);

			// get the number of bid per sale in hashmap called salestats with sale as key and number of bids as value, if there are zero bids then the sale failed
			for (Sale tempsale : allSalesLastWeek) {
				List<Bid> bidspersale = bidServiceImpl.getBidsBySale(tempsale);
				System.out.println(tempsale.getId());
				System.out.println(bidspersale.size());
				salestats.put(tempsale,bidspersale.size());
			}

			// create another hashmap to get the frequency a value ( number of bids ) appears in the salestats hashmap
			//Map<Integer, Integer> bidscounts = new HashMap<Integer, Integer>();
			//for ( Integer b : salestats.values()){
			//	int bidnumber =  bidscounts.get(b) == null ? 0 : bidscounts.get(b);
			//	bidscounts.put(b, bidnumber +1);
			//}
			//System.out.println(bidscounts);

			// create a TreeMap to get the frequency a value ( number of bids ) appears in the salestats hashmap
			NavigableMap<Integer, Integer> bidscounts = new TreeMap<Integer, Integer>();
			for ( Integer b : salestats.values()){
				int bidnumber =  bidscounts.get(b) == null ? 0 : bidscounts.get(b);
				bidscounts.put(b, bidnumber +1);
			}
			System.out.println(bidscounts);



			if(allSalesLastWeek.size() > 0) {
				map.put("success", true);
				map.put("message", "these are the stadistics for last week");
				map.put("total number of succesful sales, those WITH bids", salestats.size()-bidscounts.get(0));
				map.put("total number of failed sales, those WITHOUT bids", bidscounts.get(0));
				map.put("maximum number of bids a particular sales has got", bidscounts.lastKey());


				//map.put("all sales of last week", allSalesLastWeek);
				//map.put("message", "get all sales of last week");
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
