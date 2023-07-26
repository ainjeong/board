package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class BoardViewResponse {
	
	private Long id;
	private String title;
	private String writer;
	private String content;
	private LocalDateTime createdAt, updatedAt;
	private int view;
	private Long userId;
	private List<CommentResponseDto> comments;
	
	public BoardViewResponse(Board board) {
		this.id=board.getId();
		this.title=board.getTitle();
		this.writer=board.getWriter();
		this.content=board.getContent();
		this.createdAt=board.getCreatedAt();
		this.updatedAt=board.getUpdatedAt();
		this.view=board.getView();
		this.userId=board.getUser().getId();
		this.comments=board.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
	}

}
