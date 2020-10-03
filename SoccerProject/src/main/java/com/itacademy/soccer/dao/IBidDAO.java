package com.itacademy.soccer.dao;

import com.itacademy.soccer.dto.Sale;
import com.itacademy.soccer.dto.Team;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Bid;
import com.itacademy.soccer.dto.Player;

import java.util.List;

public interface IBidDAO extends JpaRepository<Bid,Long>{

   
    List<Bid> findBySaleIs(Sale sale);

	List<Bid> findByTeamIs(Team team);

//	List<Bid> findByPlayerIs(Player player);
     
}
