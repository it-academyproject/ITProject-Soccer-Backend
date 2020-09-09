package com.itacademy.soccer.game;

import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.service.impl.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class VerifyDataTeam {

//    @Autowired
//    TeamServiceImpl teamServiceImpl;

    public void createTeamInitial(TeamServiceImpl teamService){
        List<Team> teamsToShow = teamService.getAllTeams();
        if ( teamsToShow.size() == 0){
            Team team1 = new Team();

            team1.setName("Free market");
            team1.setFoundation_date(new Date());
            team1.setBadge("x");
            team1.setBudget(0F);
            team1.setWins(0);
            team1.setLosses(0);
            team1.setDraws(0);

            teamService.createTeam(team1);
        }
    }
}
