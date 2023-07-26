package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.HeartRequestDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Heart;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.HeartRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class HeartService {
	@Autowired
	HeartRepository heartRepository;
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	UserRepository userRepository;

	@Transactional
	public Heart saveHeart(HeartRequestDto heartRequestDto) throws Exception {
		User user = userRepository.findById(heartRequestDto.getUserId()).get();
		
		Board board = boardRepository.findById(heartRequestDto.getBoardId()).get();
		
		if(heartRepository.findByUserAndBoard(user, board).isPresent()) {
			throw new IllegalStateException("이미 좋아요가 눌린 게시물입니다.");
		}
		
		Heart heart = Heart.builder()
				.board(board)
				.user(user)
				.build();
		return heartRepository.save(heart);
	}
	@Transactional
	public void deleteHeart(HeartRequestDto heartRequestDto) {
		User user = userRepository.findById(heartRequestDto.getUserId()).get();
		
		Board board = boardRepository.findById(heartRequestDto.getBoardId()).get();
		
		Heart heart = heartRepository.findByUserAndBoard(user, board).get();
		
		heartRepository.delete(heart);
	}

	public List<Heart> findByUserId(Long id) {
		// TODO Auto-generated method stub
		return heartRepository.findByUserId(id);
	}
	
}
