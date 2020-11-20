package com.itacademy.soccer.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.impl.SaleServiceImpl;
import com.itacademy.soccer.service.impl.TeamServiceImpl;
import com.itacademy.soccer.util.Verify;

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
@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
@RequestMapping("/api")
public class BidsController {

	@Autowired
	BidServiceImpl bidServiceImpl;

	@Autowired
	SaleServiceImpl saleServiceImpl;

	@Autowired
	TeamServiceImpl teamServiceImpl;

	@GetMapping("/sales/{id}/bids")
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

	@PostMapping("/sales/{id}/bids")
	public HashMap<String, Object> createBid(@PathVariable(name = "id") Long id, @RequestBody Bid bid) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			Optional<Sale> sale = saleServiceImpl.getOptionalSale(id);
			Optional<Team> team = teamServiceImpl.getOptionalTeam(bid.getTeamId());
			Optional<Bid> lastBid = (!sale.isPresent()) ? Optional.empty()
					: bidServiceImpl.getLastBidBySaleId(sale.get().getId());
			float percentage = 1.2f; // equivalent to 20% than the last bid

			if (!sale.isPresent()) {

				throw new Exception("The Sale Id is incorrect!");

			} else if (!team.isPresent()) {

				throw new Exception("The Team Id is incorrect!");

			} else if (team.get().getBudget() < bid.getBidPrice()) {

				throw new Exception("This team does not have enough budget for this bid!");

			} else if (!Verify.notNullZero(bid.getBidPrice()) || bid.getBidPrice() < sale.get().getInitialPrice()) {

				throw new Exception("The Price of Bid can't be null or less than the initial sale!");

			} else if (lastBid.isPresent()
					&& !Verify.isPercentHigher(bid.getBidPrice(), lastBid.get().getBidPrice(), percentage)) {
				throw new Exception(
						"The Bid Price must be at least " + ((int) (percentage * 100 - 100)) + "% higher than the last bid");

			} else if (Verify.isExpired(sale.get().getLimitDate())) {

				throw new Exception("This Sale is already expired!!");

			} else if (Verify.inTwoMinToExpire(new Date(), sale.get().getLimitDate())) {

				if (sale.get().getFirstLimitDate() == null) {
					sale.get().setFirstLimitDate(sale.get().getLimitDate());
				}

				sale.get().setLimitDate(new Date(sale.get().getLimitDate().getTime() + 120000));

				sale = Optional.ofNullable(saleServiceImpl.updateSale(sale.get()));

				if (!sale.isPresent()) {
					throw new Exception("Something went wrong with updating the sale deadline!");
				}
			}

			Bid newBid = new Bid();
			newBid.setTeamId(team.get().getId());
			newBid.setBidPrice(bid.getBidPrice());
			newBid.setOperationDate(new Date());
			newBid.setSale(sale.get());
			newBid.setTeam(team.get());

			newBid = bidServiceImpl.createBid(newBid);

			map.put("Bid", BidJson.parseBidToJson(newBid));
			map.put("success", true);
			map.put("message", "Bid was created succefull!");
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "BID isn't created! : " + e.getMessage());
		}

		return map;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/bids/{id}")
	public HashMap<String, Object> updateBid(@PathVariable(name = "id") Long id, @RequestBody Bid bid) {

		HashMap<String, Object> map = new HashMap<>();

		try {

			Optional<Bid> oldBid = bidServiceImpl.getBidById(id);

			if (!oldBid.isPresent()) {

				throw new Exception("The Bid Id is incorrect!");

			} else if (!Verify.notNullZero(bid.getBidPrice()) || oldBid.get().getBidPrice() >= bid.getBidPrice()) {

				throw new Exception("The Price of Bid can't be null, zero or higher than the current Bid!");

			}

			oldBid.get().setBidPrice(bid.getBidPrice());

			Bid newBid = bidServiceImpl.updateBid(oldBid.get());

			map.put("bid", BidJson.parseBidToJson(newBid));
			map.put("success", true);
			map.put("message", "All working perfectly");
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "Bid was not updated! : " + e.getMessage());
		}

		return map;
	}

	@PreAuthorize("hasRole('ADMIN')")
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

	@PreAuthorize("hasRole('ADMIN')")
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
