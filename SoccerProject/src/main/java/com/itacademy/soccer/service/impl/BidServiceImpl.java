package com.itacademy.soccer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IBidDAO;
import com.itacademy.soccer.dao.ISaleDAO;
import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.IBidService;

@Service
public class BidServiceImpl implements IBidService{

	@Autowired
	IBidDAO iBidDAO;
	
	@Autowired
	ISaleDAO iSaleDAO;
	
	@Autowired
	ITeamDAO iTeamDAO;
	
	@Override
	public List<Bid> listBidsBySale(Long saleId) { //OK
		
		Sale sale = iSaleDAO.findById(saleId).get();
		List<Bid> bidList = sale.getBids();
		
		return bidList;
	}

	@Override
	public Bid createBidBySale(Long saleId, Bid bid) { //FALTA PROBAR ESTE METODO!!!!

		Team team = iTeamDAO.findById( bid.getTeam().getId() ).get();
		Sale sale = iSaleDAO.findById(saleId).get();
		bid.setSale(sale);
		bid.setTeam(team);
		
		return iBidDAO.save(bid);
	}

	@Override
	public Bid updateBid(Long bidId, Bid bid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sale deleteBid(Long bidId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLastBidBySale(Long saleId) {
		// TODO Auto-generated method stub
		
	}

}
