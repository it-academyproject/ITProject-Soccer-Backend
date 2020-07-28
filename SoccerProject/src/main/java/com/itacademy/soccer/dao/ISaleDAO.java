package com.itacademy.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Sale;

public interface ISaleDAO extends JpaRepository<Sale,Long>{

}
