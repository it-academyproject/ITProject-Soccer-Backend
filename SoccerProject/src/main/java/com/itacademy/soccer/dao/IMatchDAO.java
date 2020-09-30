/**
 * @author KevHaes
 *
 */
package com.itacademy.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.soccer.dto.Match;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author KevHaes
 *
 */
public interface IMatchDAO extends JpaRepository<Match, Long> {

    @Override
    default Optional<Match> findById(Long id){
        return findById(id);
    }
}
