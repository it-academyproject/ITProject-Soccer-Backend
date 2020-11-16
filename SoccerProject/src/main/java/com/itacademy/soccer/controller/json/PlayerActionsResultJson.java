package com.itacademy.soccer.controller.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itacademy.soccer.dto.Player;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerActionsResultJson extends PlayerJson {

    public PlayerActionsResultJson() {}

    public static PlayerJson parsePlayerToJson(Player player) {
        return new PlayerJson() {
            {
                setId(player.getId().toString());
                setName(player.getName());
                setNumberOfAssists(player.getNumberOfAssists());
                setNumberOfFouls(player.getNumberOfFouls());
                setNumberOfGoals(player.getNumberOfGoals());
                setNumberOfRedCards(player.getNumberOfRedCards());
                setNumberOfSaves(player.getNumberOfSaves());
                setNumberOfYellowCards(player.getNumberOfYellowCards());
            }
        };
    }
    public static PlayerJson parsePlayerToJson(Player player, String what) {
        return new PlayerJson() {
            {
                setId(player.getId().toString());
                setName(player.getName());

                switch (what) {
                    case "assists":
                        setNumberOfAssists(player.getNumberOfAssists());
                        break;

                    case "fouls":
                        setNumberOfFouls(player.getNumberOfFouls());
                        break;


                    case "goals":
                        setNumberOfGoals(player.getNumberOfGoals());
                        break;


                    case "saves":
                        setNumberOfSaves(player.getNumberOfSaves());
                        break;


                    case "red":
                        setNumberOfRedCards(player.getNumberOfRedCards());
                        break;


                    case "yellow":
                        setNumberOfYellowCards(player.getNumberOfYellowCards());
                        break;

                    default:
                        break;
                }
            }
        };
    }

}
