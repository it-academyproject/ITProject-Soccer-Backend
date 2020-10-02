package com.itacademy.soccer.dao;

import com.itacademy.soccer.dto.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Bid;

import java.util.List;

public interface IBidDAO extends JpaRepository<Bid,Long>{

    //TODO B29
    List<Bid> findBySaleIs(Sale sale);
     
}
