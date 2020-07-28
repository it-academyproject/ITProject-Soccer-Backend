package com.itacademy.soccer.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SalesController {

	@GetMapping("/sales")
	public HashMap<String,Object> listAllSales(){
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("sales", null);
		map.put("success", true);
		map.put("message", "All working perfectly");
		
		return map;
	}
	
	@GetMapping("/sales/{id}")
	public HashMap<String,Object> getSalesById(@PathVariable(name="id") Long salesId){
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("sale", salesId);
		map.put("success", true);
		map.put("message", "All working perfectly");
		
		return map;
	}
	
	@GetMapping("/players/{id}/sales")
	public HashMap<String,Object> getSalesByPlayer(@PathVariable(name="id") Long playerId){
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("playerId", playerId);
		map.put("sales", null);
		map.put("success", true);
		map.put("message", "All working perfectly");
		
		return map;
	}
	
	@PostMapping("/sales") //@RequestBody Sale 
	public HashMap<String,Object> createSale(){
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("sale", null);
		map.put("success", true);
		map.put("message", "Sale created perfectly");
		
		return map;
	}
	
	@PutMapping("/sales/{id}") //@RequestBody Sale 
	public HashMap<String,Object> updateSale(@PathVariable(name="id") Long saleId){
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("saleId", saleId);
		map.put("sale", null);
		map.put("success", true);
		map.put("message", "Sale updated perfectly");
		
		return map;
	}
	
	@DeleteMapping("/sales/{id}") 
	public HashMap<String,Object> deleteSale(@PathVariable(name="id") Long saleId){
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("saleId", saleId);
		map.put("success", true);
		map.put("message", "Sale deleted perfectly");
		
		return map;
	}
	
}
