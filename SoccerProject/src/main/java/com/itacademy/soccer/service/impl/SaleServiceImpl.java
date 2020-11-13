package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.controller.json.SalesFilterJson;
import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dao.ISaleDAO;
import com.itacademy.soccer.dto.Bid;
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
		List <Sale> allSales = listAllSales();
		List<Sale> saleFromDate = new ArrayList<>();
		
		for (Sale sale : allSales) {
			
			if (sale.getLimitDate().after(initialDate)) {
				saleFromDate.add(sale);
			}		
		}
		return saleFromDate;	
	}
	
	@Override
	public List<Sale> listAllSalesClosed() {
		Date now = new Date();
		List <Sale> allSales = listAllSales();			
		List<Sale> allSaleClosed = new ArrayList<>();
		
		for (Sale sale : allSales) {					
			if (sale.getLimitDate().before(now)) {	
				allSaleClosed.add(sale);
			}
		}		
		return allSaleClosed;		
	}
	
	@Override	
	public HashMap<String,Object> salesFilter(int maxage, int minage, int defense, int attack, int keeper, int pass){
		
		HashMap<String,Object> map = new HashMap<>();
		try {
			List<SalesFilterJson> filteredSales = this.salesFilterComparator(maxage, minage, defense, attack, keeper, pass);			
			if(!filteredSales.isEmpty()) {
				map.put("success", true);
				map.put("message", "get all sales by player characteristics");
				map.put("filtered sales", filteredSales);
			}else {
				map.put("success", false);
				map.put("message", "There is no player with those characteristics at the moment");				
			}
		}
		catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
		return map;
	}
	
	/*
	 * I go through the available sales and compare the attributes of the players sent by URL.
	 * The filter parameters received by URL will be interpreted as minimum requirements in the attributes of the players that are for sale.
	 */
	@Override
	public List<SalesFilterJson> salesFilterComparator(int maxAge, int minAge, int defense, int attack, int keeper, int pass){
		List<Sale> allSales = this.listAllSales();
		List<SalesFilterJson> filteredSalesJson = new ArrayList<SalesFilterJson>();
		
		for (Sale sale : allSales) {

			if ( (sale.getPlayer().getAge()<=maxAge && sale.getPlayer().getAge()>=minAge) 
					&& sale.getPlayer().getDefense()>=defense
					&& sale.getPlayer().getAttack()>=attack
					&& sale.getPlayer().getKeeper()>=keeper
					&& sale.getPlayer().getPass()>=pass /*&& sale.getLimitDate().compareTo(Now)>0*/) {
				
				SalesFilterJson filteredSaleJson = new SalesFilterJson();			
				filteredSaleJson.setId(sale.getId());
				filteredSaleJson.setLimitDate(sale.getLimitDate());
				filteredSaleJson.setInitialPrice(sale.getInitialPrice());
				filteredSaleJson.setLastBidPrice(getLastBidPrice(sale.getBids()));
				filteredSaleJson.setTeamName(sale.getPlayer().getTeam().getName());
				filteredSaleJson.setPlayer(sale.getPlayer());
				filteredSalesJson.add(filteredSaleJson);
			}
		}	
		return filteredSalesJson;
	}
	
	public float getLastBidPrice(List<Bid> bids) {
		Bid selectedBid= bids.get(0);
		
		for(int i = 0; i < bids.size(); i++) {
				if(bids.get(i).getOperationDate().after(selectedBid.getOperationDate())) {
					selectedBid=bids.get(i);
			}
		}		
		return selectedBid.getBidPrice();
	}

}
