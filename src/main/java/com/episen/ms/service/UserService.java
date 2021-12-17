package com.episen.ms.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    /** Method called by Spring framework to load a user by his username */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
		if(username == null){
			
			throw new UsernameNotFoundException("Invalid username or password.");
		
		}
		
        return new User("mohamed", "mohamed", new ArrayList<>());
    }
}
