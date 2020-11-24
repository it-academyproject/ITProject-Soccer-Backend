package com.itacademy.soccer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;

public interface IBidDAO extends JpaRepository<Bid, Long> {

	List<Bid> findBySaleId(Long saleId);
	
	List<Bid> findBySaleIs(Sale sale);

	List<Bid> findByTeamIs(Team team);

}
