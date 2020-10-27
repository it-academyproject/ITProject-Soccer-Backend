package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.Date;
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

	//@Override
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
		
	@Override
	public List<Sale> saleListBetweenDates(Date initialDate) {
		Date now = new Date();
		return iSaleDAO.findByLimitDateIsBetween(initialDate, now);
	}
	
	@Override
	public List<Sale> saleListFromDates(Date initialDate) {
		List <Sale> all_sales = listAllSales();
		List<Sale> sale_from_date = new ArrayList<>();
		
		for (Sale sale : all_sales) {
			
			if (sale.getLimitDate().after(initialDate)) {
				sale_from_date.add(sale);
			}		
		}
		return sale_from_date;	
	}
	
	@Override
	public List<Sale> listAllSalesClosed() {
		Date now = new Date();
		List <Sale> all_sales = listAllSales();			
		List<Sale> all_sale_closed = new ArrayList<>();
		
		for (Sale sale : all_sales) {					
			if (sale.getLimitDate().before(now)) {	
				all_sale_closed.add(sale);
			}
		}		
		return all_sale_closed;		
	}
	
	/*
	 * I go through the available sales and compare the attributes of the players sent by URL.
	 * The filter parameters received by URL will be interpreted as minimum requirements in the attributes of the players that are for sale.
	 */
	@Override
	public List<Sale> salesFilter(int maxage, int minage, int defense, int attack, int keeper, int pass){
		List<Sale> allSales = this.listAllSales();
		List<Sale> filteredSales = new ArrayList<>();
		Date Now = new Date();
		System.out.println(Now);
		
		for (Sale sale : allSales) {
			if ( (sale.getPlayer().getAge()<=maxage && sale.getPlayer().getAge()>=minage) 
					&& sale.getPlayer().getDefense()>=defense
					&& sale.getPlayer().getAttack()>=attack
					&& sale.getPlayer().getKeeper()>=keeper
					&& sale.getPlayer().getPass()>=pass && sale.getLimitDate().compareTo(Now)>0) {
				filteredSales.add(sale);					
			}				
		}	
		return filteredSales;
	}
	

}
