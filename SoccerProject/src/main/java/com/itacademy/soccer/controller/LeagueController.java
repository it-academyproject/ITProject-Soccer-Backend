package com.itacademy.soccer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.itacademy.soccer.dto.League;
import com.itacademy.soccer.service.ILeagueService;

import java.util.List;


@RestController
@RequestMapping("/api")

public class LeagueController {


    @Autowired
    ILeagueService iLeagueService;



    @GetMapping("/leagues") // SHOW ALL LEAGUES ADMIN
    public List<League> showAllUsers()
    {
        return iLeagueService.showAllLeagues();
    }

 
}