package com.itacademy.soccer.dto;


import javax.persistence.*;

@Entity
@Table(name="total_player_actions")
public class TotalPlayerActions {

    @Id
    @Column(name = "player_id")
    private Long id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "player_id")
    private Player player;


    private int goals;
    private int assists;
    private int fouls;
    @Column(name="red_cards")
    private int redCards;
    @Column(name="yellow_cards")
    private int yellowCards;
    private int saves;



    //Constructors
    public TotalPlayerActions() {
    }

    public TotalPlayerActions(Long id, Player player, int goals, int assists, int fouls, int redCards, int yellowCards, int saves) {
        this.id = id;
        this.player = player;
        this.goals = goals;
        this.assists = assists;
        this.fouls = fouls;
        this.redCards = redCards;
        this.yellowCards = yellowCards;
        this.saves = saves;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getFouls() {
        return fouls;
    }

    public void setFouls(int fouls) {
        this.fouls = fouls;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getSaves() {
        return saves;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }
}