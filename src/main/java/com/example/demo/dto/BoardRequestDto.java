package com.example.demo.dto;

import com.example.demo.entity.Board;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
	private Long id;
	private String title;
	private String writer;
	private String content;
	private String createdAt;
	private String updatedAt;
	private int view;
	private User user;
	

	//dto를 entity로
	public Board toEntity() {
		Board board = Board.builder()
				.id(id)
				.title(title)
				.writer(writer)
				.content(content)
				.view(view)
				.user(user)
				.build();
		return board;
	}
}
