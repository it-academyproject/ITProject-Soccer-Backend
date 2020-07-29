package com.itacademy.soccer.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.itacademy.soccer.service.impl.BidServiceImpl;

@RequestMapping("/api")
@RestController
public class BidsController {
	
	@Autowired
	BidServiceImpl bidServiceImpl;
	
	@GetMapping("/sales/{id}/bids")
	public HashMap<String,Object> listBidsBySale(@PathVariable(name="id") Long salesId){
		
		List<Bid> listBid = bidServiceImpl.listBidsBySale(salesId);
		List<BidJson> jsonList = BidJson.parseListToJson(listBid);
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("bids",jsonList);
		map.put("success", true);
		map.put("message", "All working perfectly");
		
		return map;
	}
	
	@PostMapping("/sales/{id}/bids")
	public HashMap<String,Object> createBidBySale(@PathVariable(name="id") Long salesId,
									 @RequestBody BidJson bidJson){
		
		Bid bid = bidJson.setJsonToObject();
		
		Bid createdBid = bidServiceImpl.createBidBySale(salesId, bid);
		BidJson json = BidJson.parseObjectToJson(createdBid);

		
		HashMap<String,Object> map = new HashMap<>();
		map.put("bid",json);
		map.put("success", true);
		map.put("message", "All working perfectly");
		
		return map;
		
	}
	
	@PutMapping("/bids/{id}")
	public HashMap<String,Object> updateBid(@PathVariable(name="id") Long salesId,
									@RequestBody BidJson bid){
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("bid",null);
		map.put("success", true);
		map.put("message", "All working perfectly");
		
		return map;
	}
	
	@DeleteMapping("/bids/{id}")
	public void deleteBid(@PathVariable(name="id") Long bidId){
		
		
	}
	
	@DeleteMapping("/sales/{id}/lastbid") //consultar con ruben!!!
	public void deleteLastBidBySale(@PathVariable(name="id") Long salesId){
		
		
	}
	
}
