package com.itacademy.soccer.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itacademy.soccer.controller.json.SaleJson;
import com.itacademy.soccer.controller.json.StatSaleJson;
import com.itacademy.soccer.controller.json.StatTeamJson;
import com.itacademy.soccer.controller.json.TeamJson;
import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.IStatService;

@Service
public class StatServiceImpl implements IStatService {

	@Autowired
	BidServiceImpl bidServiceImpl;

	@Autowired
	TeamServiceImpl teamServiceImpl;

	@Autowired
	PlayerServiceImpl playerServiceImpl;

	@Autowired
	SaleServiceImpl saleServiceImpl;

	@Override
	public Date initDateInterval(Long id) {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DATE, (int) -id);
		return c.getTime();
	}

	@Override
	public double average(double iTotalBids, double iTotalSales) {
		double iAverage = 0;
		if (iTotalSales != 0) { // CONTROL DIVISION ENTRE CERO
			iAverage = iTotalBids / iTotalSales;
		}
		return iAverage;
	}

	@Override
	public double getTotalSalesBid(List<StatSaleJson> listAverageStats) {
		int iTotalSalesBids = 0;
		for (StatSaleJson statJson : listAverageStats) {
			if (statJson.getTotalBids() != 0) {
				iTotalSalesBids++;
			}
		}
		return iTotalSalesBids;
	}

	@Override
	public HashMap<Object, Integer> sortMapbyValue(HashMap<Object, Integer> sortMap) {
		HashMap<Object, Integer> sortMapValue = sortMap.entrySet().stream()
				.sorted((Map.Entry.<Object, Integer>comparingByValue().reversed()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return sortMapValue;
	}

	// GET TO SALES SUCCESSFUL / FAILED

	@Override
	public List<Sale> getSalesStats(Long id, boolean state) {

		Date initialDate = initDateInterval(id);
		List<Sale> allSalesPeriod = saleServiceImpl.saleListBetweenDates(initialDate);

		List<Sale> saleNoBids = new ArrayList<>();
		List<Sale> saleYesBids = new ArrayList<>();
		List<Sale> resultSale = new ArrayList<>();
		List<Bid> bidsSale = new ArrayList<>();

		resultSale = null;

		for (Sale sale : allSalesPeriod) {

			bidsSale = bidServiceImpl.getBidsBySale(sale);

			if (bidsSale.size() > 0) {
				saleYesBids.add(sale);
			} else {
				saleNoBids.add(sale);
			}
		}

		if (state) {
			resultSale = saleYesBids;
		} else {
			resultSale = saleNoBids;
		}
		return resultSale;
	}

	// GET -- MAXIMUM BIDS

	@Override
	public List<StatSaleJson> maximumSaleBids() {

		List<StatSaleJson> listStatMaxBids = new ArrayList<>();

		List<StatSaleJson> listCountBidsSaleStats = getBidsperSalesJson();

		Collections.sort(listCountBidsSaleStats, Comparator.comparing(StatSaleJson::getTotalBids).reversed());
		double iMaxBids = listCountBidsSaleStats.get(0).getTotalBids();

		for (StatSaleJson statJson : listCountBidsSaleStats) {

			if (statJson.getTotalBids() == iMaxBids) {
				StatSaleJson statSaleMaxBids = statJson;
				listStatMaxBids.add(statSaleMaxBids);
			}
		}
		return listStatMaxBids;
	}

	@Override
	public List<StatSaleJson> getBidsperSalesJson() {

		int iTotalBids = bidServiceImpl.getAllBidsClosed().size();
		List<Sale> allSales = saleServiceImpl.listAllSalesClosed();

		List<StatSaleJson> listSaleBids = new ArrayList<>();
		StatSaleJson statsJson = new StatSaleJson();

		for (Sale sale : allSales) {

			List<Bid> bidsSale = bidServiceImpl.getBidsBySale(sale);
			SaleJson saleJson = new SaleJson();

			saleJson.setId(sale.getId());
			saleJson.setLimitDate(sale.getLimitDate());
			saleJson.setInitialPrice(sale.getInitialPrice());
			saleJson.setPlayer(sale.getPlayer());

			if (bidsSale.size() < 0 && iTotalBids != 0) {
				statsJson = new StatSaleJson(saleJson, 0);
				listSaleBids.add(statsJson);
			} else {
				statsJson = new StatSaleJson(saleJson, bidsSale.size());
				listSaleBids.add(statsJson);
			}
		}
		Collections.sort(listSaleBids, Comparator.comparing(StatSaleJson::getTotalBids).reversed());

		return listSaleBids;
	}

	// GET -- MOST BUYER (TEAMS WITH MAX PRICE)

	@Override
	public List<StatTeamJson> getMostBuyerBids() {

		List<StatTeamJson> listStatMaxBids = new ArrayList<>();
		List<StatTeamJson> listCountBidsTeamStats = getBidsperTeamsJson();

		Collections.sort(listCountBidsTeamStats, Comparator.comparing(StatTeamJson::getTotal_bids).reversed());
		double iMaxBids = listCountBidsTeamStats.get(0).getTotal_bids();

		for (StatTeamJson statJson : listCountBidsTeamStats) {

			if (statJson.getTotal_bids() == iMaxBids) {
				StatTeamJson statSaleMaxBids = statJson;
				listStatMaxBids.add(statSaleMaxBids);
			}
		}
		return listStatMaxBids;
	}

	// GET -- MOST BUYER (TEAMS WITH MAX BIDS)

	@Override
	public List<StatTeamJson> getMostBuyer() {

		List<StatTeamJson> listStatMaxPrice = new ArrayList<>();
		List<StatTeamJson> listCountBidsTeamStats = getBidsperTeamsJson();

		Collections.sort(listCountBidsTeamStats, Comparator.comparing(StatTeamJson::getMax_price).reversed());
		double iMaxBids = listCountBidsTeamStats.get(0).getMax_price();

		for (StatTeamJson statJson : listCountBidsTeamStats) {

			if (statJson.getMax_price() == iMaxBids) {
				StatTeamJson statSaleMaxPrice = statJson;
				listStatMaxPrice.add(statSaleMaxPrice);
			}
		}
		return listStatMaxPrice;
	}

	@Override
	public List<StatTeamJson> getBidsperTeamsJson() {

		List<StatTeamJson> listTeamBids = new ArrayList<>();
		StatTeamJson statsJson = new StatTeamJson();

		int iTotalBids = bidServiceImpl.getAllBidsClosed().size();

		List<Team> allTeams = teamServiceImpl.getAllTeams();

		for (Team team : allTeams) {

			List<Bid> bidsTeam = bidServiceImpl.getBidsByTeams(team);
			TeamJson teamJson = new TeamJson();

			teamJson.setId(team.getId());
			teamJson.setName(team.getName());
			teamJson.setFoundationDate(team.getFoundationDate());
			teamJson.setBadge(team.getBadge());
			teamJson.setBudget(team.getBudget());
			teamJson.setWins(team.getWins());
			teamJson.setLosses(team.getLosses());
			teamJson.setDraws(team.getDraws());

			System.out.println("size bids " + bidsTeam.size());
			System.out.println("itotalbids " + iTotalBids);

			if (bidsTeam.size() <= 0 || bidsTeam == null) {
				statsJson = new StatTeamJson(teamJson, 0, 0);
				listTeamBids.add(statsJson);
			} else {
				Collections.sort(bidsTeam, Comparator.comparing(Bid::getBidPrice).reversed());
				float iMaxPrice = bidsTeam.get(0).getBidPrice();

				statsJson = new StatTeamJson(teamJson, bidsTeam.size(), iMaxPrice);
				listTeamBids.add(statsJson);
			}
		}
		return listTeamBids;
	}

}
