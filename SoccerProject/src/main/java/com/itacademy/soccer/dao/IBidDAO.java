package com.itacademy.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Bid;

public interface IBidDAO extends JpaRepository<Bid,Long>{

}
