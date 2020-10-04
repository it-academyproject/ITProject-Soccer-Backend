package com.itacademy.soccer.service.impl;

import com.itacademy.soccer.dto.Player;
import com.itacademy.soccer.dto.serializable.PlayerMatchId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IPlayerActionsDAO;
import com.itacademy.soccer.dto.PlayerActions;
import com.itacademy.soccer.service.IPlayerActionsService;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerActionsServiceImpl implements IPlayerActionsService{

	@Qualifier("IPlayerActionsService")
	@Autowired
	IPlayerActionsDAO iPlayerActionsDAO;

	//Este método no lo veo bien.
	//find by playerMatchId
	@Override
	public PlayerActions findByIdPlayerIdAndIdMatchId(Long playerId, Long matchId) {

		/*PlayerMatchId playerMatchId = iPlayerActionsDAO.findById(playerMatchId);

		return iPlayerActionsDAO.findById(playerMatchId);*/

		return iPlayerActionsDAO.findByIdPlayerIdAndIdMatchId(playerId, matchId);
	}

	@Override
	public List<PlayerActions> findByIdPlayerId(Long playerId) {
		return iPlayerActionsDAO.findByIdPlayerId(playerId);
	}


//	@Override
//	public PlayerActions updatePlayerActions(PlayerActions playerActions) {
//		return iPlayerActionsDAO.save(playerActions);
//	}


//	@Override
//	public PlayerActions findByPlayerIdAndMatchId(Long playerId, Long matchId) {
//		return iPlayerActionsDAO.findByPlayerIdAndMatchId(playerId, matchId);
//	}



	@Override
	public PlayerActions save(PlayerActions playerActions) {
		return iPlayerActionsDAO.save(playerActions);
	}


	@Override
	public List<PlayerActions> findAll() {
		return null;
	}

	@Override
	public List<PlayerActions> findAll(Sort sort) {
		return null;
	}

	@Override
	public List<PlayerActions> findAllById(Iterable<PlayerMatchId> iterable) {
		return null;
	}

	@Override
	public <S extends PlayerActions> List<S> saveAll(Iterable<S> iterable) {
		return null;
	}

	@Override
	public void flush() {

	}

	@Override
	public <S extends PlayerActions> S saveAndFlush(S s) {
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<PlayerActions> iterable) {

	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public PlayerActions getOne(PlayerMatchId playerMatchId) {
		return null;
	}

	@Override
	public <S extends PlayerActions> List<S> findAll(Example<S> example) {
		return null;
	}

	@Override
	public <S extends PlayerActions> List<S> findAll(Example<S> example, Sort sort) {
		return null;
	}

	@Override
	public Page<PlayerActions> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Optional<PlayerActions> findById(PlayerMatchId playerMatchId) {
		return Optional.empty();
	}

	@Override
	public boolean existsById(PlayerMatchId playerMatchId) {
		return false;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(PlayerMatchId playerMatchId) {

	}

	@Override
	public void delete(PlayerActions playerActions) {

	}

	@Override
	public void deleteAll(Iterable<? extends PlayerActions> iterable) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	public <S extends PlayerActions> Optional<S> findOne(Example<S> example) {
		return Optional.empty();
	}

	@Override
	public <S extends PlayerActions> Page<S> findAll(Example<S> example, Pageable pageable) {
		return null;
	}

	@Override
	public <S extends PlayerActions> long count(Example<S> example) {
		return 0;
	}

	@Override
	public <S extends PlayerActions> boolean exists(Example<S> example) {
		return false;
	}


}
