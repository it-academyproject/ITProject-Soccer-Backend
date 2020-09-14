package com.itacademy.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itacademy.soccer.dto.League;

public interface ILeagueDAO extends JpaRepository<League, Long>{

}
