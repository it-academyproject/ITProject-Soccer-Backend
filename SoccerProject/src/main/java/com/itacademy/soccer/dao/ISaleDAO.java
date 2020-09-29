package com.itacademy.soccer.dao;

import com.itacademy.soccer.dto.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Sale;

import java.util.Date;
import java.util.List;

public interface ISaleDAO extends JpaRepository<Sale,Long>{

    List<Sale> findSalesByPlayer(Player player);

    //TODO B29
    Sale findSalesById(Long saleId);
    List<Sale> findByLimitDateIsBetween(Date timeago, Date now);
}
