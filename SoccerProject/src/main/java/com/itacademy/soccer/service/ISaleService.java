package com.itacademy.soccer.service;

import java.util.List;

import com.itacademy.soccer.dto.Sale;

public interface ISaleService {
	
		public Sale createSale(Sale sale);
		
		public List<Sale> listAllSales();
		
		public Sale getSaleById(Long saleId);
		
		public List<Sale> getSalesByPlayer(Long playerId);
		
		public Sale updateSale(Long saleId, Sale sale);
		
		public void deleteSale(Long saleId);

	
}
