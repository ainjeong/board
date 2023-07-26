package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Board;
import com.example.demo.entity.Heart;
import com.example.demo.entity.User;

public interface HeartRepository extends JpaRepository<Heart, Long>{
		List<Heart> findByUserId(Long id);
		Optional<Heart> findByUserAndBoard(User user, Board board);
}
