package com.itacademy.soccer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Sale;

public interface ISaleService {
	
		public Sale createSale(Sale sale);
		
		public List<Sale> listAllSales();
		
		public Sale getSaleById(Long saleId);
	
		public List<Sale> saleListByPlayer(Player player);
		
		public List<Sale> getSalesByPlayer(Long playerId);
		
		public Sale updateSale(Sale sale);
		
		public void deleteSale(Long saleId);

		public List<Sale> saleListBetweenDates(Date initialDate);

		public List<Sale> saleListFromDates(Date initialDate);

		public List<Sale> listAllSalesClosed();
		
		public HashMap<String,Object> salesFilter(int maxAge, int minAge, int defense, int attack, int keeper, int pass);
		
}
