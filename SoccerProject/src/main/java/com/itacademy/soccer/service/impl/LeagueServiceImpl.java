package com.itacademy.soccer.service.impl;

import java.util.List;

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

}
