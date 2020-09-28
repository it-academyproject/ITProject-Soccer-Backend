package com.itacademy.soccer.controller.json;

public class PlayerActionsJson {

    private String playerId;
    private String matchId;
    private String action;

    public PlayerActionsJson() {
    }

    public PlayerActionsJson(String playerId, String matchId) {
        this.playerId = playerId;
        this.matchId = matchId;
    }

    public PlayerActionsJson(String playerId) {
        this.playerId = playerId;
    }

    public PlayerActionsJson(String playerId, String matchId, String action) {
        this.playerId = playerId;
        this.matchId = matchId;
        this.action = action;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
}
