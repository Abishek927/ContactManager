package com.contact.manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.contact.manager.models.User;
import com.contact.manager.repository.userRepository;

public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private userRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		User user=repository.getUserByUserName(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Could not found user!!");
		}
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		
		
		
		return customUserDetails;
	}

}
