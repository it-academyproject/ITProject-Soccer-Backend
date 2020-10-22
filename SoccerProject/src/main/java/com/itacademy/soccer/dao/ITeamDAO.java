/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Team;

import java.util.List;
import java.util.Optional;

/**
 * @author KevHaes
 *
 */
public interface ITeamDAO extends JpaRepository<Team, Long> {


}
