package com.itacademy.soccer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itacademy.soccer.dto.serializable.PlayerMatchId;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class PlayerActionResult {
    @Id
    @JsonProperty("player_id")
    private Long playerId;
    private String name;
    private int number;


    //Empty constructor
    public PlayerActionResult() {
    }


    public PlayerActionResult(Long playerId, String name, int number) {
        this.playerId = playerId;
        this.name = name;
        this.number = number;
    }


    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}




