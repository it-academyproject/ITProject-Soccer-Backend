package com.itacademy.soccer.controller.json;

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
}
