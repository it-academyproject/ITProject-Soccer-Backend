package com.itacademy.soccer.controller;

import java.util.*;

import com.itacademy.soccer.dto.Bid;
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
	
	
	// http://localhost:8181/api/sales/filter?max-age={max-age}&min-age={min-age}&defense={defense}&attack={attack}&keeper={keeper}&pass={pass} 
	@GetMapping("/sales/filter")
	HashMap<String,Object> getFilteredSales(
			@RequestParam(value="max-age", defaultValue="100") int maxage, 
			@RequestParam(value="min-age", defaultValue="1") int minage,
			@RequestParam(value="defense", defaultValue="1") int defense, 
			@RequestParam(value="attack", defaultValue="1") int attack,
			@RequestParam(value="keeper", defaultValue="1") int keeper,
			@RequestParam(value="pass", defaultValue="1") int pass) {
		
//		HashMap<String,Object> map = new HashMap<>();
//		
//		
//		
//		try {
//			List<Sale> filteredSales = saleServiceImpl.salesFilter2(maxage, minage, defense, attack, keeper, pass);			
//			if(!filteredSales.isEmpty()) {
//				map.put("success", true);
//				map.put("message", "get all sales by player skills");
//				map.put("filtered sales", filteredSales);
//			}else {
//				map.put("success", false);
//				map.put("message", "Error getting sales: there is no player with those specifications at the moment");				
//			}
//		}
//		catch (Exception e) {
//			map.put("success", false);
//			map.put("message", "something went wrong: " + e.getMessage());
//		}
		return saleServiceImpl.salesFilter(maxage, minage, defense, attack, keeper, pass);
	}
	
	
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
	
	@PutMapping("/sales") 
	public HashMap<String,Object> updateSale(@RequestBody SaleJson saleJson){
		
		Sale p_sale = saleJson.setJsonToObject();
		
		Sale updatedSale = null;
		HashMap<String,Object> map = new HashMap<>();
		map.put("saleId", saleJson.getId());

		try {
			updatedSale = saleServiceImpl.updateSale(saleJson.getId(), p_sale);
			SaleJson json = SaleJson.parseObjectToJson(updatedSale);
			
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
