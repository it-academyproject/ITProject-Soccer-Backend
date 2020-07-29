package com.itacademy.soccer.service;

import java.util.List;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Sale;

public interface IBidService {

	public List<Bid> listBidsBySale(Long saleId);
	
	public Bid createBidBySale(Long saleId, Bid bid);
	
	public Bid updateBid(Long bidId, Bid bid);
		
	public Sale deleteBid(Long bidId);
	
	public void deleteLastBidBySale(Long saleId);
	
}
