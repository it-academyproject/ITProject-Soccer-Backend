package com.itacademy.soccer.service.impl;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itacademy.soccer.dao.IUserDAO;
import com.itacademy.soccer.dto.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private IUserDAO iUserDAO;
	
	public UserDetailsServiceImpl(IUserDAO iUserDAO) {
		this.iUserDAO = iUserDAO;
	}
	
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = iUserDAO.findUserByEmail(name);
		if(user==null) {
			throw new UsernameNotFoundException(name);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), emptyList());
	}
}
