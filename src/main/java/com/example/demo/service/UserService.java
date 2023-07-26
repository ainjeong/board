package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.JoinUserRequest;
import com.example.demo.dto.LoginUserRequest;
import com.example.demo.entity.Board;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	BoardRepository boardRepository;
	
	public Long save(JoinUserRequest dto) {
		return userRepository.save(User.builder()
				.email(dto.getEmail())
				.password(bCryptPasswordEncoder.encode(dto.getPassword()))
				.nickname(dto.getNickname())
				.build()).getId();
		
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	}

	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("USER not found"));
	}
	
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
	
	public List<Board> findByUserId(Long id){
		return boardRepository.findByUserId(id);
	}
	
}
