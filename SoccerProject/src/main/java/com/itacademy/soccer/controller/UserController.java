package com.itacademy.soccer.controller;

import com.itacademy.soccer.controller.json.UserJson;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.User;
import com.itacademy.soccer.dto.typeUser.TypeUser;
import com.itacademy.soccer.service.IPlayerService;
import com.itacademy.soccer.service.ITeamService;
import com.itacademy.soccer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")

public class UserController {

// GETS

    @Autowired
    IUserService iUserService;
    
    @Autowired 
    ITeamService iTeamService;
    
    @Autowired 
    IPlayerService iPlayerService;


    @GetMapping("/users") // SHOW ALL USERS FOR ADMIN
    public List<User> showAllUsers()
    {
        return iUserService.showAllUsers();
    }

    @GetMapping("/users/managers/{id}") // SHOW USER UNIQUE TO ADMIN
    public User showUserById(@PathVariable Long id)
    {
        return iUserService.showUserById(id);
    }


    @GetMapping("/users/teams/{id}") // SHOW USER BY TEAM (MANAGER OF THIS TEAM {ID})
    
    public HashMap <String, Object>  showUserByTeam(@PathVariable Long id) {
    	
    	HashMap<String, Object> map = new HashMap<>();    	    
    	User manager = new User();
    	
    	try {
    	   	manager =iUserService.showUserByTeam(id); //Is possible null if the postman enter a number of team doesn't exist
    		String msj="The id Team : " + id +  " has no user, this id is of a team without assigned user or that doesn't exist";        	
    	   	map.put("message", msj);
    	  
    	   	if (manager !=null) {    
        	  	 
        		if (manager.getTypeUser().equals(TypeUser.ADMIN)) {
        			msj="The Teams : " + id + " has no Manager because allow to user : ADMIN";   		
        		   	map.put("message", msj);
        	    	
        		}else {
        			msj="The Teams : " + id + " allow to user type MANAGER with user name : " + manager.getEmail();
        		   	map.put("message", msj);
        	    	
        		}
        	}
    	    
    	}catch (Exception e) {
    		
            map.put("message", "something went wrong! :" + e.getMessage());
            
		}    	
    	
     	return  map;   	
    }
    
    
// POSTS
    
	@PostMapping("/login") // LOGIN USERS (MANAGERS/ADMINS)
	public HashMap<String, Object> loginUser(@RequestBody User user) {
		HashMap<String, Object> map = new HashMap();
		
		try {
			boolean userMatch = false; // Variable to check if user and password match

			// Check that email and password are given
			if (user.getEmail() == null) { // Email not given
				map.put("message", "Email not given");
				map.put("success:", false);

			} else if (user.getPassword() == null) { // Password not given
				map.put("message", "Password not given");
				map.put("success:", false);

			} else { // Email and password are given

				for (User userChecker : iUserService.showAllUsers()) { // Compare existing users with user and password given

					if (userChecker.getEmail().equals(user.getEmail()) && userChecker.getPassword().equals(user.getPassword())) {
						user = userChecker; // Update user
						userMatch = true; // User name and password matches
					}
				}

				if (userMatch) { // Login successful - user matches
					map.put("message", "Login successful");
					map.put("email:", user.getEmail());
					map.put("type_user:", user.getTypeUser());
					if(user.getTeam()!=null) { //If user has team then show
						map.put("team_id:", user.getTeam().getId());
					}
					map.put("success:", true);

				} else { // Login not successful - email and password do not match
					map.put("message", "Email or Password not correct");
					map.put("success:", false);
				}
			}
		}

		catch (Exception e) {
			map.put("message", "something went wrong! :" + e.getMessage());
		}

		return map;
	}

	@PostMapping("/users/managers") // CREATE USERS/MANAGERS
	public HashMap<String, Object> createUserManager(@RequestBody UserJson userJson) {

		HashMap<String, Object> map = new HashMap<>();

		try { // Fields validation first -- Then create user + team + add players
			
			if ((userJson.getEmail() == null || userJson.getPassword() == null) || 
					(userJson.getEmail().equals("") || userJson.getPassword().equals(""))) { // Checks if email or password are null or empty
				map.put("message", "Please, write an email and password.");
				map.put("success:", false);

			} else if (!validateEmail(userJson.getEmail())){ // Checks if email is valid with validateEmail()		
				map.put("message", "Please, write a valid email.");
				map.put("success:", false);	
			
			} else if (!availableEmail(userJson.getEmail())){ // Checks if email is available with availableEmail()		
				map.put("message", userJson.getEmail()+" email already exists.");
				map.put("success:", false);	
			//TODO B-53 one email/user can have more than one team?
			
			} else if (userJson.getTeam_name() == null || userJson.getTeam_name().equals("")){	// Checks if team name is null or empty
				map.put("message", "Please, write a name for your team.");
				map.put("success:", false);
			
			} else if (!availableTeamName(userJson.getTeam_name())){ // Checks if team name is available with validateTeamName()
				map.put("message", userJson.getTeam_name()+" team name already exists.");
				map.put("success:", false);
			
			} else { // Create User + Team + Add players -- After validate fields
				
				// Create User
				User user = new User(userJson.getEmail(), userJson.getPassword()); // Creates new User from userJson
				iUserService.saveNewUser(user); // Creates User as Manager

				// Create Team //TODO B-53 modify method create new team at TeamController
				Team team = new Team(); // Creates new Team from userJson
				team.setName(userJson.getTeam_name()); // Adds team name from userJson
				team.setFoundation_date(new Date());
				team.setBadge(null);
				team.setBudget(300000F);
				team.setWins(0);
				team.setLosses(0);
				team.setDraws(0);

				user.setTeam(team); // Add team created to User
				iTeamService.createTeam(team); // Create team and adds it to User

				// Add Players
				String[] playersStringList = userJson.getPlayers().split(","); // Split Json String with list of players												
				Long[] playersIds = new Long[playersStringList.length];// List to store player ids
				List<Player> teamPlayers = new ArrayList<Player>(); // List to store players

				for (int i = 0; i < playersStringList.length; i++) { // Get and add players to the list
					playersStringList[i] = playersStringList[i].replaceAll("\\D+", ""); // Use regex to delete non-digits																							
					playersIds[i] = Long.parseLong(playersStringList[i]); 
					
					Optional<Player> playerOptional = iPlayerService.findById(playersIds[i]); //Find player by id
					if (playerOptional.isPresent()) { // Player by id found
						Player player = playerOptional.get();

						if (player.getTeam_id() == null) { // Check if player is free -- team_id is null
							iPlayerService.changeTeam(player, team); // Change player team
							teamPlayers.add(player); // Add player to list
							System.out.println(player.getName() +" with id="+ player.getId() +" has signed with "+ team.getName()); // Info sign player

						} else { // Player belongs to a team
							System.out.println(player.getName() + " belongs to team " + player.getTeam().getName());
						}
					} else { // Player by id not found
						System.out.println( "player with id="+ playersIds[i] +" not found!");
					}
				}

				team.setPlayersList(teamPlayers); // Add players to team

				// JSON Response
				map.put("message:", userJson.getEmail() + " Manager created!");
				map.put("success:", true);
				map.put("list of players added", teamPlayers); // show list of players added
			}
		}

		catch (Exception e) {
			map.put("message", "something went wrong! :" + e.getMessage());
		}

		return map;
	}

    @PostMapping("/users/admins") // CREATE USERS/ADMINS
    public HashMap <String, Object> createUserAdmin(@RequestBody User user)
    {
        HashMap<String, Object> map = new HashMap<>();
        try
        {
            if(user.getEmail() == null || user.getPassword() == null)
            {
                map.put("message", "Please, write an email and password.");
                map.put("success:", false);
                //throw new Exception();
            }
            else if(user.getEmail().equals("") || user.getPassword().equals(""))
            {
                map.put("message", "Please, write an email and password.");
                map.put("success:", false);
                //throw new Exception();
            }
            else
            {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
                Pattern pat = Pattern.compile(emailRegex);

                if(pat.matcher(user.getEmail()).matches())
                {
                    iUserService.saveNewAdmin(user);
                    map.put("message:", "All correct!");
                    map.put("type User:", user.getTypeUser());
                    map.put("success:", true);
                }
                else
                {
                    map.put("message", "Please, write a valid email.");
                    map.put("success:", false);
                }
            }
        }
        catch(Exception e)
        {
            map.put("message", "something went wrong! :" + e.getMessage());
        }
        return map;
    }

// PUT

    @PutMapping("/users/password/{id}") //  USER MODIFY PASSWORD
    public HashMap <String, Object> modifyUserPass(@PathVariable Long id, @RequestBody User user)
    {
        HashMap<String, Object> map = new HashMap<>();
        try
        {
            for (User userChecker: iUserService.showAllUsers())
            {
                if(userChecker.getEmail().equals(user.getEmail()))
                {
                    if(userChecker.getId() == user.getId())
                    {
                        map.put("success:", true);
                        map.put("User:", user.getEmail());
                        map.put("message:", "change successful");
                        iUserService.modifyUserPass(id, user);
                    }
                    else
                    {
                        map.put("success:",false);
                        map.put("message:", "Wrong email or id.");
                    }
               }
            }
        }
        catch(Exception e)
        {
            map.put("message", "something went wrong! :" + e.getMessage());
        }
        return map;
    }

    @PutMapping("/users/type/{id}") // MODIFY USERS TO CHANGE TO  ADMIN
    public HashMap <String, Object> modifyTypeUser(@PathVariable Long id, @RequestBody User user)
    {
        HashMap<String, Object> map = new HashMap<>();
        try
        {
            for (User userChecker: iUserService.showAllUsers())
            {
                if(userChecker.getEmail().equals(user.getEmail()) && userChecker.getId() == user.getId())
                {
                    map.put("Email:", user.getEmail());
                    map.put("Id:", userChecker.getId());
                    if(userChecker.getTypeUser().equals(user.getTypeUser()))
                    {
                        map.put("success:", false);
                        map.put("message:", "User '" + user.getEmail() + "' have same type actually.");
                    }
                    else
                    {
                        iUserService.modifyTypeUser(id, user);
                        map.put("Now User type:", user.getTypeUser());
                        map.put("success:", true);
                    }
                }
            }
        }
        catch(Exception e)
        {
            map.put("message", "something went wrong! :" + e.getMessage());
        }
        return map;
    }

// DELETE

  //  @DeleteMapping("/users/managers/{id}") // DELETE USERS ADMIN  -- Modificada la url del ENDPOINT USER
    @DeleteMapping("/users/{id}") // DELETE USERS ADMIN
    public void deleteUsers(@PathVariable Long id)
    {
        iUserService.deleteUser(id);
    }
    
    
    
    // Validates email 
    public boolean validateEmail(String email) {
    	boolean validEmail = false;
    	String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
    	Pattern pat = Pattern.compile(emailRegex);

    	if (pat.matcher(email).matches()) { // Checks if email is valid
    		validEmail = true;
    	}
    	return validEmail;
    }
    
    // Checks if email is available 
    public boolean availableEmail(String email) {
    	boolean availableEmail = true;
    	List<User> usersList =  iUserService.showAllUsers();
    	for (User user : usersList) {
    		if(user.getEmail().equalsIgnoreCase(email)) {
    			availableEmail = false;
    		};
    	}
    	return  availableEmail;
    }
    
    
    // Checks if Team name is available 
    public boolean availableTeamName(String teamName) {
    	boolean availableName = true;
    	List<Team> teamsList =  iTeamService.getAllTeams();
    	for (Team team : teamsList) {
    		if(team.getName().equalsIgnoreCase(teamName)) {
    			availableName = false;
    		};
    	}
    	return  availableName;
    }
    
    
}