package com.itacademy.soccer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

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

import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.service.impl.SaleServiceImpl;

@RestController
@RequestMapping("/api")
public class SalesController {

	@Autowired
	SaleServiceImpl saleServiceImpl;
	
	@GetMapping("/sales")
	public HashMap<String,Object> listAllSales(){
		
		List<Sale> salesList = saleServiceImpl.listAllSales();
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("sales", salesList);
		map.put("success", true);
		map.put("message", "All working perfectly");
		
		return map;
	}
	
	@GetMapping("/sales/{id}")
	public HashMap<String,Object> getSalesById(@PathVariable(name="id") Long salesId){

		HashMap<String,Object> map = new HashMap<>();
		
		try {
			Sale sale = saleServiceImpl.getSaleById(salesId);
			map.put("sale", sale);
			map.put("success", true);
			map.put("message", "All working perfectly");
			
		} catch( NoSuchElementException e) {
			map.put("sale", null);
			map.put("success", false);
			map.put("message", "Sale with id "+salesId+" not found.");
		}
		
		
		return map;
	}
	
	@GetMapping("/players/{id}/sales")
	public HashMap<String,Object> getSalesByPlayer(@PathVariable(name="id") Long playerId){
		
		List<Sale> listSales = saleServiceImpl.getSalesByPlayer(playerId);
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("playerId", playerId);
		map.put("sales", listSales);
		map.put("success", true);
		map.put("message", "Pendiente de implementar por evitar conflicto con codigo Player");
		
		return map;
	}
	
	@PostMapping("/sales") 
	public HashMap<String,Object> createSale(@RequestBody Sale p_sale){
		
		Sale sale = saleServiceImpl.createSale(p_sale);
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("sale", sale);
		map.put("success", true);
		map.put("message", "Sale created perfectly");
		
		return map;
	}
	
	@PutMapping("/sales/{id}") 
	public HashMap<String,Object> updateSale(@PathVariable(name="id") Long saleId, 
											 @RequestBody Sale p_sale){
		
		Sale sale = saleServiceImpl.updateSale(saleId, p_sale);
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("saleId", saleId);
		map.put("sale", sale);
		map.put("success", true);
		map.put("message", "Sale updated perfectly");
		
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
