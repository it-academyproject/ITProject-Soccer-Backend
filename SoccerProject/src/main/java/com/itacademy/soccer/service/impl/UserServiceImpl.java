package com.itacademy.soccer.service.impl;

import com.itacademy.soccer.controller.json.UserJson;
import com.itacademy.soccer.dao.IUserDAO;
import com.itacademy.soccer.dto.Team;
import com.itacademy.soccer.dto.User;
import com.itacademy.soccer.dto.typeUser.TypeUser;
import com.itacademy.soccer.service.ITeamService;
import com.itacademy.soccer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, IUserService {

    @Autowired
    private IUserDAO iUserDAO;
    
    @Autowired
    IUserService iUserService;
    
    @Autowired 
    ITeamService iTeamService;

    
    //GETS

    @Override
    public User loginUser(User user) { return iUserDAO.findUserByEmail(user.getEmail());
    };

    @Override
    public List<User> showAllUsers() {return iUserDAO.findAll();}

    @Override
    public User showUserById(Long id) { return iUserDAO.findById(id).get();}

    // POSTS

    @Override
    public User saveNewUser(User user) {
        user.setTypeUser(TypeUser.MANAGER);
        return iUserDAO.save(user);
    }

    @Override
    public User saveNewAdmin(User user) {
        user.setTypeUser(TypeUser.ADMIN);
        return iUserDAO.save(user);
    }
    
    @Override    
	public User showUserByTeam(Long id) {
    	
    	User manager = new User();    	
    	List<User> users = iUserDAO.findAll();
    	manager = null;
    	
    	for (User user : users) {    	
    		
    		if (user.getTeam() != null && user.getTeam().getId() == id) {
    			manager = iUserDAO.findById(user.getId()).get();
    		}
			
		}
	   
    	return manager;
		
	}
    
    // PUTS

    @Override
    public User modifyUserPass(Long id, User user)
    {
        return iUserDAO.save(user);
    }

    @Override
    public User modifyTypeUser(Long id, User user) { return iUserDAO.save(user);}

    // DELETE

    @Override
    public void deleteUser(Long id) { iUserDAO.deleteById(id);}
    
    
    
    // USER INPUT VALIDATION   
    
    // Manager validation
    public HashMap <String, Object> managerValidation(HashMap <String, Object> map, UserJson userJson) {
    	
    	if ((userJson.getEmail() == null || userJson.getPassword() == null) || 
				(userJson.getEmail().equals("") || userJson.getPassword().equals(""))) { // Checks if email or password are null or empty
			map.put("message", "Please, write an email and password.");
			map.put("success", false);

		} else if (!validateEmail(userJson.getEmail())){ // Checks if email is valid with validateEmail()		
			map.put("message", "Please, write a valid email.");
			map.put("success", false);	
		
		} else if (!availableEmail(userJson.getEmail())){ // Checks if email is available with availableEmail()		
			map.put("message", userJson.getEmail()+" email already exists.");
			map.put("success", false);	
		
		} else if (userJson.getTeam_name() == null || userJson.getTeam_name().equals("")){	// Checks if team name is null or empty
			map.put("message", "Please, write a name for your team.");
			map.put("success", false);
		
		} else if (!availableTeamName(userJson.getTeam_name())){ // Checks if team name is available with validateTeamName()
			map.put("message", userJson.getTeam_name()+" team name already exists.");
			map.put("success", false);
		} else {
			map.put("message", "Manager can be created");
			map.put("success", true);
		}
    	return map;
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

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user;

		try {
			
			user = iUserDAO.findUserByEmail(email);
			
		} catch (Exception e) {
			
			System.out.println("Player Not Founded!!! " + e.getMessage());
			throw new UsernameNotFoundException(email);
			
		}

		List<GrantedAuthority> authorities = Arrays.asList(user.getTypeUser().toString().split(" "))
				.stream().map(x -> new SimpleGrantedAuthority("ROLE_" + x)).collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User
				(user.getEmail(), user.getPassword(), authorities);
	}
    
    
    
}
