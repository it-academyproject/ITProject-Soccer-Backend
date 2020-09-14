package com.itacademy.soccer.service.impl;

import com.itacademy.soccer.dao.IUserDAO;
import com.itacademy.soccer.dto.User;
import com.itacademy.soccer.dto.typeUser.TypeUser;
import com.itacademy.soccer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDAO iUserDAO;

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
}
