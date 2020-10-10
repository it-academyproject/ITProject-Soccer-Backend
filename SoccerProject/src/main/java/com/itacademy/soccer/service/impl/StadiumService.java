package com.itacademy.soccer.service.impl;


import com.itacademy.soccer.controller.json.StadiumJson;
import com.itacademy.soccer.dao.IStadiumDAO;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Stadium;
import com.itacademy.soccer.service.IStadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class StadiumService implements IStadiumService {

    @Autowired
    IStadiumDAO repo;

    public HashMap<String,Object> verifyStrings(StadiumJson stadium, IStadiumDAO iStadiumDAO, HashMap<String, Object> map){

        if ( stadium.getCity().isEmpty()){
            map.put("success", false);
            map.put("message", "city cant be empty");
        }
        if ( stadium.getName().isEmpty()){
            map.put("success", false);
            map.put("message", "name cant be empty");
        }else {
            List<Stadium> stadiumList = iStadiumDAO.findAll();

            for (Stadium x: stadiumList) {
                // condicional para el POST
                if (x.getName().equalsIgnoreCase(stadium.getName()) && stadium.getId() == null ) {
                    map.put("success", false);
                    map.put("message", "name stadium already exists");
                    return map;
                }else
                    // condicional para el PUT, que permita repetir nombre oero en el mismo Id
                    if (x.getName().equalsIgnoreCase(stadium.getName()) && x.getStadiumId() != Long.parseLong(stadium.getId()) ) {
                        map.put("success", false);
                        map.put("message", "name stadium already exists");
                        return map;
                    }
            }
        }
        return map;
    }
    public HashMap<String,Object> verifyNumericPost(StadiumJson s, HashMap<String, Object> map){


        try{

            if ( !s.getCapacity().isEmpty() )  Integer.parseInt(s.getCapacity());
            else {
                messageError("Capacity");
                return map;
            }
            if ( !s.getAnnual_income().isEmpty() ) Double.parseDouble(s.getAnnual_income());
            else {
                messageError("Capacity");
                return map;
            }
            if ( (Integer.parseInt( s.getCapacity()) < 0 ) || ( Double.parseDouble(s.getAnnual_income()) < 0 )) {
                map.put("success", false);
                map.put("message", "Incorrect data Not < 0 " );
                return map;
            }
        }catch (Exception e){
            map.put("success", false);
            map.put("message", "Incorrect data  " );
        }
        return map;
    }
    public HashMap<String,Object> verifyNumericPut(StadiumJson s, HashMap<String, Object> map){

        Long id;  double annual;  int capaci;

        try{

            if ( !s.getId().isEmpty() ) {
                id = Long.parseLong(s.getId());
            }
            else {
                map = messageError(" Id");
                return map;
            }

            if ( !s.getCapacity().isEmpty()) {
                capaci = Integer.parseInt(s.getCapacity());
            }
            else {
                map = messageError("Capacity");
                return map;
            }

            if ( !s.getAnnual_income().isEmpty() )  {
                annual = Double.parseDouble(s.getAnnual_income());
            }
            else {
                map = messageError( "Annual income");
                return map;
            }
            if ( (Integer.parseInt( s.getCapacity()) < 0 ) || ( Double.parseDouble(s.getAnnual_income()) < 0 )) {
                map.put("success", false);
                map.put("message", "Incorrect data Not < 0 " );
                return map;
            }
        }catch (Exception e){
            map.put("success", false);
            map.put("message", "Incorrect data : " + s.getId());
        }
        return map;
    }
    public HashMap<String,Object> messageError(String data){
        HashMap<String,Object> map = new HashMap<>();
        map.put("success", false);
        map.put("message", "it cant be empty: " + data);
        return map;
    }

    public HashMap<String,Object> verifyIds(String ids, HashMap<String, Object> map){
        Long id;
        try{
            id = Long.parseLong( ids );

            //  if ( s.getId2() != null) id = Long.parseLong(s.getId2());
        }catch (Exception e){
            map.put("success", false);
            map.put("message", "Incorrect data Id : " + ids);
        }
        return map;
    }
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
