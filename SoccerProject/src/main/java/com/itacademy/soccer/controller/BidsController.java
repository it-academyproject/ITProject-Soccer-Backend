package com.itacademy.soccer.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.impl.SaleServiceImpl;
import com.itacademy.soccer.service.impl.TeamServiceImpl;
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
import org.springframework.web.bind.annotation.RestController;

import com.itacademy.soccer.controller.json.BidJson;
import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.service.impl.BidServiceImpl;

@RestController
@PreAuthorize("authenticated")
@RequestMapping("/api")
public class BidsController {

	@Autowired
	BidServiceImpl bidServiceImpl;

	@Autowired
	SaleServiceImpl saleServiceImpl;

	@Autowired
	TeamServiceImpl teamServiceImpl;

	@GetMapping("/sales/{id}/bids") // Accesible desde usuario MANAGER o ADMIN (pendiente)
	public HashMap<String, Object> listBidsBySale(@PathVariable(name = "id") Long salesId) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			List<Bid> listBid = bidServiceImpl.listBidsBySale(salesId); // implementación del service

			// convertir lista de objetos en formato dto a lista de objetos en formato json
			List<BidJson> jsonList = BidJson.parseListBidToJson(listBid);

			map.put("bids", jsonList);
			map.put("success", true);
			map.put("message", "All working perfectly");
		} catch (NoSuchElementException e) { // si sale no existe
			map.put("success", false);
			map.put("message", "Sale with id " + salesId + " doesn't exist");
		}

		return map;
	}

	// TODO B29
	@PostMapping("/bids/sales/{id}")
	public HashMap<String, Object> createBid(@PathVariable Long id, @RequestBody Bid bid) {

		HashMap<String, Object> map = new HashMap<>();
		Date now = new Date();
		String msj = "";

		Bid newBid = new Bid();

		newBid = bid;
		System.out.println("id_teamd " + bid.getTeamId());
		System.out.println("price_bid " + bid.getBidPrice());

		newBid.setBidPrice(bid.getBidPrice());

		Sale sale = saleServiceImpl.getSaleById(id);

		// SALE IS CLOSED
		if (sale.getLimitDate().before(now)) {

			msj = "Sale " + sale.getId() + " is closed";
			map.put("message", msj);
			map.put("Date_limit", sale.getLimitDate());

			// SALE IS OPEN
		} else {

			newBid.setSale(sale);

			Team team = teamServiceImpl.getOneTeamById(newBid.getTeamId());

			// TEAM BUDGET INFERIOR TO PRICE'S BID
			if (newBid.getBidPrice() > team.getBudget()) {

				msj = "Team " + team.getId() + " doesn't have enough budget";
				map.put("message", msj);
				map.put("Team_budget", team.getBudget());
				map.put("Sale_price", sale.getInitialPrice());

			} else {
				// PRICE'S BID INFERIOR TO PRICE'S SALE
				if (sale.getInitialPrice() > newBid.getBidPrice()) {

					msj = "BID " + newBid.getBidPrice() + " the offer is less than the sale price";
					map.put("message", msj);
					map.put("Team_budget", team.getBudget());
					map.put("Sale_price", sale.getInitialPrice());

				} else {
					// System.out.println(newBid.getTeam_id());
					newBid.setTeam(teamServiceImpl.getOneTeamById(newBid.getTeamId()));
					// Date now = new Date();
					newBid.setOperationDate(now);
					try {
						Bid NewlyCreatedBid = bidServiceImpl.save(newBid);
						map.put("success", true);
						map.put("message", "Bid Created");
						map.put("bid", NewlyCreatedBid);
					} catch (Exception e) {
						map.put("success", false);
						map.put("message", "Bid NOT Created ! :" + e.getMessage());
					}

				}

			}

		}

		return map;
	}

	@PostMapping("/sales/{id}/bids") // Accesible desde usuario MANAGER (pendiente)
	public HashMap<String, Object> createBidBySale(@PathVariable(name = "id") Long salesId,
			@RequestBody BidJson bidJson) {

		Bid bid = bidJson.toBid(); // convertir json en formato dto

		HashMap<String, Object> map = new HashMap<>();

		Long teamId = bidJson.getTeamId();

		try {

			Bid createdBid = bidServiceImpl.createBidBySale(salesId, bid); // ejecutar service
			BidJson json = BidJson.parseBidToJson(createdBid); // convertir dto en formato json

			map.put("bid", json);
			map.put("success", true);
			map.put("message", "All working perfectly");

		} catch (NoSuchElementException e) { // si sale o team no existe

			map.put("success", false);
			map.put("message", "Sale with id " + salesId + " or Team with id " + teamId + " doesn't exist.");
		}

		return map;

	}

	// SOLO Accesible desde usuario ADMIN (pendiente)
	// Funcionalidad por parte del Admin para modificar el precio de una puja (bid)
	// en caso de que
	// un equipo haya realizado una puja con un precio erroneo
	// Ejemplo: un equipo hizo una puja por 3300 y se queria hacer una puja de 300
	// se le comunica al admin y el admin modifica el precio correcto
	@PutMapping("/bids/{id}")
	public HashMap<String, Object> updateBid(@PathVariable(name = "id") Long bidId, @RequestBody BidJson jsonBid) {
		Bid bid = jsonBid.toBid(); // convertir formato json en formato dto

		HashMap<String, Object> map = new HashMap<>();

		try {

			Bid updatedBid = bidServiceImpl.updateBid(bidId, bid); // implementación del service
			BidJson json = BidJson.parseBidToJson(updatedBid); // convertir formato dto en json

			map.put("bid", json);
			map.put("success", true);
			map.put("message", "All working perfectly");
		} catch (NoSuchElementException e) { // si bid no existe
			map.put("success", false);
			map.put("message", "Bid with id " + bidId + "doesn't exist.");
		}

		return map;
	}

	// SOLO Accesible desde usuario ADMIN (pendiente)
	@DeleteMapping("/bids/{id}")
	public HashMap<String, Object> deleteBid(@PathVariable(name = "id") Long bidId) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			bidServiceImpl.deleteBid(bidId);
			map.put("success", true);
			map.put("message", "Bid deleted correctly");
		} catch (EmptyResultDataAccessException e) {
			map.put("success", false);
			map.put("message", "Bid with id " + bidId + " doesn't exist.");
		}

		return map;
	}

	// SOLO Accesible desde usuario ADMIN (pendiente)
	// Elimina la puja más reciente (ultima puja) de una venta concreta
	@DeleteMapping("/sales/{id}/lastbid")
	public HashMap<String, Object> deleteLastBidBySale(@PathVariable(name = "id") Long salesId) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			bidServiceImpl.deleteLastBidBySale(salesId);
			map.put("success", true);
			map.put("message", "Last Bid from sale " + salesId + " deleted correctly");
		} catch (Exception e) { // si no hay pujas(bids) en esta venta (sale)
			map.put("success", false);
			map.put("message", e.getMessage());
		}

		return map;

	}

}
