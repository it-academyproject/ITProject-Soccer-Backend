package com.itacademy.soccer.controller;

import com.itacademy.soccer.controller.json.UserJson;
import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.User;
import com.itacademy.soccer.dto.typeUser.TypeUser;
import com.itacademy.soccer.security.Constants;
import com.itacademy.soccer.service.IPlayerService;
import com.itacademy.soccer.service.ITeamService;
import com.itacademy.soccer.service.IUserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
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
				map.put("success", false);

			} else if (user.getPassword() == null) { // Password not given
				map.put("message", "Password not given");
				map.put("success", false);

			} else { // Email and password are given
				
				for (User userChecker : iUserService.showAllUsers()) { // Compare existing users with user and password given
					
					if(userChecker.getEmail().equals(user.getEmail())) {
							
						//B-61 compares the login password (unencrypted) with the encrypted password of the database
						if(bCryptPasswordEncoder.matches(user.getPassword(), userChecker.getPassword())) {
							user = userChecker; // Update user
							userMatch = true; // User name and password matches
						}
					
					}
				}

				if (userMatch) { // Login successful - user matches
					map.put("message", "Login successful");
					map.put("email", user.getEmail());
					map.put("type_user:", user.getTypeUser());
					if(user.getTeam()!=null) { //If user has team then show
						map.put("team_id", user.getTeam().getId());
					}
					map.put("success", true);
					
					//Generated Token
					String token = getToken(user.getEmail());
					map.put("Token: ", token);

				} else { // Login not successful - email and password do not match
					map.put("message", "Email or Password not correct");
					map.put("success", false);
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
			
			map =  iUserService.managerValidation(map,userJson); // Check if manager can be created -- Fields validation
			
			if (map.containsValue(true)) { // User is valid 
				
				// Create User
				User user = new User(userJson.getEmail(), userJson.getPassword()); // Creates new User from userJson
				
				// B-61 Create encrypted password
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				
				iUserService.saveNewUser(user); // Creates User as Manager

				// Create Team 
				Team team = new Team(); 
				team = iTeamService.createTeamInitial(userJson.getTeam_name()); // Create team with name provided and initial values
				team = iTeamService.createTeam(team); // Save created team in DB
				user.setTeam(team); // Add team created to User			
				
				// Add Players
				List<Player> teamPlayers = iPlayerService.getPlayersFromJson(userJson.getPlayers()); // Get list of players from userJson players list
				teamPlayers = iPlayerService.signFreePlayers(teamPlayers, team); // Sign free players from list -- only players with team_id = null			
				team.setPlayersList(teamPlayers); // Add players to team

				// JSON Response
				map.put("message", userJson.getEmail() + " Manager created!");
				map.put("success", true);
				map.put("list of players added to "+ team.getName(), teamPlayers); // Show list of players added to team
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
                map.put("success", false);
                //throw new Exception();
            }
            else if(user.getEmail().equals("") || user.getPassword().equals(""))
            {
                map.put("message", "Please, write an email and password.");
                map.put("success", false);
                //throw new Exception();
            }
            else
            {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
                Pattern pat = Pattern.compile(emailRegex);

                if(pat.matcher(user.getEmail()).matches())
                {
                	// B-61 Create encrypted password
    				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                	
                    iUserService.saveNewAdmin(user);
                    map.put("message", "All correct!");
                    map.put("type User", user.getTypeUser());
                    map.put("success", true);
                }
                else
                {
                    map.put("message", "Please, write a valid email.");
                    map.put("success", false);
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
                        map.put("success", true);
                        map.put("User", user.getEmail());
                        map.put("message", "change successful");
                        iUserService.modifyUserPass(id, user);
                    }
                    else
                    {
                        map.put("success",false);
                        map.put("message", "Wrong email or id.");
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
                    map.put("Email", user.getEmail());
                    map.put("Id", userChecker.getId());
                    if(userChecker.getTypeUser().equals(user.getTypeUser()))
                    {
                        map.put("success", false);
                        map.put("message", "User '" + user.getEmail() + "' have same type actually.");
                    }
                    else
                    {
                        iUserService.modifyTypeUser(id, user);
                        map.put("Now User type", user.getTypeUser());
                        map.put("success", true);
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
    
//JWT Method
    
    String getToken(String username) {    
    	
		String token = Jwts
				.builder()
				.setIssuedAt(new Date()).setIssuer(Constants.TOKEN_ISSUER)
				.setSubject(username)		
				.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512,
						Constants.JWT_SECRET).compact();

		return "Bearer " + token;
	}    
       
}