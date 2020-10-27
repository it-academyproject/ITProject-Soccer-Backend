package com.itacademy.soccer.service.impl;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IUserDAO;
import com.itacademy.soccer.dto.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private IUserDAO iUserDAO;
	
	public UserDetailsServiceImpl(IUserDAO iUserDAO) {
		this.iUserDAO = iUserDAO;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		User user =iUserDAO.findUserByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException(email);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), emptyList());
	}
}
