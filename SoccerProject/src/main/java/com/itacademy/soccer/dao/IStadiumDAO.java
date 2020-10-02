package com.itacademy.soccer.dao;


import com.itacademy.soccer.controller.json.StadiumJson;
import com.itacademy.soccer.dto.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStadiumDAO extends JpaRepository<Stadium, Long> {

    Stadium findByStadiumId(Long stadiumId);

}
