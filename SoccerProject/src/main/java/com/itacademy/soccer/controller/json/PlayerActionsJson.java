package com.itacademy.soccer.controller.json;

public class PlayerActionsJson {

    private Long playerId;
    private Long matchId;
    private String action;

    public PlayerActionsJson() {
    }

    public PlayerActionsJson(Long playerId, Long matchId) {
        this.playerId = playerId;
        this.matchId = matchId;
    }

    public PlayerActionsJson(Long playerId) {
        this.playerId = playerId;
    }

    public PlayerActionsJson(Long playerId, Long matchId, String action) {
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

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }
}
