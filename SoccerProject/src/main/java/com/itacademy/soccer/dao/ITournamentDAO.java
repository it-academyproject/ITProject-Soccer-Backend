package com.itacademy.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itacademy.soccer.dto.Tournament;

public interface ITournamentDAO extends JpaRepository<Tournament, Long>{

	Tournament findByName(String name);

}