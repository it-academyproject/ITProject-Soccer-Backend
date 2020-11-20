/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itacademy.soccer.dao.*;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Stadium;
import com.itacademy.soccer.dto.weather.Example;
import com.itacademy.soccer.dto.weather.Time;
import com.itacademy.soccer.dto.weather.TodaysWeather;
import com.itacademy.soccer.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.IMatchService;

/**
 * @author KevHaes
 *
 */
@Service
public class MatchServiceImpl implements IMatchService {
	/////////////// ATRIBUTES ///////////////
	@Autowired
	IMatchDAO iMatchDAO;
	@Qualifier("IPlayerActionsDAO")
	@Autowired
	IPlayerActionsDAO iPlayerActionsDAO;
	@Autowired
	ITeamDAO iTeamDAO;
	@Autowired
	IStadiumDAO iStadiumDAO;
	@Autowired
	IPlayerDAO iPlayerDAO;
	@Autowired
	PlayerActionsServiceImpl playerActionsService;

	@Autowired
	WeatherService weatherService;

	Logger logger = Logger.getLogger(MatchServiceImpl.class.getName());
/////////////// CONSTRUCTORS ///////////////



	@Override
	public List<Match> showAllMatches() {

		List<Match> matchesList = iMatchDAO.findAll();

		return matchesList;

	}

	@Override
	public List<Match> showAllmatchesForTeamByID(Long id) throws Exception{

		List<Match> matchesToShow = new ArrayList<>();

		for (Match match : showAllMatches()) {

			Team localTeam = match.getTeam_local();
			Team visitorTeam = match.getTeam_visitor();

			if (localTeam.getId().equals(id) || visitorTeam.getId().equals(id)) {
				matchesToShow.add(match);
			}
		}

		return matchesToShow;
	}

	@Override
	public Match createMatch(Long localTeamId, Long visitorTeamId, Date date, Long stadiumId) {
		Match matchToSave = new Match();
		try {
			Team localTeam = iTeamDAO.findById(localTeamId).get();
			Team visitorTeam = iTeamDAO.findById(visitorTeamId).get();
			Stadium stadium = iStadiumDAO.findByStadiumId(stadiumId);

			matchToSave.setTeam_local(localTeam);
			matchToSave.setTeam_visitor(visitorTeam);
			matchToSave.setLocal_goals(0);
			matchToSave.setVisitor_goals(0);


			//gets the weather when the match is created
			List<Example> fullWeather = weatherService.getWeather(stadium.getCity());
			String weather = fullWeather.get(0).getTodaysWeather().getToday().getDescription();
			matchToSave.setWeather(weather);



			//TODO - get the weather when the match is set in the future
			Long dateNow = System.currentTimeMillis();

			//diference in time (milliseconds) of time of the match and now
			Long diff = (date.getTime() - dateNow);

			//milliseconds to hours
			int hours   = (int) ((diff / (1000*60*60)) % 24);

			// periods is the number of time frames because the weather is given every 3 hours
			int periods = hours / 3;

			SimpleDateFormat dfinput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			/*
			Example fullWeather = weatherService.getWeatherForFive(stadium.getCity(),periods);

			//to get the weather description from the full weather information - NOT WORKING
			String weather = fullWeather.getForecastWeather().getAdditionalProperties().get(date.getTime()).getWeather().getDescription();
			 */


			matchToSave.setWeather(weather);

			matchToSave.setDate(date);

			iMatchDAO.save(matchToSave);
			playerActionsService.createActionsForPLayers(matchToSave, localTeam.getPlayersList());
			playerActionsService.createActionsForPLayers(matchToSave, visitorTeam.getPlayersList());

			// ****createMatchAcionsForMatch(matchToSave.getId());
			logger.info("message: CREATED ACTIONS ...........................................................");
		}catch (Exception e){
			logger.log( Level.SEVERE, " message: Error in  createMatch ........................");
			e.printStackTrace();
		}
		return matchToSave;
	}

//	public void createMatchAcionsForMatch(Long id){

	//To get the current weather when the match is played
	/*
	List<Example> fullWeather = weatherService.getWeather(stadium.getCity());
			String weather = fullWeather.get(0).getTodaysWeather().getToday().getDescription();
			matchToSave.setWeather(weather);
	 */
//
//	}

}
