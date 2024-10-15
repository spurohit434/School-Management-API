package com.wg.services;

import java.sql.SQLException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wg.model.User;
import com.wg.repository.UserDAO;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserDAO userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userRes = null;
		try {
			userRes = userRepository.getUserByUsername(username);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		if (userRes == null) {
			throw new UsernameNotFoundException("Could not findUser with username = " + username);
		}
		return new org.springframework.security.core.userdetails.User(username, userRes.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRes.getRole())));
	}

}