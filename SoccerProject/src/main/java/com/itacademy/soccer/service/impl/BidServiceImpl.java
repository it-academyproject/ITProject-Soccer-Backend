package com.itacademy.soccer.service.impl;

import java.util.Date;
import java.util.List;

import com.itacademy.soccer.dto.Player;
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
	public Bid createBidBySale(Long saleId, Bid bid) { //El id del team esta incluido en "bid.getTeam().getId()"

		Team team = iTeamDAO.findById( bid.getTeam().getId() ).get();
		Sale sale = iSaleDAO.findById(saleId).get();
		bid.setSale(sale);
		bid.setTeam(team);
		bid.setTeam_id(team.getId());
		
		if (bid.getOperationDate()==null) bid.setOperationDate(new Date()); //si no hay fecha poner la actual
		
		return iBidDAO.save(bid);
	}

	@Override
	public Bid updateBid(Long bidId, Bid bid) { //modifica solo el precio de la bid con id bidId

		Bid updatedBid = iBidDAO.findById(bidId).get();
		
		updatedBid.setBidPrice(bid.getBidPrice());
		
		return iBidDAO.save(updatedBid);
	}

	@Override
	public void deleteBid(Long bidId) {
		
		iBidDAO.deleteById(bidId);
	}

	@Override
	public void deleteLastBidBySale(Long saleId) throws Exception{

		Sale sale = iSaleDAO.findById(saleId).get();
		
		List<Bid> listBids = sale.getBids();
		
		if (listBids.size()==0) throw new Exception("There aren't any bids for this sale");
		
		Bid lastBid = listBids.get(0);
		
		//recorre todas las pujas (bids) y guarda en lastBid la última puja (fecha operationDate mas reciente)
		for( Bid bid : listBids) { 
			Date date = bid.getOperationDate();
			if ( date.after(lastBid.getOperationDate()) ) {
				lastBid = bid;
			}
		}
		//elimina la última puja
		iBidDAO.deleteById(lastBid.getId());
		
	}


	@Override
	public Bid save(Bid bid) {
		return iBidDAO.save(bid);
	}

	@Override
	public List<Bid> getBidsBySale (Sale sale)  { return iBidDAO.findBySaleIs(sale);}


	@Override
	public List<Bid> getAllBids() {
		
		return iBidDAO.findAll();
	}

	@Override
	public List<Bid> getBidsByTeams(Team team) {
		
		return iBidDAO.findByTeamIs(team);
	}

}
