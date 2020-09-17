package com.itacademy.soccer.service;

import com.itacademy.soccer.dto.User;

import java.util.List;

public interface IUserService {

    // CRUD METHODS

        // GETS

    public User loginUser(User user);

    public List<User> showAllUsers();

    public User showUserById(Long id);

        // POSTS

    public User saveNewUser(User user);

    public User saveNewAdmin(User user);

        // PUTS

    public User modifyUserPass(Long id, User user);

    public User modifyTypeUser(Long id, User user);

        // DELETE

    public void deleteUser(Long id);
    
    public User showUserByTeam(Long id);
}
