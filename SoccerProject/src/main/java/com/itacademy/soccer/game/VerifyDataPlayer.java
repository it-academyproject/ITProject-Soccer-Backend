package com.itacademy.soccer.game;

import com.itacademy.soccer.dao.ITeamDAO;
import com.itacademy.soccer.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Null;

public class VerifyDataPlayer {



    public Player assignInitialValues(Player player ){

       if (player.getTeam_id() == null) player.setTeam_id(1L);
        return player;
    }


}