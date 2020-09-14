package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dao.ISaleDAO;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.service.ISaleService;

@Service
public class SaleServiceImpl implements ISaleService{

	@Autowired
	private ISaleDAO iSaleDAO;
	
 	@Autowired
	private IPlayerDAO iPlayerDAO;
	
	@Override
	public Sale createSale(Sale sale) {

		Player playerIn = sale.getPlayer();
		Long playerId = playerIn.getId();
		Player player = iPlayerDAO.findById(playerId).get();
		sale.setPlayer(player);
		
		return iSaleDAO.save(sale);
		
	}

	@Override
	public List<Sale> listAllSales() {
		
		return iSaleDAO.findAll();
	}

	@Override
	public Sale getSaleById(Long saleId) {

		return iSaleDAO.findById(saleId).get();
	}

	//B26
	@Override
	public List<Sale> saleListByPlayer(Player player) {
		return iSaleDAO.findSalesByPlayer(player);
	}


	@Override
	public List<Sale> getSalesByPlayer(Long playerId) {
		
//		Player player = iPlayerDAO.findById(playerId).get();
//		List<Sale> salesList = player.getSales();
//		return salesList;
		
		return new ArrayList<Sale>();
	}

	// Modify the limit date of the sale with id passed by parameter and saves it into the repository
	@Override
	public Sale updateSale(Long idSale, Sale p_sale) {

		Sale sale = iSaleDAO.findById(idSale).get();
		
		sale.setLimitDate(p_sale.getLimitDate());
		
		return iSaleDAO.save(sale);
		
	}

	@Override
	public void deleteSale(Long saleId) {

		iSaleDAO.deleteById(saleId);
	}

}
