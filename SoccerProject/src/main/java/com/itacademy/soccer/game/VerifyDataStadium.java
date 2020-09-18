package com.itacademy.soccer.game;


import com.itacademy.soccer.dto.Stadium;
import com.itacademy.soccer.service.IStadiumService;

import java.util.HashMap;
import java.util.List;

public class VerifyDataStadium {

    public HashMap<String,Object> verifyData(Stadium stadium, IStadiumService iStadiumService, HashMap<String, Object> map){

        List<Stadium> stadiumList = iStadiumService.findAll();

        if ( stadium.getCity().isEmpty()){
            map.put("success", false);
            map.put("city", "it cant be empty");
        }
        if ( stadium.getName().isEmpty()){
            map.put("success", false);
            map.put("name", "it cant be empty");
        }else
            for ( Stadium x: stadiumList){

                if ( x.getName().equalsIgnoreCase(stadium.getName())){
                    map.put("success", false);
                    map.put("name stadium", "it already exists");
                }
            }
        return map;
    }
}
