package com.itacademy.soccer.dto;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itacademy.soccer.dao.IPlayerActionsDAO;
import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Table(name="player")
public class Player {
	//Player atributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;
	private int age;
	private String aka;
	private int keeper;
	private int defense;
	private int pass;
	private int attack;
	@Transient
	@JsonProperty("total_skills")
	private int totalSkills;
	private Long team_id;

	//Error! duplicated declaration of team object
	//@ManyToOne
	//private Team team;
	
	@OneToMany
	@JsonProperty("player_actions")
	@JsonIgnore
	List<PlayerActions> playerActions;


	//relation with team
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id", insertable = false, updatable = false)
	@JsonIgnore
	private Team team;


	//Empty constructor
	public Player() {
	}

	public Player(Long id, @NotEmpty String name, int age, String aka, int keeper, int defense, int pass, int attack, Long team_id, List<PlayerActions> playerActions, Team team) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.aka = aka;
		this.keeper = keeper;
		this.defense = defense;
		this.pass = pass;
		this.attack = attack;
		this.team_id = team_id;
		this.playerActions = playerActions;
		this.team = team;

		
	}
	@JsonIgnore
	public int getNumberOfGoalsByPlayer() {
		int sum = 0;
		for (PlayerActions playerActions : playerActions) {
				sum = sum + playerActions.getGoals();
		}
		return sum;
	}

	@JsonIgnore
	public int getNumberOfFoulsByPlayer() {
		int sum = 0;
		for (PlayerActions playerActions : playerActions) {
			sum = sum + playerActions.getFouls();
		}
		return sum;
	}

	@JsonIgnore
	public int getNumberOfAssistsByPlayer() {
		int sum = 0;
		for (PlayerActions playerActions : playerActions) {
			sum = sum + playerActions.getAssists();
		}
		return sum;
	}
	@JsonIgnore
	public int getNumberOfRedCardsByPlayer() {
		int sum = 0;
		for (PlayerActions playerActions : playerActions) {
			sum = sum + playerActions.getRedCards();
		}
		return sum;
	}
	@JsonIgnore
	public int getNumberOfYellowCardsByPlayer() {
		int sum = 0;
		for (PlayerActions playerActions : playerActions) {
			sum = sum + playerActions.getYellowCards();
		}
		return sum;
	}
	@JsonIgnore
	public int getNumberOfSavesByPlayer() {
		int sum = 0;
		for (PlayerActions playerActions : playerActions) {
			sum = sum + playerActions.getSaves();
		}
		return sum;
	}
	//getters and setters


	public Long getTeam_id() {
		return team_id;
	}

	public void setTeam_id(Long team_id) {
		this.team_id = team_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAka() {
		return aka;
	}

	public void setAka(String aka) {
		this.aka = aka;
	}

	public int getKeeper() {
		return keeper;
	}

	public void setKeeper(int keeper) {
		this.keeper = keeper;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getTotalSkills() {
		return totalSkills;
	}

	public void setTotalSkills(int totalSkills) {
		this.totalSkills = totalSkills;
	}

	public Team getTeam() {

		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}


	public List<PlayerActions> getPlayerActions() {
		return playerActions;
	}

	public void setPlayerActions(List<PlayerActions> playerActions) {
		this.playerActions = playerActions;
	}


	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", age=" + age + ", aka=" + aka + ", keeper=" + keeper
				+ ", defense=" + defense + ", pass=" + pass + ", attack=" + attack + ", team=" + team + "]";
	}

	@PostLoad
	public void calculateTotalSkills (){
		int calculatedTotalSkills = getPass() + getDefense() + getKeeper() + getAttack();
		setTotalSkills(calculatedTotalSkills);
	}

	
}
