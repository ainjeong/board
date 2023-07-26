package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddBoardRequest;
import com.example.demo.dto.BoardRequestDto;
import com.example.demo.dto.BoardViewResponse;
import com.example.demo.dto.UpdateBoardRequest;
import com.example.demo.entity.Board;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public Board save(BoardRequestDto dto) {
		
		Board board = dto.toEntity();
		boardRepository.save(board);
		
		return board;
	}
	
	public List<Board> findAll(){
		return boardRepository.findAll();
	}
	
	public BoardViewResponse findById(Long id) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("not found: " + id));
		BoardViewResponse boardViewResponse = new BoardViewResponse(board);
		boardViewResponse.setView(board.getView()+1);
		return boardViewResponse;
		
	}
	
	@Transactional
	public Board update(long id, UpdateBoardRequest request) {
		Board board = boardRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException(""));
		board.update(request.getTitle(), request.getContent(), request.getUpdatedAt());
		
		return board;
	}
	
	public void delete(long id) {
		boardRepository.deleteById(id);
	}
	
	
	//서칭
	@Transactional
	public Page<Board> search(String keyword, Pageable pageable){
		Page<Board> boardSearched = boardRepository.findByTitleContaining(keyword, pageable);
		return boardSearched;
	}
	
	//페이징
	@Transactional
    public Page<Board> getBoardList(Pageable pageable) {

        return boardRepository.findAll(pageable);

    }

	
	@Transactional
    public Boolean getListCheck(Pageable pageable) {
        Page<Board> saved = getBoardList(pageable);
        Boolean check = saved.hasNext();

        return check;
    }

	
	

	
	
	
	
}
