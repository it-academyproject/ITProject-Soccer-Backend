package com.itacademy.soccer.game;


import com.itacademy.soccer.controller.json.StadiumJson;
import com.itacademy.soccer.dto.Stadium;
import com.itacademy.soccer.service.IStadiumService;

import java.util.HashMap;
import java.util.List;

public class VerifyDataStadium {

    public HashMap<String,Object> verifyStrings(StadiumJson stadium, IStadiumService iStadiumService, HashMap<String, Object> map){
        List<Stadium> stadiumList = iStadiumService.findAll();
        if ( stadium.getCity().isEmpty()){
            map.put("success", false);
            map.put("city", "it cant be empty");
        }
        if ( stadium.getName().isEmpty()){
            map.put("success", false);
            map.put("name", "it cant be empty");
        }else {


            for (Stadium x: stadiumList) {
                System.out.println(" list ................................ " + x.getStadiumId() + "  " + x.getName());
                if (x.getName().equalsIgnoreCase(stadium.getName()) && x.getStadiumId() != Long.parseLong(stadium.getId()) ) {
                    map.put("success", false);
                    map.put("name stadium", "it already exists");
                    return map;
                }
            }
        }
        return map;
    }
    public HashMap<String,Object> verifyNumeric(StadiumJson s, HashMap<String, Object> map){
        Long  id;
        try{
             if ( s.getId() != null )   id = Long.parseLong(s.getId());
             else {
                 int capaci = Integer.parseInt(s.getCapacity());
                 double annual = Double.parseDouble(s.getCapacity());
             }
        }catch (Exception e){
            map.put("success", false);
            map.put("messages", "Incorrect data : " + s.getId());
        }
        return map;
    }
}
