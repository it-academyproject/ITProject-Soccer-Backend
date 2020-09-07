/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Match;

/**
 * @author KevHaes
 *
 */
public interface IMatchDAO extends JpaRepository<Match, Long> {

}
