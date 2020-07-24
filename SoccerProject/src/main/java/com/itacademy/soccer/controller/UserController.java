package com.itacademy.soccer.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")

public class UserController {

// GETS

    @GetMapping("/login") // LOGIN ALL USERS (MANAGERS/ADMINS)
    public String loginUser()
    {
        return "Hello World, aiam Login API :D";
    }

    @GetMapping("/users") // SHOW ALL USERS FOR ADMIN
    public List<String> showAllUsers()
    {
        List<String> showAllUsers = new ArrayList<>();
        showAllUsers.add("Fulanito de tal");
        showAllUsers.add("Menganito de cual");
        showAllUsers.add("Reduzca a 20");
        showAllUsers.add("Bienvenido a Reduzca");
        showAllUsers.add("Soy una lista de uuusers");

        return showAllUsers;
    }

    @GetMapping("/users/managers/{id}") // SHOW USER UNIQUE TO ADMIN
    public String showUniqueUser()
    {
        return "Aiam Menganito de tal :)";
    }

// POSTS

    @PostMapping("/users/managers") // CREATE USERS/MANAGERS
    public HashMap <String, Object> createUserManager()
    {
       HashMap<String, Object> userManagerData = new HashMap<>();
       userManagerData.put("Hello World, I'm Manager", true);
       userManagerData.put("Type User:", 0);
       userManagerData.put("Push to dev?",false);

       if(userManagerData.containsValue(0))
       {
           userManagerData.put("Type User: Manager", true);
       }

        return userManagerData;
    }

    @PostMapping("/users/admins") // CREATE USERS/ADMINS
    public HashMap <String, Object> createUserAdmin()
    {
        HashMap<String, Object> userAdminData = new HashMap<>();
        userAdminData.put("Hello World, I'm Admin", false);
        userAdminData.put("Type User:" , 1);
        userAdminData.put("Push to dev?", true);


        if(userAdminData.containsValue(1))
        {
            userAdminData.put("Type User: Admin", true);
        }
        return userAdminData;
    }

// PUT

    @PutMapping("/users/{id}") // MODIFY USERS ADMIN
    public String modifyUsers()
    {
        return "I'm an modified User :O";
    }

// DELETE

    @DeleteMapping("/users/managers/{id}") // DELETE USERS ADMIN
    public String deleteUsers()
    {
        return "They gonna delete me! :(";
    }

}
