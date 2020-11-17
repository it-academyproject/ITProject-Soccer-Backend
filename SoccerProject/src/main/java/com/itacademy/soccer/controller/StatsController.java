package com.itacademy.soccer.controller;

import java.util.Collections;
import java.util.Comparator;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itacademy.soccer.controller.json.StatSaleJson;
import com.itacademy.soccer.controller.json.StatTeamJson;

import com.itacademy.soccer.dto.Sale;

import com.itacademy.soccer.service.impl.BidServiceImpl;
import com.itacademy.soccer.service.impl.PlayerServiceImpl;
import com.itacademy.soccer.service.impl.SaleServiceImpl;
import com.itacademy.soccer.service.impl.StatServiceImpl;
import com.itacademy.soccer.service.impl.TeamServiceImpl;


@RestController
@PreAuthorize("authenticated")
@RequestMapping("/api/stats")
public class StatsController {

	@Autowired
	BidServiceImpl bidServiceImpl;

	@Autowired
	SaleServiceImpl saleServiceImpl;

	@Autowired
	TeamServiceImpl teamServiceImpl;

	@Autowired
	StatServiceImpl statServiceImpl;

	@Autowired
	PlayerServiceImpl playerServiceImpl;

	@GetMapping("sales/bids/days/{n_day}/successful")
	public LinkedHashMap<String, Object> getSalesStatsOK(@PathVariable Long n_day) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		try {
			List<Sale> saleBids = statServiceImpl.getSalesStats(n_day, true);

			map.put("success", true);
			map.put("Total_sales_closed", saleBids.size());
			map.put("From_date", statServiceImpl.initDateInterval(n_day));
			map.put("Sale", saleBids);

		} catch (Exception e) {

			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}

		return map;
	}

	@GetMapping("sales/bids/days/{n_day}/failed")
	public LinkedHashMap<String, Object> getSalesStatsKO(@PathVariable Long n_day) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		try {
			List<Sale> saleBids = statServiceImpl.getSalesStats(n_day, false);

			map.put("success", true);
			map.put("Total_sales_closed", saleBids.size());
			map.put("From_date", statServiceImpl.initDateInterval(n_day));
			map.put("Sale", saleBids);

		} catch (Exception e) {

			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}
		return map;
	}

	@GetMapping("sales/bids/average")
	public LinkedHashMap<Object, Object> getAverageBidsperSales() {
		LinkedHashMap<Object, Object> map = new LinkedHashMap<>();

		try {

			List<StatSaleJson> listAverageStats = statServiceImpl.getBidsperSalesJson();
			double iTotalBids = bidServiceImpl.getAllBidsClosed().size();
			double iTotalSales = saleServiceImpl.listAllSalesClosed().size();

			double iAverage = statServiceImpl.average(iTotalBids, iTotalSales);
			double iTotalSalesBids = statServiceImpl.getTotalSalesBid(listAverageStats);

			map.put("Total_bids_closed", (int) iTotalBids);
			map.put("Total_sales_closed", (int) iTotalSales);
			map.put("Total_sales_bids", (int) iTotalSalesBids);
			map.put("Average", iAverage);
			map.put("Sales", listAverageStats);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}
		return map;
	}

	@GetMapping("sales/bids/max")
	public LinkedHashMap<Object, Object> getMaxBidsperSales() {
		LinkedHashMap<Object, Object> map = new LinkedHashMap<>();

		try {
			List<StatSaleJson> listCountBidsSaleStats = statServiceImpl.getBidsperSalesJson();
			List<StatSaleJson> listStatMaxBids = statServiceImpl.maximumSaleBids();

			double iTotalBids = bidServiceImpl.getAllBidsClosed().size();
			double iTotalSales = saleServiceImpl.listAllSalesClosed().size();
			double iMaxBids = listCountBidsSaleStats.get(0).getTotalBids();

			map.put("Total_bids_closed", (int) iTotalBids);
			map.put("Total_sales_closed", (int) iTotalSales);
			map.put("Maximum_bid", (int) iMaxBids);
			map.put("Maximum_sale", listStatMaxBids);
			map.put("Sales", listCountBidsSaleStats);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}
		return map;
	}

	@GetMapping("sales/bids/buyer/most")
	public LinkedHashMap<Object, Object> getMostBuyer() {
		LinkedHashMap<Object, Object> map = new LinkedHashMap<>();

		try {

			List<StatTeamJson> listCountBidsTeamStats = statServiceImpl.getBidsperTeamsJson();
			List<StatTeamJson> listStatMaxBidsPrice = statServiceImpl.getMostBuyer();
			double iTotalSales = saleServiceImpl.listAllSalesClosed().size();

			double iTotalBids = bidServiceImpl.getAllBidsClosed().size();
			Collections.sort(listCountBidsTeamStats, Comparator.comparing(StatTeamJson::getMax_price).reversed());
			double iMaxBids = listCountBidsTeamStats.get(0).getMax_price();

			map.put("Total_bids_closed", (int) iTotalBids);
			map.put("Total_sales_closed", (int) iTotalSales);
			map.put("Maximum_price_bids", iMaxBids);
			map.put("Most_buyer", listStatMaxBidsPrice);
			map.put("Bids", listCountBidsTeamStats);

			map.put("success", true);

		} catch (Exception e) {

			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}

		return map;
	}

	@GetMapping("sales/bids/buyer/most/bidder")
	public LinkedHashMap<Object, Object> getMostBuyerBidder() {
		LinkedHashMap<Object, Object> map = new LinkedHashMap<>();

		try {
			List<StatTeamJson> listCountBidsTeamStats = statServiceImpl.getBidsperTeamsJson();
			List<StatTeamJson> listStatMaxBids = statServiceImpl.getMostBuyerBids();

			double iTotalBids = bidServiceImpl.getAllBidsClosed().size();
			Collections.sort(listCountBidsTeamStats, Comparator.comparing(StatTeamJson::getTotal_bids).reversed());
			double iMaxBids = listCountBidsTeamStats.get(0).getTotal_bids();

			map.put("Total_bids", (int) iTotalBids);
			map.put("Maximum_bids", (int) iMaxBids);
			map.put("Most_bidder", listStatMaxBids);
			map.put("Bids", listCountBidsTeamStats);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}
		return map;
	}

	@GetMapping("sales/bids/seller/most")
	public LinkedHashMap<Object, Object> getMostSeller() {
		LinkedHashMap<Object, Object> map = new LinkedHashMap<>();

		try {
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "There were no sales in that period of time, sorry!");
		}

		return map;
	}

}
