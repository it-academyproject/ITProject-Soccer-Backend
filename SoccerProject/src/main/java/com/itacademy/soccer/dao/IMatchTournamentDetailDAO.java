package com.itacademy.soccer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.MatchTournamentDetail;

public interface IMatchTournamentDetailDAO extends JpaRepository<MatchTournamentDetail, Long>{

	List<MatchTournamentDetail> findByRoundNumber (int RoundNumber);
	
}
