package com.itacademy.soccer.service;

import com.itacademy.soccer.dto.Match;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.PlayerActions;

import java.util.HashMap;
import java.util.List;


public interface IPlayerActionsService  {

    void createActionsForPLayers(Match match, List<Player> playersList);
    void initializeActionsInMatch(Match match, Player player);
    int getOnePlayerActionsInOneMatch(PlayerActions playerActions, String action);
    HashMap<String,Object> verifyIds(String ids, String id2, HashMap<String, Object> map);
}
