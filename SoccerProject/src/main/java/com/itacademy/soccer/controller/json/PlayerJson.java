package com.itacademy.soccer.controller.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itacademy.soccer.dto.Player;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerJson {

    private String id;
    private String team_id;
    private String name;
    private String age;
    private String aka;
    private String keeper;
    private String defense;
    private String pass;
    private String attack;
    
    @JsonProperty("number_assists")
    private Integer numberOfAssists;
    @JsonProperty("number_fouls")
    private Integer numberOfFouls;
    @JsonProperty("number_goals")
    private Integer numberOfGoals;
    @JsonProperty("number_red_cards")
    private Integer numberOfRedCards;
    @JsonProperty("number_saves")
    private Integer numberOfSaves;
    @JsonProperty("number_yellow_cards")
    private Integer numberOfYellowCards;

    public PlayerJson() {
    }

    public PlayerJson(String id, String aka, String name, String age,
                      String keeper, String defense, String pass, String attack, String team_id){
        this.id = id;
        this.aka = aka;
        this.name = name;
        this.age = age;
        this.keeper = keeper;
        this.defense = defense;
        this.pass = pass;
        this.attack = attack;
        this.team_id = team_id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAka() {
        return aka;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public Integer getNumberOfAssists() {
        return numberOfAssists;
    }

    public void setNumberOfAssists(Integer numberOfAssists) {
        this.numberOfAssists = numberOfAssists;
    }

    public Integer getNumberOfFouls() {
        return numberOfFouls;
    }

    public void setNumberOfFouls(Integer numberOfFouls) {
        this.numberOfFouls = numberOfFouls;
    }

    public Integer getNumberOfGoals() {
        return numberOfGoals;
    }

    public void setNumberOfGoals(Integer numberOfGoals) {
        this.numberOfGoals = numberOfGoals;
    }

    public Integer getNumberOfRedCards() {
        return numberOfRedCards;
    }

    public void setNumberOfRedCards(Integer numberOfRedCards) {
        this.numberOfRedCards = numberOfRedCards;
    }

    public Integer getNumberOfSaves() {
        return numberOfSaves;
    }

    public void setNumberOfSaves(Integer numberOfSaves) {
        this.numberOfSaves = numberOfSaves;
    }

    public Integer getNumberOfYellowCards() {
        return numberOfYellowCards;
    }

    public void setNumberOfYellowCards(Integer numberOfYellowCards) {
        this.numberOfYellowCards = numberOfYellowCards;
    }

    public Player toPlayer() {
        Player player = new Player();
        player.setId(Long.parseLong(this.getId()));
        player.setName(this.getName());
        player.setAge(Integer.parseInt(this.getAge()));
        player.setAka(this.getAka());
        player.setTeam_id(Long.parseLong(this.getTeam_id()));
        player.setKeeper(Integer.parseInt(this.getKeeper()));
        player.setDefense(Integer.parseInt(this.getDefense()));
        player.setPass(Integer.parseInt(this.getPass()));
        player.setAttack(Integer.parseInt(this.getAttack()));
        return player;
    }

    public static PlayerJson parsePlayerToJson(Player player) {
        return new PlayerJson() {
            {
                setId(player.getId().toString());
                setName(player.getName());
                setAge(String.valueOf(player.getAge()));
                setAka(player.getAka());
                setTeam_id(player.getTeam_id().toString());
                setKeeper(String.valueOf(player.getKeeper()));
                setDefense(String.valueOf(player.getDefense()));
                setPass(String.valueOf(player.getPass()));
                setAttack(String.valueOf(player.getAttack()));
                setNumberOfAssists(player.getNumberOfAssists());
                setNumberOfFouls(player.getNumberOfFouls());
                setNumberOfGoals(player.getNumberOfGoals());
                setNumberOfRedCards(player.getNumberOfRedCards());
                setNumberOfSaves(player.getNumberOfSaves());
                setNumberOfYellowCards(player.getNumberOfYellowCards());
            }
        };
    }

}
