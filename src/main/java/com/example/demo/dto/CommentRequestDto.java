package com.example.demo.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
	private String comment;
	private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
	private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
	private User user;
	private Board board;
	
	public Comment toEntity() {
		Comment comments = Comment.builder()
				.comment(comment)
				.createdDate(createdDate)
				.modifiedDate(modifiedDate)
				.user(user)
				.board(board)
				.build();
		return comments;
	}
}
