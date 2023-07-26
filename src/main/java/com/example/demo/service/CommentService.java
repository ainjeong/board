package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CommentRequestDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {
	
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BoardRepository boardRepository;
	

//	public Long save(String nickname, Long id, CommentRequestDto dto) {
//		User user = userRepository.findByNickname(nickname);
//		Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
//		
//		dto.setUser(user);
//		dto.setBoard(board);
//		
//		Comment comment = dto.toEntity();
//		commentRepository.save(comment);
//		
//		return dto.getId();
//	}

	public Comment saveComment(Long id, CommentRequestDto dto) {
		System.out.println(dto);
		Board board = boardRepository.findById(id).get();
		dto.setBoard(board);
		Comment comment = dto.toEntity();
		commentRepository.save(comment);
		
		return comment;
	}
	

	public List<Comment> findAll(){
		return commentRepository.findAll();
	}
	
	public List<Comment> commentByUserId(Long id){
		return commentRepository.findByUserId(id);
	}
}
