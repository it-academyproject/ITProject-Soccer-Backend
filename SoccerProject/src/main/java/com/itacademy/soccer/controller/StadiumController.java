package com.itacademy.soccer.controller;


import com.itacademy.soccer.controller.json.StadiumJson;
import com.itacademy.soccer.dto.Stadium;
import com.itacademy.soccer.game.VerifyDataStadium;
import com.itacademy.soccer.service.IStadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/stadium")
public class StadiumController {

    @Qualifier("IStadiumService")
    @Autowired
    IStadiumService iStadiumService;
    HashMap<String,Object> map = new HashMap<>();

    @GetMapping
    HashMap<String,Object> getAllStadium(){

        map.clear();
        List<Stadium> stadiumList = iStadiumService.findAll();

        if (stadiumList.size() > 0 ) {
            map.put("success", true);
            map.put("Stadiums: ", stadiumList);
            map.put("message", "get all stadiums");
        }else {
            map.put("success", false);
            map.put("message", "there are no stadiums in bbdd");
        }
        return map;
    }
    @GetMapping("/id")
    HashMap<String,Object> getStadium(@RequestBody StadiumJson s){
        Long das;
        System.out.println( "................strin " + s.getAa());
        map.clear();
        try{
            das = Long.parseLong(s.getAa());
        }catch (Exception e){
            map.put("success", false);
            map.put("message", "Incorrect data : " + s.getAa());
            return map;
        }
        Stadium stadium= iStadiumService.findByStadiumId( das);

        if (stadium != null ) {
            map.put("success", true);
            map.put("Stadium: ", stadium);
            map.put("message", "get stadium ");
        }else {
            map.put("success", false);
            map.put("message", "there are no stadium whith id: " + das);
        }
        return map;
    }
    @PostMapping
    HashMap<String,Object> postStadium(@RequestBody Stadium stadium){

        try {
            map.clear();
            map = new VerifyDataStadium().verifyData(stadium, iStadiumService, map);

            if ( map.size() == 0) {
                map.put("success", true);
                map.put("stadium ", stadium);
                map.put("message", " create stadium");
                iStadiumService.save(stadium);
            }
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", "something went wrong: " + e.getMessage());
        }
        return map;
    }
    @PutMapping("/update")
    public HashMap<String,Object> updateStadium(@RequestBody Stadium stadium){
        map.clear();
        try{
            map = new VerifyDataStadium().verifyData(stadium, iStadiumService, map);

            if ( map.size() == 0) {
                iStadiumService.save(stadium);
                map.put("success", true);
                map.put("stadium", stadium);
                map.put("update", HttpStatus.OK);
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("message", "something went wrong: " + e.getMessage());
        }
        return map;
    }
//    @DeleteMapping("/id")
//    public HashMap<String,Object> deleteStadium(@RequestBody StadiumJson s){
//        map.clear();
//        try {
//            iStadiumService.deleteById(s.getId());
//            map.put("success", true);
//            map.put("delete", HttpStatus.OK);
//        }catch (Exception e) {
//            map.put("success", false);
//            map.put("message", "something went wrong: " + e.getMessage());
//        }
//        return map;
//
//    }

}
