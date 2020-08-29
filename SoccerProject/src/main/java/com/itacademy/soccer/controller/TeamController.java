/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KevHaes
 *
 */
@RestController
@RequestMapping(path = "/api/teams")
public class teamController {

	@PostMapping
	// public Team createTeam(@RequestBody Team team) {
	public HashMap<String, Object> createTeam() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("teamId", 5);
		map.put("success", true);
		map.put("message", "Team Created");
		return map;
	}

	@GetMapping
	// public List<Team> getAllTeams() {
	public HashMap<String, Object> getAllTeams() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "Got all Teams");
		return map;

	}

	@GetMapping(path = "/{id}")
	// public Team getOneTeamById(@PathVariable Long id) {
	public HashMap<String, Object> getOneTeamById(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("teamId", id);
		map.put("success", true);
		map.put("message", "Got one Teams");
		return map;
	}

	@PutMapping(path = "/{id}")
	// public Team modifyOneTeamById(@PathVariable Long id, @RequestBody Team team)
	// {
	public HashMap<String, Object> modifyOneTeamById(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("teamId", id);
		map.put("success", true);
		map.put("message", "One team modified");
		return map;
	}

	@GetMapping(path = "/{id}/result")
	public HashMap<String, Object> getOneTeamByIdResults(@PathVariable Long id) {
		// public Team getOneTeamByIdResults(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("teamId", id);
		map.put("success", true);
		map.put("message", "returned one team results");
		return map;
	}

	@DeleteMapping(path = "/{id}")
	public HashMap<String, Object> deleteOneTeamById(@PathVariable Long id) {
		// public Team deleteOneTeamById(@PathVariable Long id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("teamId", id);
		map.put("success", true);
		map.put("message", "One team deleted");
		return map;
	}

}