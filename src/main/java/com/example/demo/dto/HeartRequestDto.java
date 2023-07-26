package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class HeartRequestDto {
	private Long userId;
	private Long boardId;
	
	public HeartRequestDto(Long userId, Long boardId) {
		this.userId = userId;
		this.boardId = boardId;
	}

	


	

}
