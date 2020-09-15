package com.itacademy.soccer.game;

import com.itacademy.soccer.dto.PlayerActions;

public class DataForPlayerActions {

     public int getOnePlayerActionsInOneMatch(PlayerActions playerActions, String action){
        int data = 0;

        if ( action.equalsIgnoreCase("goals")) data = playerActions.getGoals();
        if ( action.equalsIgnoreCase("assists")) data = playerActions.getAssists();
        if ( action.equalsIgnoreCase("fouls")) data = playerActions.getFouls();
        if ( action.equalsIgnoreCase("red_cards")) data = playerActions.getRedCards();
        if ( action.equalsIgnoreCase("yellow_cards")) data = playerActions.getYellowCards();
        if ( action.equalsIgnoreCase("saves")) data = playerActions.getSaves();
        return data;
    }
}
