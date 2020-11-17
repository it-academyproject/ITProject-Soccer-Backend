package com.itacademy.soccer.controller;


import com.itacademy.soccer.controller.json.StadiumJson;
import com.itacademy.soccer.dao.IStadiumDAO;
import com.itacademy.soccer.dto.Stadium;
import com.itacademy.soccer.service.IStadiumService;
import com.itacademy.soccer.service.impl.MatchServiceImpl;
import com.itacademy.soccer.service.impl.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@PreAuthorize("authenticated")
@RequestMapping("/api/stadiums")
public class StadiumController {

    @Autowired
    IStadiumDAO iStadiumDAO;
    @Autowired
    MatchServiceImpl matchServiceImpl;
    @Autowired
    StadiumService stadiumService;
    HashMap<String,Object> map = new HashMap<>();

    @GetMapping
    HashMap<String,Object> getAllStadium(){
        map.clear();
        List<Stadium> stadiumList = iStadiumDAO.findAll();

        if (stadiumList.size() > 0 ) {
            map.put("success", true);
            map.put("Stadiums: ", stadiumList);
            map.put("message", "get all stadiums");
        }else {
            map.put("success", false);
            map.put("message", "there are no stadiums in the database");
        }
        return map;
    }
    @GetMapping("/{id}")
    HashMap<String,Object> getStadium(@PathVariable String id){
        map.clear();
        map = stadiumService.verifyIds(id, map);

        if ( map.size() == 0) {

            Stadium stadium= iStadiumDAO.findByStadiumId( Long.parseLong( id ));

            if (stadium != null) {
                map.put("success", true);
                map.put("Stadium: ", stadium);
                map.put("message", "get stadium ");
            } else {
                map.put("success", false);
                map.put("message", "there are no stadium whith id: " + id);
            }
            return map;
        }
        return map;
    }
    @PostMapping
    HashMap<String,Object> postStadium(@RequestBody StadiumJson stadium){
        try {
            map.clear();
            map = stadiumService.verifyStrings(stadium, iStadiumDAO, map);
            map = stadiumService.verifyNumericPost(stadium, map);

            if ( map.size() == 0) {
                Stadium s = stadiumService.insertStadium(stadium);
                map.put("success", true);
                map.put("stadium ", s);
                map.put("message", " create stadium");
                iStadiumDAO.save(s);
            }
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", "something went wrong: " + e.getMessage());
        }
        return map;
    }
    @PutMapping
    public HashMap<String,Object> updateStadium(@RequestBody StadiumJson stadium){
        map.clear();

        try{
            map = stadiumService.verifyNumericPut(stadium, map);

            if (map.size() == 0 ) map = stadiumService.verifyStrings(stadium, iStadiumDAO, map);
            else return map;
            Stadium s = stadiumService.putStadium(stadium);

            if ( map.size() == 0) {
                iStadiumDAO.save(s);
                map.put("success", true);
                map.put("stadium update", s);
                map.put("message", HttpStatus.OK);
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("message", "something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    //TODO No funciona ... Fallo al ir a la capa service a buscar Match por el Id, prefiero no tocar esa capa service que no he hecho yo.
    @PutMapping("/stadiumId/matchId")
    public HashMap<String, Object> addMatch(@RequestBody StadiumJson s) {
        map.clear();
        try{
         //   map = verifyDataStadium.verifyIds(s, map);

            if ( map.size() == 0) {

//                Stadium stadium = iStadiumService.findByStadiumId(Long.parseLong(s.getId()));
//                Optional<Match> match = matchServiceImpl.findById(Long.parseLong(s.getId2()));
//                stadium = new InsertData().addMatch(stadium, match);
//                iStadiumService.save(stadium);
//                map.put("success", true);
//                map.put("stadium", stadium);
                map.put("update", HttpStatus.OK);
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("message", "something went wrong: " + e.getMessage());
        }
        return map;
    }

    @DeleteMapping
    public HashMap<String,Object> deleteStadium(@RequestBody StadiumJson id){
        map.clear();
        map = stadiumService.verifyIds(id.getId(), map);

        if ( map.size() == 0) {
            try {
                iStadiumDAO.deleteById(Long.parseLong( id.getId() ));
                map.put("success", true);
                map.put("message", HttpStatus.OK);
            } catch (Exception e) {
                map.put("success", false);
                map.put("message", "something went wrong: " + e.getMessage());
            }
        }
        return map;
    }

}
