package com.itacademy.soccer.controller;

import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.service.impl.BidServiceImpl;
import com.itacademy.soccer.service.impl.PlayerServiceImpl;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.itacademy.soccer.util.Verify;

@RestController
@PreAuthorize("authenticated")
@RequestMapping("/api")
public class SalesController {

	@Autowired
	SaleServiceImpl saleServiceImpl;

	@Autowired
	PlayerServiceImpl playerServiceImpl;

	@Autowired
	BidServiceImpl bidServiceImpl;

	@GetMapping("/sales")
	public HashMap<String, Object> listAllSales() {

		List<Sale> salesList = saleServiceImpl.listAllSales();
		List<SaleJson> jsonList = SaleJson.parseListSaleToJson(salesList);

		HashMap<String, Object> map = new HashMap<>();
		map.put("sales", jsonList);
		map.put("success", true);
		map.put("message", "All working perfectly");

		return map;
	}

	@GetMapping("/sales/{id}")
	public HashMap<String, Object> getSalesById(@PathVariable(name = "id") Long salesId) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			Sale sale = saleServiceImpl.getSaleById(salesId);
			SaleJson json = SaleJson.parseSaleToJson(sale);

			map.put("sale", json);
			map.put("success", true);
			map.put("message", "All working perfectly");

		} catch (NoSuchElementException e) {
			map.put("sale", null);
			map.put("success", false);
			map.put("message", "Sale with id " + salesId + " not found.");
		}

		return map;
	}

	@GetMapping("/sales/players/{id}")
	// B26
	HashMap<String, Object> getSalesByPlayer(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			Optional<Player> myplayer = playerServiceImpl.findById(id);
			List<Sale> allSalesByPlayer = saleServiceImpl.saleListByPlayer(myplayer.get());

			if (allSalesByPlayer.size() > 0) {
				map.put("success", true);
				map.put("all sales by player id", allSalesByPlayer);
				map.put("message", "get all sales by player id");
			} else {
				map.put("success", false);
				map.put("message", "Error getting all sales");

				// throw new Exception();
			}

		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "something went wrong: " + e.getMessage());
		}
		return map;
	}

	@GetMapping("/sales/filter")
	HashMap<String, Object> getFilteredSales(
			@RequestParam(required = false, value = "maxAge", defaultValue = "100") int ageMax,
			@RequestParam(required = false, value = "minAge", defaultValue = "1") int ageMin,
			@RequestParam(required = false, value = "defense", defaultValue = "1") int defense,
			@RequestParam(required = false, value = "attack", defaultValue = "1") int attack,
			@RequestParam(required = false, value = "keeper", defaultValue = "1") int keeper,
			@RequestParam(required = false, value = "pass", defaultValue = "1") int pass) {
		return saleServiceImpl.salesFilter(ageMax, ageMin, defense, attack, keeper, pass);
	}

	@PostMapping("/sales")
	public HashMap<String, Object> createSale(@RequestParam Long playerId, @RequestBody Sale sale) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			Optional<Player> player = playerServiceImpl.findById(playerId);
			String message = "Sale created perfectly";

			if (!player.isPresent()) {
				throw new Exception("Player ID doesn't exist!");

			} else {
				Optional<Sale> last = saleServiceImpl.saleListByPlayer(player.get()).stream()
						.sorted(Comparator.comparing(Sale::getLimitDate).reversed()).findFirst();
				
				if (last.isPresent() && !Verify.isExpired(last.get().getLimitDate())) {
					throw new Exception("That player already has a sale in progress!");	
				}
			}
			
			if (!Verify.notNullZero(sale.getInitialPrice())) {
				throw new Exception("Initial Price is null or empty");

			} else if (!Verify.notNullAfter3Days(sale.getLimitDate())) {
				sale.setLimitDate(new Date(System.currentTimeMillis() + 259_080_000));
				message = "Sale created with date change, as the minimum is 3 days";
			}
			
			Sale newSale = new Sale();
			newSale.setInitialPrice(sale.getInitialPrice());
			newSale.setLimitDate(sale.getLimitDate());
			newSale.setPlayer(player.get());

			newSale = saleServiceImpl.createSale(newSale);

			map.put("sale", SaleJson.parseSaleToJson(newSale));
			map.put("success", true);
			map.put("message", message);

		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Sale NOT Created : " + e.getMessage());
		}

		return map;
	}

	@PutMapping("/sales/{id}")
	public HashMap<String, Object> updateSale(@PathVariable(name = "id") Long id, @RequestBody Sale sale) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			Optional<Sale> oldSale = saleServiceImpl.getOptionalSale(id);

			if (!oldSale.isPresent()) {
				throw new Exception("This Sale ID doesn't exist!");
				
			}
			
			if (sale.getLimitDate() != null) {
				
				if (Verify.notNullEqualsBefore(sale.getLimitDate(), oldSale.get().getLimitDate())) {
					
					oldSale.get().setLimitDate(sale.getLimitDate());
					
				} else {
					throw new Exception("Limit Date can't be null, before or same than current!");
				}
				
			}
			
			if (Verify.notLessEqZeroEquals(sale.getInitialPrice(), oldSale.get().getInitialPrice())) {
				oldSale.get().setInitialPrice(sale.getInitialPrice());
				
			} else if (sale.getLimitDate() == null) {
				throw new Exception("The Initial Price can't be null, zero, negative or equal the current value!");
			}

			Sale newSale = saleServiceImpl.updateSale(oldSale.get());

			map.put("success", true);
			map.put("sale", SaleJson.parseSaleToJson(newSale));
			map.put("message", "Sale updated perfectly");
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Sale NOT Updated : " + e.getMessage());
		}

		return map;
	}

	@DeleteMapping("/sales/{id}")
	public HashMap<String, Object> deleteSale(@PathVariable(name = "id") Long saleId) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			saleServiceImpl.deleteSale(saleId);

			map.put("saleId", saleId);
			map.put("success", true);
			map.put("message", "Sale deleted perfectly");
		} catch (EmptyResultDataAccessException e) {
			map.put("saleId", saleId);
			map.put("success", false);
			map.put("message", "Sale with id " + saleId + " doesn't exist.");
		}

		return map;
	}

}
