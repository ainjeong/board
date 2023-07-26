package com.example.demo.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.demo.entity.Comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {
	private Long id;
	private String comment;
	private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
	private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));;
	private String nickname;
	private Long boardId;
	

public CommentResponseDto(Comment comment) {
	this.id = comment.getId();
	this.comment = comment.getComment();
	this.createdDate = comment.getCreatedDate();
	this.modifiedDate = comment.getModifiedDate();
	this.nickname = comment.getUser().getNickname();
	this.boardId = comment.getBoard().getId();
	
}
}
