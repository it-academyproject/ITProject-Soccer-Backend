package com.itacademy.soccer.game;

import com.itacademy.soccer.controller.json.StadiumJson;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Stadium;

import java.awt.print.PrinterException;
import java.util.Optional;

public class InsertData {

    public Stadium insertStadium(StadiumJson s){

        Stadium stadium = new Stadium();
        stadium.setStadiumId(stadium.getStadiumId());
        stadium.setName(s.getName());
        stadium.setCity(s.getCity());
        stadium.setCapacity(Integer.parseInt(s.getCapacity()));
        stadium.setAnnual_income(Double.parseDouble(s.getAnnual_income()));
        return stadium;
    }
    public Stadium putStadium(StadiumJson stadium){
        Stadium s = new Stadium();
        try {
            s.setStadiumId(Long.parseLong(stadium.getId()));
            s.setName(stadium.getName());
            s.setCity(stadium.getCity());
            s.setCapacity(Integer.parseInt(stadium.getCapacity()));
            s.setAnnual_income(Double.parseDouble(stadium.getAnnual_income()));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    public Stadium addMatch(Stadium stadium, Optional<Match> match){
        Match x = new Match();

        if ( match.isPresent()){
            x = match.get();
        }
        stadium.getMatchList().add(x);
        return stadium;
    }
}
