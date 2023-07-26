package com.example.demo.dto;

import com.example.demo.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddBoardRequest {
	private String title;
	private String content;
	private String writer;
	

}
