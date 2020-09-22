package com.itacademy.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.MatchActions;

public interface IMatchActionsDAO extends JpaRepository<MatchActions, Long>{


}
