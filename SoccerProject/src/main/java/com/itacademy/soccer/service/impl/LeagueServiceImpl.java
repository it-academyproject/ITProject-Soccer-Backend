package com.itacademy.soccer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.ILeagueDAO;
import com.itacademy.soccer.dto.League;
import com.itacademy.soccer.service.ILeagueService;

@Service
public class LeagueServiceImpl implements ILeagueService {
	
	@Autowired
	private ILeagueDAO iLeagueDAO;

	@Override
	public List<League> showAllLeagues() {
		
		return iLeagueDAO.findAll();
	}
	
	@Override
	public League getOneLeagueById(Long id) {		
		return iLeagueDAO.findById(id).get();
	}

	
	@Override
	public League updateLeague(League league) {
		return iLeagueDAO.save(league);	
		
	}

	
	@Override
	public League createLeague(League league) {		
		return iLeagueDAO.save(league);
	}

	@Override
	public void deleteLeagueById(Long id) {
		iLeagueDAO.deleteById(id);
		
	}

}
