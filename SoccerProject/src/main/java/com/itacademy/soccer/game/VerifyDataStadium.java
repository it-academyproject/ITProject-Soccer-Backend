package com.itacademy.soccer.game;


import com.itacademy.soccer.controller.json.StadiumJson;
import com.itacademy.soccer.dto.Stadium;
import com.itacademy.soccer.service.IStadiumService;

import java.util.HashMap;
import java.util.List;

public class VerifyDataStadium {

    public HashMap<String,Object> verifyStrings(StadiumJson stadium, IStadiumService iStadiumService, HashMap<String, Object> map){

        if ( stadium.getCity().isEmpty()){
            map.put("success", false);
            map.put("city", "it cant be empty");
        }
        if ( stadium.getName().isEmpty()){
            map.put("success", false);
            map.put("name", "it cant be empty");
        }else {
            List<Stadium> stadiumList = iStadiumService.findAll();

            for (Stadium x: stadiumList) {
                // condicional para el POST
                if (x.getName().equalsIgnoreCase(stadium.getName()) && stadium.getId() == null ) {
                    map.put("success", false);
                    map.put("name stadium", "it already exists");
                    return map;
                }else
                    // condicional para el PUT, que permita repetir nombre oero en el mismo Id
                if (x.getName().equalsIgnoreCase(stadium.getName()) && x.getStadiumId() != Long.parseLong(stadium.getId()) ) {
                    map.put("success", false);
                    map.put("name stadium", "it already exists");
                    return map;
                }
            }
        }
        return map;
    }
    public HashMap<String,Object> verifyNumericPost(StadiumJson s, HashMap<String, Object> map){
        double annual;  int capaci;

        try{
            if ( !s.getCapacity().isEmpty() ) capaci = Integer.parseInt(s.getCapacity());
            else {
                messageError("Capacity");
                return map;
            }

            if ( !s.getAnnual_income().isEmpty() ) annual = Double.parseDouble(s.getAnnual_income());
            else {
                messageError("Capacity");
                return map;
            }
        }catch (Exception e){
            map.put("success", false);
            map.put("messages", "Incorrect data  " );
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

    public HashMap<String,Object> verifyIds(StadiumJson s, HashMap<String, Object> map){
        Long  id;
        try{
            id = Long.parseLong(s.getId());

            if ( s.getId2() != null) id = Long.parseLong(s.getId2());
        }catch (Exception e){
            map.put("success", false);
            map.put("messages", "Incorrect data : " + s.getId());
        }
        return map;
    }
}
