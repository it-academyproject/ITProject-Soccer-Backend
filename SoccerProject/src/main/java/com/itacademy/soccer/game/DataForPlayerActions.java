package com.itacademy.soccer.game;

import com.itacademy.soccer.dto.PlayerActions;

import java.util.HashMap;

public class DataForPlayerActions {

    public int getOnePlayerActionsInOneMatch(PlayerActions playerActions, String action){
        int data = 0;

        if ( action.equalsIgnoreCase("goals")) data = playerActions.getGoals();
        else
            if ( action.equalsIgnoreCase("assists")) data = playerActions.getAssists();
            else
                if ( action.equalsIgnoreCase("fouls")) data = playerActions.getFouls();
                else
                    if ( action.equalsIgnoreCase("red_cards")) data = playerActions.getRedCards();
                    else
                        if ( action.equalsIgnoreCase("yellow_cards")) data = playerActions.getYellowCards();
                        else
                            if ( action.equalsIgnoreCase("saves")) data = playerActions.getSaves();
                                else return -1;
        return data;
    }
    public HashMap<String,Object> verifyIds(String ids, String id2, HashMap<String, Object> map){
        Long id;
        try{
            id = Long.parseLong( ids );
            System.out.println("vamos a ver ..................................  " + id2);

            if ( id2 != null) id = Long.parseLong( id2 );
        }catch (Exception e){
            map.put("success", false);
            map.put("message", "Incorrect data Id  " );
        }
        return map;
    }
}
