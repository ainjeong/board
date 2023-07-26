package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	Page<Board> findByTitleContaining(String keyword, Pageable pageable);
	List<Board> findByUserId(Long id);
	
	
}
