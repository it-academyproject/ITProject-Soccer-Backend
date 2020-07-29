package com.itacademy.soccer.controller;

import com.itacademy.soccer.dto.User;
import com.itacademy.soccer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")

public class UserController {

// GETS

    @Autowired
    IUserService iUserService;

    @GetMapping("/login") // LOGIN ALL USERS (MANAGERS/ADMINS)
    public HashMap <String,Object> loginUser(@RequestBody User user)
    {
        HashMap<String, Object> map = new HashMap();
        try
        {
            for (User userChecker: iUserService.showAllUsers())
            {
                if(userChecker.getEmail().equals(user.getEmail()))
                {
                    if(userChecker.getPassword().equals(user.getPassword()))
                    {
                        map.put("message", "All correct!");
                        map.put("email:", user.getEmail());
                        map.put("type User:", userChecker.getTypeUser());
                        map.put("success:", true);
                    }
                    else
                    {
                        map.put("message", "Mail address or Password not correct");
                        map.put("success:", false);
                    }
                }
                else
                {
                    map.put("message", "Mail address or Password not correct");
                    map.put("success:", false);
                }
            }
        }
        catch (Exception e)
        {
            map.put("message", "something went wrong! :" + e.getMessage());
        }
        return map;
    }

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

// POSTS

    @PostMapping("/users/managers") // CREATE USERS/MANAGERS
    public HashMap <String, Object> createUserManager(@RequestBody User user)
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
                    iUserService.saveNewUser(user);
                    map.put("message:", "All correct!");
                    map.put("type User:",user.getTypeUser());
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
                if(userChecker.getEmail().equals(user.getEmail()))
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

    @DeleteMapping("/users/managers/{id}") // DELETE USERS ADMIN
    public void deleteUsers(@PathVariable Long id)
    {
        iUserService.deleteUser(id);
    }

}
