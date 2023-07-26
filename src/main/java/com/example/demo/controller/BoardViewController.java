package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.BoardViewResponse;
import com.example.demo.dto.CommentResponseDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserDetailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardViewController {

	@Autowired
	BoardService boardService;
	@Autowired
	UserDetailService userDetailService;
	@Autowired
	CommentService commentService;

	@GetMapping("/write")
	public String write(@AuthenticationPrincipal User user, Model model) {
		if (user != null && user.isEnabled()) { // 예를 들어, 사용자가 활성화 상태인지 확인할 수도 있습니다.
			String nickname = user.getNickname();
			model.addAttribute("nickname", nickname);
		}

		return "write";
	}

	@GetMapping("/board/{id}")
	public String getBoard(@PathVariable Long id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		BoardViewResponse boardViewResponse = boardService.findById(id);
		System.out.println("getboardview" + boardViewResponse.getView());
		List<CommentResponseDto> comments = boardViewResponse.getComments();
		if (comments != null && !comments.isEmpty()) {
			
		model.addAttribute("comments", comments);
		}
		if (auth.isAuthenticated()) {
			User user = userDetailService.loadUserByUsername(auth.getName());

			if (boardViewResponse.getUserId().equals(user.getId())) {
				model.addAttribute("writer", true);
			}
		}
		
		model.addAttribute("board", boardViewResponse);
		return "board";
	}

	@GetMapping("/update/{id}")
	public String updateBoard(@PathVariable Long id, Model model) {
		if (id == null) {
			model.addAttribute("board", new BoardViewResponse());
		} else {
			BoardViewResponse board = boardService.findById(id);
			model.addAttribute("board", board);
		}
		return "update";
	}

}
