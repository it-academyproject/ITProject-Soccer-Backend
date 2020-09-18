package com.itacademy.soccer.controller;


import com.itacademy.soccer.controller.json.StadiumJson;
import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Stadium;
import com.itacademy.soccer.game.InsertData;
import com.itacademy.soccer.game.VerifyDataStadium;
import com.itacademy.soccer.service.IStadiumService;
import com.itacademy.soccer.service.impl.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stadium")
public class StadiumController {

    @Qualifier("IStadiumService")
    @Autowired
    IStadiumService iStadiumService;
    @Autowired
    MatchServiceImpl matchServiceImpl;
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
        map.clear();
        map = new VerifyDataStadium().verifyNumeric(s, map);

        if ( map.size() == 0) {
            Stadium stadium= iStadiumService.findByStadiumId( Long.parseLong(s.getId()));

            if (stadium != null) {
                map.put("success", true);
                map.put("Stadium: ", stadium);
                map.put("message", "get stadium ");
            } else {
                map.put("success", false);
                map.put("message", "there are no stadium whith id: " + s.getId());
            }
            return map;
        }
        return map;
    }
    @PostMapping
    HashMap<String,Object> postStadium(@RequestBody StadiumJson stadium){
        try {
            map.clear();
            map = new VerifyDataStadium().verifyStrings(stadium, iStadiumService, map);
            map = new VerifyDataStadium().verifyNumeric(stadium, map);

            if ( map.size() == 0) {
                Stadium s = new InsertData().insertStadium(stadium);
                map.put("success", true);
                map.put("stadium ", s);
                map.put("message", " create stadium");
                iStadiumService.save(s);
            }
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", "something went wrong: " + e.getMessage());
        }
        return map;
    }
    @PutMapping()
    public HashMap<String,Object> updateStadium(@RequestBody StadiumJson stadium){
        map.clear();
        try{
            map = new VerifyDataStadium().verifyStrings(stadium, iStadiumService, map);
            Stadium s = new InsertData().putStadium(stadium);

            if ( map.size() == 0) {
                iStadiumService.save(s);
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
    //TODO No funciona ... Fallo al ir a la capa service a buscar Match por el Id, prefiero no tocar esa capa service que no he hecho yo.
    @PutMapping("/stadiumId/matchId")
    public HashMap<String, Object> addMatch(@RequestBody StadiumJson s) {
        map.clear();
        try{
            map = new VerifyDataStadium().verifyIds(s, map);

            if ( map.size() == 0) {
//                System.out.println("maravilloso............111.....................");
//                Stadium stadium = iStadiumService.findByStadiumId(Long.parseLong(s.getId()));
//                System.out.println("maravilloso.................2222................"+ s.getId2());
//                Optional<Match> match = matchServiceImpl.findById(Long.parseLong(s.getId2()));
//                System.out.println("maravilloso................3333.................");
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

    @DeleteMapping("/id")
    public HashMap<String,Object> deleteStadium(@RequestBody StadiumJson s){
        map.clear();
        map = new VerifyDataStadium().verifyNumeric(s, map);

        if ( map.size() == 0) {
            try {
                iStadiumService.deleteById(Long.parseLong(s.getId()));
                map.put("success", true);
                map.put("delete", HttpStatus.OK);
            } catch (Exception e) {
                map.put("success", false);
                map.put("message", "something went wrong: " + e.getMessage());
            }
        }
        return map;
    }

}
