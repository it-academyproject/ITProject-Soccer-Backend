package com.itacademy.soccer.dao;

import com.itacademy.soccer.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDAO extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
