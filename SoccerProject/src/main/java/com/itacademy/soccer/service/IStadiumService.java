package com.itacademy.soccer.service;


import com.itacademy.soccer.controller.json.StadiumJson;
import com.itacademy.soccer.dao.IStadiumDAO;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Stadium;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public interface IStadiumService  {

    HashMap<String,Object> verifyStrings(StadiumJson stadium, IStadiumDAO iStadiumDAO, HashMap<String, Object> map);
    HashMap<String,Object> verifyNumericPost(StadiumJson s, HashMap<String, Object> map);
    HashMap<String,Object> verifyNumericPut(StadiumJson s, HashMap<String, Object> map);
    HashMap<String,Object> messageError(String data);
    HashMap<String,Object> verifyIds(String ids, HashMap<String, Object> map);
    Stadium insertStadium(StadiumJson s);
    Stadium putStadium(StadiumJson stadium);
    Stadium addMatch(Stadium stadium, Optional<Match> match);
}
