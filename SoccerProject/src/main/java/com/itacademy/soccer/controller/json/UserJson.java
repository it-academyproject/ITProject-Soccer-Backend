package com.itacademy.soccer.controller.json;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.User;
import com.itacademy.soccer.dto.typeUser.TypeUser;

@JsonIgnoreProperties(value = "password", allowSetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

	public UserJson(String id, String type_user, String email, String password, Team team) {
		this.id = id;
		this.type_user = type_user;
		this.email = email;
		this.password = password;
		parseTeamToJson(team);
	}

	public User parseToUser() {
		
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
	
	public static User parseJsonToUser(UserJson userJson) {
		
		return new User() {
			{
				setId(Long.parseLong(userJson.id));
				setEmail(userJson.email);
				setPassword(userJson.password);
				setTypeUser(TypeUser.valueOf(userJson.type_user));
				setTeam(new Team() {
					{
						setId(Long.parseLong(userJson.team_id));
					}
				});
			}
		};
	}
	
	public static UserJson parseUserToJson(User user) {
		
		return new UserJson() {
			{
				setId(user.getId().toString());
				setEmail(user.getEmail());
				setPassword(user.getPassword());
				setType_user(user.getTypeUser().toString());
				parseTeamToJson(user.getTeam());
			}
		};
	}
	
	//Pensé que era mejor separar el método para poder usarlo en el constructor
	public void parseTeamToJson(Team team) {
		if (team != null) {
			this.setTeam_id(team.getId().toString());
			this.setTeam_name(team.getName());
			this.setPlayers(team.getPlayersList().toString());
		}
	}
	
	@SuppressWarnings("serial")
	public static List<UserJson> parseListUserToJson(List<User> userList) {

		return new ArrayList<UserJson>() {
			{
				addAll(userList.stream()
								.map(user -> parseUserToJson(user))
								.collect(Collectors.toList()));
			}
		};
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
