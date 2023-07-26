package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailService implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("loadUserByUsername : " + email);
		User user =userRepository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	    log.info("found : " +user);
		return user;
	}
}
