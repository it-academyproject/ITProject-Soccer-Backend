package com.itacademy.soccer.controller;

import java.util.*;

import com.itacademy.soccer.dao.IPlayerDAO;
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
import org.springframework.web.bind.annotation.RequestParam;
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

	IPlayerDAO iPlayerDAO;

	@GetMapping("/sales")
	public HashMap<String,Object> listAllSales(){
		
		List<Sale> salesList = saleServiceImpl.listAllSales();
		List<SaleJson> jsonList = SaleJson.parseListSaleToJson(salesList);
		
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
			SaleJson json = SaleJson.parseSaleToJson(sale);

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
			Optional<Player> myplayer = iPlayerDAO.findById(id);
			List<Sale> allSalesByPlayer = saleServiceImpl.saleListByPlayer(myplayer.get());

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
	

	@GetMapping("/sales/filter")
	HashMap<String,Object> getFilteredSales(
			@RequestParam(required=false, value="maxAge", defaultValue="100") int maxage, 
			@RequestParam(required=false, value="minAge", defaultValue="1") int minage,
			@RequestParam(required=false, value="defense", defaultValue="1") int defense, 
			@RequestParam(required=false, value="attack", defaultValue="1") int attack,
			@RequestParam(required=false, value="keeper", defaultValue="1") int keeper,
			@RequestParam(required=false, value="pass", defaultValue="1") int pass) {
		return saleServiceImpl.salesFilter(maxage, minage, defense, attack, keeper, pass);
	}
	
	
	@PostMapping("/sales") //@RequestBody Sale p_sale
	public HashMap<String,Object> createSale(@RequestBody SaleJson saleJson){
		
		Sale pSale = saleJson.toSale();
		
		Sale sale = saleServiceImpl.createSale(pSale);
		SaleJson json = SaleJson.parseSaleToJson(sale);
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("sale", json);
		map.put("success", true);
		map.put("message", "Sale created perfectly");
		
		return map;
	}
	
	@PutMapping("/sales") 
	public HashMap<String,Object> updateSale(@RequestBody SaleJson saleJson){
		
		Sale pSale = saleJson.toSale();
		
		Sale updatedSale = null;
		HashMap<String,Object> map = new HashMap<>();
		map.put("saleId", saleJson.getId());

		try {
			updatedSale = saleServiceImpl.updateSale(saleJson.getId(), pSale);
			SaleJson json = SaleJson.parseSaleToJson(updatedSale);
			
			map.put("sale", json);
			map.put("success", true);
			map.put("message", "Sale updated perfectly");
		}catch(NoSuchElementException e) {
			map.put("sale", null);
			map.put("success", false);
			map.put("message", "Sale with id "+saleJson.getId()+" doesn't exist.");
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

}
