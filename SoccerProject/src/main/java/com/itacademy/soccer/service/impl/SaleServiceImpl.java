package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.controller.json.SaleJson;
import com.itacademy.soccer.dao.IPlayerDAO;
import com.itacademy.soccer.dao.ISaleDAO;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.service.ISaleService;
import com.itacademy.soccer.util.Verify;

@Service
public class SaleServiceImpl implements ISaleService {

	@Autowired
	private ISaleDAO iSaleDAO;

	@Autowired
	private IPlayerDAO iPlayerDAO;

	@Override
	public Sale createSale(Sale sale) {

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

	// B26
	@Override
	public List<Sale> saleListByPlayer(Player player) {
		return iSaleDAO.findSalesByPlayer(player);
	}

	// @Override
	public List<Sale> getSalesByPlayer(Long playerId) {

		Player player = iPlayerDAO.findById(playerId).get();

		return iSaleDAO.findSalesByPlayer(player);
	}

	@Override
	public Sale updateSale(Sale sale) {

		return iSaleDAO.save(sale);
	}
	
	public Optional<Sale> getOptionalSale(Long id) {
		
		return iSaleDAO.findById(id);
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
		List<Sale> allSales = listAllSales();
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
		List<Sale> allSales = listAllSales();
		List<Sale> allSaleClosed = new ArrayList<>();

		for (Sale sale : allSales) {
			if (sale.getLimitDate().before(now)) {
				allSaleClosed.add(sale);
			}
		}
		return allSaleClosed;
	}

	@Override
	public HashMap<String, Object> salesFilter(int maxAge, int minAge, int defense, int attack, int keeper, int pass) {

		HashMap<String, Object> map = new HashMap<>();
		
		try {
			
			List<SaleJson> filteredSales = listAllSales().parallelStream()
					.filter(sale -> sale.getPlayer().getAge() <= maxAge && sale.getPlayer().getAge() >= minAge
							&& sale.getPlayer().getDefense() >= defense && sale.getPlayer().getAttack() >= attack
							&& sale.getPlayer().getKeeper() >= keeper && sale.getPlayer().getPass() >= pass)
					.map(sale -> SaleJson.parseSaleToJson(sale)).collect(Collectors.toList());
			
			if (!Verify.isNullEmpty(filteredSales)) {
				map.put("success", true);
				map.put("message", "get all sales by player characteristics");
				map.put("filtered sales", filteredSales);
				
			} else {
				map.put("success", false);
				map.put("message", "There is no player with those characteristics at the moment");
			}
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
		
		return map;
	}

}
