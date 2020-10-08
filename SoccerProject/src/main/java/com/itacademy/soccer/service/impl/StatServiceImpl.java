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
		return  c.getTime();	
	}
	
	@Override
	public double average(double i_total_bids, double i_total_sales) {
		double i_average=0;				
		if (i_total_sales!=0) {			//CONTROL DIVISION ENTRE CERO				
			i_average = i_total_bids/i_total_sales; 				
		}
		return i_average;
	}
	
	@Override
	public double getTotalSalesBid(List<StatSaleJson> list_average_stats) {		
		int i_total_sales_bids=0;
		for (StatSaleJson statJson : list_average_stats) {				
			if( statJson.getTotal_bids() !=0 ){									
				i_total_sales_bids++;						
				}
		}
		return i_total_sales_bids;
	}
	

	@Override
	public HashMap<Object, Integer> sortMapbyValue(HashMap<Object, Integer> sortMap) {		
		HashMap<Object, Integer> sortMapValue = sortMap.entrySet()
        .stream()
        .sorted((Map.Entry.<Object, Integer>comparingByValue().reversed()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return sortMapValue;
	}

		
	//GET TO SALES SUCCESSFUL / FAILED
	
	@Override
	public List<Sale> getSalesStats(Long id, boolean state) {			
		
		Date initial_date = initDateInterval(id);			
		List<Sale> all_sales_period = saleServiceImpl.saleListBetweenDates(initial_date);		
		
		List<Sale> sale_no_bids= new ArrayList<>();	
		List<Sale> sale_yes_bids= new ArrayList<>();		
		List<Sale> result_sale= new ArrayList<>();	
		List<Bid> bids_sale = new ArrayList<>();	
		
		result_sale=null;
		
		for (Sale sale : all_sales_period) {		
		
				bids_sale = bidServiceImpl.getBidsBySale(sale);
				
				if (bids_sale.size()>0) {					
					sale_yes_bids.add(sale);				
				}else {				
					sale_no_bids.add(sale);
				}
			}
		
		if (state== true) {			
			result_sale = sale_yes_bids;				
		}else {			
			result_sale = sale_no_bids;		
		}					
		return result_sale;
	}
	

	//GET -- MAXIMUM BIDS
	
	@Override
	public List<StatSaleJson> maximumSaleBids() {		
		
		List<StatSaleJson> list_stat_max_bids = new ArrayList<>();		
		
		List<StatSaleJson> list_count_bids_sale_stats =  getBidsperSalesJson();			
		
		Collections.sort(list_count_bids_sale_stats, Comparator.comparing(StatSaleJson::getTotal_bids).reversed());		
		double i_max_bids=list_count_bids_sale_stats.get(0).getTotal_bids();
		
		for (StatSaleJson statJson : list_count_bids_sale_stats) {
			
			if(statJson.getTotal_bids()==i_max_bids) {				
				StatSaleJson stat_sale_max_bids = statJson;
				list_stat_max_bids.add(stat_sale_max_bids);				
			}			
		}				
		return list_stat_max_bids;
	}
	
	
	@Override
	public List<StatSaleJson> getBidsperSalesJson() {
	
		int i_total_bids = bidServiceImpl.getAllBidsClosed().size();			
		List<Sale> all_sales = saleServiceImpl.listAllSalesClosed();	
		
		List<StatSaleJson> list_sale_bids = new ArrayList<>(); 		
		StatSaleJson stats_json = new StatSaleJson();		
		
		for (Sale sale : all_sales) {			
			
				List<Bid> bids_sale = bidServiceImpl.getBidsBySale(sale);	
				SaleJson sale_json = new SaleJson();			
			
				sale_json.setId(sale.getId());				
				sale_json.setLimit_date(sale.getLimitDate());				
				sale_json.setInitial_price(sale.getInitialPrice());				
				sale_json.setPlayer_id(sale.getPlayer().getId());		
				
				if(bids_sale.size()<0 && i_total_bids !=0) {
					stats_json = new StatSaleJson(sale_json,0);
					list_sale_bids.add(stats_json);				
				}else {							
					stats_json = new StatSaleJson(sale_json , bids_sale.size());
					list_sale_bids.add(stats_json);				
				}
			}
		Collections.sort(list_sale_bids, Comparator.comparing(StatSaleJson::getTotal_bids).reversed());		
		
		return list_sale_bids;
	}
	
	//GET -- MOST BUYER (TEAMS WITH MAX PRICE)	

	@Override
	public List<StatTeamJson> getMostBuyerBids() {
		
		List<StatTeamJson> list_stat_max_bids = new ArrayList<>();			
		List<StatTeamJson> list_count_bids_team_stats =  getBidsperTeamsJson();		
		
		Collections.sort(list_count_bids_team_stats, Comparator.comparing(StatTeamJson::getTotal_bids).reversed());				
		double i_max_bids=list_count_bids_team_stats.get(0).getTotal_bids();
		
		for (StatTeamJson statJson : list_count_bids_team_stats) {
			
			if(statJson.getTotal_bids()==i_max_bids) {				
				StatTeamJson stat_sale_max_bids = statJson;
				list_stat_max_bids.add(stat_sale_max_bids);				
			}			
		}				
		return list_stat_max_bids;
	}
	
	//GET -- MOST BUYER (TEAMS WITH MAX BIDS)	
	
	@Override
	public List<StatTeamJson> getMostBuyer() {
		
		List<StatTeamJson> list_stat_max_price = new ArrayList<>();			
		List<StatTeamJson> list_count_bids_team_stats =  getBidsperTeamsJson();		
		
		Collections.sort(list_count_bids_team_stats, Comparator.comparing(StatTeamJson::getMax_price).reversed());				
		double i_max_bids=list_count_bids_team_stats.get(0).getMax_price();
		
		for (StatTeamJson statJson : list_count_bids_team_stats) {
			
			if(statJson.getMax_price()==i_max_bids) {				
				StatTeamJson stat_sale_max_price = statJson;
				list_stat_max_price.add(stat_sale_max_price);				
			}			
		}				
		return list_stat_max_price;
	}
	
	
	@Override
	public List<StatTeamJson> getBidsperTeamsJson() {
		
		List<StatTeamJson> list_team_bids = new ArrayList<>();		
		StatTeamJson stats_json = new StatTeamJson();
	
		int i_total_bids = bidServiceImpl.getAllBidsClosed().size();
		
		List<Team> all_teams = teamServiceImpl.getAllTeams();		
		
		
		for (Team team : all_teams) {
			
			List<Bid> bids_team = bidServiceImpl.getBidsByTeams(team);		
			TeamJson team_json = new TeamJson();	
			
			team_json.setId(team.getId());
			team_json.setName(team.getName());
			team_json.setFoundation_date(team.getFoundation_date());
			team_json.setBadge(team.getBadge());
			team_json.setBudget(team.getBudget());
			team_json.setWins(team.getWins());
			team_json.setLosses(team.getLosses());
			team_json.setDraws(team.getDraws());
			
			System.out.println("size bids " + bids_team.size());
			System.out.println("itotalbids " + i_total_bids);
			
			
			if(bids_team.size()<=0 || bids_team==null) {				
				stats_json = new StatTeamJson(team_json,0,0);
				list_team_bids.add(stats_json);				
			}else {			
				Collections.sort(bids_team, Comparator.comparing(Bid::getBid_price).reversed());				
				float i_max_price = bids_team.get(0).getBid_price();	
				
				stats_json = new StatTeamJson(team_json , bids_team.size(), i_max_price);
				list_team_bids.add(stats_json);				
			}						
		}			
		return list_team_bids;
	}
}
