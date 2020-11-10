package com.itacademy.soccer.controller.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.User;
import com.itacademy.soccer.dto.typeUser.TypeUser;

@JsonIgnoreProperties(value = "password", allowSetters = true)
public class UserJson {

	private String id;
	private String type_user;
	private String email;
	private String password;
	private String team_id;
	private String team_name;
	private String players;
	
	
	public UserJson() {
	}

	public UserJson(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public UserJson(String email, String password, String team_name, String players) { // Create Manager Constructor 
		this.email = email;
		this.password = password;
		this.team_name = team_name;
		this.players = players;
	}

	public UserJson(String id, String type_user, String email, String password, String team_id) {
		this.id = id;
		this.type_user = type_user;
		this.email = email;
		this.password = password;
		this.team_id = team_id;
	}

	public User setJsonToObject() {
		
		User user = new User();

		user.setId(Long.parseLong(this.id));
		user.setEmail(this.email);
		user.setPassword(this.password);
		user.setTypeUser(TypeUser.valueOf(this.type_user));

		Team team = new Team();

		team.setId(Long.parseLong(this.team_id));

		user.setTeam(team);

		return user;
	}
	
	public static UserJson parseObjectToJson(User user) {
		
		UserJson userJson = new UserJson();

		userJson.setId(user.getId().toString());
		userJson.setEmail(user.getEmail());
		userJson.setPassword(user.getPassword());
		userJson.setType_user(user.getTypeUser().toString());
		
		if (user.getTeam() != null) {
			userJson.setTeam_id(user.getTeam().getId().toString());
			userJson.setTeam_name(user.getTeam().getName());
			userJson.setPlayers(user.getTeam().getPlayersList().toString());
		}

		return userJson;
	}
	
	public static List<UserJson> parseListToJson(List<User> userList) {
		
		List<UserJson> userJsonList = new ArrayList<>();
		
		userList.stream().forEach(user -> userJsonList.add(parseObjectToJson(user)));
		
		return userJsonList;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType_user() {
		return type_user;
	}

	public void setType_user(String type_user) {
		this.type_user = type_user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getPlayers() {
		return players;
	}

	public void setPlayers(String players) {
		this.players = players;
	}

}
