package com.itacademy.soccer.service;

import java.util.List;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;

public interface IBidService {

	public List<Bid> listBidsBySale(Long saleId); //Muestra todas las pujas(bids) de una venta(sale) en concreto
	
	public Bid createBidBySale(Long saleId, Bid bid); //Crea una nueva puja(bid) para una venta(sale) en concreto
	
	public Bid updateBid(Bid bid);
		
	public void deleteBid(Long bidId); //Elimina la puja(bid) con id "bidId"
	
	public void deleteLastBidBySale(Long saleId) throws Exception; //Elimina la última puja (más reciente)


	public Bid save(Bid bid);
	
	public List<Bid> getBidsBySale (Sale sale);

	public List<Bid> getAllBids();

	public List<Bid> getBidsByTeams(Team team);

	public List<Bid> getBidsBySaleDate(Sale sale);

	public List<Bid> getAllBidsClosed();

}
